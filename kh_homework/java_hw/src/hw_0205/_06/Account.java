package hw_0205._06;


/*

-account:String
-balance: double
-interest Rate :double
+Account()
+Account(account:String, balance: double, interestRate:double)
+calculate Interest ():double
+deposit(money:double ):void
+withdraw(money:double ):void
+getXXX()
+setXXX()
 */

public class Account {
	// 클래스 변수 선언
	private String account;
	private double balance;
	private double interestRate;
	
	// Constructor 구현
	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Account(String account, double balance, double interestRate) {
		super();
		this.account = account;
		this.balance = balance;
		this.interestRate = interestRate;
	}


	// getter/setter 구현
	public String getAccount() {
		return account;
	}


	public void setAccount(String account) {
		this.account = account;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}


	public double getInterestRate() {
		return interestRate;
	}


	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	
	
	public double calculateInterest() {
		// 이자계산
		return this.getBalance() * this.interestRate;
	}





	public void deposit(double money) throws Exception {
		// 입금 처리 및 예외상황 처리

	}

	public void withdraw(double money) throws Exception {
		// 출금 처리 및 예외상황 처리
	}
}


