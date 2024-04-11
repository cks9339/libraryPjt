package com.goodee.library.book.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.book.dto.BookDto;

@Repository
public class BookDao {
	@Autowired SqlSession sqlSession;
	private final String namespace = "com.goodee.library.bookMapper.";
	public int createBook(BookDto dto) {
		int result = 0;
		try {
			result =sqlSession.insert(namespace+"createBook",dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int selectBookCount(String b_name) {
		int result = 0;
		try {
			result = sqlSession.selectOne(namespace+"selectBookCount",b_name);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public List<BookDto> selectBookList(BookDto dto) {
		List<BookDto> resultList = new ArrayList<BookDto>();
		try {
			resultList = sqlSession.selectList(namespace+"selectBookList",dto);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultList;	
	}
	public List<BookDto> selectBookListToday() {
		List<BookDto> resultList = new ArrayList<>();
		try {
			resultList = sqlSession.selectList(namespace+"selectBookListToday");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return resultList;
	}
	public BookDto selectBookDetail(long b_no) {
		BookDto dto = new BookDto();
		try {
			dto = sqlSession.selectOne(namespace+"selectBookDetail",b_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return dto;
	}
	public int editBook(BookDto dto) {
		int row = 0 ;
		try {
			row = sqlSession.update(namespace+"editBook",dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	public int deleteBook(long b_no) {
		int row = 0;
		try {
			row = sqlSession.delete(namespace+"deleteBook",b_no);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

}
