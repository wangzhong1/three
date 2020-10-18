package model.dao;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.resource.cci.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.vo.Download;

public class DownloadDao extends HttpServlet {

	public ArrayList<Download> query() {
		ArrayList<Download> list = new ArrayList<Download>();
		try {
			// 1.加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 2.建立与数据库的连接
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "123456");
			// 3.创建语句
			String sql = "select * from t_downloadList ";
			PreparedStatement pst = con.prepareStatement(sql);
			// 4.执行语句
			java.sql.ResultSet rs = pst.executeQuery();
			// 5.结果处理
			while (rs.next()) {
				Download download = new Download();
				download.setId(rs.getInt("id"));   // 资源id
				download.setName(rs.getString("name"));   // 资源名称
				download.setPath(rs.getString("path"));   // 资源存放相对路径
				download.setDescription(rs.getString("description"));   // 资源描述
				long size=rs.getLong("size");   // 资源大小
				String sizeStr=fileSizeTransfer(size);
				download.setSizeStr(sizeStr);
				download.setStar(rs.getInt("star"));   // 星级
				download.setImage(rs.getString("image"));  // 资源图像存放相对路径
				download.setTime(rs.getString("time"));   // 资源上传时间
				
				list.add(download);  // 将对象存放于集合中
			}
			// 6.关闭连接
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	// 根据资源id查询记录， 将记录转换为download对象进行返回
	public Download get(int id) {
		Download download = null;
		try {
			// 1.加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 2.建立与数据库的连接
			Connection con = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/user?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "123456");
			// 3.创建语句
			String sql = "select * from t_downloadList where id=? ";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			// 4.执行语句
			java.sql.ResultSet rs = pst.executeQuery();
			// 5.结果处理
			while (rs.next()) {
				download = new Download();
				download.setId(rs.getInt("id"));   // 资源id
				download.setName(rs.getString("name"));   // 资源名称
				download.setPath(rs.getString("path"));   // 资源存放相对路径
				download.setDescription(rs.getString("description"));   // 资源描述
				long size=rs.getLong("size");   // 资源大小
				String sizeStr=fileSizeTransfer(size);
				download.setSizeStr(sizeStr);
				download.setStar(rs.getInt("star"));   // 星级
				download.setImage(rs.getString("image"));  // 资源图像存放相对路径
				download.setTime(rs.getString("time"));   // 资源上传时间
				download.setSizeStr(sizeStr);
			}
			// 6.关闭连接
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return download; 
		}
	

	public String fileSizeTransfer(long fileSize) {
		String mFileSize;
		DecimalFormat df = new DecimalFormat("######0.00");
		double size = (double) fileSize;
		if (size > 1024 * 1024 * 1024) {
			size = size / (1024 * 1024 * 1024);
			mFileSize = df.format(size) + "G";
		} else if (size > 1024 * 1024) {
			size = size / (1024 * 1024);
			mFileSize = df.format(size) + "MB";
		} else if (size > 1024){
			size = size / 1024;
			mFileSize = df.format(size) + "KB";
		} else {
			mFileSize = df.format(size) + "B";
		}
		return mFileSize;
	}
	
}


