package com.ischoolbar.programmer.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.Comment;
import com.ischoolbar.programmer.entity.admin.News;
import com.ischoolbar.programmer.entity.admin.NewsCategory;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.CommentService;
import com.ischoolbar.programmer.service.admin.NewsCategoryService;
import com.ischoolbar.programmer.service.admin.NewsService;

@RequestMapping("/news")
@Controller
public class IndexController {
	
	/**
	 * 博客详情
	 */
	@Autowired
	private NewsCategoryService newsCategoryService;
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView detail(ModelAndView model,
			@RequestParam(name="id",required=true) Long id,
			Page page
			){
		News news = newsService.find(id);
		model.addObject("newsCategoryList",newsCategoryService.findAll());
		model.addObject("news", newsService.find(id));
		model.addObject("title", news.getTags());
		model.addObject("tags", news.getTags().split(","));
		//查看数量+1
		newsService.updataViewNumber(id);
		model.setViewName("home/detail");
		
		return model;
	}
	
	@RequestMapping(value="/getdetail",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getdetail(
			Page page,
			@RequestParam(name="id",required=true) Long id
			){
		Map<String,Object> ret=new HashMap<String,Object>();
		ret.put("type", "success");
		ret.put("newsList", newsService.find(id));
		return ret;
	}

	/**
	 * 按照分类显示博客列表
	 * @param model
	 * @param cid
	 * @return
	 */
	@RequestMapping(value="/category_list",method=RequestMethod.GET)
	public ModelAndView categoryList(ModelAndView model,
			@RequestParam(name="cid",required=true) Long cid,
			Page page
			){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 10);
		queryMap.put("categoryId", cid);
		model.addObject("newsCategoryList", newsCategoryService.findAll());
		model.addObject("newsList", newsService.findList(queryMap));
		NewsCategory newsCategory  = newsCategoryService.find(cid);
		model.addObject("newsCategory",newsCategory);
		model.addObject("title",newsCategory.getName()+"分类下的博客信息");
		model.setViewName("home/category_list");
		return model;
	}
	
	/**
	 * 评论数排序的博客最新10条
	 * @return
	 */
	@RequestMapping(value="/last_comment_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> lastCommentList(
			){
		Map<String,Object> ret=new HashMap<String,Object>();
		ret.put("type", "success");
		ret.put("newsList", newsService.findLastCommentList(10));
		return ret;
	}
	
	/**
	 * 随机显示5条数据
	 * @return
	 */
	@RequestMapping(value="/top_photo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> topPhoto(
			){
		Map<String,Object> ret=new HashMap<String,Object>();
		ret.put("type", "success");
		ret.put("photoList", newsService.findPhoto());
		return ret;
	}
	
	@RequestMapping(value="/get_category_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getCategoryList(Page page,
			@RequestParam(name="cid",required=true) Long cid
			){
		Map<String,Object> ret=new HashMap<String,Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("categoryId", cid);
		ret.put("type", "success");
		ret.put("newsList", newsService.findList(queryMap));
		return ret;
	}
	
	/**
	 * 按照搜索显示博客列表
	 * @param model
	 * @param cid
	 * @return
	 */
	@RequestMapping(value="/search_list",method=RequestMethod.GET)
	public ModelAndView searchList(ModelAndView model,
			@RequestParam(name="keyword",required=false,defaultValue="") String keyword,
			Page page
			){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 999);
		queryMap.put("title", keyword);
		model.addObject("newsCategoryList", newsCategoryService.findAll());
		model.addObject("newsList", newsService.findList(queryMap));
		model.addObject("title",keyword);
		model.addObject("keyword",keyword);
		model.setViewName("home/search_list");
		return model;
	}
	
	
	@RequestMapping(value="/get_search_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getSearchList(Page page,
			@RequestParam(name="keyword",required=true) String keyword
			){
		Map<String,Object> ret=new HashMap<String,Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("title", keyword);
		ret.put("type", "success");
		ret.put("newsList", newsService.findList(queryMap));
		return ret;
	}
	
	@RequestMapping(value="/comment_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> commentList(Comment comment
			){
		Map<String,Object> ret=new HashMap<String,Object>();
		if(comment==null) {
			ret.put("type", "error");
			ret.put("msg", "请填写完整的评论信息");
			return ret;
		}
		if(StringUtils.isEmpty(comment.getNickname())) {
			ret.put("type", "error");
			ret.put("msg", "请填写昵称");
			return ret;
		}
		if(StringUtils.isEmpty(comment.getContent())) {
			ret.put("type", "error");
			ret.put("msg", "请填写内容");
			return ret;
		}
		comment.setCreateTime(new Date());
		if(commentService.add(comment)<=0) {
			ret.put("type", "error");
			ret.put("msg", "评论失败");
			return ret;
		}
		newsService.updateCommentNumber(comment.getId());
		ret.put("type", "success");
		ret.put("createTime",comment.getCreateTime() );
		return ret;
	}
	
	/**
	 * 分页获取某一文章的评论
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/get_comment_list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCommentList(Page page,Long newsId){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		queryMap.put("newsId", newsId);
		ret.put("type", "success");
		ret.put("commentList", commentService.findList(queryMap));
		return ret;
	}
}
