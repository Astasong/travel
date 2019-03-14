package cn.itcast.travel.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;

/**
 * 路线图片集合信息Dao实现
 * @author Administrator
 *
 */
public class RouteImgDaoImpl implements RouteImgDao {

	//导入JdbcTemplate
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	/* 
	 * 根据路线route的rid查找对应的图片集合列表实现 
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.RouteImgDao#findAllImgByRid(int)
	 */
	@Override
	public List<RouteImg> findAllImgByRid(int rid) {
		//sql
		String sql = "select * from tab_route_img where rid = ?";
		//执行
		List<RouteImg> list = null;
		try {
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);			
		}catch(Exception e) {
			e.printStackTrace();
		}
		//返回查询结果
		return list;
	}
	


}
