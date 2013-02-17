package com.google.gwt.sample.stockwatcher.server;


import java.util.Random;

import com.google.gwt.sample.stockwatcher.client.AwardDataService;
import com.google.gwt.sample.stockwatcher.client.AwardDatas;
import com.google.gwt.sample.stockwatcher.client.DelistedException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AwardDataServiceImpl extends RemoteServiceServlet implements
		AwardDataService {

	private static final double MAX_PRICE = 100.0; // $100.00
	private static final double MAX_PRICE_CHANGE = 0.02; // +/- 2%
	  
	@Override
	public AwardDatas[] getAmmounts(String[] cities) throws DelistedException{
		 Random rnd = new Random();

		    AwardDatas[] ammounts = new AwardDatas[cities.length];
		    for (int i=0; i<cities.length; i++) {
		        if (cities[i].equals("ERR")) {
		            throw new DelistedException("ERR");
		          }
		        
		      double ammount = rnd.nextDouble() * MAX_PRICE;
		      double change = ammount * MAX_PRICE_CHANGE * (rnd.nextDouble() * 2f - 1f);

		      ammounts[i] = new AwardDatas(cities[i], ammount, change);
		    }

		    return ammounts;
	}

}
