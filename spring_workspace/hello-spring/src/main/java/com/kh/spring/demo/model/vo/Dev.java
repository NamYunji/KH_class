package com.kh.spring.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 필드만 써두고, 생성자, getter/setter, toString은 어노테이션으로 작성
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 파라미터생성자
@Getter // getter
@Setter // setter
@ToString // toString
public class Dev {
	
	private int no;
	private String name;
	private int career;
	private String email;
	private String gender;
	private String[] lang;
}
