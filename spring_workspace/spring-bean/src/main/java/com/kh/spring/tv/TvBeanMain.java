package com.kh.spring.tv;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kh.spring.tv.model.vo.LgTv;
import com.kh.spring.tv.model.vo.SamsungTv;

public class TvBeanMain {

	public static void main(String[] args) {
		/**
		 * ApplicationContext생성
		 * applicationContext 
		 * 	: bean을 관리하는 단위, bean을 관리하는 일을 하는 객체
		 */
		// 설정내용을 파일로 만들었기 때문에, 파일의 위치를 제공해줘야 함
		String configLocation = "/application-context.xml";
		// 파일 위치를 인자로 하여 applicationContext가져오기
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		System.out.println("---------------------- spring-container에 bean 초기화 완료 ----------------------");
		/*
		이 이후로는 new LgTv()할 필요 없이
		context에게 그 객체좀 주세요! 하기만 하면 됨
		[객체 가져오기]
		방법1. context변수.getBean(클래스명.class)
		방법2. context변수.getBean(등록한 bean의 id) - 이때는 object를 리턴하기 때문에 캐스팅 필요
		 */
		LgTv lgtv1 = context.getBean(LgTv.class);
		System.out.println(lgtv1);
		// com.kh.spring.tv.model.vo.LgTv@475530b9
		LgTv lgtv2 = context.getBean(LgTv.class);
		System.out.println(lgtv2);
		// com.kh.spring.tv.model.vo.LgTv@475530b9
		// 몇번 getBean을 호출하든지 같은 객체를 돌려줌
		System.out.println(lgtv1 == lgtv2); // true
		
		SamsungTv sstv = (SamsungTv) context.getBean("samsungTv");
		System.out.println(sstv);
		// com.kh.spring.tv.model.vo.SamsungTv@1d057a39
		
		// 객체의 메소드 호출
		lgtv1.powerOn();
		sstv.powerOn();
		lgtv1.changeChannel(3);
		sstv.changeChannel(5);
	}
}
