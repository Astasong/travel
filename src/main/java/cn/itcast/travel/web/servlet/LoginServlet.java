package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

/**
 * 用户登录
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取验证码
		String checkCode = request.getParameter("check");
		HttpSession session = request.getSession();
		String sessionCode = (String) session.getAttribute("CHECKCODE_SERVER");

		// 创建返回对象
		ResultInfo info = new ResultInfo();

		if (sessionCode == null || !sessionCode.equalsIgnoreCase(checkCode)) {
			info.setFlag(false);
			info.setErrorMsg("验证码错误！");
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(info);

			// 服务器响应
			// contentType设置
			response.setContentType("application/json;charset=utf-8");
			// 输出响应的json字符串
			response.getWriter().write(json);
			return;
		} 
		//获取到参数集map
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		
		try {
			//将map 封装成bean
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserService userService = new UserServiceImpl();
		//调用service的login 得到一个user对象
		User u = userService.login(user);
		if (u == null) {
			//数据库无该用户数据 登录失败 用户名或密码错误
			info.setFlag(false);
			info.setErrorMsg("用户名或密码错误！");
		}
		
		if (u != null && !"Y".equals(u.getStatus())) {
			//存在用户 但用户尚未激活
			info.setFlag(false);
			info.setErrorMsg("用户尚未激活！请激活");
			
		}
		
		if (u != null && "Y".equals(u.getStatus())) {
			//用户存在并已激活 则登录成功 将成功的user对象保存到session中
			session.setAttribute("loginUser", u);
			// 删除session的CHECKCODE_SERVER值 保证验证码只使用一次
			session.removeAttribute("CHECKCODE_SERVER");
			info.setFlag(true);
		}
		
		//响应到客户端
		ObjectMapper objectMapper = new ObjectMapper();
		//设置响应的ContentType
		response.setContentType("application/json;charset=utf-8");
		
		objectMapper.writeValue(response.getOutputStream(), info);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
