package cn.itcast.travel.dao;


import java.util.List;

import cn.itcast.travel.domain.Category;

/**
 * 分类信息Daoceng接口
 * @author Administrator
 *
 */
public interface CategoryDao {
	
	/**
	 * 分类信息查询所有
	 * @return
	 */
	List<Category> findAll();

	/**根据分类cid查找指定分类category
	 * @param cid
	 * @return
	 */
	Category findOneByCid(int cid);
}
