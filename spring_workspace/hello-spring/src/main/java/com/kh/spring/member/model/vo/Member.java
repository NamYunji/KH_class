package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@ToString
@Data
// @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode를 대신함
// cf. @RequiredArgsConstructor : notnull 필드들을 모아서 생성자를 만들어줌
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	// @NotNull 어노테이션을 붙인 필드 (null일 수 없음)
	// @NotNull
	private String id;
	
	private String password;
	private String name;
	private String gender;
	private Date birthday;
	private String email;
	private String phone;
	private String address;
	private String[] hobby;
	private Date enrollDate;
	private boolean enabled; // 회원활성화여부 true(1, 기본값), false(0)
}
