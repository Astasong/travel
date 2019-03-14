package cn.itcast.travel.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

/**
 * 用户激活
 * Servlet implementation class ActiveUserServlet
 */
@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到请求参数中的code的值
		String code = request.getParameter("code");
		//调用service层激活方法
		UserService userService = new UserServiceImpl();
		Boolean flag = userService.activeUser(code);
		String msg = null;
		if (flag) {
			//激活成功 则直接跳转到登录页面
			msg = "激活成功   ,<a href='login.html'>点击登录</a>";
		}else {
			//激活失败 则提示激活失败 请联系管理员
			msg = "激活失败 请联系管理员";
		}
		
		//设置response的contentTye
		response.setContentType("text/html;charset=utf-8");
		//响应msg
		response.getWriter().write(msg);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
