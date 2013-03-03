package com.google.gwt.sample.stockwatcher.client;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.drop.AbsolutePositionDropController;
import com.allen_sauer.gwt.dnd.client.drop.DropController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class StockWatcher implements EntryPoint {

  private AbsolutePanel mainPanel = new AbsolutePanel();
  private AbsolutePanel targetPanel = new AbsolutePanel();
  
  private AbsolutePanel dragablePanel = new AbsolutePanel();
  private FlexTable investFlexTable = new FlexTable();
  private HorizontalPanel addPanel = new HorizontalPanel();
  private TextBox newCityTextBox = new TextBox();
  private Button addProjectButton = new Button("Add");
  private Label lastUpdatedLabel = new Label();
  private Label cityNameTemp = new Label();
 
  private HorizontalPanel insertPanel = new HorizontalPanel();
  private TextArea insertCityTextA = new TextArea();
  private Button insertProjectButton = new Button("Insert");
 
  // Initial elements
  private ArrayList<String> cities = new ArrayList<String>(Arrays.asList(
		  "NEW YORK","WASHINGTON","CHICAGO", "PORTLAND", "BRIDGEPORT", "WESTMINSTER", "DENVER", "AUSTIN", "SAINT PAUL"));
  private ArrayList<Integer> zips = new ArrayList<Integer>(Arrays.asList(
		  10027,20009,60634, 97209, 6604, 80234,80229,78714,55104));
  private ArrayList<Integer> amounts = new ArrayList<Integer>(Arrays.asList(
		  5000000,3968339,4999553,6170483,4999998,5000000,4999280,4135000,5000000));
  
  // Arraylist of AwardDatas
  private ArrayList<AwardDatas> elements = new ArrayList<AwardDatas>();
  private ArrayList<String> awards = new ArrayList<String>();
  private static final int REFRESH_INTERVAL = 5000; // ms
  private AwardDataServiceAsync stockPriceSvc = GWT.create(AwardDataService.class);
  private Label errorMsgLabel = new Label();
  
  //Create a DragController for each logical area where a set of draggable
  // widgets and drop targets will be allowed to interact with one another.
  PickupDragController dragController = new PickupDragController(mainPanel, true);
  
  //create a DropController for each drop target on which draggable widgets
  // can be dropped
  DropController dropController = new AbsolutePositionDropController(dragablePanel);
  DropController dropController2 = new AbsolutePositionDropController(mainPanel);
  
  /**
   * Entry point method.
   */
  public void onModuleLoad() {
	  
	 // Add to the arraylist every city of the cities vector
	 Iterator<Integer> itr_zip = zips.iterator();
	 Iterator<Integer> itr_am = amounts.iterator();

	 for(String city:cities){ 
		 Integer zip = itr_zip.next();
		 Integer ammt = itr_am.next();
		 elements.add(new AwardDatas(city,zip,ammt,0));
	 }

	// Create draggable panel
	 RootPanel.get().setPixelSize(1000, 800);
	dragablePanel.setPixelSize(600, 100);
	
	// Add style for the draggable panel
	dragablePanel.addStyleName("watchList");
	 
 	// Create table for stock data.
	investFlexTable.setText(0, 0, "City");
	investFlexTable.setText(0, 1, "Ammount");
	investFlexTable.setText(0, 2, "Applicable Taxes");
	investFlexTable.setText(0, 3, "Remove");
		 
	// Add styles to elements in the stock list table.
	investFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
	investFlexTable.addStyleName("watchList");
	investFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
	investFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
	investFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
		
    // Assemble Add Stock panel.
	newCityTextBox.addStyleName("textBox");
	addProjectButton.addStyleName("button");
	addProjectButton.setWidth("100px");
    addPanel.add(newCityTextBox);
    addPanel.add(addProjectButton);
    addPanel.addStyleName("addPanel");
    
    // Assemble Add Stock panel.
    insertCityTextA.setVisibleLines(4);
    insertCityTextA.addStyleName("textBox");
    insertProjectButton.addStyleName("button");
    insertProjectButton.setWidth("100px");
    insertPanel.add(insertCityTextA);
    insertPanel.add(insertProjectButton);
	    
    insertPanel.addStyleName("addPanel");

    // Assemble Error panel.
    errorMsgLabel.setStyleName("errorMessage");
    errorMsgLabel.setVisible(false);
    mainPanel.add(errorMsgLabel);
	    
    // Assemble Main panel.
    mainPanel.setStyleName("main");
    mainPanel.add(dragablePanel);	// We add here the dragable zone
    mainPanel.add(investFlexTable);
    mainPanel.add(addPanel);
    mainPanel.add(insertPanel);
    mainPanel.add(lastUpdatedLabel);
  
    targetPanel.setPixelSize(300, 200);
    targetPanel.addStyleName("getting-started-blue");
    

    // Add both panels to the root panel
    RootPanel.get().add(mainPanel);
    RootPanel.get().add(targetPanel);
  //  boundaryPanel.add(targetPanel, 10, 10);


    // Positioner is always constrained to the boundary panel
    // Use 'true' to also constrain the draggable or drag proxy to the boundary panel
    dragController.setBehaviorConstrainedToBoundaryPanel(false);

    // Allow multiple widgets to be selected at once using CTRL-click
    dragController.setBehaviorMultipleSelection(true);


    // Don't forget to register each DropController with a DragController
    dragController.registerDropController(dropController);

	// create a few randomly placed draggable labels
/*  for (int i = 1; i <= 5; i++) {
      // create a label and give it style
      Label label = new Label("Label #" + i, false);
      label.addStyleName("getting-started-label");

      // add it to the DOM so that offset width/height becomes available
      mainPanel.add(label, 0, 0);
      // move the label
      mainPanel.setWidgetPosition(label, 0, 0);
      // make the label draggable
      dragController.makeDraggable(label);
	}*/
	    
/*   for(int row = 0; row < investFlexTable.getRowCount(); row++ ){

    	Widget w = investFlexTable.getWidget(row, 0);
    	dragController.makeDraggable(w);
	}*/
  // refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

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
    
    // Listen for mouse events on the Add button.
    insertProjectButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        insertCity();
      }
    });
    
    // Listen for keyboard events in the input box.
    insertCityTextA.addKeyPressHandler(new KeyPressHandler() {
      public void onKeyPress(KeyPressEvent event) {
        if (event.getCharCode() == KeyCodes.KEY_ENTER) {
          insertCity();
        }
      }
    });
    
    
  }
  

  
  /**
   * Add cities to FlexTable. Executed when the user clicks the addStockButton or
   * presses enter in the newSymbolTextBox.
   */
  private void addCity( ) {
	  final String city = newCityTextBox.getText().toUpperCase().trim();
	 
	  newCityTextBox.setFocus(true);
	
	  // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
	/*    if (!city.matches("^[0-9A-Z\\.]{1,100}$")) {
		      Window.alert("'" + city + "' is not a valid city.");
		      newCityTextBox.selectAll();
		      return;
	      }
	
	    newCityTextBox.setText("");
	    
	    */
	    
		if (!cities.contains(city)){
			Window.alert("The inserted city: '" + city + "' is not a valid city.");
		      newCityTextBox.selectAll();
		      return;
		}
		
		if (awards.contains(city)){
			Window.alert("The inserted city: '" + city + "' is already in the system.");
		      newCityTextBox.selectAll();
		      return;
		}

	    
	    //TODO: falta poner los datos en la tabla, para la ciudad que hemos introducido

		// elements.add(new AwardDatas(city,zip,ammt,0));
		 
	    int row = investFlexTable.getRowCount();
	    awards.add(city);

	    dragController.makeDraggable( cityNameTemp );	// Cada ciudad added se vuelve dragable
	    cityNameTemp.setText(city);
	    investFlexTable.setWidget(row, 0, cityNameTemp);
	//    investFlexTable.setText(row, 1, ammt.toString());
	    investFlexTable.setWidget(row, 2, new Label());
	    investFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
		investFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
		investFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");


	    // Add a button to remove this stock from the table.
	    Button removeStockButton = new Button("x");
	    removeStockButton.addStyleDependentName("remove");
	    removeStockButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        int removedIndex = awards.indexOf(city);
	        awards.remove(removedIndex);        
	        investFlexTable.removeRow(removedIndex + 1);
	      }
	    });
	    investFlexTable.setWidget(row, 3, removeStockButton);

	 // Get the stock price.
	    refreshWatchList();
	    newCityTextBox.setText("");

  }

  private void addCity(final String city) {
	 
	    int row = investFlexTable.getRowCount();
	    awards.add(city);

    //	Widget w = investFlexTable.getWidget(row, 0);
    	
    //	dragController.makeDraggable(w);
    	
	    //investFlexTable.setText(row, 0, city);
	    
	    cityNameTemp.setText(city);
	    investFlexTable.setWidget(row, 0, cityNameTemp);
	    
	//  investFlexTable.setText(row, 1, ammt.toString());
	    investFlexTable.setWidget(row, 2, new Label());
	    investFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
		investFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
		investFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");


	    // Add a button to remove this stock from the table.
	    Button removeStockButton = new Button("x");
	    removeStockButton.addStyleDependentName("remove");
	    removeStockButton.addClickHandler(new ClickHandler() {
	      public void onClick(ClickEvent event) {
	        int removedIndex = awards.indexOf(city);
	        awards.remove(removedIndex);        
	        investFlexTable.removeRow(removedIndex + 1);
	      }
	    });
	    investFlexTable.setWidget(row, 3, removeStockButton);

	 // Get the stock price.
	    refreshWatchList();
	    newCityTextBox.setText("");
	    
	    dragController.makeDraggable(newCityTextBox);	// Cada ciudad added se vuelve dragable	

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
				  
				// If the stock code is in the list of delisted codes, display an error message.
				String details = caught.getMessage();
				if (caught instanceof DelistedException) {
					details = "City '" + ((DelistedException)caught).getSymbol() + "' was delisted";
				}
			
				errorMsgLabel.setText("Error: " + details);
				    errorMsgLabel.setVisible(true);
				}
			
				public void onSuccess(AwardDatas[] result) {
					updateTable(result);
				}
		};
	
	// Make the call to the stock price service.
	//TODO: que muestre los valores apropiados
	    AwardDatas [] elem = elements.toArray(new AwardDatas[elements.size()]);
	    stockPriceSvc.getCities(elem, callback);
	    
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
	    
	    // Clear any errors.
	    errorMsgLabel.setVisible(false);
	  }
	  
	  /**
	   * Update a single row in the stock table.
	   *
	   * @param price Stock data for a single row.
	   */
	 private void updateTable(AwardDatas ammount) {
	    // Make sure the stock is still in the stock table.
	    if (!awards.contains(ammount.getCity())) {
	      return;
	    }

	    int row = awards.indexOf(ammount.getCity()) + 1;

	    // Format the data in the Price and Change fields.
	    String priceText = NumberFormat.getFormat("#,##0.00").format(
	    		ammount.getAmmount());
	    NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
	    String changeText = changeFormat.format(ammount.getChange());
	    String changePercentText = changeFormat.format(ammount.getChangePercent());

	    // Populate the Price and Change fields with new data.
	    investFlexTable.setText(row, 1, priceText);
	    Label changeWidget = (Label)investFlexTable.getWidget(row, 2);
	    changeWidget.setText(   changeText + "%");
	    
	    // Change the color of text in the Change field based on its value.
	    String changeStyleName = "noChange";
	    if (ammount.getChange() < -0.1f) {
	      changeStyleName = "negativeChange";
	    }
	    else if (ammount.getChange() > 0.1f) {
	      changeStyleName = "positiveChange";
	    }

	    changeWidget.setStyleName(changeStyleName);
	    
	  }
	 
	  private void insertCity( ) {
		   
		  final String text = insertCityTextA.getText().toUpperCase().trim();
		  insertCityTextA.setFocus(true);
		  String[] result = text.split("\\s");
		  int size = result.length;
		  int j= 0;
		  
		  if (size <2){
		    	Window.alert("It must content: CITY AMMOUNT");
			      return;
		  }
		  if (cities.contains(result[j])){
		    	Window.alert("The city: '" + result[j] + "' is already in the system.");
			      return;
		  }
 
		  
	//	  int zip = Integer.parseInt(result[j+1]);
		  int ammount = Integer.parseInt(result[j+1]);
		
		  elements.add(new AwardDatas(result[j],ammount,0));
		  addCity(result[j]);
		  amounts.add(ammount);

		  insertCityTextA.setText("");
	}
}




