package kh.java.collection.map.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/*
 * Properties : 똑같이 key,value로 다루지만, 옛날 스타일의 map임
 * 왜 옛날것임에도 불구하고 아직도 쓸까?
 * 특징 : 똑같이 key,value를 사용하는데, 그 type을 String,String으로 제한한 map 클래스
 * - 기능 : 파일 입출력메소드를 지원
 * - 설정정보 표현에 최적의 형태
 */
public class PropertiesTest {
	
	public static void main(String[] args) {
		PropertiesTest p = new PropertiesTest();
		p.test1();
		p.test2();
	}

	/*
	 * 입력
	 */
	private void test2() {
		Properties prop = new Properties();
		//파일을 읽어서 prop에 적재해주세요! 하는 메소드
		try {
//			prop.load(new FileReader("userInfo.properties"));
			prop.loadFromXML(new FileInputStream("userInfo.XML"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(prop);
		
		//1. 요소 가져오기
		String url = prop.getProperty("url");
		String userName = prop.getProperty("userName");
		String password = prop.getProperty("password");
		System.out.println(url);
		System.out.println(userName);
		System.out.println(password);
		
		//2. 전체열람메소드 Enumeration타입 (열거형)
		//?는 모든 타입을 처리가능
		Enumeration<?> en = prop.propertyNames(); //name(key) 모음
		while(en.hasMoreElements()) {
			String name = (String)en.nextElement();
			String value = prop.getProperty(name);
			System.out.println(name + " = " + value);
		}
	}

	/*
	 * 출력
	 */
	private void test1() {
		Properties prop = new Properties();
		prop.setProperty("url", "http://localhost:9090/kh-java");
		prop.setProperty("userName", "honggd");
		prop.setProperty("password", "1234!@#$");
		
		//출력하니, 역시나 저장한 순서는 무시
		System.out.println(prop);
		
		//파일에 저장
		//확장파일로 주로 쓰는 확장자명
		//.properties
		//.xml
		//+ 예외처리 필요
		try {
//			prop.store(new FileWriter("userinfo.properties"), "userInfo.properties");
			prop.storeToXML(new FileOutputStream("userinfo.xml"), "userinfo.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
