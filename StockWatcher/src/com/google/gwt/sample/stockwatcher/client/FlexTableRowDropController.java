package com.google.gwt.sample.stockwatcher.client;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.AbstractDropController;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FlexTableRowDropController extends AbstractDropController{

	StockWatcher parent;
	
	public FlexTableRowDropController(Widget dropTarget, StockWatcher parent) {
		super(dropTarget);
		this.parent = parent;
	}

	@Override
	public void onDrop(DragContext context){
		super.onDrop(context);
		AbsolutePanel target = (AbsolutePanel) getDropTarget();
		
		// Aqui se define lo que quieres que haga la el target widget cuando le
		// sueltes lo que sea
		target.setTitle(parent.currentCity.getCity()+" "+parent.currentCity.getAmmount());
		target.add(new Label(parent.currentCity.getCity()+parent.currentCity.getAmmount()));
		Label temp = new Label();
		temp.setWidth(String.valueOf(parent.currentCity.getAmmount()/10000)+"px");
		temp.setStyleName("graphic_bars");
		temp.setText(" .");
		target.add(temp);
	}
}
