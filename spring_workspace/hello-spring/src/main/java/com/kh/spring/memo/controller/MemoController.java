package com.kh.spring.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.demo.model.vo.Dev;
import com.kh.spring.memo.model.service.MemoService;
import com.kh.spring.memo.model.vo.Memo;

import lombok.extern.slf4j.Slf4j;

/**
 * 의존주입받은 객체는 우리가 작성한 
 * MemoController, MemoServiceImpl, MemoDaoImpl타입 객체가 아닌
 * proxy객체이다.
 *
 * controller - proxy - service
 * proxy :
 * Controller에서 Service의 메소드를 호출하려면,
 * field로 Service객체를 가지고 있는데
 * 이 field가 ServiceImpl이 아니라, 스프링이 만든 proxy객체였음
 * 
 * 우리는 proxy에 있는 동일한 이름의 메소드를 호출한 것이고,
 * 그 내부에서 ServiceImpl의 메소드를 대신 호출해줌
 * 
 * -> proxy : 대리인 역할을 함
 * 
 *  왜 이렇게 만들었을까?
 *  부가적으로 처리할 일을 끼워넣을 방법이 proxy를 이용하는 것
 *  advice로 작성한 것은 proxy객체에서 실행되는 구조!
 *  -> 주업무에 보조업무를 끼워넣기(weaving) 위해 proxy객체를 사용!
 *  
 * proxy객체의 종류
 * 1. jdk동적 proxy
 * 	- interface구현체인 경우
 * 	  (class com.sun.proxy.$Proxy40)
 * 2. cglib
 *  - interface구현체가 아닌 경우
 *  
 */
@Controller
@Slf4j
@RequestMapping("/memo")
public class MemoController {

	@Autowired
	private MemoService memoService;
	 
	@GetMapping("/memo.do")
	public ModelAndView selectMemoList(ModelAndView mav) {
		log.debug("memoService = {}", memoService.getClass());
		// MemoServiceImpl타입의 객체가 만들어져 주입되었을 것인데..
		// memoService = class com.sun.proxy.$Proxy40
		// proxy : 대리
		// 스프링이 의존주입해준 객체가 사실은 우리가 만든 클래스가 아니라, 스프링이 proxy객체를 만들어서 의존주입한 것
		
		// 1. 업무로직 : memo목록 조회
		List<Memo> memoList = memoService.selectMemoList();
		log.debug("memoList = {}", memoList);
		// 2. jsp에 위임
		mav.addObject("list", memoList);
		// 3. 리다이렉트
		mav.setViewName("memo/memo");
		return mav;
	}
	
	@PostMapping("/insertMemo.do")
	public String insertMemo(Memo memo, RedirectAttributes redirectAttr) {
		log.info("memo = {}", memo);
		try {
		// 1. 업무로직
		int result = memoService.insertMemo(memo);
		// 2. 시용자피드백 & 리다이렉트
		redirectAttr.addFlashAttribute("msg", "메모 등록 성공!");
		} catch (Exception e) {
			log.error("메모 추가 오류!", e);
			throw e;
		}
		return "redirect:/memo/memo.do";
	}
}
