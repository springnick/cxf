package com.nick.cxf.hello;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "hello", targetNamespace = "http://www.nick.com/hello")
public interface HelloWorld {
    String sayHi(String text);
}
