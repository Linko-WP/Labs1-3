package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AwardDataServiceAsync {

  void getAmmounts(String[] symbols, AsyncCallback<AwardDatas[]> callback);

}