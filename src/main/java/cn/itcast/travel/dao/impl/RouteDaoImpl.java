package cn.itcast.travel.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;

/**
 * 路线route信息Dao实现
 * @author Administrator
 *
 */
public class RouteDaoImpl implements RouteDao {

	//导入JdbcTemplate
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
	
	/* 根据cid查询route路线信息总记录数接口实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.RouteDao#countByCid(int)
	 */
	@Override
	public int countByCid(int cid,String rname) {
//		 String sql = "select count(*) from tab_route where cid = ?";
		//动态sql param
		StringBuilder builder = new StringBuilder("select count(*) from tab_route where 1=1 ");
		//参数param
		List param = new ArrayList();
		//判断cid
		if (cid != 0) {
			builder.append(" and cid = ?");
			param.add(cid);
		}
		//判断rname
		if (rname != null && rname.length() > 0) {
			builder.append(" and rname like ?");
			param.add("%"+rname+"%");
		}
		
        return jdbcTemplate.queryForObject(builder.toString(),Integer.class,param.toArray());
	}

	/* 根据cid分页查询route路线信息集合接口实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.RouteDao#pageQuery(int, int, int)
	 */
	@Override
	public List<Route> pageQuery(int cid, int start, int pageSize,String rname) {
//		String sql = "select * from tab_route where cid = ? limit ? , ?";
		//动态sql
		StringBuilder builder = new StringBuilder("select * from tab_route where 1=1 "); 
		//参数param
		List param = new ArrayList();
		//判断cid
		if (cid != 0) {
			builder.append(" and cid = ?");
			param.add(cid);
		}
		//判断rname
		if (rname != null && rname.length() > 0) {
			builder.append(" and rname like ?");
			param.add("%"+rname+"%");
		}
		//加入分页显示
		builder.append(" limit ? , ? ;");
		//添加分页参数
		param.add(start);
		param.add(pageSize);
		//执行
		List<Route> list = null;
		try {
			list = jdbcTemplate.query(builder.toString(),new BeanPropertyRowMapper<Route>(Route.class),param.toArray());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list; 
	}

	/* 根据指定路线idrid查询具体路线详情接口实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.RouteDao#findOne(int)
	 */
	@Override
	public Route findOne(int rid) {
		//sql
		String sql = "select * from tab_route where rid = ?";
		//查找指定rid对应的route实体
		Route route  = null;
		try {
			route = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return route;
	}
	

}
