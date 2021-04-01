package common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionCounterListener
 *
 */
/**
 * @WebListner annotaion이 있으면 자동으로 등록됨
 */
@WebListener
public class SessionCounterListener implements HttpSessionListener {

	// session 개수 카운팅
	private static int activeSessions;
	// cf. 전역변수는 타입별 초기값으로 알아서 세팅됨 -> 자동으로 0
	
	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     * 세션 생성시
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	activeSessions++;
    	System.out.println("세션 생성! : 현재 세션수는 [" + activeSessions + "]개 입니다.");
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     * 세선 해제시
     * session invalidate처리시 or session timeout 시간 종료시
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	// cf. 개발환경에서는 세션 만들자마자 세션이 해제되는 경우도 있음
    	if(activeSessions > 0)
    		activeSessions--;
    	System.out.println("세션 해제! : 현재 세션수는 [" + activeSessions + "]개 입니다.");
    	
    }
	
}
