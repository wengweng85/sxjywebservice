package com.insigma.webservice.hessianservice;

import java.util.List;

import com.insigma.webservice.client.ServiceCall;
import com.insigma.webservice.infostruct.Input;
import com.insigma.webservice.infostruct.Result;
import com.insigma.webservice.infostruct.enums.BusCode;

public class HessianServiceImpl implements HessianService {

	private ServiceCall servicecall;
	

	public ServiceCall getServicecall() {
		return servicecall;
	}

	public void setServicecall(ServiceCall servicecall) {
		this.servicecall = servicecall;
	}

	@Override
	public List<Result> hessianRpcCall( BusCode buscode, List<Input>  input){
		return servicecall.call(buscode,input);
	}

}
