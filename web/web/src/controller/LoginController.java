package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDao;
import model.vo.User;
@WebServlet(urlPatterns = "/login.do")
public class LoginController extends HttpServlet {

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		Date tasktime=new Date(0);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd:mm:ss");  //设置日期输出格式
		//格式化输出
		String nowTime = df.format(tasktime);
		PrintWriter out = response.getWriter();
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String vcode = request.getParameter("vcode");
//		System.out.println(name +"---"+ password);
		// 判断验证码是否正确
		HttpSession session = request.getSession();
		String saveVcode = (String)session.getAttribute("verifycode");
		if(vcode.equalsIgnoreCase(saveVcode)){
			// 验证码输入正确
			// 通过用户名和密码进行验证
			UserDao userDao = new UserDao();
			User user = userDao.login(userName);
			if(null != user && null != user.getUserName()){
				//用户名存在
				if(user.getPassword().equals(password)){
					//密码正确登陆成功
					request.setAttribute("user", user);
					request.getRequestDispatcher("/main.jsp").forward(request, response); 
				}else{
					// 密码错误登陆失败
					request.setAttribute("info","密码错误，登陆失败");
					request.getRequestDispatcher("/error.jsp").forward(request, response); 
				}
			}else{
				//用户名不存在
				request.setAttribute("info","用户名不存在请重新输入");
				request.getRequestDispatcher("/error.jsp").forward(request,response); 
			}
		}else{
			// 验证码验证失败
			request.setAttribute("info","请输入正确的验证码");
			request.getRequestDispatcher("/error.jsp").forward(request,response); 
		}
	
	}
}
