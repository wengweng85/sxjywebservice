package com.insigma.webservice.client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.db.HBFactory;
import com.insigma.webservice.codeconvert.CodeConvertUtil;
import com.insigma.webservice.exception.AppException;
import com.insigma.webservice.infostruct.Input;
import com.insigma.webservice.infostruct.Result;
import com.insigma.webservice.infostruct.dto.Control;
import com.insigma.webservice.infostruct.enums.BusCode;
import com.insigma.webservice.infostruct.enums.SysType;
import com.insigma.webservice.log.JkLogUtil;
import com.insigma.webservice.xml.JaxbUtil;

/**
 * ���÷���
 * @author wengsh
 *
 */
public class ServiceCall  {
	
	private HBFactory hbfactory;
	
	private JkLogUtil jkLogUtil;
	
	private CodeConvertUtil codeConvertUtil;
	
	private Log log=LogFactory.getLog(ServiceCall.class);
	
	public CodeConvertUtil getCodeConvertUtil() {
		return codeConvertUtil;
	}


	public void setCodeConvertUtil(CodeConvertUtil codeConvertUtil) {
		this.codeConvertUtil = codeConvertUtil;
	}


	public JkLogUtil getJkLogUtil() {
		return jkLogUtil;
	}


	public void setJkLogUtil(JkLogUtil jkLogUtil) {
		this.jkLogUtil = jkLogUtil;
	}


	public HBFactory getHbfactory() {
		return hbfactory;
	}


	public void setHbfactory(HBFactory hbfactory) {
		this.hbfactory = hbfactory;
	}
	

	/**
	 * webservice����
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public  List<Result> call(BusCode buscode,List<Input> inputlist) {
	    JaxbUtil jaxb =null;
	    String jkid="";
		try{
			  String requestxml="";
			//java����ת��ΪXML�ַ��� 
			jaxb = JaxbUtil.getInstance(Input.class,Result.class);  
			for(Input input:inputlist){
				input.setControl(new Control(buscode,jkLogUtil.createJkKey(SysType.SXJY.getCode())).toString());
				requestxml+=  jaxb.toXml(input,"UTF-8").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
			}
	        //�����xml */
	        //��¼��־*/
			jkid = jkLogUtil.addLog(requestxml);
			log.info("server requestxml ->"+requestxml);   
	        //����webservice���� 
			/** ����webservice���񲢷���xml */
			Service service = new Service();
			Call call = (Call) service.createCall();
			//����webservice��ַ
			call.setTargetEndpointAddress("http://10.191.21.31:8003/jcpt/services/yhBatchWebService?wsdl");
			call.setOperationName(new QName("http://service.webservice.bidb.yinhai.com/", "onSendMessageBatch"));
			call.addParameter("inputXmlStr", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			String responsexml = (String) call.invoke(new Object[] {requestxml});
			log.info("server responsexml ->"+responsexml);   
			List<Result> resultlist=new ArrayList<Result>();
			Pattern pattern = Pattern.compile("<result>.*?</result>");
			Matcher matcher = pattern.matcher(responsexml);
		    while(matcher.find()){
		    	String xml=matcher.group();
		    	System.out.println(xml);
				Result result=jaxb.fromXml(xml);
				resultlist.add(result);
			}
			//���� xmlת����java���� */
			log.info("server responsexml->"+responsexml);
			//��¼��־
			jkLogUtil.updateSuccessLog(jkid,responsexml);
			return resultlist;
		}catch(Exception e){
			//������÷����쳣
			e.printStackTrace();
			//��������־
			try{
				jkLogUtil.updateErrorLog(jkid,e.getMessage());
			}catch(AppException ex){
				return null;
			}
		    return  null;
			//throw new AppException(e.getMessage());
		}
	}
}
