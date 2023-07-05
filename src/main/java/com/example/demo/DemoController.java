package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;

@Controller
public class DemoController {
	
	@Autowired
	UserRepository repos;
	
	@GetMapping("/")
	public ModelAndView index (
			@ModelAttribute("formModel") User user,
			ModelAndView mav) {
		 mav.setViewName("index");
		 Iterable<User> list = repos.findAll();
		 mav.addObject("data", list);
		 return mav;
	}
	
	@PostMapping("/")
	@Transactional
	public ModelAndView form(
			@ModelAttribute("formModel") User user,
			ModelAndView mav) {
		System.out.println(user.getsName());
		repos.saveAndFlush(user);
		return new ModelAndView("redirect:/");
	}
	
	@PostConstruct
	public void init() {
        //初期データ作成
        User user1 = new User();
        user1.setsName("島根　花子");
        repos.saveAndFlush(user1);

        user1 = new User();
        user1.setsName("大阪　太郎");
        repos.saveAndFlush(user1);
    }
}
