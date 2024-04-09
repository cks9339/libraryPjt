package com.goodee.library.book.controller;



import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;

@Controller
public class BookViewController {
	@Autowired
	BookService service;
	
	private static final Logger LOGGER = LogManager.getLogger(BookViewController.class);

	@GetMapping("/book")
	public String bookListb(BookDto dto,Model model) {
		LOGGER.info("도서 목록화면 이동");
		dto.setTotalData(service.selectBookCount(dto.getB_name()));
		List<BookDto> resultList = service.selectBookList(dto);
		model.addAttribute("resultList", resultList);
		model.addAttribute("paging",dto);
		return "book/list";
	}
	
	@GetMapping("/book/add")
	public String createBook() {
		
		LOGGER.info("도서 등록 화면 이동");
		return "book/add";	
	}
	
	@GetMapping("/book/{b_no}")
	public String detail(@PathVariable("b_no") long b_no,Model model) {
		LOGGER.info("책번호 :"+b_no);
		
		// 1. b_no 정보 사용표시
		// 2. b_no 기준 bookDto 정보 조회
		BookDto dto = service.selectBookDetail(b_no);
		
		// 3. edip.jsp 에 bookDto 정보 전달 ( key 도 bookDto)
		model.addAttribute("bookDto", dto);
		return "book/edit";
	}

}
