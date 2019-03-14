package cn.itcast.travel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

/**
 * 分类信息接口实现
 * @author Administrator
 *
 */
public class CategoryServiceImpl implements CategoryService {

	//导入CategoryDao 
	private CategoryDao categoryDao = new CategoryDaoImpl();
	
	@Override
	public List<Category> findAll() {
		//待做：导入缓存redis操作对象jedis
		Jedis jedis = JedisUtil.getJedis();
		
		//分类信息有序排列 并不相同 则使用sortedset
		Set<Tuple> set = jedis.zrangeWithScores("Category", 0, -1);
		
		List<Category> categoryList = new ArrayList<Category>() ;
		//直接查询缓存 有内容 则直接获取输出 没有内容查询数据库 并将结果写入缓存中
		if (set == null || set.size() == 0) {
			//缓存没有查询数据库
//			System.out.println("从数据库查询");
			categoryList = categoryDao.findAll();
			//将查询结果遍历写入到缓存中
			for (Category category : categoryList) {				
				jedis.zadd("Category", category.getCid(), category.getCname());
			}
		}
		
			//查询缓存有则直接遍历输出
			for (Tuple turple : set) {
//				System.out.println("从Redis中获取");
				Category category = new Category();
				category.setCid((int)turple.getScore());
				category.setCname(turple.getElement());
				categoryList.add(category);
			}
		
		return categoryList;
	}
	
}
