package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.ProvinceCityDao;
@WebServlet(urlPatterns = "/queryProvinceCity.do")
public class queryProvinceCity extends HttpServlet {

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
		String provinceCode = request.getParameter("provinceCode");
		String jsonStr = "";
		ProvinceCityDao dao = new ProvinceCityDao();
		if (provinceCode == null) { //没有请求参数provinceCode， 表示查询所有省份列表
			ArrayList<Province> list = dao.queryProvince();
			jsonStr = new Gson().toJson(list);
		}
		//字符流输出字符串
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(jsonStr);
		out.print(jsonStr);
		out.flush();
		out.close();
		
	}

}
