package hw_0205._01;
/*
프로그램 실행 시 eclipse argument 로 공백이 없는 String 형태의 문자열을 받아 들인다
입력
예 1: Morning - > gninroM
입력받은 문자열을 뒤에서부터 출력 하는 프로그램을 작성 한다
단, String class 의 toCharArray() 함수를 이용한다
 */
//string prompt

//ch.length = 7
//0 1 2 3 4 5 6
//6 5 4 3 2 1 0
public class _01 {
	public static void main(String[] args) {
		String a = args[0];
		char[] ch = a.toCharArray();
		for (int i = ch.length - 1; i >= 0; i--) {
			System.out.print(ch[i]);
		}
	}
}
