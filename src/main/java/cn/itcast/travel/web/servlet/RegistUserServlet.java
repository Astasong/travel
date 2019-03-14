package cn.itcast.travel.web.servlet;

import java.io.IOException;
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
 * 用户注册Servlet
 */
@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	private UserService userService = new UserServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取验证码
		String checkCode = request.getParameter("check");
		HttpSession session = request.getSession();
		String sessionCode = (String) session.getAttribute("CHECKCODE_SERVER");
		
		//创建返回对象
		ResultInfo info = new ResultInfo();
		
		if (sessionCode == null || !sessionCode.equalsIgnoreCase(checkCode)) {
			info.setFlag(false);
			info.setErrorMsg("验证码错误！");
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(info);
			
			//服务器响应
			//contentType设置
			response.setContentType("application/json;charset=utf-8");
			//输出响应的json字符串
			response.getWriter().write(json);
			return;
		}
		
		//1获取到参数
		Map<String, String[]> map = request.getParameterMap();
		//2参数封装成bean
		User user = new User();
		try {
			BeanUtils.populate(user , map);	//表单提交的数据map直接封装成Bean User
		} catch (Exception e) {
			e.printStackTrace();
		}
		//3调用userService 注册
		Boolean flag = userService.regist(user);
		
		//判断
		if (flag) {
			//注册成功
			//删除session的CHECKCODE_SERVER值 保证验证码只使用一次
			session.removeAttribute("CHECKCODE_SERVER");
			info.setFlag(true);
		}else {
			info.setErrorMsg("注册失败！");
			info.setFlag(false);
		}
		//由于是异步提交 需要把对象转换成json字符串
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(info);
		
		//服务器响应
		//contentType设置
		response.setContentType("application/json,charset=utf-8");
		//输出响应的json字符串
		response.getWriter().write(json);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
