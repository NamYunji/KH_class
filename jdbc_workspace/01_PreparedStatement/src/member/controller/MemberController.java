package member.controller;

import member.model.dao.MemberDao;
import member.model.vo.Member;
import java.util.List;

/*
 * Controller : MVC 패턴 중 가장 중요한 역할
 * MVC(MODEL VIEW CONTROLLER) 패턴의 시작점이자 전체흐름을 제어 
 * view단으로부터 요청을 받아서 Dao에 다시 요청, 그 결과를 view단에 다시 전달
 */

public class MemberController {
	
	private MemberDao memberDao = new MemberDao();

	public int insertMember(Member member) {
		//DML 명령은 정수(몇 행 실행 됐는지)를 리턴함
		return memberDao.insertMember(member);
	} 

	public List<Member> selectAll() {
		return memberDao.selectAll();
	}

	public Member selectOne(String memberId) {
		return memberDao.selectOne(memberId);
	}

	public int deleteMember(String memberId) {
		return memberDao.deleteMember(memberId);
	}
	
	public int updateMember(Member m) {
		return memberDao.updateMember(m);
	}
	
	public List<Member> selectByName(String memberName) {
		return memberDao.selectByName(memberName);
	}

}