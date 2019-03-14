package cn.itcast.travel.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
 * 用户模块Servlet
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserServiceImpl();
    
	/**
	 * 用户注册功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("userServlet 的regist 方法执行了"); 测试成功 输出
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

		// 1获取到参数
		Map<String, String[]> map = request.getParameterMap();
		// 2参数封装成bean
		User user = new User();
		try {
			BeanUtils.populate(user, map); // 表单提交的数据map直接封装成Bean User
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 3调用userService 注册
		Boolean flag = userService.regist(user);

		// 判断
		if (flag) {
			// 注册成功
			// 删除session的CHECKCODE_SERVER值 保证验证码只使用一次
			session.removeAttribute("CHECKCODE_SERVER");
			info.setFlag(true);
		} else {
			info.setErrorMsg("注册失败！");
			info.setFlag(false);
		}
		// 由于是异步提交 需要把对象转换成json字符串
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(info);

		// 服务器响应
		// contentType设置
		response.setContentType("application/json,charset=utf-8");
		// 输出响应的json字符串
		response.getWriter().write(json);
	}

	/**
	 * 用户激活功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("userServlet 的active 方法执行了"); 测试成功输出
		// 获取到请求参数中的code的值
		String code = request.getParameter("code");
		// 调用service层激活方法
		//UserService userService = new UserServiceImpl();
		Boolean flag = userService.activeUser(code);
		String msg = null;
		if (flag) {
			// 激活成功 则直接跳转到登录页面
			msg = "激活成功   ,<a href='"+request.getContextPath()+"/login.html'>点击登录</a>";
		} else {
			// 激活失败 则提示激活失败 请联系管理员
			msg = "激活失败 请联系管理员";
		}

		// 设置response的contentTye
		response.setContentType("text/html;charset=utf-8");
		// 响应msg
		response.getWriter().write(msg);
	}
	
	/**
	 * 用户登录功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		// 获取到参数集map
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();

		try {
			// 将map 封装成bean
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//UserService userService = new UserServiceImpl();
		// 调用service的login 得到一个user对象
		User u = userService.login(user);
		if (u == null) {
			// 数据库无该用户数据 登录失败 用户名或密码错误
			info.setFlag(false);
			info.setErrorMsg("用户名或密码错误！");
		}

		if (u != null && !"Y".equals(u.getStatus())) {
			// 存在用户 但用户尚未激活
			info.setFlag(false);
			info.setErrorMsg("用户尚未激活！请激活");

		}

		if (u != null && "Y".equals(u.getStatus())) {
			// 用户存在并已激活 则登录成功 将成功的user对象保存到session中
			session.setAttribute("loginUser", u);
			// 删除session的CHECKCODE_SERVER值 保证验证码只使用一次
			session.removeAttribute("CHECKCODE_SERVER");
			info.setFlag(true);
		}

		// 响应到客户端
		ObjectMapper objectMapper = new ObjectMapper();
		// 设置响应的ContentType
		response.setContentType("application/json;charset=utf-8");

		objectMapper.writeValue(response.getOutputStream(), info);
	}
	
	/**
	 * 查找指定的User首页回显功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findOneUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("loginUser");
		// 对象转换成json格式
		ObjectMapper mapper = new ObjectMapper();
		// 设置响应contentType
		response.setContentType("application/json;charset=utf-8");
		// 响应到客户端
		mapper.writeValue(response.getOutputStream(), user);
	}
	
	/**用户退出功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void exist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 销毁当前session
		HttpSession session = request.getSession();
		session.invalidate();
		// 重定向到login.html
		response.sendRedirect(request.getContextPath() + "/login.html");

	}
	
}
