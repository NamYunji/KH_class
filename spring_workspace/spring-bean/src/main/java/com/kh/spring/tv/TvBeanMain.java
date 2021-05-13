package com.kh.spring.tv;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kh.spring.tv.model.vo.LgTv;
import com.kh.spring.tv.model.vo.SamsungTv;

public class TvBeanMain {

	public static void main(String[] args) {
		// ApplicationContext생성
		// applicationContext : bean을 관리하는 단위
		String configLocation = "/application-context.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		System.out.println("---------------------- spring-container bean 초기화 완료 ----------------------");
		
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
		
		lgtv1.powerOn();
		sstv.powerOn();
		lgtv1.changeChannel(3);
		sstv.changeChannel(5);
	}
}
