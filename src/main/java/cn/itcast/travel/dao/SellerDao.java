package cn.itcast.travel.dao;



import cn.itcast.travel.domain.Seller;

/**
 * 卖家信息Dao
 * @author Administrator
 *
 */
public interface SellerDao {
	

	/**
	 * 根据卖家sid查找指定卖家seller
	 * @param sid
	 * @return
	 * 
	 */
	Seller findOneBySid(int sid);
}
