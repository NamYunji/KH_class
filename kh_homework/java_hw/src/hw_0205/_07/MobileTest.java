package hw_0205._07;
/*
 * Mobile Battery OS
Ltab 500 AP-01
Otab 1000 AND-20

10분 충전
Mobile Battery OS
Ltab 600 AP-01
Otab 1080 AND-20

5분 통화
Mobile Battery OS
Ltab 550 AP-01
Otab 1020 AND-20
 */
public class MobileTest {
public static void main(String args[]) {
// 각각의 Mobile 객체 생성
	Mobile mobL = new Ltab("Ltab", 500, "AP-01");
	Mobile mobO = new Otab("Otab", 1080, "AND-20");
// 생성된 객체의 정보 출력
	System.out.println(mobL.toString());
// 각각의 Mobile 객체에 10분씩 충전
// 10분 충전 후 객체 정보 출력
// 각각의 Mobile 객체에 5분씩 통화
// 5분 통화 후 객체 정보 출력
}
}