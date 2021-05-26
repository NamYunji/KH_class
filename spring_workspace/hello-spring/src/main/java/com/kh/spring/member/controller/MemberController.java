package com.kh.spring.member.controller;

import java.beans.PropertyEditor;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j // log기능을 이용할 수 있는 어노테이션 (lombok)
@RequestMapping("/member")
@SessionAttributes({"loginMember"}) // 여러개 적을수 있음
public class MemberController {

	// 클래스레벨에 @Slf4j 어노테이션으로 사용
	// private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;
	
	// 암호화 처리
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	// 리턴타입을 void로 작성
	@GetMapping("/memberEnroll.do")
	public void memberEnroll() {
		// viewName을 지정하지 않아도 /WEB-INF/views/member/memberEnroll.jsp로 자동포워딩
		// DefaultRequestToViewNameTranslator bean이 요청주소에서 viewName을 유추함
		// /member/memberEnroll.do : 클래스레벨과 핸들러의 requestMapping에서 요청주소를 가져옴
		// member/memberEnroll : 요청주소에서 맨 앞의 /(슬래시)와 마지막의 확장자(.do)를 빼고 viewName을 만듦
		// cf. 만약 요청주소와 jsp가 다르면 직접 명시해야함
	}
	
	@PostMapping("/memberEnroll.do")
	public String memberEnroll(@ModelAttribute Member member, RedirectAttributes redirectAttr) {
		log.info("member = {}", member);
		try {
			// 0. 비밀번호 암호화처리
			String rawPassword = member.getPassword();
			String encodedPassword = bcryptPasswordEncoder.encode(rawPassword);
			// member에 암호화된 비밀번호 다시 세팅
			member.setPassword(encodedPassword);
			log.info("member(암호화처리 이후) = {}", member);
			// 1. 업무로직
			int result = memberService.insertMember(member);
			// 2. 사용자피드백 및 리다이렉트
			redirectAttr.addFlashAttribute("msg", "회원가입성공");
			// redirect:/ - 인덱스페이지(welcome file)로 이동
			// welcome file로 바로 찾게 되면 redirectAttr을 처리할 수 없음
		} catch (Exception e) {
			log.error("회원가입 오류!", e);
			throw e;
		}
		return "redirect:/";
	}
	
	/**
	 * java.sql.Date, java.util.Date 필드에 값대입시
	 * 사용자 입력값이 ""인 경우, null로 처리될 수 있도록 설정
	 * @param binder
	 */
	// initBinder - 커맨드 객체 관련 설정을 담당
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// [특정 타입에 대해 형변환해주는 editor를 등록]
		// 1. editor에서 필요한 형식 지정
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 2. editor객체 생성
		// CustomDateEditor(DateFormat dateFormat, boolean allowEmpty)
		// allowEmpty여부를 true로 바꾸기 : 빈문자열이 들어오는 것을 허용함
		PropertyEditor editor = new CustomDateEditor(format, true);
		// 3. binder에 editor 등록
		// 형변환이 필요하다면 이 editor를 사용하라
		binder.registerCustomEditor(Date.class, editor);
	}
	
	@GetMapping("/memberLogin.do")
	public void memberLogin() {}
	
	@PostMapping("/memberLogin.do")
	public String memberLogin(
			@RequestParam String id, 
			@RequestParam String password, 
			Model model, 
			RedirectAttributes redirectAttr
		) {
		// 1. 업무로직
		Member member = memberService.selectOneMember(id);
		log.info("member = {}", member);
		// 2. 로그인 여부 분기처리
		// BCryptPasswordEncoder.matches(CharSequence rawPassword, String encodedPassword)
		// : 사용자가 입력한 비밀번호와 암호화된 비밀번호의 일치여부를 판단해줌
		if(member != null && bcryptPasswordEncoder.matches(password, member.getPassword())) {
			// 로그인 성공
			// -> member객체를 session에 저장해두기 -> 로그인 상태 유지
			// model은 request에도, session에도 모두 속성 저장 가능
			// 기본값이 request여서 session으로 저장하려면 추가 설정 필요
			// loginMember를 세션속성으로 저장하려면, class레벨에 @SessionAttributes로 등록(이 이름은 세션에 저장하세요)
			model.addAttribute("loginMember", member);
		} else {
			// 로그인 실패
			redirectAttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 틀립니다.");
		}
		return "redirect:/";
	}
	
	/**
	 * @SessionAttributes를 통해서 등록한 session속성은
	 * SessionStatus객체에 대해서 사용완료(complete) 처리해야 한다.
	 * (session invalidate가 아님)
	 */
	@GetMapping("/memberLogout.do")
	public String memberLogout(SessionStatus status) {
		// 세션이 끝나지 않았을 때만
		if(!status.isComplete())
			status.setComplete(); // 세션을 다 썼어요! 하는 것. session 속성을 지우도록 허락
		// 로그아웃 시 인덱스페이지로 리다이렉트 
		return "redirect:/";
	}
	
}