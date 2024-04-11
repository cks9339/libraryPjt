package com.goodee.library.book.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.book.controller.BookApiController;
import com.goodee.library.book.dao.BookDao;
import com.goodee.library.book.dto.BookDto;

@Service
public class BookService {
	@Autowired BookDao bookDao;
	
	private static final Logger LOGGER = LogManager.getLogger(BookApiController.class);
	
	public Map<String, String> createBook(BookDto dto) {
		LOGGER.info("CreateBook 접근");
		Map<String, String> map = new HashMap<String,String>();
		int row = bookDao.createBook(dto);
		
		if(row>0) {
			map.put("res_code", "200");
			map.put("res_msg", "등록 완료");
		}else {
			map.put("res_code", "404");
			map.put("res_msg", "등록 실패");
		}
		return map;
	}

	public int selectBookCount(String b_name) {
		int result = 0 ; 
		result = bookDao.selectBookCount(b_name);
		return result;
	}

	public List<BookDto> selectBookList(BookDto dto) {
		return bookDao.selectBookList(dto);
	}

	public List<BookDto> selectBooklistToday() {
		LOGGER.info("오늘 등록된 도서 전체 조회 요청");
		
		return bookDao.selectBookListToday();
	}

	public BookDto selectBookDetail(long b_no) {
		
		return bookDao.selectBookDetail(b_no);
	}

	public int editBook(BookDto dto) {
		
		return bookDao.editBook(dto);
	}

	public int deleteBook(long b_no) {
		
		return bookDao.deleteBook(b_no);
	}

}
