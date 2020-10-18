package model.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.resource.cci.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.vo.User;


public  class UserDao {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	// 数据库的登陆账号密码
	static final String USER = "root";
	static final String PASS = "123456";
	// 用户登陆
	public User login(String userName){
		Connection conn = null;
		Statement stmt = null;
		java.sql.ResultSet rs = null;
		User user = new User();
		try {
			System.out.println(conn + "----------" + stmt + "---------" + rs);
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println(conn + "----------" + stmt + "---------" + rs);
			// 执行sql查询
			stmt = conn.createStatement();
			String sql;
			System.out.println(conn + "----------" + stmt + "---------" + rs);
			sql = "select * from t_user where userName = \'" + userName + "\'";
			rs = stmt.executeQuery(sql);
			System.out.println(conn + "----------" + stmt + "---------" + rs);
			 //4.处理数据库的返回结果(使用ResultSet类)
	        while(rs.next()){
//	        	System.out.println(rs.getString("user_name")+" "
//	                          +rs.getString("password"));
	        	// 将查询的数据放入User对象返回
	        	user.setUserName(rs.getString("userName"));
	        	user.setRole(rs.getString("role"));
	        	user.setName(rs.getString("chrName"));
	        	user.setPassword(rs.getString("password"));
	        }
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭资源
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return user;
	}
	

	

}
