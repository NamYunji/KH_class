package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import member.model.vo.Member;


/*
 * DAO
 * Data Access Object
 * -data에 접근하는 객체
 * -data가 있는 DB에 접근하는 클래스
 * -DB접근하는 일은 DAO가 혼자 다함
 * controller나 view에서 DB에 직접 접근하게 하면 안됨!
 */

/*
 * 1. 드라이버 클래스 등록 (최초 1회)
 * 2. Connection 객체 생성 (접속하고자 하는 db server의 url, user, password가 필요함)
 * 3. 자동커밋여부 설정 : false -> app에서 직접 트랜잭션 제어 (기본값 : true, 아무것도 안쓰면 true로 처리됨)
 * 4. PreparedStatement 객체생성 (이때, 미완성쿼리 필요, 미완성 : 값대입이 아직 안된 상태) 및 값대입
 * 5. 쿼리실행 - Statement객체 실행. 실제로 DB에 쿼리 요청을 보냄
 * 6. 응답에 대한 처리  ) DML : int 리턴 | DQL : ResultSet 리턴 (-> 다시 자바객체로 전환하는 과정 필요)
 * 7. (DML의 경우) 트랜잭션 처리
 * 8. 자원반납 (생성의 역순)
 */
/*
public class MemberDao {

	public int insertMember(Member member) {

//1. 드라이버 클래스 등록 (최초 1회)
		String driverClass = "oracle.jdbc.OracleDriver";
		//등록한다는 것 : 클래스를 만든다는 것
		try {
			Class.forName(driverClass); //이런 패키지에 이런 클래스가 있습니다 하고 jdbc에 소개해주는 것
//2. Connection 객체 생성 (접속하고자 하는 db server의 url, user, password가 필요함)
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; //dbDriver타입 @아이피주소or도메인:포트번호:DB이름
		String user = "student"; //대소문자 구분X
		String password = "student"; //대소문자 구분
		Connection conn = DriverManager.getConnection(url, user, password);
//3. 자동커밋여부 설정 : false -> app에서 직접 트랜잭션 제어 (기본값 : true, 아무것도 안쓰면 true로 처리됨)
		//(dml일 경우에만 유효)
		conn.setAutoCommit(false);
//4. PreparedStatement 객체생성 (이때, 미완성쿼리 필요, 미완성 : 값대입이 아직 안된 상태) 및 값대입
		String sql = "insert into member values (?, ?, ?, ?, ?, ?, ?, ?, ?, default)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, member.getMemberId()); //물음표 자리에 실제 데이터 대입
		pstmt.setString(2, member.getPassword());
		pstmt.setString(3, member.getMemberName());
		pstmt.setString(4, member.getGender());
		pstmt.setInt(5, member.getAge());
		pstmt.setString(6, member.getEmail());
		pstmt.setString(7, member.getPhone());
		pstmt.setString(8, member.getAddress());
		pstmt.setString(9, member.getHobby());
//5. 쿼리실행 - Statement객체 실행. 실제로 DB에 쿼리 요청을 보냄
//6. 응답에 대한 처리  ) DML : int 리턴 | DQL : ResultSet 리턴 (-> 다시 자바객체로 전환하는 과정 필요)
		int result = pstmt.executeUpdate(); //dml인 경우는 executeUpdate, dql인 경우는 executeQuery
		//정상 실행되면 1 리턴, 정상 실행 안되면 오류
		if(result > 0)
			conn.commit();
		else
			conn.rollback();
//7. (DML의 경우) 트랜잭션 처리
			
		} catch (ClassNotFoundException e) {
			//ojdbc.jar 프로젝트 연동실패!
			e.printStackTrace();
		}
		//예외처리 강제화
		//SQLException : 최상위 Exception
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//8. 자원반납 (생성의 역순)			
			pstmt.close();
			conn.close();
		}
		
		return 0;
	}

}
*/





/**
 * DAO
 * Data Access Object
 * DB에 접근하는 클래스
 * 
 * 1. 드라이버클래스 등록(최초1회)
 * 2. Connection객체 생성(url, user, password) 
 * 3. 자동커밋여부 설정 : true(기본값)/false -> app에서 직접 트랜잭션 제어
 * 4. PreparedStatement객체생성(미완성쿼리) 및 값대입
 * 5. Statement객체 실행. DB에 쿼리 요청
 * 6. 응답처리 DML:int리턴, DQL:ResultSet리턴->자바객체로 전환
 * 7. 트랜잭션처리(DML)
 * 8. 자원반납(생성의 역순)
 *
 */
public class MemberDao {
	String driverClass = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "student";
	String password = "student";

	public int insertMember(Member member) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, default)";
		int result = 0;
		
		try {
			//1. 드라이버클래스 등록(최초1회)
			Class.forName(driverClass);
			//2. Connection객체 생성(url, user, password)
			conn = DriverManager.getConnection(url, user, password);
			//3. 자동커밋여부 설정(DML) : true(기본값)/false -> app에서 직접 트랜잭션 제어
			conn.setAutoCommit(false);
			//4. PreparedStatement객체생성(미완성쿼리) 및 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			
			//5. Statement객체 실행. DB에 쿼리 요청
			//6. 응답처리 DML:int리턴, DQL:ResultSet리턴->자바객체로 전환
			result = pstmt.executeUpdate();
			
			//7. 트랜잭션처리(DML)
			if(result > 0)
				conn.commit();
			else 
				conn.rollback();

		} catch (ClassNotFoundException e) {
			//ojdbc6.jar 프로젝트 연동실패!
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//8. 자원반납(생성의 역순)
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<Member> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by enroll_date desc";
		List<Member> list = null;
		
		try {
			//1. 드라이버클래스 등록(최초1회)
			Class.forName(driverClass);
			//2. Connection객체 생성(url, user, password) 
			//3. 자동커밋여부 설정 : true(기본값)/false -> app에서 직접 트랜잭션 제어
			conn = DriverManager.getConnection(url, user, password);
			//4. PreparedStatement객체생성(미완성쿼리) 및 값대입
			pstmt = conn.prepareStatement(sql); 
			//5. Statement객체 실행. DB에 쿼리 요청
			rset = pstmt.executeQuery();
			//6. 응답처리 DML:int리턴, DQL:ResultSet리턴->자바객체로 전환
			//다음행 존재여부리턴. 커서가 다음행에 접근.
			list = new ArrayList<>();
			while(rset.next()) {
				//컬럼명은 대소문자를 구분하지 않는다.
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			//7. 트랜잭션처리(DML)
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//8. 자원반납(생성의 역순)
			try {
				if(rset != null)
					rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	public Member selectOne(String memberId) {		
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rset = null;
	String sql = "select * from member where member_id = ?";
	Member member = null;
	
	try {
		//1. 드라이버클래스 등록(최초1회)
		Class.forName(driverClass);
		//2. Connection객체 생성(url, user, password) 
		//3. 자동커밋여부 설정 : true(기본값)/false -> app에서 직접 트랜잭션 제어
		conn = DriverManager.getConnection(url, user, password);
		//4. PreparedStatement객체생성(미완성쿼리) 및 값대입
		pstmt = conn.prepareStatement(sql); 
		pstmt.setString(1, memberId); //"select * from member where member_id = 'honggd'" 
		//5. Statement객체 실행. DB에 쿼리 요청
		rset = pstmt.executeQuery();
		//6. 응답처리 DML:int리턴, DQL:ResultSet리턴->자바객체로 전환
		//다음행 존재여부리턴. 커서가 다음행에 접근.
		while(rset.next()) {
			//컬럼명은 대소문자를 구분하지 않는다.
			memberId = rset.getString("member_id");
			String password = rset.getString("password");
			String memberName = rset.getString("member_name");
			String gender = rset.getString("gender");
			int age = rset.getInt("age");
			String email = rset.getString("email");
			String phone = rset.getString("phone");
			String address = rset.getString("address");
			String hobby = rset.getString("hobby");
			Date enrollDate = rset.getDate("enroll_date");
			
			member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
		}
		//7. 트랜잭션처리(DML)
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		//8. 자원반납(생성의 역순)
		try {
			if(rset != null)
				rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	return member;
}

}
