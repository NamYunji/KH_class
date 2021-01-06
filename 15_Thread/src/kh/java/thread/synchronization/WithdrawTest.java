package kh.java.thread.synchronization;
/**
 * 계좌 Account에 접근하는 ATM기가 2대 있다
 * - 각 ATM기(Thread로 처리) 가 하나의 계좌에서 출금하는 상황
 * atm기는 하나의 account를 공유하고 있는 구조
 *
 */

//쓰레드는 작업내용을 반드시 run메소드 오바라이드를 통해 작업할 수 있음
public class WithdrawTest {
	public static void main(String[] args) {
		//계좌
		Account acc = new Account(1000);
		
		Thread atm1 = new Atm(acc);
		Thread atm2 = new Atm(acc);
		
		atm1.setName("atm1");
		atm2.setName("atm2");
		
		atm1.start();
		atm2.start();
	}

}
