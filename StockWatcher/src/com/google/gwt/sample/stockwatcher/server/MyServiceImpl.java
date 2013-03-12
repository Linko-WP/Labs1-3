package com.google.gwt.sample.stockwatcher.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.sample.stockwatcher.client.MyService;


public class MyServiceImpl extends RemoteServiceServlet implements MyService {

  /**
	 * 
	 */
	private static final long serialVersionUID = 7081621504101146086L;

public String myMethod(String s) {
    // Do something interesting with 's' here on the server.
	  s="pruebadecadena";
    return s;
  }
}
