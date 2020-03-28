package com.nick.cxf;

import com.nick.cxf.hello.HelloWorld;
import org.apache.cxf.Bus;

import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {
    @Autowired
    HelloWorld helloWorld;
    @Autowired
    Bus bus;

    @Bean
    Endpoint endpoint(){
        EndpointImpl endpoint=new EndpointImpl(bus,helloWorld);
        endpoint.publish("/hello");
        return endpoint;
    }
}
