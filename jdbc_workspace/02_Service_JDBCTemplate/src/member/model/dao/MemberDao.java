package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import member.model.vo.Member;

import static common.JDBCTemplate.close;

public class MemberDao {

	public List<Member> selectAll(Connection conn) {
		//3
		PreparedStatement pstmt = null;
		//4
		ResultSet rset = null;
		String sql = "select * from member order by enroll_date desc";
		List<Member> list = null;
		
		 //3. 미완성커리(?가 있는 커리)를 이용해서 PreparedStatement객체 생성
		try {
			pstmt = conn.prepareStatement(sql);
			//3.1  ? 값대입
			//미완성커리가 아니므로 생략
			//4. 실행 (DML -> int 리턴 | DQL -> ResultSet 리턴)
			rset = pstmt.executeQuery();
			//DML실행하는 쿼리 : executeUpdate
			//DQL실행하는 쿼리 : executeQuery
			
			//4.1  DQL의 경우 ResultSet을 Java객체로 옮겨담기
			list = new ArrayList<>();
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
					//테이블의 데이터 한 행 = vo의 member객체 한 개
					Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
					list.add(member);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			 //5. 자원반납(rset - pstmt) 
			/*
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
			*/
			//import static -> 클래스名.메소드名  -> 메소드名
			close(rset);
			close(pstmt);
		}

		return list;
	}

	public int insertMember(Connection conn, Member member) {
		
		return 0;
	}

}
