package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * 路线route业务接口
 * @author Administrator
 *
 */
public interface RouteService {

	PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

	Route findOne(int rid);

	Boolean isFavorite(String ridStr, int uid);

	void addFavorite(int rid, int uid);
	
	
}
