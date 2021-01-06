package kh.java.thread.synchronization;

public class Atm extends Thread {
	
	private Account acc;

	public Atm(Account acc) {
		this.acc = acc;
	}
	
	@Override
	public void run() {
		//잔액이 남아있는 한 출금 요청
		while(acc.getBalance() > 0) {
			int money = (int)(Math.random() * 3 + 1) * 100;
			acc.withdraw(money);
			
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "종료!");
	}
	
}










