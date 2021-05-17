package com.kh.spring.tv.model.vo;

public class LgTv implements Tv {
	// 의존객체로 RemoteControl을 가짐
	private RemoteControl remocon;

	/**
	 * bean>property
	 * setter를 이용해서 의존주입 (Dependency Injection)한다.
	 */
	// remocon에 대한 setter생성
	public void setRemocon(RemoteControl remocon) {
		System.out.println("setRemocon : " + remocon);
		this.remocon = remocon;
	}
	/**
	 * bean>constructor
	 * 생성자를 이용해서 의존주입 (Dependency Injection)한다.
	 * (의존객체를 인자로 받음)
	 */
	public LgTv(RemoteControl remocon) {
		System.out.println("LgTv객체생성 : " + remocon);
		this.remocon = remocon;
	}
	
	// 생성자
	public LgTv() {
		System.out.println("LgTv객체를 생성했습니다.");
	}
	// 추상메소드 구현
	@Override
	public void powerOn() {
		System.out.println("LgTv의 전원을 켰습니다.");
	}
	
	public void changeChannel(int no) {
		this.remocon.changeChannel(no); // = LgTv.remocon.changeChannel(no)
	}
}
