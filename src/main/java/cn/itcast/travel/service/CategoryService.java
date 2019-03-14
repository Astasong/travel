package cn.itcast.travel.service;

import java.util.List;

import cn.itcast.travel.domain.Category;

/**
 * 分类信息接口
 * @author Administrator
 *
 */
public interface CategoryService {
	
	List<Category> findAll();
}
