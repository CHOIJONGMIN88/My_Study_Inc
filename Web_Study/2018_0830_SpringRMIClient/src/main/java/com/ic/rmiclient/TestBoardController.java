package com.ic.rmiclient;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.BoardDao;
import vo.BoardVo;

@Controller
public class TestBoardController {
	
	BoardDao boardDaoRMI;

	public BoardDao getBoardDaoRMI() {
		return boardDaoRMI;
	}

	public void setBoardDaoRMI(BoardDao boardDaoRMI) {
		this.boardDaoRMI = boardDaoRMI;
	}
	
	
	//�Խ��� ��� ��û
	@RequestMapping("/board_list.do")
	public String list(Model model) {
		
		//���� ������ �ִ� BoardDao��ü�κ��� ����� ������
		
		List<BoardVo> list = boardDaoRMI.selectList();
		System.out.println(list.size());
		model.addAttribute("list",list);
		
		
		
		return "/WEB-INF/views/board_list.jsp";
	}
	
	
	
	
	

}
