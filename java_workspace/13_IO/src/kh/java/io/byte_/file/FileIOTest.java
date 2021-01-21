package kh.java.io.byte_.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileIOTest {

	public static void main(String[] args) {
		FileIOTest f = new FileIOTest();
		f.test1();
		System.out.println("--- 정상종료 ---");
	}

	//FileInputStream : byte기반, 주스트림, input
	public void test1() {
		String filePath = "test.txt";
		//try바깥에 FileInputStream 객체 선언
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(filePath);
			//바이트 기반 -> read한 결과 : int (-1을 처리하기 위해 byte가 아닌, int)
			int data = 0; // 읽어온 데이터를 담을 변수 
			while((data = fis.read()) != -1) {
				System.out.print(data + " ");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		
		finally {
			//모든 stream클래스는 자원반납 필요
			//try에 fis를 선언하면 에러 : fis라는 변수가 없다
			//why? try, catch, finally는 모두 다른 블럭
			//try에 선언하면 다른 데서 못씀 -> try바깥에 선언
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
