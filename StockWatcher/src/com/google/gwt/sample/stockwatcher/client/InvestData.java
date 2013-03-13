package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;

public class InvestData implements Serializable {
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String city;
	//TODO : insertar el projecto en todas las funciones (si hay tiempo)
	  private double org_zip;
	  private double ammount;
	  private double change;

	  public InvestData() {
	  }

	  public InvestData(String city, double ammount, double change) {
	    this.city = city;
	    this.ammount = ammount;
	    this.change = change;
	  }
	  
	  public InvestData(String city, double zip,  double ammount, double change) {
		this.city = city;
		this.org_zip = zip;
		this.ammount = ammount;
		this.change = change;
	  }

	  public String getCity() {
	    return this.city;
	  }

	  public double getAmmount() {
	    return this.ammount;
	  }

	  public double getChange() {
	    return this.change;
	  }

	  public double getChangePercent() {
	    return 10.0 * this.change / this.ammount;
	  }

	  public void setCity(String city) {
	    this.city = city;
	  }

	  public void setAmmount(double ammount) {
	    this.ammount = ammount;
	  }

	  public void setChange(double change) {
	    this.change = change;
	  }
	
}
