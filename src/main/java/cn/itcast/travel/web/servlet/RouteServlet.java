package cn.itcast.travel.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

/**
 * Servlet implementation class RouteCategoryServlet
 */
@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	RouteService routeService = new RouteServiceImpl();
	
	/**
	 * route路线根据分类cid分页查询展示
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到cid
		String cidStr = request.getParameter("cid");
		//获取currentPage
		String currentPageStr = request.getParameter("currentPage");
		//获取到pageSize
		String pageSizeStr = request.getParameter("PageSize");
		//获取到搜索的rname
		String rname = request.getParameter("rname");
		
		//处理直接请求url参数的中文乱码
		rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
		
		
		//定义cid
		int cid = 0;
		//判断cidStr
		if (cidStr != null && cidStr.length() > 0 && !cidStr.equals("null")) {
			cid = Integer.parseInt(cidStr);
		}
		
		//定义currentPage
		int currentPage = 0;	//当前页码 如果没有参数传递 则默认为第一页
		if (currentPageStr != null && currentPageStr.length() > 0) {
			currentPage = Integer.parseInt(currentPageStr);
		}else {
			currentPage = 1;
		}
		
		//定义pageSize
		int pageSize = 0;	//每页展示数据条目数 如果没有参数传递 则默认为10条
		if (pageSizeStr != null && pageSizeStr.length() > 0) {
			pageSize = Integer.parseInt(pageSizeStr);
		}else {
			pageSize = 10;
		}
		
		//调用Service的方法
		PageBean<Route> pb = routeService.pageQuery(cid,currentPage,pageSize,rname);
		//json格式输出Pb到前端
		response.setContentType("application/json;charset=utf-8");
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getOutputStream(), pb);
	}
	
	
	/**
	 * route_detail路线详情页面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ridStr = request.getParameter("rid");
		//调用service方法得到route对象
		Route route = routeService.findOne(Integer.parseInt(ridStr));
		//转化route对象成json对象
		ObjectMapper mapper = new ObjectMapper();
		//设置json格式的contentType
		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(), route);
	}
	
	
	/**
	 * 路线route是否已经收藏判断
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到参数值rid
		String ridStr = request.getParameter("rid");
		//获取到session
		HttpSession session = request.getSession();
		//检测session中是否已经加入user对象（用户是否已登录）
		User currentUser = (User) session.getAttribute("loginUser");
		//判断
		int uid = 0;
		if (currentUser != null) {
			//用户已登录
			uid = currentUser.getUid();
		}
		//执行检测是否已经收藏
		Boolean flag = routeService.isFavorite(ridStr,uid);
		
		//转化route对象成json对象
		ObjectMapper mapper = new ObjectMapper();
		//设置json格式的contentType
		response.setContentType("application/json;charset=utf-8");
		mapper.writeValue(response.getOutputStream(), flag);
	}
	
	
	/**
	 * 收藏操作控制类
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取到参数值rid
		String ridStr = request.getParameter("rid");
		//获取到登录的用户信息
		User user = (User) request.getSession().getAttribute("loginUser");
		//调用routeService 执行收藏操作业务
		routeService.addFavorite(Integer.parseInt(ridStr),user.getUid());
		
	}
}
