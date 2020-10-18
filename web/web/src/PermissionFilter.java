import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.vo.User;


public class PermissionFilter implements Filter {

	private String notCheckPath;



	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// 将ServletRequest类型参数转换为HttpServletRequest类型
		HttpServletRequest request = (HttpServletRequest)req;
		String path = request.getServletPath(); //获取请求的URL-Pattern地址
		System.out.println("请求地址url-pattern:" + path);
		//请求地址不在不需过滤的列表范围内，需要判断是否已经登录
		if (notCheckPath.indexOf(path) == -1) {
			HttpSession session = request.getSession();
			if (session.getAttribute("currentUser") == null) {
				request.setAttribute("info", "没有权限访问");
				request.getRequestDispatcher("/error.jsp").forward(request, resp);
			
			}else { //已经登录，直接放行
				chain.doFilter(req, resp);
			}
			}
		}



	@Override
	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		//从web.xml配置文件的filter中读取名为notCeckPath的初始值
		notCheckPath = config.getInitParameter("notCheckPath");
	}

}
