package common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTemplate {
	
	static String driverClass = "oracle.jdbc.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static String user = "student";
	static String password = "student";
	
	// <1> DriverClass등록 (최초1회)
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
	
	// <7> Connection만 자원반납 - [ProdService - conn]	
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
