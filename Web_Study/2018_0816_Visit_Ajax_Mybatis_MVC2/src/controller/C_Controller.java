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

import action.CheckPwdAction;
import action.DeleteAction;
import action.InsertAction;
import action.InsertForm;
import action.ListAction;
import action.MoCompAction;
import action.PickOneAction;
import action.interface_.Action;

import javax.servlet.RequestDispatcher;

/**
 * Servlet implementation class SungDeleteAction
 */
@WebServlet("*.do")
public class C_Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		String[] uri_array = uri.split("/");
		String fn = uri_array[uri_array.length - 1];
		String dir = uri_array[uri_array.length - 2];
		String forward_page = "";
		Action action = null;
		System.out.println(uri);
		System.out.println(fn);
		System.out.println(uri_array[1]);

		
		
		
		//해당 디렉터리
		if (!dir.equals("visit")) {
			
			forward_page = "/404page.jsp";
			request.getRequestDispatcher(forward_page).forward(request, response);
			return;

		}
		
		
		
		
		
		

		if (fn.equals("list.do")) {
			action = new ListAction();
			forward_page = action.excute(request, response);
		} else if (fn.equals("delete.do")) {
			action = new DeleteAction();
			forward_page = action.excute(request, response);
			System.out.println("삭제한다.");

		} else if (fn.equals("insert.do")) {
			action = new InsertAction();
			forward_page = action.excute(request, response);

		} else if (fn.equals("insert_form.do")) {
			action = new InsertForm();
			forward_page = action.excute(request, response);

		} else if (fn.equals("modify_comp.do")) {
			action = new MoCompAction();
			forward_page = action.excute(request, response);

		} else if (fn.equals("check_pwd.do")) {
			action = new CheckPwdAction();
			forward_page = action.excute(request, response);

		} else if (fn.equals("pickone.do")) {
			action = new PickOneAction();
			forward_page = action.excute(request, response);
			System.out.println("수정한다.");
		}else 
			forward_page="/404page.jsp";

		if (forward_page == null)
			return;

		if (forward_page.startsWith("redirect:")) {
			forward_page = forward_page.replaceAll("redirect:", "");
			response.sendRedirect(forward_page);
			return;
		} else {
			RequestDispatcher disp = request.getRequestDispatcher(forward_page);
			disp.forward(request, response);
		}

	}
}