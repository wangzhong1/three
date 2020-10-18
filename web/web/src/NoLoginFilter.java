import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.dao.UserDao;
import model.vo.User;


public class NoLoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest res, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
//		System.out.println("notzhixing le =----------------");
		response.setContentType("text/html;charset=UTF-8");
		UserDao userDao = new UserDao();
		// 判断cookie中是否有之前登陆的密码
		HttpServletRequest request = (HttpServletRequest) res;
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();
		if(null != cookies){
			String userName = null;
			String password = null;
			// cookie中的内容不为空
			for(Cookie cookie : cookies){
				// 获取cookie中存放的userName 和password
				if("userName".equals(cookie.getName())){
					userName = cookie.getValue();
				}else if ("password".equals(cookie.getName())){
					password = cookie.getValue();
				}
			}
			// 若cookie中有值则去验证用户名密码是否正确
			if(null != userName && null != password){
				User user = userDao.login(userName);
				if(null != user){
					// 若是用户名正确则比较密码是否验证成功
					if(password.equals(user.getPassword())){
						// 密码正确
						request.setAttribute("user", user);
						session.setAttribute("loginUser", user);
						request.getRequestDispatcher("/index.jsp").forward(request, response); 
					}else{
						chain.doFilter(request, response);			
					}
				}
			}else{
				chain.doFilter(request, response);			
			}
		}else{
			chain.doFilter(request, response);			
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
