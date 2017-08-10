package com.insigma.webservice.job;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.insigma.db.HBFactory;
import com.insigma.webservice.exception.AppException;

/**
 * 调用统一门户系统同步接口定时类
 * @author wengsh
 */
public class JkLogCleanJob {
	
	
	private HBFactory hbfactory;
	
	
	
	public HBFactory getHbfactory() {
		return hbfactory;
	}



	public void setHbfactory(HBFactory hbfactory) {
		this.hbfactory = hbfactory;
	}

	public void work() {
		 try{
			 Connection conn =hbfactory.getConnection();
			 //删除一个月前的日志
			 String sql="delete from  JK_CLIENT_LOG t where t.jk_call_begin<=sysdate-30";
			 Statement stat= conn.createStatement();
			 int updatecount=stat.executeUpdate(sql);
			 System.out.println("删除的日志记录数:"+updatecount);
			 conn.commit();
		 }catch(AppException e){
			 e.printStackTrace();
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	}

}
