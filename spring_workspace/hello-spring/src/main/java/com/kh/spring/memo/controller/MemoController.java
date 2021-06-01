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

@Controller
@Slf4j
@RequestMapping("/memo")
public class MemoController {

	@Autowired
	private MemoService memoService;
	
	@GetMapping("/memo.do")
	public ModelAndView memo(ModelAndView mav) {
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
