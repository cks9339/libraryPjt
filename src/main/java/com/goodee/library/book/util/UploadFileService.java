package com.goodee.library.book.util;

import java.io.File;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.book.controller.BookApiController;

@Service
public class UploadFileService {
	
	private static final Logger LOGGER = LogManager.getLogger(UploadFileService.class);
	
	public String upload(MultipartFile file) {
		LOGGER.info("파일 서버에 저장");
		boolean result = false; // 판단의 기준점
		
		
		// book01.jpg
		String origin = file.getOriginalFilename();
		String extension = origin.substring(origin.lastIndexOf("."),origin.length());
		UUID uuid = UUID.randomUUID();
		String change = uuid.toString().replaceAll("-", "");
		
		File savedFile = new File("C:\\library\\upload\\"+change+extension);
		if(!savedFile.exists()) {
			savedFile.mkdirs();
		}
		try {
			file.transferTo(savedFile);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result) {
			return change+extension;
		}else {
			return null;
		}

		
	}

	public boolean delete(String b_thumbnail) {
		// TODO Auto-generated method stub
		return true;
	}
	
	
}
