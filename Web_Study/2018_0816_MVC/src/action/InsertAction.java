package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertAction {

	public String excute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		System.out.println("--insert�ߴٰ� ġ��...");
		
		return "redirect:list.do";	
	}

}
