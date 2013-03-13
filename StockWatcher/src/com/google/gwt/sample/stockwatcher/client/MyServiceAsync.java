package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

interface MyServiceAsync {
  public void initialize_db(String s, AsyncCallback<String> callback);
}
