package member.view;

import java.util.Scanner;
import member.controller.MemberController;
import member.model.vo.Member;
import java.util.List;

public class MemberMenu {
	// 필드로 선언하기
	private MemberController memberController = new MemberController();

	// 스캐너는 필드로 선언해서 다른 메소드와 분류
	private Scanner sc = new Scanner(System.in);

	// 메뉴 생성
	public void mainMenu() {
		String menu = "===== 회원 관리 프로그램 =====\n" + 
					  "1. 회원 전체조회\n" + 
					  "2. 회원 ID조회\n" + 
					  "3. 회원 이름조회\n" + 
					  "4. 회원 가입\n" + 
					  "5. 회원 정보변경\n" + 
					  "6. 회원 탈퇴\n" + 
					  "0. 프로그램 끝내기\n" + 
					  "-------------------------\n" + 
					  "선택 : ";

		// while반복문에서 메뉴 반복제공
		while (true) {
			System.out.println(menu);
			// 사용자 입력값을 받음
			int choice = sc.nextInt();

			// 다른 스위치 문에서도 사용할 수 있도록
			Member member = null;
			int result = 0;
			String msg = null;
			List<Member> list = null; // java.util.List
			String memberId = null;
			String memberName = null;
			
		
			// switch문으로 처리
			switch (choice) {
			case 1:
				// List<Member> list = null;
				list = memberController.selectAll();
				// Controller객체에 전체회원을 조회해달라고 요청, 그 결과로 list객체가 넘어옴
				displayMemberList(list); // list를 전달해서 출력
				break;

			case 2:
				memberId = inputMemberId(); // 조회할 아이디를 입력받음
				member = memberController.selectOne(memberId); // 조회한 아이디를 selectOne을 호출하면서 전달
				// 이 아이디를 가진 회원을 전달해줘, 리턴된 member객체가 null일 수도 있음
				displayMember(member); // 결과를 보여줌
				break;

			//3. 이름조회는 이름 일부를 입력해도 조회가 될 수 있도록한다.
			case 3:
				memberName = inputMemberName();
				list = memberController.selectByName(memberName);
				displayMemberList(list);
				break;
			case 4: // 회원가입
				// 1. 신규회원정보 입력 -> Member객체로 만듦
				member = inputMember();
				System.out.println(">>> 신규회원 확인 : " + member); // 확인용
				// 2. controller에 회원가입 요청 (메소드 호출) -> DML요청 - int(처리된 행의 개수) 리턴 - 그걸 가지고 정상처리 여부
				// 확인
				result = memberController.insertMember(member);
				// 3. int에 따른 분기처리
				// result값에 따른 메시지 변수
				msg = result > 0 ? "회원 가입 성공!" : "회원 가입 실패!";
				displayMsg(msg); // 사용자 feedback을 보내는 메소드
				break;
				
			//5. 회원정보변경은 암호, 이메일, 전화번호, 주소, 취미를 일괄변경하도록한다.
			case 5:
				member = updateMember();
				result = memberController.updateMember(member);
				msg = result > 0 ? "회원 수정 성공!" : "회원 수정 실패!";
				displayMsg(msg);				
				break;
			//6. 탈퇴는 delete처리한다.
			case 6:
				memberId = inputMemberId();
				result = memberController.deleteMember(memberId);
				msg = result > 0 ? "회원 탈퇴 성공!" : "회원 탈퇴 실패!";
				displayMsg(msg);
				break;
			case 0:
				System.out.println("정말로 끝내시겠습니까? (y/n) : ");
				if (sc.next().charAt(0) == 'y')
					return; // 현재 메소드(mainMenu)를 호출한 곳인 run클래스로 돌아감
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		}
	}

	/**
	 * 회원정보변경 메소드
	 * @return
	 */
	private Member updateMember() {
		Member m = new Member();
		System.out.print("변경할 회원 아이디 : ");
		m.setMemberId(sc.next());
		System.out.print("암호 : ");
		m.setPassword(sc.next());
		System.out.print("이메일 : ");
		m.setEmail(sc.next());
		System.out.print("전화번호(-빼고입력): ");
		m.setPhone(sc.next());
		System.out.print("주소 : ");
		sc.nextLine();
		m.setAddress(sc.nextLine());
		System.out.print("취미(,로 공백없이 나열): ");
		m.setHobby(sc.next());
		return m;
	}
	
	private String inputMemberName() {
		System.out.print("조회할 이름 입력 : ");
		return sc.next();
	}

	/**
	 * DB에서 조회한 1명의 회원 출력
	 * @param member
	 */
	private void displayMember(Member member) {
		if(member == null)
			System.out.println(">>>> 조회된 회원이 없습니다.");
		else {
			System.out.println("****************************************************************");
			System.out.println(member);
			System.out.println("****************************************************************");
		}
	}

	/**
	 * 조회할 회원아이디 입력
	 * @return
	 */
	private String inputMemberId() {
		System.out.print("아이디 입력 : ");
		return sc.next();
	}

	/**
	 * DB에서 조회된 회원객체 n개를 출력
	 * @param list
	 */
	private void displayMemberList(List<Member> list) {
    	// 0행이 리턴될 경우
		if(list == null || list.isEmpty()) {
			System.out.println(">>>> 조회된 행이 없습니다.");	
		}
		// 리턴된 행이 있는 경우
		else {
			System.out.println("*********************************************************");
			for(Member m : list) {
				System.out.println(m);
			}
			System.out.println("*********************************************************");
		}
	}

	/**
	 * DML처리결과 통보용 
	 * @param msg
	 */
	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
	}

	/**
	 * 신규회원 정보 입력
	 * @return
	 */
	private Member inputMember() {
		// 회원정보 입력받기
		System.out.println("새로운 회원정보를 입력하세요.");
		// 멤버 객체 생성
		Member member = new Member();
		// 스캐너로 입력받은 값을 setter로 전달해 필드값 채우기
		System.out.print("아이디 : ");
		member.setMemberId(sc.next());
		System.out.print("이름 : ");
		member.setMemberName(sc.next());
		System.out.print("비밀번호 : ");
		member.setPassword(sc.next());
		System.out.print("나이 : ");
		member.setAge(sc.nextInt());
		System.out.print("성별(M/F) : ");// m, f (소문자 불가) - 체크제약 조건 위배
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));
		System.out.print("이메일: ");
		member.setEmail(sc.next());
		System.out.print("전화번호(-빼고 입력) : ");
		member.setPhone(sc.next());
		sc.nextLine(); // 버퍼에 남은 개행문자 날리기용 (next계열 - (개행문자 날리기용) - nextLine)
		System.out.print("주소 : ");
		member.setAddress(sc.nextLine());
		System.out.print("취미(,로 나열할것) : ");
		member.setHobby(sc.nextLine());
		return member;
	}

	

}
