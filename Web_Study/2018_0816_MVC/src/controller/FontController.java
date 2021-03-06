package controller;

/**
 * Servlet implementation class SungDeleteAction
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.ha.backend.Sender;

import action.InsertAction;
import action.InsertFormAction;
import action.ListAction;
import action.ViewAction;

import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class SungDeleteAction
 */
@WebServlet("*.do")
public class FontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		String uri = request.getRequestURI();
		System.out.println(uri);
		String forward_page = "";
		
		int index = uri.lastIndexOf("/");
		String cmd = uri.substring(index+1).replaceAll(".do", "");
		if(cmd.equals("list")) {
			System.out.println("리스트.do라서 처리를 하고 리스트 action으로");
			ListAction action = new ListAction();
			forward_page = action.execute(request, response);
		}
		else if(cmd.equals("view")) {
			System.out.println("뷰.do라서 처리를 하고 뷰 action으로");
			ViewAction action = new ViewAction();
			forward_page = action.excute(request,response);
		}
		else if(cmd.equals("modify"))
			System.out.println("모디파이.do라서 처리를 하고 모디파이action으로");
		else if(cmd.equals("insert")) {
			System.out.println("인서트.do라서 처리를 하고 인서트 action으로");
			InsertAction action = new InsertAction();
			forward_page = action.excute(request,response);
		}
		else if(cmd.equals("insert_form")) {
			System.out.println("인서트_폼.do라서 처리를 하고 인서트_폼 action으로");
			InsertFormAction action = new InsertFormAction();
			forward_page = action.excute(request,response);
		}
		
		
		
		if(forward_page.startsWith("redirect:")) {
			forward_page = forward_page.replaceAll("redirect:", "");
			response.sendRedirect(forward_page);
		}else {
		
			RequestDispatcher disp = request.getRequestDispatcher(forward_page);
			disp.forward(request, response);
		}
			

	}

}
