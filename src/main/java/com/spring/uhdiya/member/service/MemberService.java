package com.spring.uhdiya.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import com.spring.uhdiya.member.dao.MemberDAO;
import com.spring.uhdiya.member.dto.MemberDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class MemberService {
	@Autowired
	private MemberDAO memberDAO;
	
	public List<MemberDTO> memberList(){
		return memberDAO.memberList();
	}

	public void insertMember(MemberDTO member) {
		// TODO Auto-generated method stub
		memberDAO.insertMember(member);
	}

	public Map<String, String> validateHandling(Errors errors) {
		 Map<String, String> validatorResult = new HashMap<>();

	     for (FieldError error : errors.getFieldErrors()) {
	            String validKeyName = String.format("valid_%s", error.getField());
	            validatorResult.put(validKeyName, error.getDefaultMessage());
	     }

	     return validatorResult;
	}
	
	public MemberDTO login(MemberDTO member) {
		// TODO Auto-generated method stub
		return memberDAO.login(member);
	}

	public int modMember(@Valid MemberDTO memberDTO) {
		return memberDAO.modMember(memberDTO);
		
	}

	public MemberDTO selectOne(String id) {
		return memberDAO.selectOne(id);
	}
	
	
	
	
}