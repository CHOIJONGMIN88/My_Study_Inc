package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.interface_.Action;
import dao.VisitDao;
import vo.VisitVo;

public class MoCompAction implements Action {

	@Override
	public String excute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("utf-8");

		String name = request.getParameter("name");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		int idx = Integer.parseInt(request.getParameter("idx"));
		String ip = request.getRemoteAddr();

		content = content.replaceAll("\n", "<br>");

		VisitVo vo = new VisitVo(name, content, pwd, ip);
		vo.setIdx(idx);

		int res = VisitDao.getInstance().update(vo);

		String redirect_page = "redirect:list.do";

		return redirect_page;
	}

}
