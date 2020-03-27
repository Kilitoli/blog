package com.ischoolbar.programmer.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.admin.News;
import com.ischoolbar.programmer.entity.admin.NewsCategory;

/***
 * 博客类声明
 *
 */
@Repository
public interface NewsDao {
	public int add(News news);
	public int edit(News news);
	public int delete(String id);
	public List<News>findList(Map<String,Object> queryMap);
	public List<News> findAll();
	public int getTotal(Map<String, Object> queryMap);
	public 	News find(Long id);
	public int updateCommentNumber(Long id);
	public int updataViewNumber(Long id);
	public List<News>findLastCommentList(int pagesize);
	public List<News>findPhoto();
}
