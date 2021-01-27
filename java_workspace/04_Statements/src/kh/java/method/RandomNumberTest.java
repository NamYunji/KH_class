package kh.java.method;

import java.util.Random;
import java.util.Scanner;

public class RandomNumberTest {
	public static void main(String[] args) {	
		//sdf : 형식객체
		//java.text.SimpleDateFormat import
		//SimpleDateFormat : 날짜 타입의 정보를 받아서 괄호()안의 형식에 맞게 표현해줌
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		//java.util.Date import
		//변수에 담아서 출력
		//sdf.format(new Date()); 
		//format(); String을 리턴하는 DateFormate클래스의 메소드
		/*
		 * Formats a Date into a date/time string.
		 * (날짜 타입에 형식을 입혀서 날짜/시간 형식으로 된 문자열을 만들어준다)
			Parameters:date the time value to be formatted into a time string.
			(파라미터 : 시간 문자열 형식으로 전환될 날짜/시간 값을 인자로 받는다)
			Returns:the formatted time string.
			(전환된 시간 문자열을 리턴)
		 */
//		String s = sdf.format(new Date());
//		//출력
//		System.out.println(s);
//		//바로 출력
//		System.out.println(sdf.format(new Date()));
		
		RandomNumberTest r = new RandomNumberTest();
		r.test1();
		r.test2();
		r.test3();
	}
	
	//난수 : 임의의 수
	/*
	 * 방법 1. java.util.Random
	 */
	public void test1() {
		//1. Random객체 만들기
		Random rnd = new Random();
		
//		int num = rnd.nextInt(); //int범위
		
		//경우의 수, 시작값(최소값) 정함

//		int num = rnd.nextInt(10); //0~9까지의 10개의 난수 제공
		int num = rnd.nextInt(10) + 1; // 경우의 수 + 시작값까지 설정 -> 1~10까지의 난수 제공
		System.out.println(num);
		
		
		//101 ~ 200 : 경우의 수 : 100, 시작값 101
		int num2 = rnd.nextInt(100) + 101;
		System.out.println(num2);
		
		//0.0(포함) ~ 1.0(미포함)의 난수 리턴
		double dnum = rnd.nextDouble();
		System.out.println(dnum);
		
		//true, false 중 하나 리턴
		boolean bool = rnd.nextBoolean();
		System.out.println(bool);
	}
	
	/*
	 * 방법 2. Math.random()
	 * Math클래스가 제공하는 random메소드 이용
	 * + java.lang패키지라서 import 불필요
	 * 0.0(포함) ~ 1.0(미포함) 실수를 리턴
	 */
	public void test2() {
		double num = Math.random();
		System.out.println(num);
		
		//정수형 난수를 얻는 법
		//(int형변환)(Math.random() * 경우의 수)  + 최소값
		//0.247152... -> *10 -> 2.47152.... -> int로 형변환
		//Math.random()은 0.0부터 0.999999999...까지다
		//Math.random() *45 = 0.0 ~ 44.99999999... -> (0~44)+1
		int i = (int)(Math.random() * 10 ) + 1;
		// 0.0~0.9 -> 0 ~ 9.999999 -> 0 ~ 9 -> 1 ~ 10
		i = (int)(Math.random() * 45 ) + 1;		
		
		System.out.println(i);
	}
	
	//동전 앞뒤 맞추기
	public void test3() {
		//사용자에게 앞/뒤를 선택하게 함
		Scanner scanner = new Scanner(System.in);
		System.out.println("동전 앞/뒤를 입력(1. 앞, 2. 뒤) -> ");
		int user = scanner.nextInt();
		
		//앞/뒤를 랜덤으로 둠
		int coin = (int)(Math.random() * 2) + 1;
		String result = user == coin? "정답" : "오답";
		System.out.println(result);
		System.out.println("user =" + user + ", coin = " + coin);
	}

}
