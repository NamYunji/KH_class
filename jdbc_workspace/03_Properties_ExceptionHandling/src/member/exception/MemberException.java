package member.exception;

/*
 * checkedException : extends Exception
 * uncheckedException : extends RuntimeException
 */
public class MemberException extends RuntimeException {
//throwable cause : 애초에 발생한 exception
	
	public MemberException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MemberException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
