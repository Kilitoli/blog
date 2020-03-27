package com.ischoolbar.programmer.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.admin.NewsDao;
import com.ischoolbar.programmer.entity.admin.News;
import com.ischoolbar.programmer.entity.admin.NewsCategory;
import com.ischoolbar.programmer.service.admin.NewsService;
@Service
public class NewsServiceImpl implements NewsService {
	@Autowired
	private NewsDao newsDao;
	
	@Override
	public int add(News news) {
		// TODO Auto-generated method stub
		return newsDao.add(news);
	}

	@Override
	public int edit(News news) {
		// TODO Auto-generated method stub
		return newsDao.edit(news);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return newsDao.delete(id);
	}

	@Override
	public List<News> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return newsDao.findList(queryMap);
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return newsDao.getTotal(queryMap);
	}

	@Override
	public List<News> findAll() {
		// TODO Auto-generated method stub
		return newsDao.findAll();
	}

	@Override
	public News find(Long id) {
		// TODO Auto-generated method stub
		return newsDao.find(id);
	}

	@Override
	public int updateCommentNumber(Long id) {
		// TODO Auto-generated method stub
		return newsDao.updateCommentNumber(id);
	}

	@Override
	public int updataViewNumber(Long id) {
		// TODO Auto-generated method stub
		return newsDao.updataViewNumber(id);
	}

	@Override
	public List<News> findLastCommentList(int pagesize) {
		// TODO Auto-generated method stub
		return newsDao.findLastCommentList(pagesize);
	}

	@Override
	public List<News> findPhoto() {
		// TODO Auto-generated method stub
		return newsDao.findPhoto();
	}



}
