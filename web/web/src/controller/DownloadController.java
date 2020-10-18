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
		
		// 1.��ȡҪ���ص��ļ��ľ���·��
		String path = request.getServletContext().getRealPath("/WebRoot/"+download.getPath());
		// 2.��ȡҪ���ص��ļ���
		String fileName = path.substring(path.lastIndexOf("\\") + 1);
		// 3.����content-disposition��Ӧͷ��������������ص���ʽ���ļ�
		// ����context-disposition��Ӧͷ�������������������ʽ�򿪣�����ע���ļ��ַ��������ʽ������utf-8����Ȼ���������
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
		// 4.��ȡҪ���ص��ļ�������
		// �ַ���������FileReader in = new FileReader(path);
		InputStream in = new FileInputStream(path);
		int len = 0;
		// �������ݻ�����
		byte[] buffer = new byte[1024];
		ServletOutputStream out = response.getOutputStream();
		// 7.��FileInputStream��д�뵽buffer������
		while ((len = in.read(buffer))!=-1) {
			// 8.ʹ��OutputStream��������������������ͻ��������
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
	}

}