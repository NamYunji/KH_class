package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import member.model.vo.Member;

//static import / 두개의 메소드 명이 같으므로 close라고만 써줌
import static common.JDBCTemplate.close;

public class MemberDao {
	

	// <3> 미완성커리(?가 있는 커리)를 이용해서 PreparedStatement객체 생성
	// <3.1> [DML] ? 값대입
	// <4> 실행 (DML -> int 리턴 | DQL -> ResultSet 리턴)
	// [DML]executeUpdate
	// [DQL] executeQuery
	// <4.1> [DQL] ResultSet을 Java객체로 옮겨담기
	// <5> Connection을 제외하고 자원반납 (생성 객체의 역순)
	//    [DML] pstmt
	//    [DQL] rset - pstmt 
	
	public List<Member> selectAll(Connection conn) {
		// <3> 미완성커리(?가 있는 커리)를 이용해서 PreparedStatement객체 생성
		//PreparedStatement
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		//sql문
		String sql = "select * from member order by enroll_date desc";
		//리턴값
		List<Member> list = null;
		
		//<3>에 대한 try
		try {
			// <3> 미완성커리(?가 있는 커리)를 이용해서 PreparedStatement객체 생성
			pstmt = conn.prepareStatement(sql);
			// <3.1> [DML] ? 값대입
			//DQL -> 미완성커리가 아니므로 생략
			
			// <4> 실행 (DML -> int 리턴 | DQL -> ResultSet 리턴)
			// [DML]executeUpdate
			// [DQL] executeQuery
			rset = pstmt.executeQuery();
			// <4.1> [DQL] ResultSet을 Java객체(list)로 옮겨담기
			//list를 객체화
			list = new ArrayList<>();
			//while문을 통해 member객체에 한 행씩 옮겨담음
			while (rset.next()) {
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
					//한행 한행의 데이터를 member객체로 바꾸는 것
					//result set - 테이블의 데이터 한 행 = vo의 member객체 한 개
					//테이블 한 행과 member객체 한 개가 짝을 이룬다
					Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
					//list에 추가
					list.add(member);
				}
		} 
		  //<3>에 대한 catch
		  catch (SQLException e) {
			e.printStackTrace();
		} 
		
		  finally {
			//Service, Dao 분기처리만 진행
			// <5> Connection을 제외하고 자원반납 (생성 객체의 역순)
			//    [DML] pstmt
			//    [DQL] rset - pstmt 

			/*
			try {
				if(rset != null) //rset이 생성도 안되었을 경우를 대비하여 try/catch
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
			*/
			  
			//JDBCTemplate 처리 &	static import 처리 후  
			//import static -> 클래스名.메소드名  -> 메소드名
			close(rset);
			close(pstmt);
		}

		return list;
	}



	public int insertMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, default)";
		
		try {
			//PreparedStatment객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,m.getMemberId());
			pstmt.setString(2,m.getPassword());
			pstmt.setString(3,m.getMemberName());
			pstmt.setString(4,m.getGender());
			pstmt.setInt(5,m.getAge());
			pstmt.setString(6,m.getEmail());
			pstmt.setString(7,m.getPhone());
			pstmt.setString(8,m.getAddress());
			pstmt.setString(9,m.getHobby());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
//		System.out.println("result@dao="+result);
		
		return result;
	}

	public Member selectOneMember(Connection conn, String memberId) {
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member where member_id = ?";
		
		try {
			//1. PreparedStatement객체 생성, 미완성 쿼리 값대입
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			//2. 쿼리 실행, ResultSet => Member
			rset = pstmt.executeQuery();
			while(rset.next()) {
				m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setPassword(rset.getString("password"));
				m.setMemberName(rset.getString("member_name"));
				m.setGender(rset.getString("gender"));
				m.setAge(rset.getInt("age"));
				m.setEmail(rset.getString("email"));
				m.setPhone(rset.getString("phone"));
				m.setAddress(rset.getString("address"));
				m.setHobby(rset.getString("hobby"));
				m.setEnrollDate(rset.getDate("enroll_date"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//3. ResultSet, PreparedStatement객체 반납
			close(rset);
			close(pstmt);
		}
		
//		System.out.println("member@dao="+m);
		
		return m;
	}
	
	

	/**
	 * 아이디를 가지고 회원정보조회
	 * 
	 * @param memberId
	 * @return
	 */
	public Member selectOne(Connection conn, String memberId){
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//미리준비된 statement(query)
		String query = "select * from member where member_id=?";
		
		try {
			//미완성쿼리문을 가지고 객체생성함
			pstmt = conn.prepareStatement(query);
			//쿼리문 완성작업
			pstmt.setString(1, memberId);
			//쿼리문실행
			//pstmt에 이제 완성된 쿼리를 가지고 있기때문에 파라미터없이 실행한다.
			rset = pstmt.executeQuery();
			
			if(rset.next()){
				m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setPassword(rset.getString("password"));
				m.setMemberName(rset.getString("member_name"));
				m.setGender(rset.getString("gender"));
				m.setAge(rset.getInt("age"));
				m.setEmail(rset.getString("email"));
				m.setPhone(rset.getString("phone"));
				m.setAddress(rset.getString("address"));
				m.setHobby(rset.getString("hobby"));
				m.setEnrollDate(rset.getDate("enroll_date"));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			
		}
		
		return m;
	}

	public List<Member> selectByName(Connection conn, String memberName) {
		ArrayList<Member> list = null;
		
		//사용후 반납해야할(close)자원들은 try~catch문 바깥에서 선언해야 한다.
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from member where member_name like ?";
		try {
			
			//3. 쿼리문을 실행할 statement객체 생성
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%"+memberName+"%");
			//4. 쿼리문 전송, 실행결과 받기
			rset = pstmt.executeQuery();
			
			//5. 받은 결과값들을 객체에 옮겨 저장하기
			list = new ArrayList<Member>();
			
			while(rset.next()){
				Member m = new Member();
				m.setMemberId(rset.getString("member_id"));
				m.setPassword(rset.getString("password"));
				m.setMemberName(rset.getString("member_name"));
				m.setGender(rset.getString("gender"));
				m.setAge(rset.getInt("age"));
				m.setEmail(rset.getString("email"));
				m.setPhone(rset.getString("phone"));
				m.setAddress(rset.getString("address"));
				m.setHobby(rset.getString("hobby"));
				m.setEnrollDate(rset.getDate("enroll_date"));

				list.add(m);
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int updateMember(Connection conn, Member m) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update member set "
					 + " password=?"
					 + ",email=?"
					 + ",phone=?"
					 + ",address=?"
					 + " where member_id=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, m.getPassword());
			pstmt.setString(2, m.getEmail());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getMemberId());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String memberId) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from member where member_id=?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}	



}
