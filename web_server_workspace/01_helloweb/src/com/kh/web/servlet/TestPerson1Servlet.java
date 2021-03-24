package com.kh.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet
 * webService�� ���� java class
 * �Ϲ� �ڹ�Ŭ�����̳� webService�뵵�� ���� �� ��
 * 
 * �׷����� �ݵ�� HttpServlet�� ����ؾ� ��
 * (extends HttpServlet)
 * import javax.servlet.http.HttpServlet;
 * 
 * -> ����ڰ� /web/testPerson1.do�� ��û���� �� ó���� �� �ִ� �ڹ�Ŭ������ �� ��!
 *
 * �ڹ� Ŭ���� ��ü�δ� ����û�� �޾Ƶ��� �� ������,
 * �װͿ� �ʿ��� ���׵��� HttpServlet�� �̸� �����صΰ�
 * �װ� ����ؼ� custom servlet class�� ����� ���� ��!
 * 
 * 
 * Servlet �����ֱ�
 * �Ϲ� Ŭ������ HttpServlet�� ����ϰ�, doGet()�� �������̵� �ϸ�,
 * ��� �Ϲ� Ŭ������ ���������� ���� ��û�� ó���� �� �ִ� Servlet Ŭ������ �ɱ�?
 * 
 * *** Servlet�� Ư¡ : singletone ��� ***
 * -> Servlet�� WAS���� ���� �� �ϳ��� ��ü�� ��������� ó���ȴ�.
 * (��û�� ���� ������ ��ü�� �����, ��û�� ������ ��ü�� �ݳ��ϰ� �ȴٸ�, ��ȿ����)
 * �׷��� �ѹ��� servlet�� �����, �װ��� ������
 * 
 * - ���� client ȣ��� 1ȸ
 * 1. ��ü ���� (by Servlet �⺻ ������ ȣ��)  
 * 	  but �츮�� �ϴ� �� �ƴ�, tomcat�� ���� ��!
 * 2. init() �޼ҵ� ȣ��
 * 
 * - client �� ��û���� ó��
 * 3. HttpServlet(�θ�Ŭ����)�� service() �޼ҵ� ȣ��
 * 4. ���۹�Ŀ� ���� doGet() | doPost() ȣ��
 * 
 * - ������ 1ȸ (tomcat �����)
 * 5. destroy ȣ�� (-> tomcat����� ��ü ��ȯ)
 *	    �ϳ��� ��û�� ������ ��ȯ�Ǵ� ���� �ƴ�, tomcat ����ÿ� ��ȯ�Ǵ� ��
 *
 * ���� �����ø����̼ǿ����� ������ ó���� �� �Ǿ�����
 * ���� ���� ���ÿ� ó���ϵ��� �ϳ��� ��û�� �ϳ��� ������� �����޾Ƽ� ó��
 * 
 * �ϳ��� ������ ���� Ŭ���̾�Ʈ�� ���������� ��û�� �����ٸ�
 * ������ �̱۾������� 1�� �� ó���ϰ�, 2�� ����, 2�� ó���ϰ�, 3�� ����...
 * ��Ƽ�������� Ŭ���̾�Ʈ ��û���� �����ϴ� �����尡 �ϳ��� ������
 * -> ��ٸ��� �ʰ�, ��û ���� ��� ������ ����
 * like 3���� ��ȭ, 3���� ��ȭ����
 * 
 */
public class TestPerson1Servlet extends HttpServlet{
	
	// Servlet �����ֱ� �˾ƺ���
	// 1. �⺻������ ����
	public TestPerson1Servlet() {
		super();
		System.out.println("�⺻������ TestPerson1Servlet() ȣ��!");
	}
	
	// 2. init() ȣ��
	@Override
	public void init(ServletConfig config) {
		System.out.println("init(ServletConfig) ȣ��!");
	}
	
	// 3. service() ȣ��
	// �������̵� ���� �ʰ� �״�� �� ��!
	
	// 5. destroy() ȣ��
	@Override
	public void destroy() {
		System.out.println("destroy() ȣ��!");
	}
	
	// [ G E T �� �� ]
	// 1. doGet() �޼ҵ� �������̵�
	// 1-1. �Ķ���� �ۼ�
	// cf. �� �Ķ���͵鵵 javax.servlet ��Ű���� �ִ� �������̽���
	// 1-2. ���ܴ����� (IOException, ServletException)
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		// 4. �ſ�û�� ���Ǵ� servlet��ü�� �����ϴ�.
		// �� ��û�� servlet��ü�� ������ ��ü���� Ȯ��
		System.out.println(this.hashCode());
		
		// [http://localhost:9090/web/testPerson1.do?name=ȫ�浿&color=�Ķ�&animal=�����&food=¥���&food=«��]
		
		// [ 1 . R E Q U E S T - ����� �Է°� �������� ]
		
		// �Է°��� �ܼ����� ���
		// 1. ����� �Է°� (form�±׳� �ۼ��� �����) ��������
		// -> request.getParameter("html�±��� ���Ӱ�")
		// 1-1. String ������ ���
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String animal = request.getParameter("animal");
		
		// �Է°��� �ܼ����� ���
		// 1. ����� �Է°� (form�±׳� �ۼ��� �����) ��������
		// -> request.getParameterValues("html�±��� ���Ӱ�")
		// cf. getParameter()�� ���� ù��° �� �ϳ��ۿ� �� ������
		// 1-1. String �迭�� ���
		// cf. �� �޼ҵ�� String �迭�� ������
		String[] foodArr = request.getParameterValues("food");
		
		// ����� �Է°��� �� �����Դ��� Ȯ��
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		System.out.println("foodArr = " + Arrays.toString(foodArr));
		
		// �� ������ ����ڰ� ��û�� testPerson1.do�� ��Ī�ϵ��� ����ؾ� ��!
		// .do��� ������ ����. ���� Ȯ���� ������ ���� �� �� ��. �ϳ��� ���ڿ��� ���� ��
		
		
		// [ 2. R E S P O N S E - ����޽��� �ۼ� ]
		// html�� ����޽����� �ۼ���
		
		// 1. html�� �ۼ��� ���̶�� ���信 �̸� ������
		// text/html - �� ���� �޽��� ���ǵ� text�����̰� html �������� ���ž�!
		// charset=utf-8 - character�� utf-8���� ���ڵ���!
		response.setContentType("text/html; charset=utf-8");
		// 2. ��½�Ʈ�� PrintWriter ������ response.getWriter()���
		// PrintWriter Ÿ������ ������
		// -> response.getWriter()
		PrintWriter out = response.getWriter();
		// 3. out.println("html �ۼ�")
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>���� �˻� ���</title>");
		out.println("<body>");
		out.println("<h1>���� ���� �˻� ��� GET</h1>");
		out.println("<p>" + name + "���� ���� ���� �˻� ����� </p>");
		out.println("<p>" + color + "���� �����մϴ�.</p>");
		out.println("<p>�����ϴ� ������" + animal + "�Դϴ�.</p>");
		out.println("<p>�����ϴ� ������" + Arrays.toString(foodArr) + "�Դϴ�.</p>");
		out.println("</body>");
		out.println("</head>");
		out.println("</html>");
	}
	
	// [ P O S T �� �� ]
	// �޼ҵ常 �ٸ���, ������ get��İ� ����
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		
		// [ 1 . R E Q U E S T - ����� �Է°� �������� ]
		
		// �Է°��� �ܼ����� ���
		// 1. ����� �Է°� (form�±׳� �ۼ��� �����) ��������
		// -> request.getParameter("html�±��� ���Ӱ�")
		// 1-1. String ������ ���
		String name = request.getParameter("name");
		String color = request.getParameter("color");
		String animal = request.getParameter("animal");
		
		// �Է°��� �ܼ����� ���
		// 1. ����� �Է°� (form�±׳� �ۼ��� �����) ��������
		// -> request.getParameterValues("html�±��� ���Ӱ�")
		// cf. getParameter()�� ���� ù��° �� �ϳ��ۿ� �� ������
		// 1-1. String �迭�� ���
		// cf. �� �޼ҵ�� String �迭�� ������
		String[] foodArr = request.getParameterValues("food");
		
		// ����� �Է°��� �� �����Դ��� Ȯ��
		System.out.println("name = " + name);
		System.out.println("color = " + color);
		System.out.println("animal = " + animal);
		System.out.println("foodArr = " + Arrays.toString(foodArr));
		
		// �� ������ ����ڰ� ��û�� testPerson1.do�� ��Ī�ϵ��� ����ؾ� ��!
		// .do��� ������ ����. ���� Ȯ���� ������ ���� �� �� ��. �ϳ��� ���ڿ��� ���� ��
		
		
		// [ 2. R E S P O N S E - ����޽��� �ۼ� ]
		// html�� ����޽����� �ۼ���
		
		// 1. html�� �ۼ��� ���̶�� ���信 �̸� ������
		// text/html - �� ���� �޽��� ���ǵ� text�����̰� html �������� ���ž�!
		// charset=utf-8 - character�� utf-8���� ���ڵ���!
		response.setContentType("text/html; charset=utf-8");
		// 2. ��½�Ʈ�� PrintWriter ������ response.getWriter()���
		// PrintWriter Ÿ������ ������
		// -> response.getWriter()
		PrintWriter out = response.getWriter();
		// 3. out.println("html �ۼ�")
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>���� �˻� ���</title>");
		out.println("<body>");
		out.println("<h1>���� ���� �˻� ��� POST</h1>");
		out.println("<p>" + name + "���� ���� ���� �˻� ����� </p>");
		out.println("<p>" + color + "���� �����մϴ�.</p>");
		out.println("<p>�����ϴ� ������" + animal + "�Դϴ�.</p>");
		out.println("<p>�����ϴ� ������" + Arrays.toString(foodArr) + "�Դϴ�.</p>");
		out.println("</body>");
		out.println("</head>");
		out.println("</html>");
	}
}
