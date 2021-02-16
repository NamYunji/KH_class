package member.controller;

import java.util.List;

import member.model.service.MemberService;
import member.model.vo.Member;

public class MemberController {
	
	private MemberService memberService = new MemberService();

	public List<Member> selectAll() {
		
		return memberService.selectAll();
		//selectAll의 리턴타입도 List<Member>임
	}

	public int insertMember (Member member) {
		return memberService.insertMember(member);
	}
}
