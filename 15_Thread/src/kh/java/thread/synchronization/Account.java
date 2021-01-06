package kh.java.thread.synchronization;

public class Account {
	
	private int balance;
	
	public Account(int balance) {
		this.balance = balance;
	}
	
	
	public int getBalance() {
		return balance;
	}

	/*
	 * atm1이 acc를 쓰고 나면, lock을 획득하고 다 사용하고 나면, 대기중이던 th2가 다시 접근할 수 있음
	 * 한번에 한 쓰레드만 이용할 수 있게 하는 것 : 동기화
	 * 사용할수 있는 상태 : runnable상태
	 * 사용할수 없고 대기중인 상태 : blocked상태
	 * 동기화처리
	 * 결국 객체 단위로 동기화 처리 : account 임계영역(한번에 한쓰레드만 들어갈 수 있는 영역)으로 설정하게 된다
	 * lock을 획득한 순간, 다 사용하기 전까지, 다른 메소드에게 방해받지 않음
	 * 1.synchronized 메소드 만들기
	 * 2. synchronized block 사용하기(추천)
	 */
	//출금메소드 : atm기는 이 메소드를 호출해서  출금가능하다
	
	//1. synchronized 메소드 만들기
//	public synchronized void withdraw(int money) {
//		String threadName = Thread.currentThread().getName();
//		System.out.println("[" + threadName + " -> 잔액 : ￦ " + balance + "]");
//		
//		if(money <= balance) {
//			balance -= money;
//			System.out.println("[" + threadName + " -> 출금 : ￦ " + money + ", 잔액 : ￦" + balance + "]");
//		}
//		else {
//			System.out.println("[" + threadName + " -> 출금 : ￦ " + money + ", 잔액이 부족합니다.]");
//			
//		}
//	}
	
	// 2. 임계영역 설정하기 , 여러 쓰레드 사용시 영역을 작게 작게 잡을수록 효율성 高
	// synchronized (this) {} , 괄호안에는 임계영역으로 설정할 객체가 들어감. 그리고
	public void withdraw(int money) {
		String threadName = Thread.currentThread().getName();
		synchronized (this) {
		System.out.println("[" + threadName + " -> 잔액 : ￦ " + balance + "]");
		
		if(money <= balance) {
			balance -= money;
			System.out.println("[" + threadName + " -> 출금 : ￦ " + money + ", 잔액 : ￦" + balance + "]");
		}
		else {
			System.out.println("[" + threadName + " -> 출금 : ￦ " + money + ", 잔액이 부족합니다.]");
		
		}
	}
	}
	

}
