package com.kh.spring.member.model.vo;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
// security를 사용하는 회원객체는 UserDetails라는 인터페이스를 상속해야 함
// UserDetails를 구현해야 security에서 검사 가능
public class Member implements UserDetails{

	// security가 필요로 하는 항목 1. username, 2. password
	private String id; // username (이름은 중요치 않고, username의 역할을 하는 필드가 있느냐 없느냐의 여부)
	private String password; // password
	private String name;
	private String gender;
	private Date birthday;
	private String email;
	private String phone;
	private String address;
	private String[] hobby;
	private Date enrollDate;
	
	/**
	 * security가 필요로 하는 항목 3. 
	 * 복수개의 권한을 처리할 수 있도록 GrantedAuthority타입을 여러개 가질 수 있도록 list 생성
	 */

	// SimpleGrantedAuthority : 문자열 data를 처리할 수 있는 GrantedAuthority의 하위 클래스
	// cf. 문자열 data : ROLE_USER, ROLE_ADMIN
	// -> 문자열로 주면 SimpleGrantedAuthority가 알아서 권한 관리해줌
	private List<SimpleGrantedAuthority> authorities;
	
	// security가 필요로 하는 항목 4. enabled (활성화 여부)
	private boolean enabled;
	
	/**
	 * Collection타입
	 * : list, set의 부모 interface
	 * 
	 * Collection<? extends GrantedAuthority> = Collection<GrantedAuthority>
	 * - ? extends GrantedAuthority - GrantedAuthority를 상속하는 무언가
	 * -> GrantedAuthority의 자식타입이면 ok다! (상한선)
	 * -> GrantedAuthority의 하위타입만 받겠다
	 * 
	 * cf. <? super Integer>
	 * - Integer의 부모타입이면 ok다! (하한선)
	 */
	// authority에 대한 getter
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	// username에 대한 getter
	@Override
	public String getUsername() {
		return id;
	}
	// 유효기간이 만료되었는지의 여부
	@Override
	public boolean isAccountNonExpired() {
		return true; // 사용 가능
	}
	
	// 계정이 잠겼는지
	@Override
	public boolean isAccountNonLocked() {
		return true; // 잠기지 않음
	}
	
	// 비밀번호 유효기간이 만료되었는지
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // 만료 안됨
	}
}
