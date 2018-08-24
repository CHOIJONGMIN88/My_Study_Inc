package com.ic.myupload;

import java.io.File;
import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import myconst.MyConstant;
import vo.PhotoVo;
import vo.PhotoVo2;

@Controller
public class PhotoContoroller {

	// DispatcherServlet���� �ڵ����� �������ش�.
	@Autowired
	HttpSession session;
	@Autowired
	HttpServletRequest request;
	@Autowired
	ServletContext application;

	@RequestMapping("/insert_form.do")
	public String insert_form() {

		return MyConstant.PhotoController.VIEW_PATH + "insert_form.jsp";
	}

	@RequestMapping("/insert_form2.do")
	public String insert_form2() {

		return MyConstant.PhotoController.VIEW_PATH + "insert_form2.jsp";
	}

	// @RequestMapping(value="/insert.do",produces="text/html; charset=utf-8")
	// @ResponseBody
	@RequestMapping("/insert.do")
	public String insert(PhotoVo vo, Model model) throws Exception {

		String web_path = "/resources/upload/";
		String abs_path = application.getRealPath(web_path);

		// ���ε�� ȭ������ üũ
		MultipartFile photo = vo.getPhoto();
		if (photo.isEmpty() == false) {// ���ε� ȭ���� �ִ� ���
			vo.setFilename(photo.getOriginalFilename());
			//

			new File(abs_path).mkdirs();
			File f = new File(abs_path, vo.getFilename());
			// ������ ����

			while (f.exists()) {
				long tm = System.currentTimeMillis();// ���� �ý��� �ð�
				vo.setFilename(String.format("%d_%s", tm, vo.getFilename()));
				f = new File(abs_path, vo.getFilename());
			}

			// �ӽ�ȭ�� =>����� ��ġ�� ����
			photo.transferTo(f);
		}
		System.out.println(abs_path);
		model.addAttribute("vo", vo);

		return MyConstant.PhotoController.VIEW_PATH + "insert_result.jsp";
	}

	@RequestMapping("/insert2.do")
	public String insert2(PhotoVo2 vo, Model model) throws IllegalStateException, IOException {

		String web_path = "/resources/upload/";
		String abs_path = application.getRealPath(web_path);

		MultipartFile[] photo_array = vo.getPhoto();
		String[] filename_array = new String[photo_array.length];
		for (int i = 0; i < photo_array.length; i++) {
			filename_array[i] = photo_array[i].getOriginalFilename();

			MultipartFile photo = photo_array[i];

			if (photo.isEmpty() == false) {// ���ε� ȭ���� �ִ� ���
				//

				new File(abs_path).mkdirs();
				File f = new File(abs_path, filename_array[i]);
				// ������ ����

				while (f.exists()) {
					long tm = System.currentTimeMillis();// ���� �ý��� �ð�
					filename_array[i] = String.format("%d_%s", tm, filename_array[i]);
					f = new File(abs_path, filename_array[i]);
				}

				// �ӽ�ȭ�� =>����� ��ġ�� ����
				photo.transferTo(f);
			}

		}
		vo.setFilename(filename_array);
		model.addAttribute("vo",vo);

		return MyConstant.PhotoController.VIEW_PATH + "insert_result2.jsp";
	}

}
