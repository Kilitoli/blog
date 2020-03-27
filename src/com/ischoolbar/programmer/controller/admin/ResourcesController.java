package com.ischoolbar.programmer.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.NewsCategory;
import com.ischoolbar.programmer.entity.admin.Resources;
import com.ischoolbar.programmer.entity.admin.User;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.NewsCategoryService;
import com.ischoolbar.programmer.service.admin.ResourcesService;


/***
 * 博客资源控制器
 *
 */
@RequestMapping("/admin/resources")
@Controller
public class ResourcesController {	
	@Autowired
	private ResourcesService resourcesService;
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("resource/list");
		return model;
	}
	
	/**
	 * 获取资源列表
	 * @param name
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(name="name",required=false,defaultValue="") String name,Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		ret.put("rows", resourcesService.findList(queryMap));
		ret.put("total", resourcesService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 博客资源添加
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Resources resources){
		Map<String, String> ret = new HashMap<String, String>();
		if(resources == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的资源信息！");
			return ret;
		}
		if(StringUtils.isEmpty(resources.getName())){
			ret.put("type", "error");
			ret.put("msg", "请填写资源名！");
			return ret;
		}
		if(resourcesService.add(resources) <= 0){
			ret.put("type", "error");
			ret.put("msg", "资源添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}
	
	/**
	 * 编辑资源
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(Resources resources){
		Map<String, String> ret = new HashMap<String, String>();
		if(resources == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的分类信息!");
			return ret;
		}
		if(StringUtils.isEmpty(resources.getName())){
			ret.put("type", "error");
			ret.put("msg", "请填写分类名！");
			return ret;
		}

		if(resourcesService.edit(resources) <= 0){
			ret.put("type", "error");
			ret.put("msg", "分类修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "分类修改成功！");
		return ret;
	}
	
	/**
	 * 批量删除资源
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(String id){
		Map<String, String> ret = new HashMap<String, String>();
		if(id == "0"){
			ret.put("type", "error");
			ret.put("msg", "选择要删除的数据！");
			return ret;
		}
		try {
			if(resourcesService.delete(id) <= 0){
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员！");
				return ret;
			}
		} catch (Exception e) {
			ret.put("type", "error");
			ret.put("msg", "该分类下有博客信息，不能删除");
			return ret;
		}
		
		ret.put("type", "success");
		ret.put("msg", "删除成功！");
		return ret;
	}
}
