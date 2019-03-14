package cn.itcast.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;

/**
 * 用户Dao实现
 * @author Administrator
 *
 */
public class UserDaoImpl implements UserDao{

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());
			
	/* 根据用户名查询数据库实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.UserDao#findUserByUsername(java.lang.String)
	 */
	@Override
	public User findUserByUsername(String username) {
		//sql语句
		String sql = "select * from tab_user where username = ?";
		User user = null;
		try {
			//执行查询操作 BeanPropertyRowMapper查询为空会报错 故try……catch
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
					username);
		} catch (Exception e) {
			//queryForObject 默认返回值不为空 为空需要做异常处理 这里为空则直接返回null
			return user;
		}
		return user;
	}

	/* 用户数据插入数据库表实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.UserDao#insert(cn.itcast.travel.domain.User)
	 */
	@Override
	public void insert(User user) {
		// sql语句
		String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) "
				+ "values(?,?,?,?,?,?,?,?,?)";
		// 执行用户数据插入到数据库
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(),
				user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
	}

	/* 
	 * 根据邮箱激活码查询数据库用户数据实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.UserDao#findUserByCode(java.lang.String)
	 */
	@Override
	public User findUserByCode(String code) {
		// sql语句
		String sql = "select * from tab_user where code = ?";
		User user = null;
		try {
			// 执行查询操作 BeanPropertyRowMapper查询为空会报错 故try……catch
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
		} catch (Exception e) {
			// queryForObject 默认返回值不为空 为空需要做异常处理 这里为空则直接返回null
			return user;
		}
		return user;
	}

	/* 更新user实体的status属性 为已激活实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.dao.UserDao#updateUser(cn.itcast.travel.domain.User)
	 */
	@Override
	public void updateUser(User user) {
		// sql语句
		String sql = "update tab_user set status = ? where code = ?";
		// 执行用户数据插入到数据库
		jdbcTemplate.update(sql, user.getStatus(),user.getCode());
			
	}

	/*	用户登录dao实现
	 *  (non-Javadoc)
	 * @see cn.itcast.travel.dao.UserDao#findUserByUsernameAndPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		// sql语句
		String sql = "select * from tab_user where username = ? and password = ?";
		User user = null;
		try {
			// 执行查询操作 BeanPropertyRowMapper查询为空会报错 故try……catch
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);
		} catch (Exception e) {
			// queryForObject 默认返回值不为空 为空需要做异常处理 这里为空则直接返回null
			return user;
		}
		return user;
	}

}
