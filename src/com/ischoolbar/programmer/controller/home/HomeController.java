package com.ischoolbar.programmer.controller.home;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.service.admin.NewsCategoryService;
import com.ischoolbar.programmer.service.admin.NewsService;
import com.ischoolbar.programmer.service.admin.ResourcesService;

@RequestMapping("/index")
@Controller
public class HomeController {
	
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private ResourcesService resourcesService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView home(ModelAndView model){
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 10);
		model.addObject("newsCategoryList",newsCategoryService.findAll());
		model.addObject("newsList", newsService.findList(queryMap));
		model.setViewName("home/home");
		return model;
	}
	@RequestMapping(value="/resource",method=RequestMethod.GET)
	public ModelAndView resource(ModelAndView model){
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 10);
		model.addObject("newsCategoryList",newsCategoryService.findAll());
		model.addObject("resourceList", resourcesService.findList(queryMap));
		model.setViewName("resource/resource");
		return model;
	}
	
	@RequestMapping(value="/about",method=RequestMethod.GET)
	public ModelAndView about(ModelAndView model){
		
		model.addObject("newsCategoryList",newsCategoryService.findAll());
		model.setViewName("about/about");
		
		return model;
	}
	
	@RequestMapping(value="/note",method=RequestMethod.GET)
	public ModelAndView note(ModelAndView model){
		
		model.addObject("newsCategoryList",newsCategoryService.findAll());
		model.setViewName("note/note");
		
		return model;
	}
}
