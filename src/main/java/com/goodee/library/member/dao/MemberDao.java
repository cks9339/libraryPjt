package com.goodee.library.member.dao;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.goodee.library.member.dto.MemberDto;
import com.goodee.library.member.service.MemberService;

@Repository
public class MemberDao {
	
	@Autowired private SqlSession sqlSession;
	@Autowired PasswordEncoder passwordEncoder;
	
	private final String namespace = "com.goodee.library.memberMapper.";
	
	private static final Logger LOGGER = LogManager.getLogger(MemberService.class);
	
	public int idDoubleCheck(String m_id) {
		LOGGER.info("아이디 중복 검사");
		// 1. 정수형 변수 result 선언
		int result = 0;
		// 2. try catch 문 구성
		try {
			result = sqlSession.selectOne(namespace+"idDoubleCheck",m_id);
			LOGGER.info("dao 결과확인" + result);
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			LOGGER.error("아이디 중복 검사시 에러 발생" + errors.toString());
		}
		// 3. try문 에서 mybatis로 데이터 조회 요청 (idDoubleCheck)
		// 4. 파라미터 m_id
		// 5. 수행 결과를 return
		return result;
	}

	public int createMember(MemberDto dto) {
		LOGGER.info("회원정보 데이터베이스 추가");
		int result = 0;
		
		try {
			dto.setM_pw(passwordEncoder.encode(dto.getM_pw()));
			result = sqlSession.insert(namespace+"createMember",dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public MemberDto selectMemberOne(MemberDto dto) {
		LOGGER.info("아이디 기준 멤버 조회");
		MemberDto loginedDto = new MemberDto();
		
		try {
			loginedDto = sqlSession.selectOne(namespace+"selectMemberOne",dto.getM_id());
			if(loginedDto != null) {
				// 비밀번호 일치 여부 확인
				if(!passwordEncoder.matches(dto.getM_pw(), loginedDto.getM_pw())) {
					loginedDto = null;
				}else {
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loginedDto;
	}

	public List<MemberDto> selectMemberAll() {
		LOGGER.info("회원 목록 정보 전체 조회");
		List<MemberDto> resultList = new ArrayList<>();
		try {
			resultList = sqlSession.selectList(namespace+"selectMemberAll");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public int updateMember(MemberDto dto) {
		LOGGER.info("회원 업데이트");
		int result = 0;
		try {
			result = sqlSession.update(namespace+"updateMember",dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public MemberDto selectMno(long m_no) {
		LOGGER.info("다시 찾기");
		MemberDto selectResult = new MemberDto();
		try {
			selectResult = sqlSession.selectOne(namespace+"selectMno",m_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectResult;
	}

	public int deleteMember(long m_no) {
		LOGGER.info("회원 탈퇴");
		int result = 0;
		try {
			result = sqlSession.update(namespace+"deleteMember",m_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
