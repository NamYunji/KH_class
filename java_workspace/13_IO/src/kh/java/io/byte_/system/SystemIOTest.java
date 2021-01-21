package kh.java.io.byte_.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * byte기반으로 작동하는 표준입출력
 * System.in : 사용자 키보드 입력
 * cf. Scanner(System.in) : 스캐너에게 읽어올 대상(키보드 입력값)을 지정해준 것 / 스캐너는 파일도 읽어올 수 있음
 * System.out : 가리키고 있는 것 : 콘솔
 */
public class SystemIOTest {
	public static void main(String[] args) {
		SystemIOTest s = new SystemIOTest();
//		s.test1();
		s.test2();
	}

	/*
	 * System.in은 기본스트림 -> 단독으로 쓰일 수 있음
	 * read() : 대상으로부터 1byte를 읽어와서 int로 리턴하는 메소드
	 *  but, byte로 읽어와서 왜 int를 리턴하지?
	 *  ->1byte는 256가짓수를 표현할 수 있는데, 256가지 이상을 표현하기 위해 int를 리턴함
	 * 256(0~256) + 1 -> -1(읽어올 값이 없는 경우, 다 읽어왔을 때 리턴되는 값)
	 * -> 257가지를 표현해야 하는데, byte로는 표현할 수 없음
	 * -> architecture상 효율성이 좋은 int사용
	 * 
	 * 이클립스에서는 Ctrl + z를 입력하면 -1을 리턴 -> 실행중지(terminated)
	 */

	public void test1() {
		System.out.print("키보드로 입력값을 주세요 : ");
		int data = 0; //읽어온 값을 담아둘 변수
		try {
			/* read()
			 * Reads the next byte of data from the input stream.
			 * The value byte is returned as an int in the range 0 to 255.
			 * If no byte is available because the end of the streamhas been reached, the value -1 is returned.
			 * This method blocks until input data is available, the end of the stream is detected,or an exception is thrown. 
			 * Returns:the next byte of data, or -1 if the end of the stream is reached
			 * Throws:IOException - if an I/O error occurs.
			 */
			
			//while문을 사용하는 이유 : 한번 읽어오면 끝나지 않도록, 읽어온 값을 계속 데이터에 넣어줌
			//1. 데이터에 값대입 : data에 system.in에 read한 값을 담아라 
			//2. 그 값이 -1이 아니라면 반복해라 -> true일 때까지만 반복하는 것.
		while((data = System.in.read()) != -1) {
			System.out.println("input = " + data);
		}
		} catch(IOException e) {
			e.printStackTrace();
		}
		/* 콘솔 출력
		키보드로 입력값을 주세요 : a(엔터)
		-> 아스키코드 값으로 처리됨
		input = 97 // : 우리가 입력해준 a
		input = 13 // : \r
		input = 10 // : \n
		-> 반복문 3번 실행 : byte씩 처리한다는 것의 의미
		buffer안에 97, 13, 10이 들어와있음
		-> System.in이 입력buffer에서 그 값들을 1byte씩 읽어서 jvm에 가져옴
		-> 1byte씩 while이 돌게됨
		
		cf. 한글의 경우 utf-8방식, 3byte로 읽어옴
		키보드로 입력값을 주세요 : 한 //(엔터)
		input = 237
		input = 149
		input = 156
		input = 13
		input = 10
		*/
		
		//but System.in 하나만 쓰기에는 너무 힘듦, buffer가 열일..
	}
	
	/**
	 * 2004년 1.5버전 나오기 전
	 * Scanner클래스 등정전의 사용자 입력값처리는 다음과 같다
	 */
	public void test2() {
		/* BufferedReader
		 * Reads text from a character-input stream,
		 * buffering characters so as to provide for the efficient reading of characters, arrays, and lines. 
		 * The buffer size may be specified, or the default size may be used.
		 * The default is large enough for most purposes. 
		 */
		
		//BufferedReader : 문자기반 / 보조스트림 -> 단독으로 쓰일 수 없음 -> 주스트림을 전달하면 됨
//		BufferedReader br = new BufferedReader(System.in);
		// The constructor BufferedReader(InputStream) is undefined
		// System.in은 byte기반이기 때문에 인자로 직접적으로 연결하여 사용 불가
		
		//보조스트림 : 기본스트림에 연결하여 사용 / 보조스트림 여러개를 연결할 수 있음
		// new 문자기반보조스트림(new 브릿지스트림(바이트기반기본스트림))
		// 보조스트림의 인자로 브릿지스트림의 객체를 전달하고, 그 생성자의 인자로 주스트림을 전달한다
		// System.in(주스트림) -> InputStreamReader(브릿지스트림 )-> BufferedReader(보조스트림)
//		InputStreamReader isr = new InputStreamReader(System.in);
//		BufferedReader br = new BufferedReader(isr);

		//but isr을 제어할 필요가 없어서, 직접적으로 안에 넣어줌
		//연산처리과정 상 마지막 연결된 보조스트림으로 입출력 제어 및 자원 반납
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//BufferedReader가 성능상 좋은 이유 : 사용자 입력값을 라인단위로 처리 가능 -> return을 String단위로 처리할 수 있음
		//내부적으로 buffer가 있고, 거기에 사용자 입력값을 쌓아뒀다가, enter를 기준으로 보여줌
		String data = "";
		//data에 br.readLine을 담아라, 입력값이 없다면, 중지해라
		//예외처리 필요 (IOException)
		System.out.println("키보드로 입력하세요 : ");
		try {
			//int sum = 0;
			while((data = br.readLine()) != null) {
				System.out.println(data);
				//but 숫자로 읽어오려면 형변환 필요
				//sum += Integer.parseInt(data);
				//System.out.println(sum);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//자원반납 -> 마지막에 사용한 보조스트림만 닫아주고, 예외처리 필요
			//표준입출력을 사용하는 String클래스는 반납하면 안됨
			//why? 보조스트림을 여러개 끼우면, 마지막 보조스트림만 닫으면 InputStreamReader, System.in도 함께 닫힘
			//but System.in(표준스트림)은 한번 닫히면 프로그램 종료 전까지는 다시 사용할 수 없음
			//표준입출력을 제외한 모든 io는 닫아줘야함
//			try {
//				br.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
		/* 콘솔출력
		 * 키보드로 입력하세요 : 
		  안녕하세요 // (입력)
		  안녕하세요 // -> 엔터를 기준으로 라인단위로 읽어옴
		  테스트 // (입력)
  		  테스트 // -> 엔터를 기준으로 라인단위로 읽어옴
  		  -> 엔터 쳤을 때만 while이 돌게됨
		 * 
		 */
		
		/* 콘솔출력
		 * 키보드로 입력하세요 : 
			100 //(입력)
			100
			200 //(입력)
			300
			300 //(입력)
			600
		 */
		
		//--> Scanner : 형변환, 예외처리가 이미 되어있어서, 가져다 쓰기만 하면 됨 
	}
}
