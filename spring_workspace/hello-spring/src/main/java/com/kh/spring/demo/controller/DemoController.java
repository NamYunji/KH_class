package com.kh.spring.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.demo.model.service.DemoService;
import com.kh.spring.demo.model.validator.DevValidator;
import com.kh.spring.demo.model.vo.Dev;

/**
 * 사용자 요청 하나당 이를 처리하는 controller의 메소드(Handler)가 하나씩 존재
 * cf. handler : 컨트롤러의 요청을 처리하는 메소드
 * Controller 하나, Handler 여러개
 */

/**
* HttpServletRequest
* HttpServletResponse
* HttpSession
* java.util.Locale : 요청에 대한 Locale
* InputStream/Reader : 요청에 대한 입력스트림
* OutputStream/Writer : 응답에 대한 출력스트림. ServletOutputStream, PrintWriter
* @PathVariable: 요청url중 일부를 매개변수로 취할 수 있다.
* @RequestParam
* @RequestHeader
* @CookieValue
* @RequestBody
  뷰에 전달할 모델 데이터 설정
* ModelAndView
* Model
* ModelMap 
* @ModelAttribute : model속성에 대한 getter
* @SessionAttribute : session속성에 대한 getter
* SessionStatus: @SessionAttribute로 등록된 속성에 대하여 사용완료(complete)처리
* Command객체 : http요청 파라미터를 커맨드객체에 저장한 VO객체
* Error, BindingResult : Command객체에 저장결과, Command객체 바로 다음위치시킬것.
  기타
* MultipartFile : 업로드파일 처리 인터페이스. CommonsMultipartFile
* RedirectAttributes : DML처리후 요청주소 변경을 위한 redirect를 지원
*
*/
@Controller
@RequestMapping("/demo")
public class DemoController {
	
	/**
	 * spring용 logging클래스
	 */
	private static final Logger log = LoggerFactory.getLogger(DemoController.class);
	
	@Autowired
	private DemoService demoService;
	
	// 사용자 요청을 처리하는 handler
	// handler로 사용하기 위해서는 rquestMapping 어노테이션 반드시 필요
	@RequestMapping("/devForm.do")
	public String devForm() {
		log.info("/demo/devForm.do 요청!"); // INFO : com.kh.spring.demo.controller.DemoController - /demo/devForm.do 요청!
		System.out.println("demo/devForm.do 요청!"); // demo/devForm.do 요청!
		// viewResolver 빈에 의해서 /WEB-INF/views + demo/devForm + .jsp  -> jsp파일로 위임
		return "demo/devForm";
	}
	
	@RequestMapping("/dev1.do")
	public String dev1(HttpServletRequest request, HttpServletResponse response) {
		//1. 사용자 입력값 처리
		String name = request.getParameter("name");
		int career = Integer.valueOf(request.getParameter("career"));
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String[] lang = request.getParameterValues("lang");
		
		Dev dev = new Dev(0, name, career, email, gender, lang);
		log.info("dev = {}", dev);
		
		//2. 업무로직
		
		//3. jsp에 출력
		request.setAttribute("dev", dev);
		
		return "demo/devResult";
	}
	
	/**
	 * name값과 일치하는 매개변수에 전달.
	 * 1. name값(userName)이 매개변수(name)와 일치하지 않는다면, name="userName" 지정
	 * 	(name속성값이 매개변수명보다 우선순위가 높음)
	 * 2. required="true" (기본값) 사용자가 선택적으로 입력하는 필드는 false로 명시할 것
	 * 3. defaultValue를 지정한 경우, 값이 없거나, 형변환 오류가 발생해도 기본값으로 정상처리된다.
	 */
	@RequestMapping("/dev2.do")
	public String dev2(
			// @RequestParam(name="userName") String name,
			@RequestParam String name,
			@RequestParam(defaultValue= "1") int career,
			@RequestParam String email,
			@RequestParam String gender,
			@RequestParam(required = false) String[] lang,
			Model model
		) {
		Dev dev = new Dev(career, name, career, email, gender, lang);
		log.info("dev = {}", dev);
		// INFO : com.kh.spring.demo.controller.DemoController - dev 
		// = Dev(no=10, name=신사, career=10, email=ss@naver.com, gender=F, lang=[C, Python])
		
		// jsp에 위임
		model.addAttribute("dev", dev); // jsp에서 scope="request"에 저장되어 있음.
		// request.setAttribute()와 같은 효과
		return "demo/devResult";
		
		// 기본값을 지정해야 해결 가능
		// 값이 없거나 문제가 생기면 기본값은 1
		// WARN : org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver - Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: ""]
	}
	
	/**
	 * 매개변수 Dev객체를 command객체라고 부름
	 * cf. command pattern : 하나씩 짝지어주는 패턴
	 *     ex. ctrl + c -> copy, ctrl + v -> paste, ctrl + x -> cut
	 * 	   이걸 누르면 이걸 실행해라 -> mapping 하나씩 찾아서 연결시킨 것
	 * 	   이걸 command pattern이라고 부름
	 * 
	 * Dev의 구조
	 * int no
	 * String name
	 * int career
	 * String email
	 * String[] lang
	 * 
	 * 이번 요청은 form을 제출했는데,
	 * name = 홍길동
	 * career = 10
	 * email = hgd@naver.com
	 * lang = java & lang = python
	 * 
	 * 동일한 이름을 가진 필드에 홍길동은 name필드, 10은 career필드 이렇게 각각의 값을 짝지어줌
	 * -> command 객체
	 * 
	 * @ModelAttribute 모델에 등록된 속성을 가져오는 애노테이션
	 * Dev객체는 handler도착 전에 model에 등록되어 있음을 의미
	 * command객체 앞 @ModelAttributes는 생략이 가능하다
	 * 
	 * @Valid 커맨드객체 유효성 검사용
	 * Error, BindingResult : Command객체에 저장결과, Command객체 바로 다음 위치 시킬것
	 */
	@RequestMapping(value = "/dev3.do", method = {RequestMethod.POST})
	public String dev3(Dev dev) {
	// public String dev3(@ModelAttribute Dev dev) {
		log.info("dev = {}", dev);
		return "demo/devResult";
	}
	
	@RequestMapping(value = "/dev4.do", method = RequestMethod.POST)
	public String dev4(@Valid Dev dev, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			String errors = "";
			List<ObjectError> errorList = bindingResult.getAllErrors();
			for(ObjectError err : errorList) {
				errors += "{" + err.getCode() + ":" + err.getDefaultMessage() + "} ";
			}
			throw new IllegalArgumentException(errors);
		}
		
		return "demo/devResult";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new DevValidator());
	}
	
	@RequestMapping(value = "insertDev.do", method = RequestMethod.POST)
	public String insertDev(@ModelAttribute Dev dev, RedirectAttributes redirectAttr) {
		log.info("dev = {}", dev);
		
		try {
			// 1. 업무로직
			int result = demoService.insertDev(dev);
			
			// 2. 사용자 피드백 & 리다이렉트
			// session scope에 저장 후 한번 쓰고 지워주는 기능까지 처리해줌
			redirectAttr.addFlashAttribute("msg", "dev 등록 성공!");
		} catch(Exception e) {
			log.error("dev 등록 오류!", e);
			throw e;
		}
		return "redirect:/demo/devList.do";
	}
	
	@RequestMapping(value = "/devList.do", method = RequestMethod.GET)
	public String devList(Model model) {
		//1. 업무로직
		List<Dev> list = demoService.selectDevList();
		log.info("list = {}", list);
		log.info("1234567890");		
		//2. jsp위임
		model.addAttribute("list", list);
		return "demo/devList";
	}
	
}
