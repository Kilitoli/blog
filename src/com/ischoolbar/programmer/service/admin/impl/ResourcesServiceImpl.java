package com.ischoolbar.programmer.service.admin.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.dao.admin.NewsDao;
import com.ischoolbar.programmer.dao.admin.ResourcesDao;
import com.ischoolbar.programmer.entity.admin.News;
import com.ischoolbar.programmer.entity.admin.NewsCategory;
import com.ischoolbar.programmer.entity.admin.Resources;
import com.ischoolbar.programmer.service.admin.NewsService;
import com.ischoolbar.programmer.service.admin.ResourcesService;
@Service
public class ResourcesServiceImpl implements ResourcesService {

	@Autowired
	private ResourcesDao resourcesDao;
	
	@Override
	public int add(Resources resources) {
		// TODO Auto-generated method stub
		return resourcesDao.add(resources);
	}

	@Override
	public int edit(Resources resources) {
		// TODO Auto-generated method stub
		return resourcesDao.edit(resources);
	}

	@Override
	public int delete(String id) {
		// TODO Auto-generated method stub
		return resourcesDao.delete(id);
	}

	@Override
	public List<Resources> findList(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return resourcesDao.findList(queryMap);
	}

	@Override
	public List<Resources> findAll() {
		// TODO Auto-generated method stub
		return resourcesDao.findAll();
	}

	@Override
	public int getTotal(Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		return resourcesDao.getTotal(queryMap);
	}

	@Override
	public Resources find(int id) {
		// TODO Auto-generated method stub
		return resourcesDao.find(id);
	}

}
