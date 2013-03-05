package com.google.gwt.sample.stockwatcher.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface InvestDataServiceAsync {

  void getCities(InvestData[] cities, AsyncCallback<InvestData[]> callback);

}