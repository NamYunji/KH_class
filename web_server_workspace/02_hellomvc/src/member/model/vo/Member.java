package member.model.vo;

import java.sql.Date;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * VO(Value Object) 클래스
 * VO객체는 DB테이블의 한 행의 정보를 가지고 있는 객체
 * DB 테이블과 같은 구조여야 함
 *
 */
public class Member implements HttpSessionBindingListener{
	private String memberId;
	private String password;
	private String memberName;
	private String memberRole;
	private String gender;
	private Date birthday;
	private String email;
	private String phone;
	private String address;
	private String hobby;
	private Date enrollDate;
	// 시분초 정보가 필요 없을 때는 sql.Date
	// 시분초 정보가 필요할 때는 timeStamp
	// 한글자 짜리라도 String으로 함
	// why? PrepareStatement에는 char관련 메소드가 없기 때문
	
	// 생성자
	public Member() {
		super();
	}	
	public Member(String memberId, String password, String memberName, String memberRole, String gender, Date birthday,
			String email, String phone, String address, String hobby, Date enrollDate) {
		super();
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.memberRole = memberRole;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
		this.enrollDate = enrollDate;
	}

	// getter / setter
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberRole() {
		return memberRole;
	}
	public void setMemberRole(String memberRole) {
		this.memberRole = memberRole;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public Date getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(Date enrolldate) {
		this.enrollDate = enrolldate;
	}

	// toString
	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", password=" + password + ", memberName=" + memberName
				+ ", memberRole=" + memberRole + ", gender=" + gender + ", birthday=" + birthday + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", hobby=" + hobby + ", enrolldate=" + enrollDate + "]";
	}
		
	// session객체에 현재객체가 속성으로 등록될 때 호출되는 event listener
	// member객체가 session에 속성으로 저장될 때 - 로그인 시 loginMember로 저장됨
	// 사용자 로그인관련 정보에 대한 로그를 남길 때 사용 (로그인 내역 관리)
	@Override
	public void valueBound(HttpSessionBindingEvent ev) {
		System.out.println(memberName + "[" + memberId + "]님이 로그인 했습니다.");
	}
	// session객체에 현재객체가 속성에서 해제될 때 호출되는 event listener
	// session이 무효화될 때도 호출됨 - 로그아웃시 value가 속성에서 제거됨
	@Override
	public void valueUnbound(HttpSessionBindingEvent ev) {
		System.out.println(memberName + "[" + memberId + "]님이 로그아웃 했습니다.");
	}
}