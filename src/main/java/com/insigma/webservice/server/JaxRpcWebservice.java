package com.insigma.webservice.server;

import javax.xml.rpc.ServiceException;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

@SuppressWarnings("deprecation")
public class JaxRpcWebservice extends ServletEndpointSupport  {
	private WebService webservice;  
    
    protected void onInit() throws ServiceException {  
       //在Spring容器中获取Bean的实例  
    	webservice= (WebService) getApplicationContext().getBean("webservice");  
    }  
    
}
