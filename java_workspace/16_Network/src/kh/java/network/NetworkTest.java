package kh.java.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class NetworkTest {

	public static void main(String[] args) {
		NetworkTest n = new NetworkTest();
//		n.test1();
//		n.test2();
		n.test3();
		
	}
	
	/*
	 * URL 연결 요청
	 * -> 그 결과를 파일로 저장까지
	 */
	private void test3() {
		String address = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%BD%94%EB%A1%9C%EB%82%98";
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			URL url = new URL(address);
			//url 연결시키기
			URLConnection conn = url.openConnection();
			//요청을 받아와서 읽어올 준비
			//대상 : 외부연결. 프로그램으로부터 연결로부터 얻어온 인풋스트림이 필요한 것
			//연결요청을 하고 돌아온 응답을 대상으로 한 것 , 그 연결로 부터 읽어들이겠다
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            bw = new BufferedWriter(new FileWriter("search_result.html"));
			
            String data = "";
			while((data = br.readLine()) != null) {
				//받아옴
//				System.out.println(data);
				bw.write(data);
				bw.write("\n");
			}
			
			System.out.println("검색완료!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		finally {
			try {
			bw.close();
			br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * URL
	 * protocol + hostname + port + 자원에 대한 path들을 관리해주는 자바 클래스
	 * https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
	 * 프로토콜 : https:// 요청에 대한 format을 제한
	 * 호스트네임 : docs.oracle.com
	 * 포트 생략 (포트 : 서비스번호)
	 * :433 -> 프로토콜에 대한 기본포트는 생략가능 https-433 http-80, ftp-20
	 * 해당자원에 대한 path : /javase/8/docs/api/java/util/ArrayList.html
	 * 
	 * protocol : 통신규약 http https ftp.. 
	 * like 한 전화번호로 전화도 할 수 있고, 문자도 보낼 수 있는 것 처럼
	 * port : 서비스 번호 (컴퓨터내의 특정프로그램을 가리키는 논리적인 번호)
	 * 		정보교환하는데 있어서의 연결통로, 이 프로그램 할 때는 이 통로로 정보를 보낼게요~~라고 정해놓는 것
	 * 
	 * 
	 */
	
	//URL Parsing
	private void test2() {
		try {
//			URL url = new URL("https://docs.oracle.com:443/javase/8/docs/api/java/util/ArrayList.html");
			//프로토콜 부분만 조각내서 출력
			URL url = new URL("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%BD%94%EB%A1%9C%EB%82%98");
			//물음표 뒷부분 부터는 사용자 입력값
			// ?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EC%BD%94%EB%A1%9C%EB%82%98
			System.out.println(url.getProtocol());
			//도메인부분만 출력
			System.out.println(url.getHost());
			//포트부분만 출력 (:433을 따로 추가해줘야 나옴)
			System.out.println(url.getPort());
			//:433이 생략된 경우 (프로토콜에 대한 기본포트가 생략되었음)
			System.out.println(url.getDefaultPort());
			//path부분만 출력
			System.out.println(url.getPath());
			//사용자입력값
			//한글 입력값부분은 유니코드 문자로 변환돼서 전송되고 받아짐 (%EC%BD%94%EB%A1%9C%EB%82%98)
			System.out.println(url.getPath());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * InetAddress
	 * IP주소에 대한 정보를 가진 클래스
	 * IP & HostName
	 * ip : 4바이트로 이루어진 실체 주소
	 * ex. 10.10.10.10
	 * 4바이트 : int와 똑같은 크기 -> 255보다 작은수여야 함
	 * 총 경우의 수 : 42억개 - version 4
	 * 최근에는 version 6를 사용하는 6byte 형태도 나옴
	 * 
	 * window + r -> cmd창 -> ip config
	 *    IPv4 주소 . . . . . . . . . : 192.168.35.117
	 * 
	 * 모든 메소드가 static이라 객체 만들 필요 无, but 만들어줘도 됨
	 * 
	 * 주소를 가지고 ip주소를 관리하는 서버가 따로 있음 (DNS Domian Name Server)
	 * my pc(naver.com) -> dns -> naver.com
	 * 
	 * hostname : 흔히 말하는 도메인 (for 사람. 사람이 이해하기에 좋음)
	 * ex. naver.com, iei.or.kr
	 * but 실제로 컴퓨터가 찾아가는 주소는 ip
	 */
	public void test1() {
		InetAddress naver;
		try {
			naver = InetAddress.getByName("naver.com");
			System.out.println(naver.getHostAddress());
			
			InetAddress[] arr = InetAddress.getAllByName("naver.com");
			//출력 결과 : 4개 (연결된 서버가 4개가 있음)
			System.out.println(arr.length);
			for(InetAddress ip : arr)
				//그 4개 서버를 출력함
				System.out.println(ip.getHostAddress());
			//내 컴퓨터의 ip주소
			InetAddress localhost = InetAddress.getLocalHost();
			//cmd에서 본 ip와 동일한 주소가 콘솔창에 출력됨
			System.out.println(localhost.getHostAddress());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	

}
