package com.ischoolbar.programmer.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.admin.NewsCategoryDao;
import com.ischoolbar.programmer.entity.admin.NewsCategory;
import com.ischoolbar.programmer.service.admin.NewsCategoryService;
/***
 * 新闻分类实现类
 * @author llq
 *
 */
@Service
public class NewsCategoryImpl implements NewsCategoryService {

	@Autowired
	private NewsCategoryDao newsCategorydao; 
	@Override
	public int add(NewsCategory newsCategory) {
		// TODO Auto-generated method stub
		return newsCategorydao.add(newsCategory);
	}

	@Override
	public int edit(NewsCategory newsCategory) {
		// TODO Auto-generated method stub
		return newsCategorydao.edit(newsCategory);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return newsCategorydao.delete(id);
	}

	@Override
	public List<NewsCategory> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return newsCategorydao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return newsCategorydao.getTotal(queryMap);
	}

	@Override
	public List<NewsCategory> findAll() {
		// TODO Auto-generated method stub
		return newsCategorydao.findAll();
	}

	@Override
	public NewsCategory find(Long id) {
		// TODO Auto-generated method stub
		return newsCategorydao.find(id);
	}

}
