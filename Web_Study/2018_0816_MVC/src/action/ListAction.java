package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListAction {
	
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		List<String> list = new ArrayList<String>();

		
		list.add("Java");
		list.add("Jsp");
		list.add("Oracle");

		list.add("Spring");
		///////////////
		///모두 가정 데배에서 가져온다는//////
		///////////////
		request.setAttribute("list", list);
		
		
		return "list.jsp";
	}

}
