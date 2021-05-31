package com.kh.spring.log;

import org.apache.log4j.Logger;

/**
 * Logging
 * 서버의 운영로그, 오류로그, 사용자접속로그 등 유용한 정보를 기록
 * - 콘솔로그
 * 	System.out.printxx 보다 효율적인 로그관리가 가능
 *  
 * - 파일로그
 * 
 * 똑같이 찍는 건데 왜 System.out.prinxx보다 효율적인가?
 * 로깅 프레임워크는 Level을 지원함
 * Log4j의 Level(Priority) 우선순위
 * - fatal : 아주 심각한 에러 발생
 * - error : 요청 처리 중 오류 발생
 * - warn  : 경고성 메시지. 현재 실행에는 문제가 없지만 향후 잠재적 오류가 될 가능성이 있음 
 * 			 ex. deprecated같은 경우
 * - info  : 요청 처리 중 상태변경 등의 정보성 메시지
 * - debug : 개발중에 필요한 로그. 운영상에는 필요없음 (돌아가는 것을 확인차 찍는 것)
 * - trace : 개발용. debug의 범위를 한정해서 로깅할 때 사용.
 * 			 ex. 메소드를 여러개 실행하다보면, 어디서부터 어디까지가 이 메소드에서 사용된 것인지 보고 싶을때, 시작과 끝을 trace로 작성해둠
 * 순의 우선순위를 가지고 로깅을 하게 됨
 * 로깅 시 각 용도에 맞게 로깅메시지를 호출해야 함
 *
 * Slf4j : 스프링이 제공하는 로깅 추상체
 * 	(private static final Logger log = LoggerFactory.getLogger(MemberController.class);)
 * Slf4j없이 순수하게 Log4j 프레임워크만 테스트할 것!
 */

public class Log4jTest {
	
	// org.apache.log4j.Logger
	private static final Logger log = Logger.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {
		log.fatal("fatal");
		log.error("error");
		log.warn("warn");
		log.info("info");
		log.debug("debug");
		log.trace("trace");
		
		// [콘솔확인]
		// FATAL: com.kh.spring.log.Log4jTest - fatal
		// ERROR: com.kh.spring.log.Log4jTest - error
		// WARN : com.kh.spring.log.Log4jTest - warn
		// INFO : com.kh.spring.log.Log4jTest - info
		// debug, trace는 찍히지 않음
		// logging level설정이 info로 되어있음 -> info이상의 레벨만 찍힘
		// -> 용도별로 로깅 처리 단계를 기준으로 정해둠 -> sysout보다 효율적 로그관리
		
		// [log4j.xml에서 level을 debug로 변경후 콘솔확인]
		// FATAL: com.kh.spring.log.Log4jTest - fatal
		// ERROR: com.kh.spring.log.Log4jTest - error
		// WARN : com.kh.spring.log.Log4jTest - warn
		// INFO : com.kh.spring.log.Log4jTest - info
		// DEBUG: com.kh.spring.log.Log4jTest - debug
	}
}
