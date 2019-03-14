package cn.itcast.travel.service.impl;

import java.util.List;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

public class RouteServiceImpl implements RouteService{

	//导入RouteDao
	private RouteDao routeDao = new RouteDaoImpl();
	//导入CategoryDao
	private CategoryDao categoryDao = new CategoryDaoImpl();
	//导入SellerDao
	private SellerDao sellerDao = new SellerDaoImpl();
	//导入RouteImgDao
	private RouteImgDao routeImgDao = new RouteImgDaoImpl();
	//导入FavoriteDao
	private FavoriteDao favoriteDao = new FavoriteDaoImpl();
	
	@Override
	public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
		//引入pageBean
		PageBean<Route> routePageBean = new PageBean<Route>();
		
		//设置属性值
		//总记录数
		int totalCount = routeDao.countByCid(cid,rname);
		routePageBean.setTotalCount(totalCount);
		//总页数
		int totalPage = (totalCount-1)/pageSize + 1;
		routePageBean.setTotalPage(totalPage);
		//当前页码
		routePageBean.setCurrentPage(currentPage);
		//每页条目数
		routePageBean.setPageSize(pageSize);
		
		//调用dao分页查询到route列表
		List<Route> routes = routeDao.pageQuery(cid,(currentPage-1)*pageSize,pageSize,rname);
		
		//pageBean 设置数据集合
		routePageBean.setList(routes);
		
		return routePageBean;
	}


	@Override
	public Route findOne(int rid) {
		//调用dao得到route对象
		Route route = routeDao.findOne(rid);
		//调用Categorydao补全属性值category
		Category category = categoryDao.findOneByCid(route.getCid());
		route.setCategory(category);
		//调用sellerDao补全属性值seller
		Seller seller = sellerDao.findOneBySid(route.getSid());
		route.setSeller(seller);
		//调用routeImageDao补全属性值routeImgList
		List<RouteImg> routeImgList = routeImgDao.findAllImgByRid(rid);
		route.setRouteImgList(routeImgList);
		//查询路线收藏次数
		int count = favoriteDao.findCountByRid(rid);
		//设置route中count属性值
		route.setCount(count);
		return route;
	}

	@Override
	public Boolean isFavorite(String ridStr, int uid) {
		Favorite favorite = favoriteDao.getFavoriteByRidAndUid(Integer.parseInt(ridStr),uid);
		if (favorite != null) {
			return true ;
		}else {
			return false;
		}
		
	}


	@Override
	public void addFavorite(int rid, int uid) {
		//调用favoriteDao执行收藏操作
		favoriteDao.addFavorite(rid,uid);
	}

}
