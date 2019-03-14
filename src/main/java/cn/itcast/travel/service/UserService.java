package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

/**
 * 用户操作接口
 * @author Administrator
 *
 */
public interface UserService {

	/**注册接口
	 * @param user
	 * @return
	 */
	Boolean regist(User user);

	/**用户邮箱激活
	 * @param code
	 * @return
	 */
	Boolean activeUser(String code);

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	User login(User user);

}
