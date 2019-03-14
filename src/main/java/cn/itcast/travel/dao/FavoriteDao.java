package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

/**
 * FavoriteDao
 * @author Administrator
 *
 */
public interface FavoriteDao {

	/**
	 * 根据路线rid and 用户uid查找指定的收藏对象
	 * @param rid
	 * @param uid
	 * @return
	 */
	Favorite getFavoriteByRidAndUid(int rid, int uid);

	/**根据指定路线rid查询收藏次数
	 * @param rid
	 * @return
	 */
	int findCountByRid(int rid);

	/**
	 * 根据路线rid和用户uid添加收藏
	 * @param rid
	 * @param uid
	 */
	void addFavorite(int rid, int uid);
	
	
}
