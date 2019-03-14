package cn.itcast.travel.dao;


import java.util.List;

import cn.itcast.travel.domain.RouteImg;

/**
 * 路线图片集合信息Dao
 * @author Administrator
 *
 */
public interface RouteImgDao {
	
	/**
	 * 根据路线route的rid查找对应的图片集合列表
	 * @param rid
	 * @return
	 */
	List<RouteImg> findAllImgByRid(int rid);
}
