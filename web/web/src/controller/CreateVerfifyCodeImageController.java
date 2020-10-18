package controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.Createlmage;

@WebServlet(urlPatterns = "/CreateVerfifyCodeImage.do")

public class CreateVerfifyCodeImageController extends HttpServlet {

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
		//1.生成4位长度的随机字符串
		Createlmage createImage = new Createlmage();
		String vCode = createImage.createCode(); //写一个类和方法来实现

		//2.将生成的4位字符串保存在session范围内
		//茯取session対象
		HttpSession session = request.getSession();
		//存放
		session.setAttribute("verifycode", vCode);
		//设置返回内容
		response.setContentType("img/jpeg");
		//浏览器不缓存响应内容-验证码图片，点一次刷新一次，所以不能缓存出现
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		//设置验证码失效时间
		response.setDateHeader("Expires", 0);
		//3.生成一 张图片. 图片内容为刚生成的4随机串
		BufferedImage image = createImage.CreateImage(vCode);
		
		//4.将图片发给客户端
		//获取输出流对象
		ServletOutputStream out=response.getOutputStream();
		//输出
		ImageIO.write(image, "JPEG", out);
		//刷新输出缓冲器（立即输出，而不是等待输出缓存满后才送至网络）
		out.flush();
		out.close();

	}

}