package com.kh.spring.member.controller;

import java.beans.PropertyEditor;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

/**
 * Model
 * MVC패턴의 Model이 아님
 * view단에 전달하기 위한 데이터를 저장하는 map
 * 
 * - Request scope, Session scope 등을 model이라는 추상체 하나로 관리
 * - Controller에서 생성된 data를 jsp에 전달하기 위한 용도
 * 
 * 핸들러에 요청하면 핸들러 매핑이 알아서 객체화해서 제공해줌
 * 1. Model <<interface>> 
 * 	- 데이터를 넣는 메서드 : addAttribute(name, value)
 * 	- viewName : String을 리턴
 * 2. ModelMap
 * 	- Model 인터페이스의 구현체
 *  - 데이터를 넣는 메서드 : addAttribute(name, value)
 * 	- viewName : String을 리턴
 * ----------------------------------------------
 * 3. ModelAndView
 *  - Model과 viewName을 하나로 합쳐놓은 것
 *  - 데이터를 넣는 메서드 : addObject(name, value)
 *  - setViewName()을 이용해서 viewName설정
 *  - ModelAndView객체 자체를 리턴
 * 
 * -> 무엇을 사용하든 결국은 ModelAndView로 통합돼서 spring container에 의해 관리됨
 * 
 * [모델에 저장된 속성에 대한 getter]
 * - @ModelAttribute : request scope에 있는 속성을 가져오기
 * 	  사용법1. 핸들러의 매개변수 앞에 사용
 * 	  사용법2. 메소드 레벨에 작성
 * - @SessionAttribute : session scope에 있는 속성을 가져오기
 * - name, required(기본값 : true) 지정이 가능하다.
 * 
 * - @ModelAttribute의 사용법2.
 * 	 메소드레벨에 작성
 *   이 메소드안에서 model속성을 setter로 사용
 *   현재 컨트롤러 클래스의 모든 핸들러에 앞서 실행되며, 모든 요청에서 해당 데이터에 접근 가능
 * 
 */
@Controller
@Slf4j // log기능을 이용할 수 있는 어노테이션 (lombok)
@RequestMapping("/member")
@SessionAttributes({"loginMember", "next", "msg"}) // 여러개 적을수 있음
public class MemberController {

	// 클래스레벨에 @Slf4j 어노테이션으로 사용
	// private static final Logger log = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;
	
	// 암호화 처리
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	/** 메소드레벨에 작성하는 @ModelAttribute("namespace지정")
	 * view단에서는 ${common.adminEmail}, ${common.adminPhone}으로 사용가능 
	 * ${namespace.key값)으로 사용하기
	 */
	@ModelAttribute("common")
	public Map<String, Object> common() {
		log.info("@ModelAttribute(\"common\")");
		// 공통속성을 map으로 세팅
		Map<String, Object> map = new HashMap<>();
		map.put("adminEmail", "admin@kh.or.kr");
		map.put("adminPhone", "070-1234-5678");
		// map으로 리턴하면 @ModelAttribute로 인해 model속성에 담김
		return map;
	}
	
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
	// @RequestHeader를 통해 Referer를 가져옴, referer가 없는 경우를 대비해 required는 false로 설정
	public void memberLogin(
			// 아이디가 틀렸을 경우를 위해 next를 가져옴
			@SessionAttribute(required = false) String next,
			@RequestHeader(name= "Referer", required = false) String referer, 
			Model model) {
		log.info("referer = {}", referer);
		// dev목록에서 로그인 시
		// INFO : com.kh.spring.member.controller.MemberController - referer = http://localhost:9090/spring/demo/devList.do
		// url로 직접접근 시
		// INFO : com.kh.spring.member.controller.MemberController - referer = null
		// next가 존재할 때는 next를 referer로 덮어쓰지 않도록 next가 null이 아닐 경우의 조건 추가
		if(next == null && referer != null)
		model.addAttribute("next", referer); // sessionScope에 저장
	}
	
	@PostMapping("/memberLogin.do")
	public String memberLogin(
			@RequestParam String id, 
			@RequestParam String password, 
			@SessionAttribute(required = false) String next,// 세션에 있는 속성을 가져오는 어노테이션
			Model model, 
			RedirectAttributes redirectAttr
		) {
		// 1. 업무로직
		Member member = memberService.selectOneMember(id);
		log.info("member = {}", member);
		// 평문으로 된 비밀번호를 업데이트하기 위해 암호화된 비밀번호 얻어오기
		log.info("encryptedPassword = {}", bcryptPasswordEncoder.encode(password));
		// INFO : com.kh.spring.member.controller.MemberController - encryptedPassword = $2a$10$JcKgiKrshI1TXWwlm5ru8eybzawC8xM13vn6i6ksPTsSestNTUhXy
		// 콘솔에 찍힌 암호화된 비밀번호를 복사해서 sql에서 각각 업데이트 진행
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
			// 성공했을 때만 사용한 next값은 제거
			model.addAttribute("next", null);
		} else {
			// 로그인 실패
			redirectAttr.addFlashAttribute("msg", "아이디 또는 비밀번호가 틀립니다.");
			// 다시 로그인하도록
			return "redirect:/member/memberLogin.do";
		}
		// return "redirect:/";
		return "redirect:" + (next != null? next : "/");
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
		// 사용하고 있던 세션객체 자체를 버리는 게 아닌, 관련된 속성만 무효화처리 하는 것
		// 객체 재사용 가능, 로그아웃한다고 해서 jsessionid가 바뀌지 않음
		// 로그아웃 시 인덱스페이지로 리다이렉트 
		return "redirect:/";
	}
	
	/**
	 * 로그인한 사용자 정보 열람하기
	 */
	@GetMapping("/memberDetail.do")
	// @SessioinAttribute(name = "loginMember") Member loginMember
	// = @SessionAttribute Member loginMember
	// name속성을 적지 않으면 자동으로 필드명에서 유추해서 필드명과 같은 속성을 가져옴
	/* [Model 사용시]
	public String memberDetail(@SessionAttribute(name = "loginMember") Member loginMember) {
		// session에 저장된 속성은 jsp에서 바로 사용할 수 있으나
		// 여기서도 이렇게 꺼내서 볼 수 있다는 것을 알아두기
		log.info("loginMember = {}", loginMember);
		return "member/memberDetail";
	}
	*/
	// [ModelAndView사용시]
	// 1. ModelAndView를 리턴타입으로 적기
	// 2. 인자로 ModelAndView를 쓴다고 요청
	// 3. addObject()를 통해 속성 저장
	// 4. setViewName()을 통해 viewName 설정
	// 5. ModelAndView 리턴
	// (+ jsp에서 꺼내쓰기) : ${속성명} (ex. ${time})
	public ModelAndView memberDetail(ModelAndView mav, @SessionAttribute(name = "loginMember") Member loginMember) {
		log.info("loginMember = {}", loginMember);
		// 속성 저장
		mav.addObject("time", System.currentTimeMillis());
		// viewName 설정
		mav.setViewName("member/memberDetail");
		// mav 리턴
		return mav;
	}
	
	@PostMapping("/memberUpdate.do")
	public ModelAndView updateMember(
								@ModelAttribute Member member, 
								// 같은 타입이면 command객체와 동일한 필드는 바로 loginMember에 반영해줌 -> session에 바로 반영됨
								@ModelAttribute("loginMember") Member loginMember,
								ModelAndView mav, 
								HttpServletRequest request
								) {
		log.debug("member = {}", member);
		log.debug("loginMember = {}", loginMember);
		try {
			// 1. 업무로직
			int result = memberService.updateMember(member);
			// 2. 사용자 피드백 & 리다이렉트
			
			// 리다이렉트 방식1. 문자열로 viewName 지정
			// url 뒤에 next값이 붙음
			// mav.setViewName("redirect:/member/memberDetail.do");
			
			// 리다이렉트 방식2. view객체로 viewName 지정
			// 리다이렉트시 자동생성되는 quertyString 방지
			RedirectView view = new RedirectView(request.getContextPath() + "/member/memberDetail.do");
			view.setExposeModelAttributes(false); // false로 지정!
			mav.setView(view);
			
			// ModelAndView와 RedirectAttributes 충돌 시 flashMap을 직접 사용
			// redirectAttr.addFlashAttribute("msg", "회원정보 수정 성공!");
			FlashMap flashMap = RequestContextUtils.getOutputFlashMap(request);
			flashMap.put("msg", "사용자 정보 수정 성공!!");
		} catch (Exception e) {
			log.error("member 수정 오류", e);
			throw e;
		}
		return mav;
	}
	
	/**
	 * spring ajax을 이용하는 방법 (json을 리턴함)
	 * 1. gson - 응답메시지에 json문자열을 직접 출력 (servlet, jsp에서 사용해본 방식)
	 * 2. jsonView 빈을 통해 처리 -> model에 담긴 data를 json으로 변환, 응답에 출력
	 * 	   우리는 model에 data만 담아두고, jsonView에게 처리를 맡기기만 하면 됨
	 * 3. @responseBody - 리턴된 자바객체를 json으로 변환, 응답에 출력
	 * 	   우리는 어노테이션만 붙이고 리턴만 하면 됨
	 * 	   (@ResponseBody : 리턴된 객체를 응답메시지에 직접 출력해주는 역할)
	 * 4. ResponseEntity<응답에 작성할 자바객체>
	 * 	   ex. ResponseEntity<Map<String, Object>>
	 */
	/**
	 * jsonView 방식
	 */
	@GetMapping("/checkIdDuplicate1.do")
	public String checkIdDuplicate1(@RequestParam String id, Model model) {
		// 1. 업무로직
		// 저 아이디로 기존회원이 있는가 확인
		Member member = memberService.selectOneMember(id);
		// member가 null인지의 여부를 변수에 담아둠 (null이어야 true)
		boolean available = member == null ;
		
		// 2. Model에 속성 저장
		model.addAttribute("available", available);
		model.addAttribute("id", id);
		
		return "jsonView";
	}
	/**
	 * @responseBody 방식
	 * 그냥 java객체를 리턴하면 json으로 변환하고 응답에 출력해줌
	 */
	@GetMapping("/checkIdDuplicate2.do")
	@ResponseBody
	public Map<String, Object> checkIdDuplicate2(@RequestParam String id) {
		// 1. 업무로직
		// 저 아이디로 기존회원이 있는가 확인
		Member member = memberService.selectOneMember(id);
		// member가 null인지의 여부를 변수에 담아둠 (null이어야 true)
		boolean available = member == null ;
		
		// 2. map에 요소 저장 후 리턴
		// model필요 없음
		Map<String, Object> map = new HashMap<>();
		map.put("available", available);
		map.put("id", id);

		return map;
		// model이 아닌 리턴한 map을 변환한 것!
	}
	/**
	 * ResponseEntity<응답에 작성할 자바객체> 방식
	 */
	@GetMapping("/checkIdDuplicate3.do")
	// ResponseEntity에서 처리해주기 때문에 responseBody 필요없음
	// ResponseEntity의 요소로 Map을 리턴
	public ResponseEntity<Map<String, Object>> checkIdDuplicate3(@RequestParam String id) {
		// 1. 업무로직
		// 저 아이디로 기존회원이 있는가 확인
		Member member = memberService.selectOneMember(id);
		// member가 null인지의 여부를 변수에 담아둠 (null이어야 true)
		boolean available = member == null ;
		
		// 2. map에 요소 저장 후 리턴
		// model필요 없음
		Map<String, Object> map = new HashMap<>();
		map.put("available", available);
		map.put("id", id);

		// ResponseEntity객체를 만들어서 전달
		return ResponseEntity
				.ok() // 응답헤더 200번
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE) // "application/json;charset=UTF-8" -> header값으로 json이라는 것을 알림
				.body(map); // body에 map담기
	}
}

