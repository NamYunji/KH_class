package test03.ncs.test2;

/**
주어진 String 데이터를 “,”로 나누어 5개의 실수 데이터들을 꺼내어 합과 평균을 구한다.
단, String 문자열의 모든 실수 데이터를 배열로 만들어 계산한다.
 */
public class StringTest {
	public static void main(String[] args) {
		double data[]= new double[5];
		String str = "1.22,4.12,5.93,8.71,9.34";
		 // str에서 데이터를 분리한다.
		String[] st = str.split(",");

		//for~each 문 사용한다.
		double sum = 0;
		int i = 0;
		for (String s : st) {
			// 배열에 실수 데이터를 넣는다.
			data[i] = Double.parseDouble(s);
			//배열 데이터의 합을 구한다.
			sum += data[i];
			i++;
		}
		
		// 결과 값을 출력 한다.
		System.out.println(sum);
		}
}
