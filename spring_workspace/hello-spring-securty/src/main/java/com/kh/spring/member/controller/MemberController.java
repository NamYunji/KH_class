package com.kh.spring.member.controller;

import java.beans.PropertyEditor;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/member")
// 이제 sessionAttributes 필요 없음
// @SessionAttributes("loginMember")
public class MemberController {

	@GetMapping("/memberLogin.do")
	public void memberLogin() {}
	
	// 이제 post요청은 필요 없음
//	@PostMapping("/memberLogin.do")
//	public void memberLogin_() {}
	
	/**
	 * Authentication의 구성요소
		- Principal : 인증된 사용자객체
		- Credentials : 인증에 필요한 비밀번호
		- Authorities : 인증된 사용자가 가진 권한
	 */
	// 방법1. security context holder bean으로 부터 직접 가져오기
//	@GetMapping("/memberDetail.do")
//	public void memberDetail(Model model) {
		// SecurityContextHolder로부터 getContext -> securityContext
		// securityContext로 부터 getAuthentication() -> authentication
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// principal - authentication으로 부터 getPrincipal
		// credentials = authentication.getCredentials()
		// authorities - authentication.getAuthorities()
//		Member principal = (Member) authentication.getPrincipal();
//		model.addAttribute("loginMember", principal);
//		log.debug("authentication = {}", authentication);
//		log.debug("principal = {}", principal);
//	}
	
	// 방법2. handler mapping한테 security 인증된 사용자 authentication 요청하기
	@GetMapping("/memberDetail.do")
	public void memberDetail(Authentication authentication, Model model) {
		Member principal = (Member) authentication.getPrincipal();
		model.addAttribute("loginMember", principal);
		
		log.debug("authentication = {}", authentication);
		// authentication = org.springframework.security.authentication.UsernamePasswordAuthenticationToken@23abe407: Principal: Member(id=honggd, password=$2a$10$qHHeJGgQ9teamJyIJFXbyOBtl7nIsQ37VP2jrz89dnDA7LgzS.nYi, name=카길동, gender=M, birthday=2021-05-04, email=honggd@naver.com, phone=01012341234, address=서울시 강남구, hobby=[운동,  등산], enrollDate=2021-05-20, authorities=[ROLE_USER], enabled=true); Credentials: [PROTECTED]; Authenticated: true; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@166c8: RemoteIpAddress: 0:0:0:0:0:0:0:1; SessionId: B95C1041773474D93729781512D4490A; Granted Authorities: ROLE_USER
		log.debug("principal = {}", principal);
	}
	
	@PostMapping("memberUpdate.do")
	// authentication을 하나 받아와서 oldAuthentication으로 설정해두기
	public String memberUpdate(
				Member updateMember, 
				Authentication oldAuthentication, 
				RedirectAttributes redirectAttr) {
		// 수정된 member객체는 updateMember에 담겨있음
		log.debug("updateMember = {}", updateMember);
		
		// 1. 업무로직 : db의 member객체를 수정
		// 기존의 mvc흐름대로 진행 (생략)
		
		// 2. SecurityContext의 authentication객체를 수정
		// 기존의 oldAuthentication객체를 대체할 새로운 authentication객체 만들기
		// principal, credentials, authorities를 전달해서 새로 authentication만들기

		// updateMember에 누락된 정보 password, authorities 추가
		// updateMember에는 수정한 정보만 가지고 있을 뿐 온전한 member객체가 아님
		// 필수적으로 필요한 password, authorities는 누락된 상태
		// password(credentials), authorities를 채워서 온전한 principal로 전달해야 함
		
		// credentials, authorities는 oldAuthentication에서 가져오기
		// 1) 패스워드 oldAuthentication.getCredentials()
		updateMember.setPassword(((Member) oldAuthentication.getPrincipal()).getPassword());
		// getCredentials는 내부적으로 값비교할때만 사용되고 가져올 수가 없기 때문에 principal에서 가져오기
		
		// 2) 권한 oldAuthentication.getAuthorities()
		Collection<? extends GrantedAuthority> oldAuthorities = oldAuthentication.getAuthorities();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		// forEach를 통해 authority 하나씩 꺼내오기
		for(GrantedAuthority auth : oldAuthorities) {
			// SimpleGrantedAuthority 객체 생성
			// SimpleGrantedAuthority : GrantedAuthority의 자식 클래스. 문자열을 인자로 받음
			// auth.getAuthority() - 문자열을 인자로 auth객체 생성
			SimpleGrantedAuthority simpleAuth = new SimpleGrantedAuthority(auth.getAuthority());
			authorities.add(simpleAuth);
		}
		updateMember.setAuthorities(authorities);
		
		// principal -> updateMember
		// credentials -> oldAuthentication.getCredentials()
		// authorities -> oldAuthentication.getAuthorities()
		Authentication newAuthentication = 
				new UsernamePasswordAuthenticationToken(
							updateMember,
							oldAuthentication.getCredentials(),
							oldAuthentication.getAuthorities()
				);
		// newAuthentication객체를 securityContextHolder의 securityContext 하위에 setting
		SecurityContextHolder.getContext().setAuthentication(newAuthentication);
		
		// 3. 사용자 피드백 & 리다이렉트
		return "redirect:/member/memberDetail.do";
	}
	/**
	 * 커맨드객체 이용시 사용자입력값(String)을 특정필드타입으로 변환할 editor객체를 설정
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//Member.birthday:java.sql.Date 타입 처리
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//커스텀에디터 생성 : allowEmpty - true (빈문자열을 null로 변환처리 허용)
		PropertyEditor editor = new CustomDateEditor(sdf, true);
		binder.registerCustomEditor(java.sql.Date.class, editor);
	}
	
}
