package common;

import static common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//매번 중복되는 부분을 처리하려면..?
//공통된 코드를 미리 구현해두고, 사용시 간편하게 가져다 쓸 수 있도록!

/*
 * Service, Dao클래스의 공통부분을 static 메소드로 제공
 * 예외처리를 공통부분에서 작성하므로, 사용(호출)하는 쪽의 코드를 간결히 할 수 있다.
 */
public class JDBCTemplate {
	
	//Service단의 필드들을 가져와서 필드로 지정
	
	//String driverClass = "oracle.jdbc.OracleDriver";
	//String url = "jdbc:oracle:thin:@localhost:1521:xe";
	//String user = "student";
	//String password = "student";		
		//static자원에서는 instance변수를 참조를 할 수 없음	
		//instance는 객체가 만들어져야 사용할 수 있지만
		//static은 바로 사용할 수 있기 때문에
		//존재하지 않는 것들을 참조하게 됨
		//참조할 수 있도록 static으로 만들어줌
		
		//static으로 바꿔줌
		static String driverClass = "oracle.jdbc.OracleDriver";
		static String url = "jdbc:oracle:thin:@localhost:1521:xe";
		static String user = "student";
		static String password = "student";


		// <1> DriverClass등록 (최초1회)
		//DriverClass는 최초 1회만 등록해주면 됨
		//매번 호출할 때마다 실행할 필요 없음
		// --> static 초기화 블럭을 이용해서 딱 한번만 등록할 수 있도록 <2>와 분리함
		// --> 클래스가 사용될 때 딱 한번 실행됨
		static {
			try { 
				Class.forName(driverClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		
		// <2> Connection객체 생성 (url, user, password)
		// <2.1> [DML] 자동커밋 false설정
		public static Connection getConnection() {
			
			Connection conn = null;
			
			try {
				// <2> Connection객체 생성 (url, user, password)
				conn = DriverManager.getConnection(url, user, password);
				// <2.1> [DML] 자동커밋 false설정
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return conn;
		}
		
		// <7> Connection만 자원반납 - [MemberService - conn]	
		public static void close(Connection conn) {
			try {
				if (conn != null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// <5> Connection을 제외하고 자원반납 - [Dao - rset]
		public static void close(ResultSet rset) {
			try {
				if(rset != null)
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// <5> Connection을 제외하고 자원반납 - [Dao - pstmt]		
		public static void close(PreparedStatement pstmt) {
			try {
				if(pstmt != null)
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		//<6> [DML] 트랜잭션처리 (commit)					
		public static void commit(Connection conn) {
		
			try {
				if(conn != null)
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//<6> [DML] 트랜잭션처리 (rollback)		
		public static void rollback(Connection conn) {
			
			try {
				if (conn != null)
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}