package com.insigma.webservice.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.webservice.client.ServiceCall;

/**
 * 调用统一门户系统同步接口定时类
 * @author wengsh
 */
public class JkJob {
	
	private ServiceCall servicecall;
	

	public ServiceCall getServicecall() {
		return servicecall;
	}
	public void setServicecall(ServiceCall servicecall) {
		this.servicecall = servicecall;
	}
	Log log=LogFactory.getLog(JkJob.class);
	public void work() {
		System.out.println("调用统一门户系统同步接口,当前时间为："+new Date().toLocaleString());
		
	}

}
