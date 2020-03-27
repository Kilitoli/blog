package com.ischoolbar.programmer.controller.admin;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ischoolbar.programmer.entity.admin.Authority;
import com.ischoolbar.programmer.entity.admin.Comment;
import com.ischoolbar.programmer.entity.admin.Log;
import com.ischoolbar.programmer.entity.admin.Menu;
import com.ischoolbar.programmer.entity.admin.News;
import com.ischoolbar.programmer.entity.admin.NewsCategory;
import com.ischoolbar.programmer.entity.admin.Resources;
import com.ischoolbar.programmer.entity.admin.Role;
import com.ischoolbar.programmer.entity.admin.User;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.AuthorityService;
import com.ischoolbar.programmer.service.admin.CommentService;
import com.ischoolbar.programmer.service.admin.LogService;
import com.ischoolbar.programmer.service.admin.MenuService;
import com.ischoolbar.programmer.service.admin.NewsCategoryService;
import com.ischoolbar.programmer.service.admin.NewsService;
import com.ischoolbar.programmer.service.admin.ResourcesService;
import com.ischoolbar.programmer.service.admin.RoleService;
import com.ischoolbar.programmer.service.admin.UserService;
import com.ischoolbar.programmer.util.MenuUtil;

/**
 * 系统操作类控制器
 * @author zero
 *
 */
@Controller
@RequestMapping("/system")
public class SystemController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private LogService logService;
	
	@Autowired
	private NewsCategoryService newsCategoryService;

	@Autowired
	private NewsService newsService;
	
	@Autowired
	private CommentService CommentService;
	
	@Autowired
	private ResourcesService resourcesService;
	
	/**
	 * 系统登录后的主页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView model,HttpServletRequest request){
		List<Menu> userMenus = (List<Menu>)request.getSession().getAttribute("userMenus");
		model.addObject("topMenuList", MenuUtil.getAllTopMenu(userMenus));
		model.addObject("secondMenuList", MenuUtil.getAllSecondMenu(userMenus));
		model.setViewName("system/index");
		return model;//WEB-INF/views/+system/index+.jsp = WEB-INF/views/system/index.jsp
	}

	/**
	 * 系统登录后的欢迎页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/welcome",method=RequestMethod.GET)
	public ModelAndView welcome(ModelAndView model){
		model.setViewName("system/welcome");
		return model;
	}
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	public ModelAndView home(ModelAndView model){
		model.setViewName("home/home");
		return model;
	}
	/**
	 * 登陆页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(ModelAndView model){
		model.setViewName("system/login");
		return model;
	}
	
	/**
	 * 登录表单提交处理控制器
	 * @param user
	 * @param cpacha
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> loginAct(User user,String cpacha,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null){
			ret.put("type", "error");
			ret.put("msg", "请填写用户信息！");
			return ret;
		}
		if(StringUtils.isEmpty(user.getUsername())){
			ret.put("type", "error");
			ret.put("msg", "请填写用户名！");
			return ret;
		}
		if(StringUtils.isEmpty(user.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "请填写密码！");
			return ret;
		}
		User findByUsername = userService.findByUsername(user.getUsername());
		if(findByUsername == null){
			ret.put("type", "error");
			ret.put("msg", "该用户名不存在！");
			logService.add("登录时，用户名为"+user.getUsername()+"的用户不存在!");
			return ret;
		}
		if(!user.getPassword().equals(findByUsername.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "密码错误！");
			logService.add("用户名为"+user.getUsername()+"的用户登录时输入密码错误!");
			return ret;
		}
		//说明用户名密码及验证码都正确
		//此时需要查询用户的角色权限
		Role role = roleService.find(findByUsername.getRoleId());
		List<Authority> authorityList = authorityService.findListByRoleId(role.getId());//根据角色获取权限列表
		String menuIds = "";
		for(Authority authority:authorityList){
			menuIds += authority.getMenuId() + ",";
		}
		if(!StringUtils.isEmpty(menuIds)){
			menuIds = menuIds.substring(0,menuIds.length()-1);
		}
		List<Menu> userMenus = menuService.findListByIds(menuIds);
		//把角色信息、菜单信息放到session中
		request.getSession().setAttribute("admin", findByUsername);
		request.getSession().setAttribute("role", role);
		request.getSession().setAttribute("userMenus", userMenus);
		ret.put("type", "success");
		ret.put("msg", "登录成功！");
		logService.add("用户名为{"+user.getUsername()+"}，角色为{"+role.getName()+"}的用户登录成功!");
		return ret;
	}
	
	/**
	 * 后台退出注销功能
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.setAttribute("admin", null);
		session.setAttribute("role", null);
		request.getSession().setAttribute("userMenus", null);
		return "redirect:login";
	}
	
	/**
	 * 修改密码页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit_password",method=RequestMethod.GET)
	public ModelAndView editPassword(ModelAndView model){
		model.setViewName("system/edit_password");
		return model;
	}
	
	@RequestMapping(value="/edit_password",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> editPasswordAct(String newpassword,String oldpassword,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(newpassword)){
			ret.put("type", "error");
			ret.put("msg", "请填写新密码！");
			return ret;
		}
		User user = (User)request.getSession().getAttribute("admin");
		if(!user.getPassword().equals(oldpassword)){
			ret.put("type", "error");
			ret.put("msg", "原密码错误！");
			return ret;
		}
		user.setPassword(newpassword);
		if(userService.editPassword(user) <= 0){
			ret.put("type", "error");
			ret.put("msg", "密码修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "密码修改成功！");
		logService.add("用户名为{"+user.getUsername()+"}，的用户成功修改密码!");
		return ret;
	} 
	
	//用户管理layui
	@RequestMapping(value="/layui",method=RequestMethod.GET)
	public ModelAndView lay(ModelAndView model,HttpServletRequest request){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		model.addObject("roleList", roleService.findList(queryMap));
		model.setViewName("layui/houtai");
		return model;
	}
	
	@RequestMapping(value="/layui1",method=RequestMethod.GET)
	public ModelAndView lay1(ModelAndView model,HttpServletRequest request){
		model.setViewName("layui/layui");
		return model;
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> laygetList(Page page,
			@RequestParam(name="username",required=false,defaultValue="") String username,
			@RequestParam(name="roleId",required=false) Long roleId,
			@RequestParam(name="sex",required=false) Integer sex
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", username);
		queryMap.put("roleId", roleId);
		queryMap.put("sex", sex);
		queryMap.put("offset", page.getLayoffset());
		queryMap.put("pageSize", page.getLimit());
		ret.put("data", userService.findList(queryMap));
		ret.put("count", userService.getTotal(queryMap));
		ret.put("msg", "");
		ret.put("code", 0);
		return ret;
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(User user){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的用户信息！");
			return ret;
		}
		if(StringUtils.isEmpty(user.getUsername())){
			ret.put("type", "error");
			ret.put("msg", "请填写用户名！");
			return ret;
		}
		if(StringUtils.isEmpty(user.getPassword())){
			ret.put("type", "error");
			ret.put("msg", "请填写密码！");
			return ret;
		}
		if(user.getRoleId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属角色！");
			return ret;
		}
		if(isExist(user.getUsername(), 0l)){
			ret.put("type", "error");
			ret.put("msg", "该用户名已经存在，请重新输入！");
			return ret;
		}
		if(userService.add(user) <= 0){
			ret.put("type", "error");
			ret.put("msg", "用户添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "角色添加成功！");
		return ret;
	}
	
	@RequestMapping(value="/upload_photo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> uploadPhoto(MultipartFile photo,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(photo == null){
			ret.put("type", "error");
			ret.put("msg", "选择要上传的文件！");
			return ret;
		}
		if(photo.getSize() > 1024*1024*1024){
			ret.put("type", "error");
			ret.put("msg", "文件大小不能超过10M！");
			return ret;
		}
		//获取文件后缀
		String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".")+1,photo.getOriginalFilename().length());
		if(!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())){
			ret.put("type", "error");
			ret.put("msg", "请选择jpg,jpeg,gif,png格式的图片！");
			return ret;
		}
		String savePath = request.getServletContext().getRealPath("/") + "/resources/upload/";
		File savePathFile = new File(savePath);
		if(!savePathFile.exists()){
			//若不存在改目录，则创建目录
			savePathFile.mkdir();
		}
		String filename = new Date().getTime()+"."+suffix;
		try {
			//将文件保存至指定目录
			photo.transferTo(new File(savePath+filename));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			ret.put("type", "error");
			ret.put("msg", "保存文件异常！");
			e.printStackTrace();
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "用户上传成功！");
		ret.put("filepath",request.getServletContext().getContextPath() + "/resources/upload/" + filename );
		return ret;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(@RequestParam(name="id[]",required=false,defaultValue="") String id){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(id)){
			ret.put("type", "error");
			ret.put("msg", "选择要删除的数据！");
			return ret;
		}
		if(id.contains(",")){
			id = id.substring(0,id.length()-1);
		}
		if(userService.delete(id) <= 0){
			ret.put("type", "error");
			ret.put("msg", "用户删除失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "用户删除成功！");
		return ret;
	}
	
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(User user){
		Map<String, String> ret = new HashMap<String, String>();
		if(user == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的用户信息！");
			return ret;
		}
		if(StringUtils.isEmpty(user.getUsername())){
			ret.put("type", "error");
			ret.put("msg", "请填写用户名！");
			return ret;
		}
//		if(StringUtils.isEmpty(user.getPassword())){
//			ret.put("type", "error");
//			ret.put("msg", "请填写密码！");
//			return ret;
//		}
		if(user.getRoleId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属角色！");
			return ret;
		}
		if(isExist(user.getUsername(), user.getId())){
			ret.put("type", "error");
			ret.put("msg", "该用户名已经存在，请重新输入！");
			return ret;
		}
		if(userService.edit(user) <= 0){
			ret.put("type", "error");
			ret.put("msg", "用户修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "角色修改成功！");
		return ret;
	}
	//日志管理layui
	
	@RequestMapping(value="/loglist",method=RequestMethod.GET)
	public ModelAndView loglist(ModelAndView model){
		model.setViewName("layui/log");
		return model;
	}
	@RequestMapping(value="/log",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(Page page,
			@RequestParam(name="content",required=false,defaultValue="") String content
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("content", content);
		queryMap.put("offset", page.getLayoffset());
		queryMap.put("pageSize", page.getLimit());
		ret.put("data", logService.findList(queryMap));
		ret.put("count", logService.getTotal(queryMap));
		ret.put("msg", "");
		ret.put("code", 0);
		return ret;
	}
	
	@RequestMapping(value="/logadd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> logadd(Log log){
		Map<String, String> ret = new HashMap<String, String>();
		if(log == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的日志信息！");
			return ret;
		}
		if(StringUtils.isEmpty(log.getContent())){
			ret.put("type", "error");
			ret.put("msg", "请填写日志内容！");
			return ret;
		}
		log.setCreateTime(new Date());
		if(logService.add(log) <= 0){
			ret.put("type", "error");
			ret.put("msg", "日志添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "日志添加成功！");
		return ret;
	}
	
	
	@RequestMapping(value="/logdelete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> logdelete(@RequestParam(name="id[]",required=false,defaultValue="") String id){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(id)){
			ret.put("type", "error");
			ret.put("msg", "选择要删除的数据！");
			return ret;
		}
		if(id.contains(",")){
			id = id.substring(0,id.length()-1);
		}
		if(logService.delete(id) <= 0){
			ret.put("type", "error");
			ret.put("msg", "日志删除失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "日志删除成功！");
		return ret;
	}
	
	//分类管理layui
	@RequestMapping(value="/newscategorylist",method=RequestMethod.GET)
	public ModelAndView newscategorylist(ModelAndView model){
		model.setViewName("layui/news_category");
		return model;
	}
	
	@RequestMapping(value="/newscategory",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(name="name",required=false,defaultValue="") String name,Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("offset", page.getLayoffset());
		queryMap.put("pageSize", page.getLimit());
		ret.put("data", newsCategoryService.findList(queryMap));
		ret.put("count", newsCategoryService.getTotal(queryMap));
		ret.put("msg", "");
		ret.put("code", 0);
		return ret;
	}
	
	@RequestMapping(value="/newscategoryadd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(NewsCategory newsCategory){
		Map<String, String> ret = new HashMap<String, String>();
		if(newsCategory == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的分类信息！");
			return ret;
		}
		if(StringUtils.isEmpty(newsCategory.getName())){
			ret.put("type", "error");
			ret.put("msg", "请填写分类名！");
			return ret;
		}
		if(newsCategoryService.add(newsCategory) <= 0){
			ret.put("type", "error");
			ret.put("msg", "分类添加失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}
	

	@RequestMapping(value="/newscategoryedit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> edit(NewsCategory newsCategory){
		Map<String, String> ret = new HashMap<String, String>();
		if(newsCategory == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的分类信息!");
			return ret;
		}
		if(StringUtils.isEmpty(newsCategory.getName())){
			ret.put("type", "error");
			ret.put("msg", "请填写分类名！");
			return ret;
		}

		if(newsCategoryService.edit(newsCategory) <= 0){
			ret.put("type", "error");
			ret.put("msg", "分类修改失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "分类修改成功！");
		return ret;
	}
	

	@RequestMapping(value="/newscategorydelete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> newscategorydelete(@RequestParam(name="id[]",required=false,defaultValue="") String id){
		Map<String, String> ret = new HashMap<String, String>();
		if(id==null){
			ret.put("type", "error");
			ret.put("msg", "选择要删除的数据！");
			return ret;
		}
		try {
			if(newsCategoryService.delete(id) <= 0){
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
	
	
	//博客管理layui
	@RequestMapping(value="/newslist",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.addObject("newsCategoryList", newsCategoryService.findAll());
     	model.setViewName("layui/news");
		return model;
	}

	@RequestMapping(value="/news",method=RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> getList(
				@RequestParam(name="title",required=false,defaultValue="") String title,
				@RequestParam(name="author",required=false,defaultValue="") String author,
				@RequestParam(name="categoryId",required=false,defaultValue="") Long categoryId,
				Page page
				){
			Map<String, Object> ret = new HashMap<String, Object>();
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("title", title);
			queryMap.put("author", author);
			if(categoryId!=null&&categoryId.longValue()!=-1) {
				queryMap.put("categoryId", categoryId);
			}
			queryMap.put("offset", page.getLayoffset());
			queryMap.put("pageSize", page.getLimit());
			ret.put("data", newsService.findList(queryMap));
			ret.put("count", newsService.getTotal(queryMap));
			ret.put("msg", "");
			ret.put("code", 0);
			return ret;
		}
	
	@RequestMapping(value="/newsadd",method=RequestMethod.GET)
	public ModelAndView add(ModelAndView model){
		model.addObject("newsCategoryList", newsCategoryService.findAll());
		model.setViewName("layui/newsadd");
		return model;
	}
	
	@RequestMapping(value="/newsadd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> add(News news){
		Map<String,String> ret = new HashMap<String, String>();
		if(news == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的信息！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getTitle())){
			ret.put("type", "error");
			ret.put("msg", "新闻标题不能为空！");
			return ret;
		}
		if(news.getCategoryId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择新闻分类！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getAbstrs())){
			ret.put("type", "error");
			ret.put("msg", "新闻摘要不能为空！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getTags())){
			ret.put("type", "error");
			ret.put("msg", "新闻标签不能为空！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getPhoto())){
			ret.put("type", "error");
			ret.put("msg", "新闻封面图片必须上传！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getAuthor())){
			ret.put("type", "error");
			ret.put("msg", "新闻作者不能为空！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getContent())){
			ret.put("type", "error");
			ret.put("msg", "新闻内容不能为空！");
			return ret;
		}
		news.setCreateTime(new Date());
		if(newsService.add(news)<=0) {
			ret.put("type", "error");
			ret.put("msg", "添加失败，请联系管理员");
		}
		ret.put("type", "success");
		ret.put("msg", "添加成功！");
		return ret;
	}
	
	@RequestMapping(value="/newsedit",method=RequestMethod.GET)
	public ModelAndView edit(ModelAndView model,Long id) {
		model.addObject("newsCategoryList", newsCategoryService.findAll());
		model.addObject("news", newsService.find(id));
		model.setViewName("layui/newsedit");
		return model;
	}
	
	@RequestMapping(value="/newsedit",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> edit(News news){
		Map<String,String> ret = new HashMap<String, String>();
		if(news == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的信息！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getTitle())){
			ret.put("type", "error");
			ret.put("msg", "新闻标题不能为空！");
			return ret;
		}
		if(news.getCategoryId() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择新闻分类！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getAbstrs())){
			ret.put("type", "error");
			ret.put("msg", "新闻摘要不能为空！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getTags())){
			ret.put("type", "error");
			ret.put("msg", "新闻标签不能为空！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getPhoto())){
			ret.put("type", "error");
			ret.put("msg", "新闻封面图片必须上传！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getAuthor())){
			ret.put("type", "error");
			ret.put("msg", "新闻作者不能为空！");
			return ret;
		}
		if(StringUtils.isEmpty(news.getContent())){
			ret.put("type", "error");
			ret.put("msg", "新闻内容不能为空！");
			return ret;
		}
		if(newsService.edit(news)<=0) {
			ret.put("type", "error");
			ret.put("msg", "修改失败，请联系管理员");
		}
		ret.put("type", "success");
		ret.put("msg", "修改成功！");
		return ret;
	}
	
	@RequestMapping(value="/newsdelete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> newsdelete(@RequestParam(name="id[]",required=false,defaultValue="") String id){
		Map<String,String> ret=new HashMap<String,String>();
		if(id==null) {
			ret.put("type", "error");
			ret.put("msg", "请选择要删除的博客");
			return ret;
		}
		try {
			if(newsService.delete(id)<=0) {
				ret.put("type", "error");
				ret.put("msg", "删除失败，请联系管理员");
				return ret;
			}
		} catch (Exception e) {
			ret.put("type", "error");
			ret.put("msg", "该博客下有评论，不能删除！");
			return ret;	
		}
		
		ret.put("type", "success");
		ret.put("msg", "删除成功！");
		return ret;
	}
	
	//评论layui
	@RequestMapping(value="/commentlist",method=RequestMethod.GET)
	public ModelAndView commentlist(ModelAndView model){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("offset", 0);
		queryMap.put("pageSize",99);
		model.addObject("newsList", newsService.findList(queryMap));
		model.setViewName("layui/comment");
		return model;
	}

	@RequestMapping(value="/comment",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getList(
			@RequestParam(name="nickname",required=false,defaultValue="") String nickname,
			@RequestParam(name="content",required=false,defaultValue="") String content,
			Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("nickname", nickname);
		queryMap.put("content", content);
		queryMap.put("offset", page.getLayoffset());
		queryMap.put("pageSize", page.getLimit());
		ret.put("data", CommentService.findList(queryMap));
		ret.put("count", CommentService.getTotal(queryMap));
		ret.put("msg", "");
		ret.put("code", 0);
		return ret;
	}
	
	@RequestMapping(value="/commentdelete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> commentdelete(@RequestParam(name="id[]",required=false,defaultValue="") String id){
		Map<String, String> ret = new HashMap<String, String>();
		if(id==null){
			ret.put("type", "error");
			ret.put("msg", "选择要删除的数据！");
			return ret;
		}
		if(id.contains(",")) {
			id=id.substring(0,id.length()-1);
		}
		if(CommentService.delete(id) <= 0){
			ret.put("type", "error");
			ret.put("msg", "删除失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功！");
		return ret;
	}
	
	//资源layui
	@RequestMapping(value="/resourcelist",method=RequestMethod.GET)
	public ModelAndView resourceslist(ModelAndView model){
		model.setViewName("layui/resource");
		return model;
	}
	
	@RequestMapping(value="/resource",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> resources(
			@RequestParam(name="name",required=false,defaultValue="") String name,Page page
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("name", name);
		queryMap.put("offset", page.getLayoffset());
		queryMap.put("pageSize", page.getLimit());
		ret.put("data", resourcesService.findList(queryMap));
		ret.put("count", resourcesService.getTotal(queryMap));
		ret.put("msg", "");
		ret.put("code", 0);
		return ret;
	}
	

	@RequestMapping(value="/resourceadd",method=RequestMethod.POST)
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
	

	@RequestMapping(value="/resourceedit",method=RequestMethod.POST)
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
	

	@RequestMapping(value="/resourcedelete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> resourcedelete(@RequestParam(name="id[]",required=false,defaultValue="")String id){
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
	
	private boolean isExist(String username,Long id){
		User user = userService.findByUsername(username);
		if(user == null)return false;
		if(user.getId().longValue() == id.longValue())return false;
		return true;
	}
}
