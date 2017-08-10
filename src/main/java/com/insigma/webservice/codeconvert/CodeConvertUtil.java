package com.insigma.webservice.codeconvert;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.insigma.db.HBFactory;
import com.insigma.webservice.exception.AppException;

public class CodeConvertUtil {
	
	private  HBFactory hbfactory;
	
	public HBFactory getHbfactory() {
		return hbfactory;
	}


	public void setHbfactory(HBFactory hbfactory) {
		this.hbfactory = hbfactory;
	}


	/**
	 * 通过接口对应关系表转换平台将本地的代码转换为接口平台对应代码值
	 * @param localcodetype
	 * @param localcodevalue
	 * @return
	 */
	public  String getRometeCodeByLocalCode(String localcodetype,String localcodevalue)  throws  AppException{
		String sql="select aaa107 from aa99 t where t.aaa100='"+localcodetype+"' and aaa102='"+localcodevalue+"'";
		String aaa107="";                                                                   
		JdbcTemplate jdbctemplate=hbfactory.getJdbcTemplate();
		aaa107=jdbctemplate.queryForObject(sql,String.class );
		return aaa107;
	}
	
	
	/**
	 * 通过接口对应关系表转换平台将接口代码转换为本地代码
	 * @param localcodetype
	 * @param localcodevalue
	 * @return
	 */
	public  String getLocalCodeByRemoteCode(String localcodetype,String remotecodevalue)  throws  AppException{
		String sql="select aaa102 from aa99 t where t.aaa100='"+localcodetype+"' and aaa107='"+remotecodevalue+"'";
		String aaa102="";                                                                  
		JdbcTemplate jdbctemplate=hbfactory.getJdbcTemplate();
		aaa102=jdbctemplate.queryForObject(sql,String.class );
		return aaa102;
	}

}
