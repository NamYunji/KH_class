package com.kh.test.model;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TestDao {
	
	String driverClass = "oracle.jdbc.OracleDriver";

	String url = "jdbc:oracle:thin:@192.168.10.3:1521:xe";

	String user = "kh";

	String password = "kh";
	
	
	public List<Test> selectList() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null; // java.sql.ResultSet
		// 4. PreparedStatement객체생성(미완성쿼리) 및 값대입
		String sql = "select * from test";
		List<Test> list = null;

		try {
			Class.forName(driverClass);

			conn = DriverManager.getConnection(url, user, password);


			pstmt = conn.prepareStatement(sql);

			rset = pstmt.executeQuery();

			list = new ArrayList<>();
			while(rset.next()) {
				Test test = new Test();
				test.setSeq(rset.getInt("seq"));
				test.setWriter(rset.getString("writer"));
				test.setTitle(rset.getString("title"));
				test.setContent(rset.getString("content"));
				test.setRegDate(rset.getDate("regdate"));
				list.add(test);
			}
			// 7. 트랜잭션처리(DQL - 생략)
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 8. 자원반납(생성의 역순)
			try {
				if (rset != null)
					rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
	
	public List<Test> selectList(Connection conn) {
		List<Test> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String  sql = "select * from test";
		
		try {
		// 3. PreparedStatement객체 생성(미완성쿼리)
		pstmt = conn.prepareStatement(sql);
		// 4. 쿼리문 실행
		rset = pstmt.executeQuery();
		// 4.1 ResultSet -> Java객체 옮겨담기
		list = new ArrayList<>();			
		// rset.next() 결과집합이 여러행일 때 다음행 있니? -> 행을 가리키는 포인터를 다음행으로 옮겨주는 역할
		while(rset.next()) {
			Test test = new Test();
			test.setSeq(rset.getInt("seq"));
			test.setWriter(rset.getString("writer"));
			test.setTitle(rset.getString("title"));
			test.setContent(rset.getString("content"));
			test.setRegDate(rset.getDate("regdate"));
			list.add(test);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		// 5. 자원반납(생성역순 rset - pstmt) 
		close(rset);
		close(pstmt);
	}
	return list;
	}
}
