package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

/**
 * Servlet implementation class CategoryServlet
 * 
 */
@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	CategoryService categoryService = new CategoryServiceImpl();

	public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用分类业务类categoryService
		List<Category> list = categoryService.findAll();
		
		//设置response响应contentType
		response.setContentType("application/json;charset=utf-8");
		//使用jackson输出json
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), list);
		
	}


}
