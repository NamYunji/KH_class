package com.kh.spring.security.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.spring.security.model.dao.SecurityDao;

import lombok.extern.slf4j.Slf4j;

@Service("securityService") // 클래스 이름을 가지고 빈 아이디가 결정되기 때문에, securityService로 이름 지정
@Slf4j
public class SecurityServiceImpl implements SecurityService {
	
	@Autowired
	private SecurityDao securityDao;

	// UserDetailsService의 추상메소드 구현
	// 사용자 id를 받아서 db에 가서 조회한 후 리턴
	// 비밀번호 일치 여부는 authentication manager에서 해줄 것임!
	// 최소한의 db에서 id로 조회만 해주면 됨!
	// authentication manager에서 이 메소드를 호출함
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		// member객체 하나를 리턴함
		// 다형성 - member가 UserDetails의 자식클래스이므로 이런식으로 사용 가능함
		UserDetails member = securityDao.loadUserByUsername(id);
		log.debug("member = {}", member);
		
		// 조회된 member객체가 null이라면 예외 던짐
		if(member == null)
			throw new UsernameNotFoundException(id);
		// 최종적으로 member 리턴
		return member;
	}
}
