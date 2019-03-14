package cn.itcast.travel.dao;

import java.util.List;

import cn.itcast.travel.domain.Route;

/**
 * 路线route信息Dao
 * @author Administrator
 *
 */
public interface RouteDao {

	/**
	 * 根据cid查询route路线信息总记录数接口
	 * @param cid
	 * @return
	 */
	int countByCid(int cid,String rname);

	/**根据cid分页查询route路线信息集合接口
	 * @param cid
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	List<Route> pageQuery(int cid, int start, int pageSize,String rname);

	/** 
	 * 根据指定路线idrid查询具体路线详情接口
	 * @param rid
	 * @return
	 */
	Route findOne(int rid);
	 
	
}
