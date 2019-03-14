package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtilsImpro;
import cn.itcast.travel.util.UuidUtil;

/**
 * 用户操作接口实现
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService{

	private UserDao userDao = new UserDaoImpl();
	
	/* 注册接口实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.service.UserService#regist(cn.itcast.travel.domain.User)
	 */
	@Override
	public Boolean regist(User user) {
		//查询数据库注册用户是否已经存在
		User existUser = userDao.findUserByUsername(user.getUsername());
		if (existUser != null) {
			//如果存在 则注册失败（用户名已经存在）
			return false;
		}
		
		//补全User实体属性
		user.setStatus("N");//表示注册未激活状态
		user.setCode(UuidUtil.getUuid());//uuid随机生成唯一的激活码
		
		//不存在 执行注册操作
		userDao.insert(user);
		
		//注册完成发送激活码邮件
		String emailMsg = "<a href='http://localhost:8021/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";
		MailUtilsImpro.sendMail("ljh_sp3612@163.com", emailMsg);
		
		return true;
	}

	/* 用户邮件激活实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.service.UserService#activeUser(java.lang.String)
	 */
	@Override
	public Boolean activeUser(String code) {
		if (code == null || "".equals(code)) {
			//激活码为空 直接返回FALSE 激活失败
			return false;
		}
		
		//根据激活码查询数据库 
		User user = userDao.findUserByCode(code);
		//判断
		if (user == null) {
			//查询没有对应用户 激活失败
			return false;
		}else {
			//查询存在对应用户 则激活成功 并修改user实体的status属性值为‘Y’
			user.setStatus("Y");
			userDao.updateUser(user);
			//返回激活成功
			return true;
		}
		
	}

	/* 用户登录实现
	 * (non-Javadoc)
	 * @see cn.itcast.travel.service.UserService#login(cn.itcast.travel.domain.User)
	 */
	@Override
	public User login(User user) {
		return userDao.findUserByUsernameAndPassword(
				user.getUsername(),user.getPassword());
		
	}

	
}
