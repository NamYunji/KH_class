package member.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import common.JDBCTemplate;
import member.model.dao.MemberDao;
import member.model.vo.Member;

//static import-> 클래스명 쓰지 않아도 호출가능
//이때 메소드 명까지 써줌 (*)
import static common.JDBCTemplate.*;

/*
 * 첫번째 프로젝트
 * Dao의 역할
 * 1. DriverClass등록 (최초1회)
 * 2. Connection객체 생성 (url, user, password)
 * 2.1. 자동커밋 false설정
 * 3. 미완성커리(?가 있는 커리)를 이용해서 PreparedStatement객체 생성
 * 3.1  ? 값대입
 * 4. 실행 (DML -> int 리턴 | DQL -> ResultSet 리턴)
 * DML실행하는 쿼리 : executeUpdate
 * DQL실행하는 쿼리 : executeQuery
 * 4.1  DQL의 경우 ResultSet을 Java객체로 옮겨담기
 * 5. DML의 경우 트랜잭션처리 (commit / rollback)
 * 6. 자원반납(DML의 경우 pstmt - conn) (DQL의 경우 rset - pstmt - conn) 
 * 	  (생성의 역순)
 * 
 * 
 * 
 */

/*
 * Service
 * 1. DriverClass등록 (최초1회)
 * 2. Connection객체 생성 (url, user, password)
 * 2.1. 자동커밋 false설정
 * ----------Dao 요청-----------
 * 6. DML의 경우 트랜잭션처리 (commit / rollback)
 * 7. 자원반납(conn)
 * 
 * Dao
 * 3. 미완성커리(?가 있는 커리)를 이용해서 PreparedStatement객체 생성
 * 3.1  ? 값대입
 * 4. 실행 (DML -> int 리턴 | DQL -> ResultSet 리턴)
 * DML실행하는 쿼리 : executeUpdate
 * DQL실행하는 쿼리 : executeQuery
 * 4.1  DQL의 경우 ResultSet을 Java객체로 옮겨담기
 * 
 * 5. 자원반납(rset - pstmt) 
 * 
 * 
 */

/*
 * 분기처리
 * 
 * Dao가 하는 일
 * 실제 DB와 연결되는 부분(3~4)
 * 
 * Serive가 하는 일
 * 3~4를 제외한 나머지
 * 그 중 5번 중요! (트랜잭션에서 commit like 결재도장 찍기)
 * 서비스가 주도적으로 일을 하고 DB처리는 DAO에 시킴
 * 
 * 자원반납
 * 
 */

/*
* Service
* 1. DriverClass등록 (최초1회)
* 2. Connection객체 생성 (url, user, password)
* 2.1. 자동커밋 false설정
* ----------Dao 요청-----------
* 6. DML의 경우 트랜잭션처리 (commit / rollback)
* 7. 자원반납(conn)
*/
/*
public class MemberService {
	
	private MemberDao memberDao = new MemberDao();

	public List<Member> selectAll() {
		//필드로 설정
		//1
		String driverClass = "oracle.jdbc.OracleDriver";
		//2
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "student";
		String password = "student";
		Connection conn = null; //import sql.Connection
		
		//1. DriverClass등록 (최초1회)
		try {
			Class.forName(driverClass); //jvm에게 이 클래스가 있으니 가져다 써! 하고 알려주는 과정
			//2. Connection객체 생성 (url, user, password)
			conn = DriverManager.getConnection(url, user, password);
			//2.1. 자동커밋 false설정
			//----------Dao 요청-----------
			//6. DML의 경우 트랜잭션처리 (commit / rollback)
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//7. 자원반납(conn)
		
		return list; //최종적으로 list 리턴
	}

}
*/


public class MemberService {
	
	private MemberDao memberDao = new MemberDao();

		public List<Member> selectAll() {
			//static메소드는 이태릭체
			//Connection conn = JDBCTemplate.getConnection();
			Connection conn = getConnection();
			List<Member> list = memberDao.selectAll(conn);
			//JDBCTemplate.close(conn);
			close(conn);
			return list;
		}
	
	public List<Member> selectAll_() {
		String driverClass = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "student";
		String password = "student";
		Connection conn = null;
		List<Member> list = null;
		
		try {
			//1. DriverClass등록(최초1회)
			Class.forName(driverClass);
			//2. Connection객체생성 url, user, password
			conn = DriverManager.getConnection(url, user, password);
			//2.1 자동커밋 false설정
			conn.setAutoCommit(false);
			//------Dao 요청 -------
			//Connection객체 전달
			list = memberDao.selectAll(conn);
			//6. 트랜잭션처리(DML) commit/rollback
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {			
			//7. 자원반납(conn) 
			try {
				if (conn != null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	


	//매번 중복되는 부분을 처리하려면..?
	//공통된 코드를 미리 구현해두고, 간편하게 가져다 쓸 수 있도록!
	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		
		//DML -> 트랜잭션 처리
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public Member selectOneMember(String memberId) {
		//1. Connection객체 생성
		Connection conn = getConnection();
		//2. dao에 Connection객체, memberId를 전달해서 Member객체를 리턴받음.
		Member m = memberDao.selectOneMember(conn, memberId);
		//3. Connection 자원반납
		close(conn);
		
//		System.out.println("member@service="+m);
		return m;
	}
	

	public List<Member> selectByName(String memberName) {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectByName(conn, memberName);
		close(conn);
		return list;
	}
	
	public Member selectOne(String memberId){
		Connection conn = getConnection();
		Member m = memberDao.selectOne(conn, memberId);
		close(conn);
		return m;
	}
	
	public int updateMember(Member m) {
		Connection conn = getConnection();
		int result = memberDao.updateMember(conn, m);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int deleteMember(String memberId) {
		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn, memberId);
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}	

}
