package cn.itcast.travel.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.User;

/**
 * 查找session的用户信息 回显欢迎用户
 * Servlet implementation class findUserServlet
 */
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 User user = (User) request.getSession().getAttribute("loginUser");
		//对象转换成json格式
		ObjectMapper mapper = new ObjectMapper();
		//设置响应contentType
		response.setContentType("application/json;charset=utf-8");
		//响应到客户端
		mapper.writeValue(response.getOutputStream(), user);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
