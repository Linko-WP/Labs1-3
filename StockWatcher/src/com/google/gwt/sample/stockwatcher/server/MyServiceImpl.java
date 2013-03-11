package com.google.gwt.sample.stockwatcher.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.stockwatcher.client.MyService;


public class MyServiceImpl extends RemoteServiceServlet implements MyService {

  public String myMethod(String s) {
    // Do something interesting with 's' here on the server.
	  s="pruebadecadena";
    return s;
  }
}
