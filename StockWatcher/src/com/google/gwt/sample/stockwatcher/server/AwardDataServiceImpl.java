package com.google.gwt.sample.stockwatcher.server;


import java.util.Random;

import com.google.gwt.sample.stockwatcher.client.AwardDataService;
import com.google.gwt.sample.stockwatcher.client.AwardDatas;
import com.google.gwt.sample.stockwatcher.client.DelistedException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AwardDataServiceImpl extends RemoteServiceServlet implements
		AwardDataService {

	private static final double MAX_AWARD= 50.0; // $100.00
	private static final double MAX_PRICE_CHANGE = 10; // +/- 2%
	  
	
	@Override
	public AwardDatas[] getCities(AwardDatas[] cities) throws DelistedException{
		
		
		AwardDatas[] ammounts = new AwardDatas[cities.length];

		Random rnd = new Random();

		    for (int i=0; i<cities.length; i++) {
		        if (cities[i].equals("ERR")) {
		            throw new DelistedException("ERR");
		          }
		        
		      double ammount = cities[i].getAmmount() * MAX_AWARD;
		      double change = (MAX_PRICE_CHANGE * (rnd.nextDouble() * 2f - 1f));

		      ammounts[i] = new AwardDatas(cities[i].getCity(), cities[i].getAmmount(), change);
		    }

		    return ammounts;
	}

}
