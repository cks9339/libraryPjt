package com.goodee.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;

@Controller
public class HomeController {

	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
	@Autowired BookService bookService;
	
	
	@GetMapping({"","/"})
	public String home(Model model) {
		LOGGER.info("도서관 관리 시스템 시작");
		
		List<BookDto> resultList = new ArrayList<>();
		resultList = bookService.selectBooklistToday();
//		for(BookDto dto : resultList) {
//			LOGGER.info(dto.getB_name());
//		}
		model.addAttribute("newBookList", resultList);
		return "home";
	}
	
}
