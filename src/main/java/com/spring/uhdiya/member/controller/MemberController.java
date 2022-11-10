package com.spring.uhdiya.member.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.validation.Errors;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.uhdiya.member.dto.MemberDTO;
import com.spring.uhdiya.member.service.MemberService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/member/memberList")
	public String memberList(Model model) {
		List<MemberDTO> memberList = memberService.memberList();
		
		model.addAttribute("memberList", memberList);
		
		return "/member/memberList";
	}
	
	// 그냥 회원가입페이지
	@RequestMapping("/member/insertForm")
	public String dispSignup(MemberDTO memberDTO) {
		return "/member/insertForm";
	}
	// 회원가입
	@PostMapping(value="/member/insertMember")
	public String insertMember(@Valid MemberDTO memberDTO, Errors errors, Model model,HttpServletRequest request, HttpServletResponse response)throws Exception {
		if (errors.hasErrors()) {
			// 회원가입 실패시, 입력 데이터를 유지
			model.addAttribute("memberDTO", memberDTO);
			// 유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult = memberService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			return "/member/insertForm";
		}
		System.out.println("유효성검사 통과");
		model.addAttribute("result","회원가입성공. 로그인페이지로 이동합니다.");
		memberService.insertMember(memberDTO); // 오류났을경우를 대비해 boolean으로 수정하기.
		return "/member/login";
	}
	
	// 그냥 로그인페이지 접근
	@RequestMapping("/member/login")
	public String login() {
		return "/member/login";
	}
	// 로그인
	@PostMapping(value="/member/login")
	public ModelAndView login(@ModelAttribute("member") MemberDTO member, Model model,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		MemberDTO memberDTO = memberService.login(member);
		
		if(memberDTO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", memberDTO);
			session.setAttribute("isLogOn", true);
			String str = memberDTO.getName();
			model.addAttribute("result",str+"님 환영합니다. 정상적으로 로그인되셨습니다.");
			mav.setViewName("/index");
		} else {
			model.addAttribute("result","로그인 실패. 아이디 또는 비밀번호를 확인해주세요.");
		}
		return mav;
	} 
	
	@RequestMapping(value="/member/logout", method=RequestMethod.GET)
	public String logout( Model model,HttpServletRequest request, HttpServletResponse resposne) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		session.invalidate();
		
		model.addAttribute("result","정상적으로 로그아웃되었습니다.");
		return "/index";
	}
	
	
	// 그냥 정보수정페이지
	@RequestMapping("/member/modMember")
	public String dispModMember(@ModelAttribute("member") MemberDTO memberDTO,Model model,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HttpSession session = request.getSession();
		memberDTO = (MemberDTO) session.getAttribute("member");
		
		memberDTO=memberService.selectOne(memberDTO.getId());
		model.addAttribute("memberDTO",memberDTO);
		
		return "/member/modMember";
	}
	// 정보수정
	@PostMapping(value="/member/modMember")
	public String modMember(@Valid MemberDTO memberDTO, Errors errors, Model model,HttpServletRequest request, HttpServletResponse response)throws Exception {
		if (errors.hasErrors()) {
			// 정보수정실패시 실패시, 입력 데이터를 유지
			model.addAttribute("memberDTO", memberDTO);
			// 유효성 통과 못한 필드와 메시지를 핸들링
			Map<String, String> validatorResult = memberService.validateHandling(errors);
			for (String key : validatorResult.keySet()) {
				model.addAttribute(key, validatorResult.get(key));
			}
			return "/member/modMember";
		}
		System.out.println("유효성검사 통과");
		int result = memberService.modMember(memberDTO);
		model.addAttribute("result","정상적으로 수정되었습니다. 메인페이지로 이동합니다.");
		return "/index";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}