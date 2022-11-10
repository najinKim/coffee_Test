package com.spring.uhdiya.member.dao;

import java.util.List;

import javax.validation.Valid;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.uhdiya.member.dto.MemberDTO;

@Repository
public class MemberDAO {
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<MemberDTO> memberList(){
		return sqlSessionTemplate.selectList("mapper.member.memberList");
	}

	public void insertMember(MemberDTO member) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert("mapper.member.insertMember", member);
	}

	public MemberDTO login(MemberDTO member) {
		
		
		MemberDTO memberDTO = sqlSessionTemplate.selectOne("mapper.member.loginById", member);
		return memberDTO;
	}

	public int modMember(@Valid MemberDTO memberDTO) {
		int result = sqlSessionTemplate.update("mapper.member.updateMember", memberDTO);
		return result;
	}

	public MemberDTO selectOne(String id) {
		MemberDTO member = (MemberDTO) sqlSessionTemplate.selectOne("mapper.member.selectMemberById", id);
		return member;
	}
	
	
	
}