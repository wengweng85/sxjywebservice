package com.insigma.webservice.log;

import java.util.Date;



/**
 * 接口日志DTO
 * @author wengsh
 * @date 2013-12-9
 *
 */
public class JkLogDTO {
	private String jkId;//表流水号
	private String jkkey;//接口流水号
	private String jkSendXml;//发送信息
	private String jkType;//接口类型
	private String jkSender;//接口发起方
	private String jkReciver;//接口接收方
	private Date jkCallStart;//开始时间
	private Date jkCallEnd;//结束时间
	private String jkResult;//调用结果 
	private String jkReturnCode;//业务返回码
	private String jkReturnMsg;//业务返回码说明
	private String jkReturnXml;//返回的xml头
	public String getJkId() {
		return jkId;
	}
	public void setJkId(String jkId) {
		this.jkId = jkId;
	}
	public String getJkkey() {
		return jkkey;
	}
	public void setJkkey(String jkkey) {
		this.jkkey = jkkey;
	}

	
	public String getJkType() {
		return jkType;
	}
	public void setJkType(String jkType) {
		this.jkType = jkType;
	}
	public String getJkSender() {
		return jkSender;
	}
	public void setJkSender(String jkSender) {
		this.jkSender = jkSender;
	}
	
	public String getJkReciver() {
		return jkReciver;
	}
	public void setJkReciver(String jkReciver) {
		this.jkReciver = jkReciver;
	}
	public Date getJkCallStart() {
		return jkCallStart;
	}
	public void setJkCallStart(Date jkCallStart) {
		this.jkCallStart = jkCallStart;
	}
	public Date getJkCallEnd() {
		return jkCallEnd;
	}
	public void setJkCallEnd(Date jkCallEnd) {
		this.jkCallEnd = jkCallEnd;
	}
	public String getJkResult() {
		return jkResult;
	}
	public void setJkResult(String jkResult) {
		this.jkResult = jkResult;
	}
	
	public String getJkReturnCode() {
		return jkReturnCode;
	}
	public void setJkReturnCode(String jkReturnCode) {
		this.jkReturnCode = jkReturnCode;
	}
	public String getJkReturnMsg() {
		return jkReturnMsg;
	}
	public void setJkReturnMsg(String jkReturnMsg) {
		this.jkReturnMsg = jkReturnMsg;
	}
	public String getJkSendXml() {
		return jkSendXml;
	}
	public void setJkSendXml(String jkSendXml) {
		this.jkSendXml = jkSendXml;
	}
	public String getJkReturnXml() {
		return jkReturnXml;
	}
	public void setJkReturnXml(String jkReturnXml) {
		this.jkReturnXml = jkReturnXml;
	}
	
}
