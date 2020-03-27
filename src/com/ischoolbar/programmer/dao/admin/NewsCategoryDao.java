package com.ischoolbar.programmer.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.admin.NewsCategory;

/***
 * 博客分类声明
 *
 */
@Repository
public interface NewsCategoryDao {
	public int add(NewsCategory newsCategory);
	public int edit(NewsCategory newsCategory);
	public int delete(String id);
	public List<NewsCategory>findList(Map<String,Object> queryMap);
	public List<NewsCategory> findAll();
	public int getTotal(Map<String, Object> queryMap);
	public 	NewsCategory find(Long id);
}
