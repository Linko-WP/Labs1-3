package com.google.gwt.sample.stockwatcher.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("AwardDatas")
public interface AwardDataService extends RemoteService {
	
	AwardDatas[] getAmmounts(String[] symbols);
}
