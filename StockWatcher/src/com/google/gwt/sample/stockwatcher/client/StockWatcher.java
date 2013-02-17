package com.google.gwt.sample.stockwatcher.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StockWatcher implements EntryPoint {

  private VerticalPanel mainPanel = new VerticalPanel();
  private FlexTable investFlexTable = new FlexTable();
  private HorizontalPanel addPanel = new HorizontalPanel();
  private TextBox newCityTextBox = new TextBox();
  private Button addProjectButton = new Button("Add");
  private Label lastUpdatedLabel = new Label();
  private ArrayList<String> stocks = new ArrayList<String>();
  private static final int REFRESH_INTERVAL = 5000; // ms
  private AwardDataServiceAsync stockPriceSvc = GWT.create(AwardDataService.class);

  /**
   * Entry point method.
   */
  public void onModuleLoad() {
    // Create table for stock data.
	 investFlexTable.setText(0, 0, "City");
	 investFlexTable.setText(0, 1, "Project");
	 investFlexTable.setText(0, 2, "Award Ammount");
	 investFlexTable.setText(0, 3, "Remove");
	 
	// Add styles to elements in the stock list table.
	 investFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
	 investFlexTable.addStyleName("watchList");
	 investFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	 investFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
	 investFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
	
    // Assemble Add Stock panel.
    addPanel.add(newCityTextBox);
    addPanel.add(addProjectButton);
    addPanel.addStyleName("addPanel");

    // Assemble Main panel.
    mainPanel.add(investFlexTable);
    mainPanel.add(addPanel);
    mainPanel.add(lastUpdatedLabel);

    // Associate the Main panel with the HTML host page.
    RootPanel.get("stockList").add(mainPanel);

    // Move cursor focus to the input box.
    newCityTextBox.setFocus(true);
    

    
 // Setup timer to refresh list automatically.
    Timer refreshTimer = new Timer() {
      @Override
      public void run() {
        refreshWatchList();
      }


    };
    refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

    // Listen for mouse events on the Add button.
    addProjectButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        addCity();
      }
    });
    
    
    // Listen for keyboard events in the input box.
    newCityTextBox.addKeyPressHandler(new KeyPressHandler() {
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          addCity();
        }
      }
    });
    
    
  }
  

  
  /**
   * Add stock to FlexTable. Executed when the user clicks the addStockButton or
   * presses enter in the newSymbolTextBox.
   */
  private void addCity() {
	  final String symbol = newCityTextBox.getText().toUpperCase().trim();
	    newCityTextBox.setFocus(true);

	    // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
	    if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
	      Window.alert("'" + symbol + "' is not a valid symbol.");
	      newCityTextBox.selectAll();
	      return;
	    }

	    newCityTextBox.setText("");

	    // Don't add the stock if it's already in the table.
	    if (stocks.contains(symbol))
	      return;

	    // Add the stock to the table.
	    int row = investFlexTable.getRowCount();
	    stocks.add(symbol);
	    investFlexTable.setText(row, 0, symbol);
	    investFlexTable.setWidget(row, 2, new Label());
	    investFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
		investFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
		investFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");


	    // Add a button to remove this stock from the table.
	    Button removeStockButton = new Button("x");
	    removeStockButton.addStyleDependentName("remove");
	    removeStockButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        int removedIndex = stocks.indexOf(symbol);
	        stocks.remove(removedIndex);        
	        investFlexTable.removeRow(removedIndex + 1);
	      }
	    });
	    investFlexTable.setWidget(row, 3, removeStockButton);

	 // Get the stock price.
	    refreshWatchList();


  }

  /**
   * Generate random stock prices.
   */
  private void refreshWatchList() {
	  
	// Initialize the service proxy.
	    if (stockPriceSvc == null) {
	      stockPriceSvc = GWT.create(AwardDataService.class);
	    }

	    // Set up the callback object.
	    AsyncCallback<AwardDatas[]> callback = new AsyncCallback<AwardDatas[]>() {
	      public void onFailure(Throwable caught) {
	        // TODO: Do something with errors.
	      }

	      public void onSuccess(AwardDatas[] result) {
	        updateTable(result);
	      }
	    };

	    // Make the call to the stock price service.
	    stockPriceSvc.getAmmounts(stocks.toArray(new String[0]), callback);
    
  }
  
	  /**
	   * Update the Price and Change fields all the rows in the stock table.
	   *
	   * @param prices Stock data for all rows.
	   */
	  private void updateTable(AwardDatas[] prices) {
	    for (int i = 0; i < prices.length; i++) {
	      updateTable(prices[i]);
	    }

	    // Display timestamp showing last refresh.
	    lastUpdatedLabel.setText("Last update : "
	        + DateTimeFormat.getMediumDateTimeFormat().format(new Date()));

	  }
	  
	  /**
	   * Update a single row in the stock table.
	   *
	   * @param price Stock data for a single row.
	   */
	  private void updateTable(AwardDatas ammount) {
	    // Make sure the stock is still in the stock table.
	    if (!stocks.contains(ammount.getCity())) {
	      return;
	    }

	    int row = stocks.indexOf(ammount.getCity()) + 1;

	    // Format the data in the Price and Change fields.
	    String priceText = NumberFormat.getFormat("#,##0.00").format(
	    		ammount.getAmmount());
	    NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
	    String changeText = changeFormat.format(ammount.getChange());
	    String changePercentText = changeFormat.format(ammount.getChangePercent());

	    // Populate the Price and Change fields with new data.
	    investFlexTable.setText(row, 1, priceText);
	    Label changeWidget = (Label)investFlexTable.getWidget(row, 2);
	    changeWidget.setText(changeText + " (" + changePercentText + "%)");
	    
	    // Change the color of text in the Change field based on its value.
	    String changeStyleName = "noChange";
	    if (ammount.getChangePercent() < -0.1f) {
	      changeStyleName = "negativeChange";
	    }
	    else if (ammount.getChangePercent() > 0.1f) {
	      changeStyleName = "positiveChange";
	    }

	    changeWidget.setStyleName(changeStyleName);
	    
	  }
}



