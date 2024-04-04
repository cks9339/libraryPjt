package com.goodee.library.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.library.member.controller.MemberApiController;
import com.goodee.library.member.dao.MemberDao;
import com.goodee.library.member.dto.MemberDto;

@Service
public class MemberService {
	
	@Autowired MemberDao dao;
	private static final Logger LOGGER = LogManager.getLogger(MemberService.class);
	
	public Map<String, String> createMember(MemberDto dto) {
		
		LOGGER.info("회원가입 결과 처리");
		Map<String, String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다");
		// 1. dao 에게 data insert 요청
		int result = 0;
		if(dao.idDoubleCheck(dto.getM_id())>0) {
			// 회원가입 x
			map.put("res_code", "409");
			map.put("res_msg", "중복된 아이디 입니다.");
		}else {
			result = dao.createMember(dto);
			if(result>0) {
				// 회원 가입 성공
				map.put("res_code", "200");
				map.put("res_msg", "회원가입 성공입니다.");
			}
		}
		// 2. insert 결과를 가지고 Map 데이터 재구성
		
		return map;
	}

	public Map<String, String> loginMember(MemberDto dto, HttpSession session) {
		LOGGER.info("로그인 결과 처리");
		Map<String, String> map = new HashMap<String,String>();
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다");

		// dao 에게 memberDto 정보를 전달
		MemberDto loginedMember = dao.selectMemberOne(dto);
		if(loginedMember != null) {
			session.setAttribute("loginedMember", loginedMember);
			session.setMaxInactiveInterval(60*30);
			// 로그인 성공 시점
			map.put("res_code", "200");
			map.put("res_msg", loginedMember.getM_name()+"님 환영합니다");
			

			
		}
		// dao 가 데이터베이스에서 로그인 성공한 회원정보를 return
		// 로그인 성공 회원정보가 null 이아니면 res_code 200
		return map;
	}

	public List<MemberDto> selectMemberAll() {
		LOGGER.info("회원 목록 조회 요청");
		// dao 에게 List<MemberDto> 정보 전달 받아와서 컨트롤러에게 return
		return dao.selectMemberAll();
	}

	public Map<String, String> updateMember(MemberDto dto, HttpSession session) {
		Map<String, String> map = new HashMap<String,String>();
		
		map.put("res_code", "404");
		map.put("res_msg", "오류가 발생했습니다.");
		
		int result = 0;
		result = dao.updateMember(dto);
		if(result >0) {
			MemberDto selectUpdateMno = dao.selectMno(dto.getM_no());
			session.setAttribute("loginedMember", selectUpdateMno);
			session.setMaxInactiveInterval(60*30);
			
			map.put("res_code","200");
			map.put("res_msg",selectUpdateMno.getM_name()+"님 수정완료" +"이메일:"+selectUpdateMno.getM_mail());
		}
		LOGGER.info("result 값 " + result);
		
		return map;
	}

}


