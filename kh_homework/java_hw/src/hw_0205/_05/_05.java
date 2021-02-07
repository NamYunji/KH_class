package hw_0205._05;
/*
 * 다음에 선언된 String 객체를 이용하여 조건에 맞는 프로그램을 작성 하시오
String str = "LGcns";
1) Java API Documentation
을 참조 하여 str 내용을 아래와 같이 출력 되도록 하시오
LGCNS
lgcns
LG
cns
 */
public class _05 {
public static void main(String[] args) {
	String str = "LGcns";
	//LGCNS
	System.out.println(str);
	//lgcns
	System.out.println(str.toLowerCase());
	//LG
	System.out.println(str.substring(0, 2));
	//cns
	System.out.println(str.substring(3-1, 5));
}
}
