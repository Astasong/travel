package cn.itcast.travel.dao.impl;


import java.util.Date;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;

/**
 * FavoriteDao实现
 * @author Administrator
 *
 */
public class FavoriteDaoImpl implements FavoriteDao {

	//导入JdbcTemplate
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	/* 
	 * 根据路线rid and 用户uid查找指定的收藏对象实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.FavoriteDao#getFavoriteByRidAndUid(int, int)
	 */
	@Override
	public Favorite getFavoriteByRidAndUid(int rid, int uid) {
		//sql 
		String sql = "select * from tab_favorite where rid = ? and  uid = ?";
		//执行 
		Favorite favorite = null;
		try {
			favorite = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid,uid);			
		}catch(Exception e) {
			return favorite;
		}
		return favorite;
	}

	/* 根据指定路线rid查询收藏次数实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.FavoriteDao#findCountByRid(int)
	 */
	@Override
	public int findCountByRid(int rid) {
		//sql
		String sql = "select count(*) from tab_favorite where rid = ?";
		//执行并返回
		return jdbcTemplate.queryForObject(sql, Integer.class, rid);
	}

	/* 根据路线rid和用户uid添加收藏实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.FavoriteDao#addFavorite(int, int)
	 */
	@Override
	public void addFavorite(int rid, int uid) {
		//sql
		String sql = "insert into tab_favorite values(?,?,?)";
		//执行
		jdbcTemplate.update(sql, rid,new Date(),uid);
	}
	
	
}
