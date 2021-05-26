package com.kh.spring.demo.model.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.spring.demo.model.vo.Dev;

import lombok.extern.slf4j.Slf4j;

// Validator : supports와 validate메소드 override
@Slf4j
public class DevValidator implements Validator {
	/**
	 * supports() -> boolean 리턴
	 * (검사하고자 하는 객체가 적합한 객체인지 판단)
	 * 검사하고자 하는 객체가 Dev타입일 때만 유효성 검사 지원
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// command객체의 class객체가 인자로 넘어옴
		return Dev.class.isAssignableFrom(clazz); // 전달된 클래스 객체가 dev클래스가 맞는지 -> boolean형 리턴
	}

	/**
	 * validate()
	 * (supports메소드에서 true일 때만 validate() 메소드 실행)
	 * 필드별로 유효성 검사를 실시
	 * 부적합한 값인 경우, error객체에 해당 오류사실을 저장해둠
	 */
	@Override
	public void validate(Object target, Errors errors) {
		
		// 인자의 obejct객체가 command객체임
		Dev dev = (Dev)target;
		log.info("dev = {}", dev);
		
		// 1. 이름 검사
		String name = dev.getName();
		// 입력하지 않은 경우
		if(name == null || name.isEmpty()) {
			errors.rejectValue("name", "error-name", "개발자명 누락");			
			// rejectValue(@Nullable String field, String errorCode, String defaultMessage)
		}
		// 한글이 아닌 경우
		// Pattern.matches("유효성검사 패턴", 검사할 요소) -> boolean형 리턴
		if(!Pattern.matches("[가-힣]{2,}", name)) {
			errors.rejectValue("name", "error-name", "한글이름 부적절");
		}
		
		// 2. 경력 검사
		int career = dev.getCareer();
		// 경력이 0보다 작은 경우
		if(career < 0) {
			errors.rejectValue("career","error-career", "경력기간 오류!");
		}
	}
}
