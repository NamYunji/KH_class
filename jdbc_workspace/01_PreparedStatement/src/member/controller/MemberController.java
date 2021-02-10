package member.controller;

import member.model.dao.MemberDao;
import member.model.vo.Member;
import java.util.List;


/*
 * MVC(MODEL VIEW CONTROLLER) 패턴의 시작점이자 전체흐름을 제어 
 * view단으로부터 요청을 받아서 dao에 다시 요철
 * 그 결과를 view단에 다시 전달
 */
		
		public class MemberController {
			private MemberDao memberDao = new MemberDao();

			public int insertMember(Member member) {
				return memberDao.insertMember(member);
			}


//1. 드라이버 클래스 등록 (최초 1회)
//2. Connection 객체 생성 (접속하고자 하는 db server의 url, user, password가 필요함)
//3. 자동커밋여부 설정 : false -> app에서 직접 트랜잭션 제어 (기본값 : true, 아무것도 안쓰면 true로 처리됨)
//4. PreparedStatement 객체생성 (이때, 미완성쿼리 필요, 미완성 : 값대입이 아직 안된 상태) 및 값대입
//5. 쿼리실행 - Statement객체 실행. 실제로 DB에 쿼리 요청을 보냄
//6. 응답에 대한 처리  ) DML : int 리턴 | DQL : ResultSet 리턴 (-> 다시 자바객체로 전환하는 과정 필요)
//7. (DML의 경우) 트랜잭션 처리
//8. 자원반납 (생성의 역순)

		



		public List<Member> selectAll() {
			return memberDao.selectAll();
		}


		public Member selectOne(String memberId) {

			return memberDao.selectOne(memberId);
		}
	
}