package com.goodee.library.member.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import com.goodee.library.HomeController;

@Controller
public class MemberViewController {
	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
	
	@GetMapping("/join")
	public String openJoin() {
		LOGGER.info("회원가입 화면 이동");
		return "member/join";
	}
	
	@GetMapping("/login")
	public String openLogin() {
		LOGGER.info("로그인 화면 이동");
		return "member/login";
		
	}
	
	
	
	
}
