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
//import static 패키지名.클래스名.메소드名;
import static common.JDBCTemplate.*;

/*
 * DAO의 처리 순서
 * 
 * <1> 드라이버 클래스 등록 (최초 1회)
 *    드라이버 클래스 = ojdb6.jar 안에 있는 시작점 같은 클래스
 * <2> Connection 객체 생성 (접속하고자 하는 db server의 url, user, password가 필요함)
 * <3> 자동커밋여부 설정 : false -> app에서 직접 트랜잭션 제어 
 * 				   (기본값 : true / 아무것도 안쓰면 true -> 자동커밋처리됨)
 * <4> PreparedStatement 객체생성 (이때 미완성쿼리 필요 | 미완성 : 값대입이 아직 안된 상태) 및 값대입
 * <5> 쿼리실행 - Statement객체 실행. (실제로 DB에 쿼리 요청을 보내는 과정)
 * <6> 응답에 대한 처리 ) DML : int 리턴 
 * 				   DQL : ResultSet 리턴 (-> 다시 자바객체로 전환하는 과정 필요)
 * <7> (DML의 경우) 트랜잭션 처리
 * <8> 자원반납 (생성의 역순)
 */

/*
 * 분기처리
 * 
 * --------------- < SERVICE > ---------------
 * <1> DriverClass등록 (최초1회)
 * <2> Connection객체 생성 (url, user, password)
 * <2.1> [DML] 자동커밋 false설정
 * --------------- D A O 요 청 코 드 --------------- 
 * <6> [DML] 트랜잭션처리 (commit / rollback)
 * <7> 자원반납 (생성 객체의 역순)
 *    [DML] conn 
 *    [DQL] conn
 *    
 *    
 *    
 * --------------- < D  A  O > ---------------
 * <3> 미완성커리(?가 있는 커리)를 이용해서 PreparedStatement객체 생성
 * <3.1> [DML] ? 값대입
 * <4> 실행 (DML -> int 리턴 | DQL -> ResultSet 리턴)
 * DML실행하는 쿼리 : executeUpdate
 * DQL실행하는 쿼리 : executeQuery
 * <4.1> [DQL] ResultSet을 Java객체로 옮겨담기
 * <5> Connection을 제외하고 자원반납 (생성 객체의 역순)
 *    [DML] pstmt
 *    [DQL] rset - pstmt 
 *    
 * cf. 자원반납
 * 자기가 맡은 객체만 반납함
 * <SERVICE> Connection 객체 생성 -> Connection 객체 반납
 * <DAO> PreparedStatement, ResultSet 객체 생성 -> ResultSet, PreparedStatement 객체 반납
 * 
 */





public class MemberService {

	// Dao 객체 생성
	private MemberDao memberDao = new MemberDao();

	
	/*
	 * -------------------------------------------------------------------
	 * --------------------- Service, Dao 분기 처리만 진행 ---------------------
	 * -------------------------------------------------------------------
	 */
	public List<Member> selectAll_() {
		
//지역변수 영역///////////////////////////////////////////////////////
		// <1> DriverClass등록 (최초1회)		
		String driverClass = "oracle.jdbc.OracleDriver";
		// <2> Connection객체 생성 (url, user, password)
		//url, user, password
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "student";
		String password = "student";
		//connection 객체
		Connection conn = null;
		//리턴값
		List<Member> list = null;
		
//과정 진행/////////////////////////////////////////////////////////
		
		//<1>에 대한 try
		try { 
			// <1> DriverClass등록 (최초1회)
			//jvm에게 이 클래스가 있으니 이따 가져다 써! 하고 알려주는 것
			Class.forName(driverClass);
			
			// <2> Connection객체 생성 (url, user, password)
			conn = DriverManager.getConnection(url, user, password);
			
			// <2.1> [DML] 자동커밋 false설정
			//DQL이므로 필요없으나 그냥 해줌
			//DQL의 경우, setAutoCommit을 false라고 해도 commit처리 안해줘도 커밋됨
			//setAutoCommit : 한 connection에 실행하는 쿼리가 여러개 있다면, 실행하는 족족 커밋시킴(true)
			conn.setAutoCommit(false); 

			// --------------- D A O 요 청 코 드 --------------- 
			//Connection객체 전달
			//memberDao에 동일한 메소드 요청
			//**이 때 conn을 전달함
			//connection 객체를 전달하지 않으면, dao는 분담하는 일을 아무것도 못함
			list = memberDao.selectAll(conn);
			
			// <6> [DML] 트랜잭션처리 (commit / rollback)
			// DQL - 건너뜀
			
		} 
		
		//<1>에 대한 catch
		//ojdbc6.jar가 연동되지 않았을 때, 클래스를 찾을 수 없으니 오류
		  catch (ClassNotFoundException e) {
			e.printStackTrace();
		//<2>에 대한 catch
		//PrepareStatement가 실행되려면 Connection이 전제조건임
		//connection : java app에서 db server로의 연결을 위한 통로역할
		//통로가 있어야 내용물이 왔다갔다 할 수 있음
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		  finally {			
			// <7> 자원반납 (생성 객체의 역순)
//		    [DML] conn 
//		    [DQL] conn
			try {
				if (conn != null)
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	/*
	 * -------------------------------------------------------------------
	 * ------------------------ JDBC Template 사용 ------------------------
	 * -------------------------------------------------------------------
	 */	
	public List<Member> selectAll() {
		
		// <1> DriverClass등록 (최초1회)
		// <2> Connection객체 생성 (url, user, password)
		// <2.1> [DML] 자동커밋 false설정
		// +
		//static import 전  호출 : 클래스名.static메소드();
		// Connection conn = JDBCTemplate.getConnection();
		//static import 후  호출 : static메소드();
		//클래스名없이 메소드로만 가져다 쓸 수 있다
		Connection conn = getConnection();
		//Dao 요청
		List<Member> list = memberDao.selectAll(conn);
		// <7> 자원반납 (생성 객체의 역순)
		// JDBCTemplate.close(conn);
		close(conn);
		return list;
	}


	//매번 중복되는 부분을 처리하려면..?
	//공통된 코드를 미리 구현해두고, 간편하게 가져다 쓸 수 있도록!
	public int insertMember(Member member) {
		// <1> DriverClass등록 (최초1회)
		// <2> Connection객체 생성 (url, user, password)
		// <2.1> [DML] 자동커밋 false설정
		Connection conn = getConnection();
		//Dao 요청
		int result = memberDao.insertMember(conn, member);
		
		//<6> [DML] 트랜잭션처리 (commit / rollback)	
		//JDBCTemplate 처리 전
		/*
		if (result > 0) {
			try {
				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			else {
				try {
					conn.rollback();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		*/
		
		//JDBCTemplate 처리 후		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		// <7> 자원반납 (생성 객체의 역순)
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
