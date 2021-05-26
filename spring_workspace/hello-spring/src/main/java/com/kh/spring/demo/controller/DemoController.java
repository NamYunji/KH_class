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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.demo.model.service.DemoService;
import com.kh.spring.demo.model.validator.DevValidator;
import com.kh.spring.demo.model.vo.Dev;

/**
 * 사용자 요청 하나당 이를 처리하는 controller의 메소드(Handler)가 하나씩 존재
 * cf. handler : 컨트롤러의 사용자 요청을 처리하는 메소드
 * Controller 하나, Handler 여러개 
 */

/**
* [핸들러 메소드가 가질 수 있는 매개변수]
* HttpServletRequest
* HttpServletResponse
* HttpSession (request.getSession하지 않아도, 현재 요청이 사용하고 있는 session객체가 자동으로 넘어옴)
* java.util.Locale : 요청에 대한 Locale(위치정보)
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
* 
* @ModelAttribute : model속성에 대한 getter
* @SessionAttribute : session속성에 대한 getter
* SessionStatus: @SessionAttributes로 등록된 속성에 대하여 사용완료(complete)처리
* 
* Command객체 : http요청 파라미터를 커맨드객체에 저장한 VO객체
* @valid 커맨드객체 유효성 검사용
* Error, BindingResult : Command객체에 저장결과, Command객체 바로 다음위치시킬것.
* 기타
* MultipartFile : 업로드파일 처리 인터페이스. CommonsMultipartFile
* RedirectAttributes : DML처리후 요청주소 변경을 위한 redirect를 지원
* ...
*/
/**
 * @RequestMapping
 * 모든 핸들러 메소드는 /demo로 시작함
 * 클래스 레벨에 공통된 부분을 requestMapping으로 작성함
 */
@Controller // bean으로 등록함(@component를 상속했으므로)과 동시에 controller역할까지 해주는 어노테이션
@RequestMapping("/demo")
public class DemoController {
	
	/**
	 * org.slf4j.Logger
	 * spring용 logging클래스
	 * 필드로 등록
	 * private static final Logger log = LoggerFactory.getLogger(현재클래스.class);
	 * 사용법
	 * log.info("문자열 입력");
	 * console 출력
	 * INFO : 풀네임 클래스명 - 로깅 메시지
	 */
	private static final Logger log = LoggerFactory.getLogger(DemoController.class);
	
	@Autowired // 의존주입
	private DemoService demoService;
	
	/**
		handler : 컨트롤러 클래스에서 사용자 요청을 처리하는 메소드
		보통 return값은 String임
		handler로 사용하기 위해서는 rquestMapping 어노테이션 반드시 필요
	*/
	@RequestMapping("/devForm.do") // @RequestMapping("연결주소") : 이 handler 메소드를 어느 요청 주소와 연결할지
	public String devForm() {
		log.info("/demo/devForm.do 요청!"); // INFO : com.kh.spring.demo.controller.DemoController - /demo/devForm.do 요청!
		System.out.println("demo/devForm.do 요청!"); // demo/devForm.do 요청!
		// jsp 위임
		// viewResolver 빈 -> /WEB-INF/views + demo/devForm + .jsp  -> jsp파일로 위임
		return "demo/devForm";
	}
	
	@RequestMapping("/dev1.do")
	public String dev1(HttpServletRequest request, HttpServletResponse response) {
		// request, response객체를 쓰려면 인자로 적어줌
		// 1. 사용자 입력값 처리
		String name = request.getParameter("name");
		int career = Integer.valueOf(request.getParameter("career"));
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String[] lang = request.getParameterValues("lang"); // 여러개가 넘어오기 때문에 배열로 담아둠
		
		Dev dev = new Dev(0, name, career, email, gender, lang);
		// log.info 사용법
		// 객체를 { } 안에 문자열로 출력
		log.info("dev = {}", dev);
		// INFO : com.kh.spring.demo.controller.DemoController - dev = Dev(no=10, name=신사, career=10, email=ss@naver.com, gender=F, lang=[C, Javascript])
		
		// 2. 업무로직
		
		// 3. jsp에 출력
		// 메소드 인자로 request, response를 넘겼기 때문에 관련 메소드들 이전과 똑같이 사용 가능
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
			// 1. 사용자 입력값 처리
			/**
			 * @RequestParam 자료형 변수명
			 * String name = request.getParameter("name"); 와 같은 효과
			 * 핸들러 메소드의 인자에 작성
			 * name값과 일치하는 매개변수에 전달
			 * 사용자 입력값을 받아와서 변수에 담고, 설정한 자료형에 맞게 형변환까지 해줌
			 * 1) name 속성
			 * 	(name="전달된 name값")
			 * 	전달된 name값이 매개변수와 일치하지 않는다면, name="전달된 name값" 작성
			 * 	(name속성값이 매개변수명보다 우선순위가 높음)
			 * 	ex. 전달된 name값이 userName인데, 사용할 매개변수가 name이라면
			 * 		@RequestParam(name="userName") String name
			 * 		-> 사용자 name값인 userName이 넘어와서 name매개변수에 담김
			 * 2) required 속성
			 * 	(required = true|false)
			 * 	기본값 : true -> 값이 넘어오지 않을 경우 오류
			 * 	false -> 값이 넘어오지 않아도 오류가 나지 않음
			 *  -> 사용자가 선택적으로 입력하는 필드는 false로 명시할 것
			 * 	ex. radio나 checkbox의 경우 빈문자열로도 넘어가지 않기 때문에 오류가 남
			 * 		required = false 속성을 추가하면 오류나지 않음
			 * 3) defaultValue 속성
			 *  값이 없거나 형변환 오류가 생길 때의 기본값을 지정
			 *  (defaultValue = "기본값 지정") - 문자열로 감싸서 작성할 것 
			 * 	ex. input:number 태그에 아무내용도 쓰지 않고 넘길 시 0이 아닌 공란이 넘어감
			 * 		@RequestParam int career -> 사용자 입력값을 String으로 받아서 int로 정수 형변환됨
			 * 		but 빈문자열은 숫자로 변경 불가 -> 오류 발생
			 * 		MethodArgumentTypeMismatchException:Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: ""] 
			 * 		defaultValue = "1" 속성 추가 -> 오류나지 않고 기본값으로 정상처리 
			 * 
			*/
			@RequestParam String name,
			@RequestParam(defaultValue = "1") int career,
			@RequestParam String email,
			@RequestParam String gender,
			@RequestParam(required = false) String[] lang,
			Model model
		) {
		Dev dev = new Dev(career, name, career, email, gender, lang);
		log.info("dev = {}", dev);
		// INFO : com.kh.spring.demo.controller.DemoController - dev 
		// = Dev(no=10, name=신사, career=10, email=ss@naver.com, gender=F, lang=[C, Python])
		
		// 2. jsp에 위임
		// spring에서는 jsp에 정보를 전달하기 위해서 request가 아닌 model을 사용함
		model.addAttribute("dev", dev); // jsp에서 기본적으로 scope="request"에 저장되어 있음.
		// request.setAttribute()와 같은 효과
		return "demo/devResult";
		
		// 기본값을 지정해야 해결 가능
		// 값이 없거나 문제가 생기면 기본값은 1
		// WARN :  org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver - Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'int'; nested exception is java.lang.NumberFormatException: For input string: ""]
	}
	
	/**
	 * @ModelAttribute Dev dev
	 * (vo객체가 있을 경우에만 사용 가능)
	 * 
	 * 매개변수 Dev객체를 command객체라고 부름
	 * cf. command pattern : 하나씩 짝지어주는 패턴
	 *     ex. ctrl + c -> copy, ctrl + v -> paste, ctrl + x -> cut
	 * 	   (명령어) 이걸 누르면 이걸 실행해라 -> mapping : 하나씩 찾아서 연결시킨 것
	 * 	   이걸 command pattern이라고 부름
	 * 
	 * [Dev의 구조]
	 * int no
	 * String name
	 * int career
	 * String email
	 * String[] lang
	 * 
	 * [이번 요청]
	 * name = 홍길동
	 * career = 10
	 * email = hgd@naver.com
	 * lang = java & lang = python
	 * 
	 * [spring container의 역할]
	 * 해당 name과 동일한 이름을 가진 필드에 각각의 값을 짝지어줌
	 * ex. name : 홍길동 - name필드, career : 10 - career필드
	 * -> 연결시켜준다 -> command pattern -> command 객체
	 * 
	 * @ModelAttribute
	 * 모델에 등록된 속성을 가져오는 어노테이션
	 * Dev객체는 handler도착 전에 model에 등록되어 있음
	 * 	(이전에는 model에 addAttribute해서 사용했으나, 이제는 담지 않아도 jsp에서 command객체 사용 가능)
	 * 	-> dev라는 이름으로 객체가 담겨서 jsp로 넘어감
	 * command객체 앞 @ModelAttributes는 생략이 가능하다
	 */
	/**
	 * @RequestMapping의 속성
	 * [value속성]
	 * : url 매핑
	 * 대표속성 -> 속성을 제시하지 않고, 바로 ""안에 사용 가능
	 * 
	 * [method속성]
	 * : 해당 핸들러의 메소드 제한
	 * 생략 - 모든 메소드 처리
	 * RequestMethod.POST - post요청만 처리
	 * RequestMethod.GET - get요청만 처리
	 * {} - String 배열의 형태로 여러 메소드 작성 가능
	 * 
	 */
	@RequestMapping(value = "/dev3.do", method = RequestMethod.POST) 
	// public String dev3(@ModelAttribute Dev dev) {
	public String dev3(Dev dev) {
		log.info("dev = {}", dev);
		return "demo/devResult";
	}
	
	/**
	 * 서버측 유효성 검사
	 * Validator인터페이스를 구현해서 dev객체에 대한 유효성 검사 진행
	 * 
	 * @Valid
	 * : 커맨드객체 유효성 검사용
	 * 
	 * BindingResult
	 * : 오류 발생시 에러에 담아둔 것을 확인 가능
	 * Error, BindingResult : Command객체에 저장결과, Command객체 바로 다음 위치 시킬것
	 * bindingResult.hasErrors() - 에러가 담겨있는지 확인하는 메소드 - boolean형 리턴
	 * bindingResult.getAllErrors() - 모든 에러들을 가져오는 메소드 - 복수개 리턴
	 * err.getDefaultMessage() - 에러 메시지 가져오기
	 * err.getCode() - 에러 코드 가져오기
	 */
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
	
	// validator객체를 사용하기 위해서는 initBinder메소드 필요
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new DevValidator());
	}
	
	/**
	 * RedirectAttributes
	 */
	@RequestMapping(value = "insertDev.do", method = RequestMethod.POST)
	public String insertDev(@ModelAttribute Dev dev, RedirectAttributes redirectAttr) {
		log.info("dev = {}", dev);
		try {
			// 1. 업무로직
			int result = demoService.insertDev(dev);
			
			// 2. 사용자 피드백 & 리다이렉트
			// session scope에 저장 후 한번 쓰고 지워주는 기능까지 처리해줌 -> jsp에서 remove할 필요 없음
			// request.getSession().setAttribute()와 같은 역할
			redirectAttr.addFlashAttribute("msg", "dev 등록 성공!");
		} catch(Exception e) {
			// error 기록 남기기 : log.error("오류메시지", e)
			// e.printStackTrace()와 같은 효과
			log.error("dev 등록 오류!", e);
			throw e;
		}
		// 리다이렉트
		// redirect:요청주소
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
	
//	@RequestMapping(value = "/demo/updateDev.do", method = RequestMethod.GET)
	// requestMapping기능을 가진 get메소드 전용 어노테이션
	// @RequestMapping 어노테이션을 이용해서 method = RequestMethod.GET하여 적는 것과 동일함
	@GetMapping("/updateDev.do")
	public String updateDev(Model model, @RequestParam(name = "no", required = true) int no) { 
		try {
			// 1. 업무로직
			Dev dev = demoService.selectOneDev(no);
			if (dev == null)
					throw new IllegalArgumentException("존재하지 않는 개발자 정보 : " + no);
			log.info("dev = {}", dev);
			// 2. jsp위임
			model.addAttribute("dev", dev);
		} catch(Exception e) {
			log.error("Dev 수정페이지 오류!", e);
			throw e;
		}
		return "demo/devUpdateForm";
	}
	
	// 인자가 다르므로 메소드 오버로딩 -> get방식과 메소드명이 같아도 ok
//	@RequestMapping(value = "/demo/updateDev.do", method = RequestMethod.POST)
	// requestMapping기능을 가진 post메소드 전용 어노테이션
	// @RequestMapping 어노테이션을 이용해서 method = RequestMethod.POST하여 적는 것과 동일함
	@PostMapping("/updateDev.do")
	public String updateDev(@ModelAttribute Dev dev, RedirectAttributes redirectAttr) {
		log.info("수정요청 dev = {}", dev);
		try {
			// 1. 업무로직
			int result = demoService.updateDev(dev);
			if(result == 0)
				throw new IllegalArgumentException("존재하지 않는 개발자 정보 : " + dev.getNo());
			// 2. 사용자피드백 & 리다이렉트
			redirectAttr.addFlashAttribute("msg", "개발자 정보 수정 성공!");
		} catch(Exception e) {
			log.error("개발자 정보 수정 오류!", e);
			throw e;
		}
			return "redirect:/demo/devList.do";
	}
	
	@PostMapping("/deleteDev.do")
	public String deleteDev(@RequestParam int no, RedirectAttributes redirectAttr) {
		try {
			// 1. 업무로직
			int result = demoService.deleteDev(no);
			if (result == 0)
				throw new IllegalArgumentException("존재하지 않는 개발자 정보 : " + no);
			// 2. 사용자피드백 & 리다이렉트
			redirectAttr.addFlashAttribute("msg", "개발자 정보 삭제 성공!");
		} catch (Exception e) {
			log.error("게빌자 정보 삭제 오류!", e);
			throw e;
		}
		return "redirect:/demo/devList.do";
	}
}
