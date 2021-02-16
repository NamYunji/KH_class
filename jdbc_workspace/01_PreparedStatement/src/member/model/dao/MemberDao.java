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
 * -data에 접근하는 객체  = data가 있는 DB에 접근하는 클래스
 * -DB접근하는 일은 DAO가 혼자 다함
 * controller나 view에서 DB에 직접 접근하게 하면 안됨!
 */

/*
 * DAO의 처리 순서
 * 
 * 1. 드라이버 클래스 등록 (최초 1회)
 *    드라이버 클래스 = ojdb6.jar 안에 있는 시작점 같은 클래스
 * 2. Connection 객체 생성 (접속하고자 하는 db server의 url, user, password가 필요함)
 * 3. 자동커밋여부 설정 : false -> app에서 직접 트랜잭션 제어 
 * 				   (기본값 : true / 아무것도 안쓰면 true -> 자동커밋처리됨)
 * 4. PreparedStatement 객체생성 (이때 미완성쿼리 필요 | 미완성 : 값대입이 아직 안된 상태) 및 값대입
 * 5. 쿼리실행 - Statement객체 실행. (실제로 DB에 쿼리 요청을 보내는 과정)
 * 6. 응답에 대한 처리 ) DML : int 리턴 
 * 				   DQL : ResultSet 리턴 (-> 다시 자바객체로 전환하는 과정 필요)
 * 7. (DML의 경우) 트랜잭션 처리
 * 8. 자원반납 (생성의 역순)
 */

public class MemberDao {

	// 다른 메소드에서도 이용할 수 있도록 필드로 설정
	// driverClass, url/user/password
	String driverClass = "oracle.jdbc.OracleDriver";

	String url = "jdbc:oracle:thin:@localhost:1521:xe"; // [접속할 DB의 주소] DB_Driver타입 @아이피주소or도메인:포트번호:DB이름
	String user = "student"; // 대소문자 구분X
	String password = "student"; // 대소문자 구분

	public int insertMember(Member member) {

		// finally절에서도 접근할 수 있도록
		// 지역변수로 선언
		// Connection 객체, sql, PreparedStatement, result변수
		Connection conn = null;
		String sql = "insert into member values (?, ?, ?, ?, ?, ?, ?, ?, ?, default)";
		PreparedStatement pstmt = null;
		int result = 0;

//1. 드라이버 클래스 등록 (최초 1회)
		// 등록한다는 것 : 클래스 객체로 만든다는 것
		// 메소드 : Class.forName();
		try { // classNotFoundException - 예외처리 강제화 - try/catch
			Class.forName(driverClass); // 이런 패키지에 이런 클래스가 있습니다 하고 jdbc에 소개해주는 것

//2. Connection 객체 생성 (접속하고자 하는 db server의 url, user, password가 필요함)
			// url, user, password
			// Connection 객체 생성 (java.sql.Connection)
			conn = DriverManager.getConnection(url, user, password);
			// SqlException (DB접속 관련 최상위 예외 클래스) - add catch clause
//3. 자동커밋여부 설정 : false -> app에서 직접 트랜잭션 제어 
			// (dml일 경우에만 유효)
			// setAutoCommit(true) - 기본값
			// setAutoCommit(false) - 실행 결과에 따라 직접 commit/rollback 할 수 있도록 설정
			conn.setAutoCommit(false);
//4. PreparedStatement 객체생성 
			// (이때, 미완성쿼리 필요, 미완성 : 값대입이 아직 안된 상태) 및 값대입
			// 미완성 쿼리
			// String 변수 = "전달할 sql문";
			// "insert into 테이블名 values (?, ?, ?, ?, ?, ?, ?, ?, ?, default)";
			// 실제 값이 들어가야 할 부분은 물음표로 처리)
			// 주의 : 세미콜론을 문자열 안에 찍지 않음
			pstmt = conn.prepareStatement(sql);
			// PreparedStatement 변수 = Connection객체.preparedStatement(미완성쿼리 변수); -> 쿼리를 실행하는
			// 객체
			// 물음표 자리에 실제 데이터 하나씩 대입
			// 변수.set자료형(자리수, parameter.getter());
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			// enrollDate는 default(sysdate)사용

//5. 쿼리실행 - Statement객체 실행. 실제로 DB에 쿼리 요청을 보냄
//6. 응답에 대한 처리  ) DML : int 리턴 | DQL : ResultSet 리턴 (-> 다시 자바객체로 전환하는 과정 필요)
			// 과정 5-6 한번에 처리
			result = pstmt.executeUpdate();
			// dml인 경우는 executeUpdate, dql인 경우는 executeQuery
			// 정상 실행되면 1 리턴, 정상 실행 안되면 오류
			// 정상 처리 시 커밋
			if (result > 0)
				conn.commit();
			// 오류 시 롤백
			else
				conn.rollback();
//7. (DML의 경우) 트랜잭션 처리

		} catch (ClassNotFoundException e) {
			// ojdbc6.jar를 프로젝트와 연동실패 시 ClassNotFoundException 발생
			// Referenced Librarires에 ojdbc6.jar를 등록이 제대로 안됐을 때
			e.printStackTrace();
		}
		// 예외처리 강제화
		// SQLException : 최상위 Exception
		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
//8. 자원반납 (생성의 역순)			
			try {
				if (pstmt != null)
					pstmt.close(); // try/catch
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (conn != null)
					conn.close(); // try/catch
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

	public List<Member> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null; // java.sql.ResultSet
		// 4. PreparedStatement객체생성(미완성쿼리) 및 값대입
		String sql = "select * from member order by enroll_date desc";
		List<Member> list = null;

		try {
			// 1. 드라이버클래스 등록(최초1회)
			Class.forName(driverClass);

			// 2. Connection객체 생성(url, user, password)
			conn = DriverManager.getConnection(url, user, password);

			// 3. 자동커밋여부 설정 : true(기본값)/false -> app에서 직접 트랜잭션 제어
			// dql -> 트랜잭션 처리 불필요 -> setAutoCommit x

			// 4. PreparedStatement객체생성(미완성쿼리) 및 값대입
			pstmt = conn.prepareStatement(sql);
			// 물음표 없음. 채워놓을 데이터 없음 -> 값대입 생략
			// 5. Statement객체 실행. DB에 쿼리 요청
			// dql -> executeQuery
			rset = pstmt.executeQuery();
			// resultSet에 저장
			// 6. 응답처리 DML:int리턴, DQL:ResultSet리턴->자바객체로 전환
			// ResultSet : 테이블 자체가 리턴된 것
			// 반복문을 통해 한 행 한 행 접근해서 값들을 읽어온 후 멤버 객체로 바꿔줌
			// 멤버 객체를 리스트에 추가
			list = new ArrayList<>();
			// 다음행 존재여부리턴. next 호출시 커서가 다음행에 접근.
			while (rset.next()) {
				// rset.next - 다음행이 있니? 있으면 true 리턴 -> 커서가 다음 행으로 이동 -> 그 행의 정보를 가져옴
				// -> ...다음행이 없을 때까지 -> false 리턴 -> while문 종료
				// 컬럼명은 대소문자를 구분하지 않는다.
				String memberId = rset.getString("member_id"); // ("컬럼 인덱스 / 컬럼 이름")
				String password = rset.getString("password"); // 컬럼 이름으로 하는걸 추천, 인덱스 번호가 바뀌어도 영향 X
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");

				// 행에서 추출한 데이터들로 Member객체 생성
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby,
						enrollDate);
				// list에 추가
				list.add(member);
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

	public Member selectOne(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null; // 한 행이라도 resultSet에 담겨서 옴
		String sql = "select * from member where member_id = ?";
		// 정확한 아이디를 입력했을 때 하나만 리턴하는 거니까 where절 필요
		Member member = null; // 리스트가 아닌, 멤버 객체변수
		// 최종적으로 Member를 리턴함

		try {
			// 1. 드라이버클래스 등록(최초1회)
			Class.forName(driverClass);
			// 2. Connection객체 생성(url, user, password)
			// 3. 자동커밋여부 설정 : true(기본값)/false -> app에서 직접 트랜잭션 제어
			conn = DriverManager.getConnection(url, user, password);

			// 4. PreparedStatement객체생성(미완성쿼리) 및 값대입
			pstmt = conn.prepareStatement(sql);
			// 쿼리에 물음표가 있음 -> 값대입 필요
			pstmt.setString(1, memberId);
			// ex. honggd -> 'honggd'문자열로 바꿔줌
			// "select * from member where member_id = 'honggd'"

			// 5. Statement객체 실행. DB에 쿼리 요청
			rset = pstmt.executeQuery();
			// 6. 응답처리 DML:int리턴, DQL:ResultSet리턴->자바객체로 전환
			// 다음행 존재여부리턴. 커서가 다음행에 접근.
			// 조회된 행이 있으면 while문 한 번 실행, 없으면 실행하지 않고 지나침
			while (rset.next()) {
				// 컬럼명은 대소문자를 구분하지 않는다.
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

				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby,
						enrollDate);
			}
			// 7. 트랜잭션처리(DML)
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

		return member;
	}

	public List<Member> selectByName(String memberName) {
		List<Member> list = new ArrayList<Member>();
		
		//사용후 반납해야할(close)자원들은 try~catch문 바깥에서 선언해야 한다.
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from member where member_name like ?";
		try {
			//2. 등록된 클래스를 이용해서 db연결함. 
			//통행권 Connection객체 생성
			conn = DriverManager.getConnection(url, user, password);
			
			//3. 쿼리문을 실행할 statement객체 생성
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + memberName + "%");
			
			//4. 쿼리문 전송, 실행결과 받기
			rset = pstmt.executeQuery();
			
			//5. 받은 결과값들을 객체에 옮겨 저장하기
			//rset가 null인경우는 모두 Exception처리된다.
			
			while(rset.next()){
				//커서가 가리키는 다음행에서 컬럼 정보를 읽어온다.
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member m = new Member(memberId, password, memberName, 
									  gender, age, email, phone, 
									  address, hobby, enrollDate);
				list.add(m);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			//자원반납 순서는 생성의 역순이다.
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	
	public int updateMember(Member m) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		//키워드, 값 사이의 공란주의
		String query = "update member set "
					 + " password=?"
					 + ",email=?"
					 + ",phone=?"
					 + ",address=?"
					 + " where member_id=?";
		
		try {
			//2. 드라이버매니져로부터 connection객체 얻기
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			//3. 미완성쿼리 생성 및 갑대입, 실행
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getPassword());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getMemberId());
			result = pstmt.executeUpdate();
			
			//커밋처리
			if(result>0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int deleteMember(String memberId) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String query = "delete from member where member_id=?";
		try {
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();
			
			//커밋처리
			if(result>0) conn.commit();
			else conn.rollback();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
