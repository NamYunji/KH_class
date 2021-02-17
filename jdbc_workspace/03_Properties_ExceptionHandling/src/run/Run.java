package run;

import member.view.MemberMenu;

public class Run {
	public static void main(String[] args) {
		//MemberMenu 클래스를 객체로 만들고, 그 안의 mainMenu를 실행 시킬 것
		new MemberMenu().mainMenu(); 
		//메인메뉴 실행 후, 프로그램 종료
		System.out.println("----프로그램 종료----");
	}

}


