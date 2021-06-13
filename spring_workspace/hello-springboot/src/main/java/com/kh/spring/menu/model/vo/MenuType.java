package com.kh.spring.menu.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * java.lang.Enum을 상속
 * 상속한 Enum에 이미 valueOf가 있기 때문에 다른 이름의 valueOf 메소드 사용하기
 */
public enum MenuType {

	KR("kr"), CH("ch"), JP("jp");
	
	// 필드
	private String value;
	
	// 생성자
	// enum생성자의 접근제한자는 private이다.
	// 외부에서 접근/생성 불가
	MenuType(String value) {
		this.value = value;
	}
	
	// 게터 - 내부적 선언한 값을 가져옴
	@JsonValue // json변환시 이것을 사용하라는 어노테이션 -> json으로 변환할 때 내부적으로 사용되는 값이 이용되도록
	public String getValue() {
		return this.value;
	}
	
	// 세터
	public static MenuType menuTypevalueOf(String value) {
		switch (value) {
		case "kr": return KR;
		case "ch": return CH;
		case "jp": return JP;			
		default: 
			throw new AssertionError("Unknown MenuType : " + value);
		}
	}
}
