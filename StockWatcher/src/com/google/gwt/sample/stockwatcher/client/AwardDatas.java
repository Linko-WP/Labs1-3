package com.google.gwt.sample.stockwatcher.client;

public class AwardDatas {
	
	  private String city;
	  //TODO : add project associated
	  private double ammount;
	  private double change;

	  public AwardDatas() {
	  }

	  public AwardDatas(String city, double ammount, double change) {
	    this.city = city;
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
