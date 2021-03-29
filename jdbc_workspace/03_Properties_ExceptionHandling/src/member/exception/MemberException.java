package member.exception;

/*
 * Exception을 만드는 방법
 * checkedException : extends Exception
 * uncheckedException : extends RuntimeException
 */
public class MemberException extends RuntimeException {
	
	// 생성자 만들기
	public MemberException() {
		super();
	}

	// throwable cause : 애초에 발생한 exception
	// - 지금은 sqlException이 처음 발생했고, 그것을 MemberException에 담아서 보냄
	public MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MemberException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemberException(String message) {
		super(message);
	}

	public MemberException(Throwable cause) {
		super(cause);
	}
}
