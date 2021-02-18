package member.controller;

import java.util.List;

import member.model.service.MemberService;
import member.model.vo.Member;

public class MemberController {
	
	//MemberService 클래스를 필드로 객체화
	//MemberService의 메소드를 이 클래스에서 호출할 수 있도록 (has a 포함관계)
	private MemberService memberService = new MemberService();

	public List<Member> selectAll() {
		
		return memberService.selectAll();
	}

	public int insertMember (Member member) {
		return memberService.insertMember(member);
	}
	
	public Member selectOne(String memberId) {
		return memberService.selectOneMember(memberId); 
	}

	public int deleteMember(String memberId) {
		return memberService.deleteMember(memberId);
	}
	
	public int updateMember(Member m) {
		return memberService.updateMember(m);
	}
	
	public List<Member> selectByName(String memberName) {
		return memberService.selectByName(memberName);
	}	
}
