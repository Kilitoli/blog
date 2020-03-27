package com.ischoolbar.programmer.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ischoolbar.programmer.entity.admin.News;
import com.ischoolbar.programmer.entity.admin.NewsCategory;
import com.ischoolbar.programmer.entity.admin.Resources;

/***
 * 资源类声明
 *
 */
@Repository
public interface ResourcesDao {
	public int add(Resources resources);
	public int edit(Resources resources);
	public int delete(String id);
	public List<Resources>findList(Map<String,Object> queryMap);
	public List<Resources> findAll();
	public int getTotal(Map<String, Object> queryMap);
	public Resources find(int id);
}
