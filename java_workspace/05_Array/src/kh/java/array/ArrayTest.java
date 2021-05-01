package kh.java.array;

public class ArrayTest {
	/*
	 * [배열] 변수의 업그레이드
	 * 변수 : 값을 보관하는 공간. 딱 하나의 값을 하나의 자료형으로 보관
	 * 배열 : 연속된 공간에 여러개의 값을 저장할 수 있도록 함 
	 * 실제 메모리상에도 값들이 연속적으로 존재
	 * 하나의 자료형. 여러개의 값
	 * 배열은 각 저장된 값마다 인덱스 번호가 부여됨 (0부터 시작)
	 * 배열의 길이가 5라면 인덱스는 0 ~ 4 -> 배열의 마지막 인덱스 : 배열의 길이 - 1 
	 * 만약 배열 길이 밖의 인덱스를 찾으면 ? ArrayIndexOutOfRange
	*/

	public static void main(String[] args) {
		ArrayTest a = new ArrayTest();
		// a.test0();
		// a.test1();
		a.test2();
	}

	// 변수 - 하나의 값만 저장할 수 있기 때문에 다섯개의 변수를 다 만들어줘야 함
	public void test0() {
		int kor = 80;
		int math = 70;
		int eng = 90;
		int society = 75;
		int science = 45;
		
		// 총점, 평균
		int total = kor + math + eng + society + science;
		double avg = (double)total / 5;
		
		System.out.printf("총점 : %d점, 평균 : %.2f%n", total, avg);
	}
	/**
	 * 배열 : 동일한 자료형의 값이 여러개인 경우
	 * 만약 자료형이 다르다면 함께 묶을 수 없음
	 * => arr이라는 변수 하나만 만들어두고, index를 이용
	 */
	public void test1() {
		// 1. 배열변수 선언
		// 인트배열 - 하나의 자료형 -> 자바에서는 자료형과 변수명을 분리해서 쓰는걸 선호함
		int[] arr;
		// int arr[]; c계열 언어에서 이 형태를 선호함
		// 2. 배열 할당 : 배열 길이를 반드시 지정할 것
		// 메모리 heap영역에 int값을 담을 수 있는 공간 할당
		// 메모리상에 다섯개의 연속된 공간을 만들어 둔 것
		arr = new int[5];
		
		// 배열공간이 만들어지면 바로 사용할 수 있을까?
		System.out.println(arr[0]); // 0
		System.out.println(arr[1]); // 0
		System.out.println(arr[2]); // 0
		System.out.println(arr[3]); // 0
		System.out.println(arr[4]); // 0
		// 배열은 각 타입별 초기값으로 미리 처리됨
		// [기본형의 기본값] int 0, double 0.0, char '', boolean false (0이 false이므로)
		// [참조형의 기본값] null (값없음을 의미하는 keyword)
		// 변수는 공간을 만들고 값대입한 후에야 사용가능했으나, 배열은 공간을 만들면 바로 기본값으로 세팅되므로 값대입하지 않아도 그즉시 사용가능
		
		// 3. 배열 각 번지에 값대입
		// 각 번지수에 값을 넣어줌
		arr[0] = 80;
		arr[1] = 70;
		arr[2] = 90;
		arr[3] = 75;
		arr[4] = 45;
		
		// 4. 총점, 평균
		int total = arr[0] + arr[1] + arr[2] + arr[3] + arr[4];
		double avg = (double)total / arr.length;
		System.out.println(arr.length); // 배열은 length라는 속성에 배열의 길이가 기록됨
		System.out.printf("총점 : %d점, 평균 : %.2f%n", total, avg);
		
		System.out.println("----------");
		System.out.println(arr[0]); // 80
		System.out.println(arr[1]); // 70
		System.out.println(arr[2]); // 90
		System.out.println(arr[3]); // 75
		System.out.println(arr[4]); // 45
		System.out.println("----------");
		// 번지수가 0부터 시작하기 때문에 반복문 사용 가능
		for(int i = 0; i < 5; i++) {
			System.out.println(arr[i]);
		}
		// 반복문을 이용한 총점 구하기
		int total2 = 0;
		// magic number - 의미를 찾기 힘듦 -> 맥락상의 값을 알수있도록 변수값을 쓰는 것이 좋음
		for(int i = 0; i < arr.length; i++) {
			total2 += arr[i];
		}
		System.out.println("반복문을 이용한 총점 : " + total2);
	}
	
	public static void test2() {
		double[] arr;
		arr = new double[3];
		arr[0] = 1.1;
		arr[1] = 2.2;
		arr[2] = 3.3; // 배열의 마지막 인덱스 : arr.length - 1
		// arr[3] = 4.4; ArrayIndexOutOfBoundsException:3 존재하지 않는 인덱스를 참조시
		
		/**
		 * jvm은 os에 요청을 하면, 이 공간에서 프로그램 돌려!하고 램에 메모리 공간을 할당받음
		 * 할당 받은 공간을 3공간으로 나눔
		 * 1. call stack : method call stack
		 * 		메소드를 호출하면 이 공간에서 그 일을 진행
		 * 		stack은 뭔가 쌓이는 걸 의미함. 밑에서부터 위로 차곡차곡  쌓임
		 * 		[밑] main method - test0 - test1 - test2 [위]
		 * 		밑에서부터 쌓이고 위에 있는것부터 실행, 다 실행되면 메소드를 반납하고 다시 main으로 돌아옴
		 * 		메소드 안에 선언한 변수들은 다 이 call stack안에 선언한 것
		 * 		이 call stack내의 call stack frame안에  변수 공간을 마련한 것
		 * 		call stack frame = 하나의 메소드를 실행하는 공간
		 * 
		 * 		double[] arr;
		 * 		-> method2라는 call stack frame안에 double[] 자료형의 공간을 만들고 그 공간의 이름을 arr이라고 지정함
		 * 		arr = new double[3];
		 * 		
		 * 2. heap
		 * 3. static
		 */
	}
}
