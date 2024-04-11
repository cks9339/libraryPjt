package com.goodee.library.book.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.book.dto.BookDto;
import com.goodee.library.book.service.BookService;
import com.goodee.library.book.util.UploadFileService;
import com.goodee.library.member.controller.MemberApiController;

@Controller
public class BookApiController {
	@Autowired UploadFileService uploadFileService;
	@Autowired BookService bookService;
	private static final Logger LOGGER = LogManager.getLogger(BookApiController.class);
	
	// 1. post방식
	// 2. url은 /book
	// 3. dto , Multipartfile 객체
	// 4. json(RequestBody 어노테이션x (온전히 json형태로 받는게 아니기떄문) @RequestParam으로)
	@ResponseBody
	@PostMapping("/book")
	public Map<String, String> createBook(BookDto dto , @RequestParam("file") MultipartFile file){
		LOGGER.info("도서 등록 기능");
		// UploadFileService에 Upload 메소드 구성
		// return 업로드 된 파일명
		Map<String, String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "도서 등록중 오류가 발생 했습니다.");
		
		String savedFileName = uploadFileService.upload(file);
		
		if(savedFileName != null) {
			map.put("res_msg", "파일 업로드는 성공 했습니다.");
			dto.setB_thumbnail(savedFileName);
			map = bookService.createBook(dto);
		}else {
			map.put("res_msg", "파일 업로드중 오류가 발생 했습니다.");
		}
		return map;
	}
	
	@ResponseBody
	@PostMapping("/book/{b_no}")
	public Map<String, String> editBook(BookDto dto, @RequestParam("file") MultipartFile file){
		Map<String, String> map = new HashMap<String,String>();
		LOGGER.info("bookEdit접근");
		map.put("res_code", "404");
		map.put("res_msg", "도서 수정중 오류가 발생했습니다.");
		
		if("".equals(file.getOriginalFilename()) == false) {
			// 1. 새로운 파일을 서버에 업로드
			String savedFileName = uploadFileService.upload(file);
			// 2. 정상적으로 업로드 되었다면 기존 파일 삭제
			if(savedFileName != null) {
				if(uploadFileService.delete(dto.getB_thumbnail())) {
					// 정상 삭제
					dto.setB_thumbnail(savedFileName);
				}else {
					map.put("res_msg", "기존 파일 삭제가 실패하였습니다.");
				}

			}else {
				map.put("res_msg", "파일 업로드중 오류가 발생 했습니다.");
			}
			// 3. b_thumbnail 정보를 수정


		}
		// 4. 도서 정보 수정
		// b_thumbnail 여부에 따라 쿼리가 달라짐.
		int row = bookService.editBook(dto);
		if(row>0) {
			map.put("res_code", "200");
			map.put("res_msg", "수정 성공");
		}
		return map;
	}
	
	// 도서정보삭제
	@ResponseBody
	@DeleteMapping("/book/{b_no}")
	public Map<String, String> delete(@PathVariable long b_no , BookDto dto){
		Map<String, String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "수정 실패");
		dto = bookService.selectBookDetail(b_no);
		LOGGER.info("dto 정보확인 :"+dto.getB_thumbnail());
		int row = 0;
		row = bookService.deleteBook(b_no);
		
		if(row>0) {
			if(uploadFileService.delete(dto.getB_thumbnail())){
				map.put("res_code", "200");
				map.put("res_msg", "삭제 성공");
			}
			
		}
		
		return map;
	}
	// 1. 데이터베이스에서삭제
	// 2. 파일도 서버에서 삭제
	// 3. 코드 수행결과를 프론트에게 전달
	
}
