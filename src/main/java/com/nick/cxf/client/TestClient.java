package com.nick.cxf.client;

import com.nick.cxf.hello.HelloWorld;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.net.ssl.*;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class TestClient {
    public static final String url = "https://localhost:8080/webservice/hello?wsdl";

    static MyTrustManager trustManager=new MyTrustManager();
    static HostnameVerifier hostnameVerifier=new HostnameVerifier() {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    };

    public static void main(String[] args) {

        client1();

    }
    public static void client2() {
        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        clientFactory.createClient(url);
    }
    public static void client1() {
        try {
            TrustManager[] trustManagerArray = new TrustManager[]{trustManager};
//            QName serviceName = new QName("http://www.nick.com/hello", "HelloWorldImplService");
//            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
//            SSLContext  sslc = SSLContext.getInstance("TLS");
//

//
//            sslc.init(null, trustManagerArray, null);
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
//
//            URL wsdlURL =new URL(url);
//            Service service = Service.create(wsdlURL, serviceName);
//            HelloWorld proxy = (HelloWorld)service.getPort(HelloWorld.class);

//            ((BindingProvider)proxy).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlURL.toString());
//            ((BindingProvider)proxy).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "username");
//            ((BindingProvider)proxy).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "pwd");

            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(url);

            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(HelloWorld.class);

            // 创建一个代理接口实现
            HelloWorld proxy = (HelloWorld) jaxWsProxyFactoryBean.create();

            Client client = ClientProxy.getClient(proxy);

            HTTPConduit http = (HTTPConduit) client.getConduit();
           TLSClientParameters tlsClientParameters= http.getTlsClientParameters();
           if(tlsClientParameters==null){
               tlsClientParameters = new TLSClientParameters();
           }
           tlsClientParameters.setDisableCNCheck(true);
           tlsClientParameters.setHostnameVerifier(hostnameVerifier);
            tlsClientParameters.setTrustManagers(trustManagerArray);
            http.setTlsClientParameters(tlsClientParameters);

//            HTTPClientPolicy hcp = new HTTPClientPolicy();
//            hcp.setProxyServer(proxyHost);
//            hcp.setProxyServerPort(proxyport);
//            http.setClient(hcp);


            System.out.println("返回结果:" + proxy.sayHi("nick"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyTrustManager implements X509TrustManager{
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}