package com.ischoolbar.programmer.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



/***
 * 博客留言控制器
 *
 */
@RequestMapping("/admin/note")
@Controller
public class NoteController {	
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		model.setViewName("note/list");
		return model;
	}
}
