package com.google.gwt.sample.stockwatcher.server;


import java.util.Random;

import com.google.gwt.sample.stockwatcher.client.InvestDataService;
import com.google.gwt.sample.stockwatcher.client.InvestData;
import com.google.gwt.sample.stockwatcher.client.DelistedException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class InvestDataServiceImpl extends RemoteServiceServlet implements
		InvestDataService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Comento esto porque parece ser que no se usa
	//private static final double MAX_AWARD= 50.0; // $100.00
	private static final double MAX_PRICE_CHANGE = 10; // +/- 2%
	  
	
	@Override
	public InvestData[] getCities(InvestData[] cities) throws DelistedException{
		
		
		InvestData[] ammounts = new InvestData[cities.length];

		Random rnd = new Random();

		    for (int i=0; i<cities.length; i++) {
		        if (cities[i].equals("ERR")) {
		            throw new DelistedException("ERR");
		          }
		        
		      //double ammount = cities[i].getAmmount() * MAX_AWARD;
		      double change = (MAX_PRICE_CHANGE * (rnd.nextDouble() * 2f - 1f));

		      ammounts[i] = new InvestData(cities[i].getCity(), cities[i].getAmmount(), change);
		    }

		    return ammounts;
	}

}
