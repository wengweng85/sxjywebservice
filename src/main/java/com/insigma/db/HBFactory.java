package com.insigma.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.insigma.webservice.exception.AppException;


/**
 * 数据库工具支持mysql及oracle
 * @author wengsh 
 *
 */
public class HBFactory {

	Log log= LogFactory.getLog(HBFactory.class);

	private JdbcTemplate jdbcTemplate;

  
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/**
	 * 获取连接Connection
	 * @return
	 */
	public Connection getConnection() throws AppException{
		try{
			return jdbcTemplate.getDataSource().getConnection();
		}catch (SQLException ex){
			throw new AppException(ex);
		}

	}

	
}
