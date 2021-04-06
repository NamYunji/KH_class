package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MvcUtils {

	/**
	 * 단방향 암호화 알고리즘 종류
	 * 평문을 주면 암호화된 텍스트를 리턴해주는 함수
	 * - md5
	 * - sha1 (160 byte)
	 * - sha256 (256 byte)
	 * - sha512 (512 byte)
	 * cf. 바이트 수가 높을수록 보안성이 좋음, sha256 이상을 사용하는 것을 추천
	 * 
	 * 1. MessageDigest객체 - 단방향 암호화 처리 - hash값을 만들어줌
	 * MessageDigest의 리턴결과는 2진 데이터 (byte배열)-> 읽어낼 수 없음, 기록하기도 힘듦
	 * 2. Base64 인코딩 처리 : 암호화된 byte[](이진데이터)를 64개의 문자로 변환
	 */
	public static String getSha512(String password) {
		String encryptedPassword = null;
		// 1. 암호화
		MessageDigest md = null;
		// 알고리즘 이름 등록
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			// 등록한 알고리즘 이름을 찾지 못했을 때의 예외
			e.printStackTrace();
		}
		// byte[]
		byte[] bytes = null;
		try {
			// getBytes("어떤 문자로 작성된 평문인지") - 문자열을 byte배열로 변환해주는 메소드
			bytes = password.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// update(전달할 byte배열) - MessageDigest객체에 byte배열 전달
		md.update(bytes);
		// digest() - 암호화처리 -> 인코딩된 암호화 byte배열 리턴
		byte[] encryptedBytes = md.digest();
		// 01로 이루어진 바이트배열이기 때문에 string에 담아서 찍어야 함
		// new String() - byte배열을 문자열로 바꿔줌
		System.out.println("암호화 처리후 : " + new String(encryptedBytes));
		
		// 2. 문자 인코딩 처리
		// Base64 인코더의 static 메소드인 getEncoder() - encoder를 가져옴
		// encodeToString(인코딩 처리할 것) - 인코딩 처리된 문자열 리턴
		encryptedPassword = Base64.getEncoder().encodeToString(encryptedBytes);
		System.out.println("인코딩 처리후 : " + encryptedPassword);
		return encryptedPassword;
	}
}
