package common;

import static common.JDBCTemplate.getConnection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

//매번 중복되는 부분을 처리하려면..?
//공통된 코드를 미리 구현해두고, 간편하게 가져다 쓸 수 있도록!

/*
 * Service, Dao클래스의 공통부분을 static 메소드로 제공
 * 예외처리를 공통부분에서 작성하므로, 사용(호출)하는 쪽의 코드를 간결히 할 수 있다.
 */
public class JDBCTemplate {
	
	//cf. DB접속하는 정보는 소스코드와 분리하는 것의 의의
	//보안상 민감한 정보를 자바소스와 분리해두면 프로젝트시 소스코드 공유만 공유 가능
	//공유 시 정보를 공유하지 말고, 소스코드만 공유할 것!
	//.gitignore 파일 에 이 파일을 관리하지 않겠다고 명시하기
	
	//Cannot make a static reference to the non-static field driverClass
	//static자원에서는 instance변수를 참조를 할 수 없음	
	//값대입을 하지 않은 상태로 바꿈
	
		static String driverClass;
		static String url;
		static String user;
		static String password;
		
		static {
			
			//java.util.Properties
			//data-source.properties의 내용을 읽어서 변수에 대입함
			Properties prop = new Properties();
			//data-source.properties 파일경로를 변수로 만들어줌
			String fileName = "resources/data-source.properties";
			
			try {
				//prop객체에 읽어옴
				prop.load(new FileReader(fileName));
				//잘 읽어왔는지 출력
				System.out.println(prop);
				//출력 : {driverClass=oracle.jdbc.OracleDriver, user=student, password=student, 
						//url=jdbc:oracle:thin:@localhost:1521:xe}
				//값대입
				driverClass = prop.getProperty("driverClass");
				url = prop.getProperty("url");
				user = prop.getProperty("user");
				password = prop.getProperty("password");
						
			} 
			  //prop.load에 대한 catch
			  catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				//1. DriverClass등록(최초1회)
				Class.forName(driverClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		public static Connection getConnection() {
			Connection conn = null;
			try {
				//2. Connection객체생성 url, user, password
				conn = DriverManager.getConnection(url, user, password);
				//2.1 자동커밋 false설정
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return conn;
		}
		
		public static void close(Connection conn) {
			//7. 자원반납(conn) 
			try {
				if (conn != null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(ResultSet rset) {
			try {
				if(rset != null)
				rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void close(PreparedStatement pstmt) {
			try {
				if(pstmt != null)
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		public static void commit(Connection conn) {
		
			try {
				if(conn != null)
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public static void rollback(Connection conn) {
			
			try {
				if (conn != null)
				conn.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}