package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewAction {

	public String excute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		String book = request.getParameter("book");
		String ret = "";
		if(book.equals("Java"))
			ret = "이 책은 자바에 대한 내용입니다.";

		else if(book.equals("Oracle"))
			ret = "이 책은 오라클에 대한 내용입니다.";

		else if(book.equals("Spring"))
			ret = "이 책은 스프링에 대한 내용입니다.";

		else if(book.equals("Jsp"))
			ret = "이 책은 jsp에 대한 내용입니다.";
		else
			ret = "에러";
		
		request.setAttribute("ret", ret);
		request.setAttribute("bookName", book);
		return "view.jsp";
		
	}

}
