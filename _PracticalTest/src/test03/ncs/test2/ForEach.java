package test03.ncs.test2;

public class ForEach {
	public static void main(String[] args) {
		ForEach fe = new ForEach();
//		fe.test1();
//		fe.test2();
		fe.test3();
	}
	
	private void test1() {
		
		String data = "java§oracle§html§spring§maven§kh/java/html";
		
		//split메소드 : 사이즈설정 + 구분자를 기준으로 문자열을 나눠서, 배열에 각각 넣어줌
		String[] arr = data.split("§");
		for(String s : arr) {
			System.out.print("["+s+"] ");
		}
	}

	private void test2() {
		String data = "p001 핸드폰 노트8 120만원*p002 핸드폰 v10 98만원*c001 컴퓨터 엘지xnote 100만원*e001 가전 전자레인 30만원*e002 가전 세탁기 80만원";
		//정규표현식 : 대괄호 안에 넣어줌
		//어떻게 처리해줄지에 대한 표현식
		String[] d = data.split("[*]");
		
		for (String s : d) {
		
			System.out.println("[" + s + "]");
		}
	}

	private void test3() {
		//split은 구분자 사이의 공백도 포함
		String s = "one two, three,\t four; \t\nfive..,six";
		//스페이스바는 split, StringTokenizer여부와 상관없이 다 구분자로 취급해줌
		String[] a = s.split("[ ,.;\t\n]");
		for (String str : a) {
			System.out.println("[" + str + "]");
		}
		System.out.println(a.length);
	}
	

}
