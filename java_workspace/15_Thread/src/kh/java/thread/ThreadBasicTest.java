package kh.java.thread;

public class ThreadBasicTest {

	public static void main(String[] args) {
		ThreadBasicTest t = new ThreadBasicTest();
//		t.test0();
		t.test1();
//		t.test2();
		System.out.println("<메인끝>");
	}


	/*
	 * 쓰레드 생성방법 2
	 * Runnable 인터페이스 구현
	 */
	private void test2() {
		Runnable run1 = new CustomThread2('|');
		Runnable run2 = new CustomThread2('-');
		
		Thread th1 = new Thread(run1);
		Thread th2 = new Thread(run2);
		
		th1.start();
		th2.start();
		
	}


	//싱글스레드
	private void test0() {
		//A
		for (int i = 0; i < 100; i++) {
			System.out.println('|');
		}
		//B
		for (int i = 0; i < 100; i++) {
			System.out.println('-');
		}
	}
	
	//멀티스레드
	/**
	 * 스레드 생성방법
	 * 1. Thread 클래스 상속
	 */
	//일꾼 객체를 만들었음
	private void test1() {
		Thread th1 = new CustomThread1('|');
		Thread th2 = new CustomThread1('-');
	//가서 일하라는 명령
		//우선순위는 정해줄 수 있으나, 실행결과는 매번 다름
		//우선순위 지정(1~10) 기본값 5
		th2.setPriority(Thread.MAX_PRIORITY);
		th2.setPriority(Thread.MIN_PRIORITY);
		th1.start();
		th2.start();
		
	}
}
