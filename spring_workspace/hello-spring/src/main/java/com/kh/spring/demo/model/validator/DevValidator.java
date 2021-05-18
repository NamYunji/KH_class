package com.kh.spring.demo.model.validator;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.spring.demo.model.vo.Dev;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DevValidator implements Validator {

	/**
	 * supports() -> boolean 리턴
	 * 검사하고자 하는 객체가 Dev타입일 때만 유효성 검사 지원
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		// 전달된 클래스 객체가 dev클래스가 맞는지
		return Dev.class.isAssignableFrom(clazz);
	}

	/**
	 * validate()
	 * 필드별로 유효성 검사를 실시
	 * 부적합한 값인 경우, error객체에 해당 오류사실을 저장해둠
	 */
	@Override
	public void validate(Object target, Errors errors) {
		// 이때의 obejct객체가 command객체임
		Dev dev = (Dev)target;
		log.info("dev = {}", dev);
		// 1. 이름 검사
		String name = dev.getName();
		if(name == null || name.isEmpty()) {
			errors.rejectValue("name", "error-name", "개발자명 누락");			
			// rejectValue(@Nullable String field, String errorCode, String defaultMessage)
		}
		if(!Pattern.matches("[가-힣]{2,}", name)) {
			errors.rejectValue("name", "error-name", "한글이름 부적절");
		}
		// 2. 경력 검사
		int career = dev.getCareer();
		if(career <= 0) {
			errors.rejectValue("career","error-career", "경력기간 오류!");
		}
	}
}
