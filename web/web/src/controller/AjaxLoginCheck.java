package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserDao;
import model.vo.User;
@WebServlet(urlPatterns = "/ajaxLoginCheck.do")
public class AjaxLoginCheck extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ����������������ʽΪutf-8����ֹ���Ĳ�������
		request.setCharacterEncoding("utf-8");
		// 1.���ձ����ĸ�Ԫ�ص�name����ֵ��ȡ���������ֵ
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String vcode = request.getParameter("vcode");
		String autoLogin = request.getParameter("autoLogin");
		// 2.��ȡHttpSession����
		HttpSession session = request.getSession();
		// ȡ��CreateVerifyImageController�д�ŵ���֤���ַ���
		String saveVcode = (String) session.getAttribute("verifycode");
		// ��ŷ���ֵMap
		Map<String, Object> map = new HashMap<String, Object>();
		// �Ƚ��������֤���������ɵ���֤���Ƿ���ͬ
		if (!vcode.equalsIgnoreCase(saveVcode)) {
			// ��map�д�ŷ�������
			map.put("code", 1);
			map.put("info", "��֤�벻��ȷ!");
		} else { // ��֤����ȷ
			UserDao userDao = new UserDao();
			User user = new userDao.get(userName);
			if (user == null) { // �û���������
				map.put("code", 2);
				map.put("info", "�û���������");
			} else { //�û�������
				if (!user.getPassword().equals(password)) { //���벻��ȷ
					map.put("code", 3);
					map.put("info", "���벻��ȷ");
				} else { // �û���������ȷ
					// ����Ҫ���ݵ����ݴ����session��Χ�У� һ���Ự�׶ε����г��򶼿��Դ��л�ȡ
					session.setAttribute("currentUser", user);
					if (autoLogin != null) { //���½ѡ��
						// ����cookie
						Cookie cookie1 = new Cookie("userName", userName);
						cookie1.setMaxAge(7 * 24 *24 *24);
						response.addCookie(cookie1);
						Cookie cookie2 = new Cookie("password", password);
						cookie2.setMaxAge(7 * 24 *24 *24);
						response.addCookie(cookie2);
					}
					map.put("code", 0);
					map.put("info", "��¼�ɹ�");
					
				}
			}
		}
		// ���ùȸ��Gson�⽫map��������ת��Ϊjson�ַ���
		String jsonStr = new Gson().toJson(map);
		// �ַ�������ַ���
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}

}