package com.nick.cxf.hello;

import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(name = "hello", targetNamespace = "http://www.nick.com/hello",endpointInterface = "com.nick.cxf.hello.HelloWorld")
@Component
public class HelloWorldImpl implements HelloWorld{
    @Override
    public String sayHi(String text) {
        System.out.println("============sayHi "+text);
        return "hello, "+text;
    }
}
