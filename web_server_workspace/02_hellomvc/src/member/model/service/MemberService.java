package member.model.service;

import java.sql.Connection;

import member.model.dao.MemberDao;
import member.model.vo.Member;

//static import-> 클래스명 쓰지 않아도 호출가능
//이때 메소드 명까지 써줌 (*)
import static common.JDBCTemplate.*;

/*
* 1. DriverClass등록 (최초1회)
* 2. Connection객체 생성 (url, user, password)
* 2.1. 자동커밋 false설정
* ----------Dao 요청-----------
* 6. DML의 경우 트랜잭션처리 (commit / rollback)
* 7. 자원반납(conn)
*/

public class MemberService {
	// Dao 객체 생성
	private MemberDao memberDao = new MemberDao();
	
	// 이 상수를 가져와서 사용할 것
	public static final String MEMBER_ROLE = "U";
	public static final String ADMIN_ROLE = "A";
	
	public Member selectOne(String memberId) {
		// 1. DriverClass등록 (최초1회) -> JDBCTemplate에서 진행함
		// 2. connection 객체 생성
		Connection conn = getConnection();
		// ----------Dao 요청-----------
		// dao에 Connection객체, memberId를 전달해서 Member객체를 리턴받음.
		Member member = memberDao.selectOne(conn, memberId);
		// 6. Connection 자원반납
		close(conn);
		// Member 객체 리턴
		return member;
	}

	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		close(conn);
		
		return result;
	}
}
