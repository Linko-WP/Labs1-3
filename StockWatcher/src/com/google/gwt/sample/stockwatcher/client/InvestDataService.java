package com.google.gwt.sample.stockwatcher.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("MyDbHandler")
public interface InvestDataService extends RemoteService {
	
	InvestData[] getCities(InvestData[] cities) throws DelistedException;
}
