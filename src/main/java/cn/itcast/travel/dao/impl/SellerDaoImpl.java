package cn.itcast.travel.dao.impl;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;

/**
 * 卖家信息Dao实现
 * @author Administrator
 *
 */
public class SellerDaoImpl implements SellerDao {

	//导入JdbcTemplate
	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

	
	/* 
	 * 根据卖家sid查找指定卖家seller实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.SellerDao#findOneBySid(int)
	 */
	@Override
	public Seller findOneBySid(int sid) {
		//sql
		String sql = "select * from tab_seller where sid = ?";
		//执行
		Seller seller = null;
		try {
			seller = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
		}catch(Exception e) {
			e.printStackTrace();
		}
		//返回结果对象
		return seller;
	}
	
	

}
