package com.insigma.webservice.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.webservice.client.ServiceCall;

/**
 * ����ͳһ�Ż�ϵͳͬ���ӿڶ�ʱ��
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
		System.out.println("����ͳһ�Ż�ϵͳͬ���ӿ�,��ǰʱ��Ϊ��"+new Date().toLocaleString());
		
	}

}
