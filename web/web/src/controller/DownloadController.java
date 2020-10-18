package controller;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.DownloadDao;
import model.vo.Download;
@WebServlet(urlPatterns = "/download.do")
public class DownloadController extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id=request.getParameter("id");
		DownloadDao dao = new DownloadDao();
		Download download=dao.get(Integer.parseInt(id));
		
		// 1.获取要下载的文件的绝对路径
		String path = request.getServletContext().getRealPath("/WebRoot/"+download.getPath());
		// 2.获取要下载的文件名
		String fileName = path.substring(path.lastIndexOf("\\") + 1);
		// 3.设置content-disposition响应头控制浏览器以下载的形式打开文件
		// 设置context-disposition响应头，控制浏览器以下载形式打开，这里注意文件字符集编码格式，设置utf-8，不然会出现乱码
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		// 4.获取要下载的文件输入流
		// 字符流输入流FileReader in = new FileReader(path);
		InputStream in = new FileInputStream(path);
		int len = 0;
		// 创建数据缓冲区
		byte[] buffer = new byte[1024];
		ServletOutputStream out = response.getOutputStream();
		// 7.将FileInputStream流写入到buffer缓冲区
		while ((len = in.read(buffer))!=-1) {
			// 8.使用OutputStream将缓冲区的数据输出到客户端浏览器
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}

}
