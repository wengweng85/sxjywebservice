package com.insigma.webservice.log;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.insigma.db.HBFactory;
import com.insigma.webservice.exception.AppException;
import com.insigma.webservice.infostruct.enums.TradeCode;


/**
 * 接口工具类
 * @author wengsh
 *
 */
public class JkLogUtil {
	
	
	private HBFactory hbfactory;
	
	
	/**
	 * 创建接口流水号,可以用于业务比对
	 * 规则为发送系统编号+每天9位流水号
	 * 
	 * @return
	 */
	public  String createJkKey(String syscode) throws AppException {
		String sqnum = "000000001";
		String ymd=new SimpleDateFormat("yyyyMMdd").format(new Date());
		Connection conn =hbfactory.getConnection();
	    CallableStatement proc=null;
		try {
			proc = conn.prepareCall("{ call pkg_jk_client.GET_KEY(?,?,?) }");
			proc.setString(1, ymd);// 接口类型
			proc.setInt(2, 9);// 流水号长度
			proc.registerOutParameter(3, Types.VARCHAR);
			proc.execute();
			sqnum = proc.getString(3);
			conn.commit();
			if(proc!=null){
				proc.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 
		return syscode+sqnum;
	}

	/**
	 * 增加日志
	 * 
	 * @param dto
	 * @param sess
	 * @return
	 */
	private  String addLog(JkLogDTO dto)  throws AppException {
		Connection conn =hbfactory.getConnection();
		CallableStatement proc = null;
		String id = "";
		try {
			proc = conn.prepareCall("{ call pkg_jk_client.ADD_LOG(?,?,?,?,?,?) }");
			proc.setString(1, dto.getJkType());// 接口类型
			proc.setString(2, dto.getJkSender());// 接口调用方
			proc.setString(3, dto.getJkReciver());// 接口调用方
			proc.setString(4, dto.getJkkey());// 唯一识别流水号
			proc.setString(5, dto.getJkSendXml());//请求的xml
			proc.setString(6, id);
			proc.registerOutParameter(6, Types.VARCHAR);
			proc.execute();
			id = proc.getString(6);
			conn.commit();
			if(proc!=null){
				proc.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 
		return id;
	}

	public HBFactory getHbfactory() {
		return hbfactory;
	}

	public void setHbfactory(HBFactory hbfactory) {
		this.hbfactory = hbfactory;
	}

	/**
	 * 修改日志
	 * 
	 * @param dto
	 * @param sess
	 * @return
	 */
	private  void updateLog(JkLogDTO dto) throws AppException {
		Connection conn =hbfactory.getConnection();
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ call pkg_jk_client.UPDATE_LOG(?,?,?,?,?) }");
			proc.setString(1, dto.getJkId());
			proc.setString(2, dto.getJkResult());
			proc.setString(3, dto.getJkReturnXml());
			proc.setString(4, dto.getJkReturnCode());
			proc.setString(5, dto.getJkReturnMsg());
			proc.execute();
			conn.commit();
			if(proc!=null){
				proc.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} 
	}
	
	/**
	 * 增加主日志
	 * @param head 消息头信息
	 * @param send_xml xml串
	 * @return
	 * @throws AppException
	 */
	public  String addLog(String requestxml) throws AppException{
		JkLogDTO jklogdto=new JkLogDTO();
		jklogdto.setJkSendXml(requestxml);//send xml
		String jkid = addLog(jklogdto);
		return jkid;
		
	}
	
	
	
	/**
	 * 更新主日志 成功 
	 * @param jkId 主日志流水号 
	 * @param jk_return_xml 返回的xml
	 */
	public  void updateSuccessLog(String jkId,String jk_return_xml ) throws AppException{
		JkLogDTO jklogdto=new JkLogDTO();
		jklogdto.setJkId(jkId);
		jklogdto.setJkResult("1");// 失败
		jklogdto.setJkReturnXml(jk_return_xml);//返回xml串
	    updateLog(jklogdto);	
	}
	

	
	/**
	 * 更新主日志 错误
	 * @param jkId 主日志流水号 
	 * @param jk_return_xml 返回的xml
	 * @param return_code 业务码
	 * @param return_msg 业务码说明
	 */
	public  void updateErrorLog(String jkId,String return_msg) throws AppException{
		JkLogDTO jklogdto=new JkLogDTO();
		jklogdto.setJkId(jkId);
		jklogdto.setJkResult("0");// 失败
		jklogdto.setJkReturnXml("");//返回xml串
		jklogdto.setJkReturnCode(TradeCode.SUCCESS.getCode());//返回码
		jklogdto.setJkReturnMsg(return_msg);//返回码说明
	    updateLog(jklogdto);	
	}

}
