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

import vo.Download;
import Dao.DownloadDao;
@WebServlet(urlPatterns = "/download.do")
public class DownloadController extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id=request.getParameter("id");
		DownloadDao dao = new DownloadDao();
		Download download=dao.get(Integer.parseInt(id));
		
		// 1.��ȡҪ���ص��ļ��ľ���·��
		String path = request.getServletContext().getRealPath("/WEB-INF/"+download.getPath());
		// 2.��ȡҪ���ص��ļ���
		String fileName = path.substring(path.lastIndexOf("\\" + 1));
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
