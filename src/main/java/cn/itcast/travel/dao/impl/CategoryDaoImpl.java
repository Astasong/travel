package cn.itcast.travel.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;

/**
 * 分类信息Dao实现
 * @author Administrator
 *
 */
public class CategoryDaoImpl implements CategoryDao {

	//导入JdbcTemplate
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
	
	/* 
	 * 分类信息查询所有实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.CategoryDao#findAll()
	 */
	@Override
	public List<Category> findAll() {
		//Sql
		String sql = "select * from tab_category ";
		//执行查询 并返回
		List<Category> list = null;
		try {
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list; 
	}

	/* 根据分类cid查找指定分类category实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.CategoryDao#findOneByCid(int)
	 */
	@Override
	public Category findOneByCid(int cid) {
		//sql
		String sql = "select * from tab_category where cid = ?";
		//执行
		Category category = null;
		try {
			category = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Category>(Category.class),cid);			
		}catch(Exception e) {
			e.printStackTrace();
		}
		//返回查询到的category对象
		return category;
	}

}
