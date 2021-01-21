package kh.java.method;

import java.util.Scanner;
/*
 * 메소드 사용의 의의 : 코드 중복을 피한다!
 */
public class MethodTest {
	public static void main(String[] args) {
		
		MethodTest m = new MethodTest();
//		m.test1();
		m.test2();
		

	}


	public void test1() {

		// 출력문의 내용을 수정할 时, 모든 출력문의 내용을 바꿔야 한다는 번거로움
//		String name1 = "홍길동";
//		int age = 20;
//		
//		System.out.println("저는 " + age + "살, " + name1 + "입니다");
//		
//		name1 = "신사임당";
//		age = 22;
//		
//		System.out.println("저는 " + age + "살, " + name1 + "입니다");
//		
//		name1 = "세종대왕";
//		age = 23;
//		
//		System.out.println("저는 " + age + "살, " + name1 + "입니다");		

		// 해결방법1 : 변수를 매개인자로 담아줌
		String name2 = "홍길동";
		int age2 = 20;
		printInfo(name2, age2);

		name2 = "신사임당";
		age2 = 22;
		printInfo(name2, age2);

		name2 = "세종대왕";
		age2 = 23;
		printInfo(name2, age2);

		// 해결방법2 : 변수에 literal을 담지 않고, printInfo호출 시, literal자체를 매개인자로 사용할 수 있음
		String name;
		int age;

		printInfo("홍길동", 20);
		printInfo("신사임당", 22);
		printInfo("세종대왕", 23);
	}

	// 해결방법 : 받아온 매개인자를 매개변수로 저장해줌
	public void printInfo(String name2, int age2) {
		// 실행 시 매개변수에서 값을 꺼내 씀
		System.out.println("저는 " + age2 + "살, " + name2 + "입니다!");
	}
	
	
	
	private void test2() {
		//Scanner클래스에서 입력받는 부분이 겹치는 경우
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("정수입력 : ");
//		int num1= scanner.nextInt();
//		System.out.print("정수입력 : ");
//		int num2= scanner.nextInt();
	
		// 해결방법 : 공통된 일을 할 메소드 호출
		int num1 = inputNum();
		int num2 = inputNum();
		System.out.println("num1 + num2 = " + (num1+num2));
		
		}
	
	// 해결방법 : 공통된 일을 할 메소드 생성
	public int inputNum() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("정수입력 : ");
		int num = scanner.nextInt();
		return num;
	}
	
	



}
