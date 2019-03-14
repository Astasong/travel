package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

/**
 * 用户Dao
 * @author Administrator
 *
 */
public interface UserDao {

	/**
	 * 根据用户名查询数据库
	 * @param username
	 * @return
	 */
	User findUserByUsername(String username);

	/**
	 * 用户数据插入数据库表
	 * @param user
	 */
	void insert(User user);

	/**
	 * 根据邮箱激活码查询数据库用户数据
	 * @param code
	 * @return
	 */
	User findUserByCode(String code);

	/**更新user实体的status属性 为已激活
	 * @param user
	 */
	void updateUser(User user);

	/**
	 * 用户登录dao
	 * @param username
	 * @param password
	 * @return
	 */
	User findUserByUsernameAndPassword(String username, String password);


}
