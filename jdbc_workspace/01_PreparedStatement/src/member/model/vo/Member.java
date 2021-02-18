package member.model.vo;

import java.sql.Date;

/*
 * vo객체는 Member테이블의 한 행과 대응한다
 * 회원 한명의 객체를 Member객체 하나에 담을 수 있음
 */

public class Member {

	private String memberId;
	private String password;
	private String memberName;
	private String gender; // M/F만 담기지만, char를 처리할 수 없어서 String으로
	private int age; // 정수면 int, 실수면 double
	private String email;
	private String phone;
	private String address;
	private String hobby;
	private Date enrollDate; // java.sql.Date
	/*
	 * sql developer - java 
	 * varchar2 - String 
	 * char - String 
	 * number - 정수 데이터면 int 
	 *        - 실수 데이터면 double 
	 * date - Date (java.sql.Date - java.util.Date의 자식클래스) 
	 * 
	 * 단어 구분
	 * 언더스코어(_) - Camel Casing ex. member_id - memberId
	 * 
	 */

	// 기본생성자
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	// parameter 생성자
	public Member(String memberId, String password, String memberName, String gender, int age, String email,
			String phone, String address, String hobby, Date enrollDate) {
		super();
		this.memberId = memberId;
		this.password = password;
		this.memberName = memberName;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.hobby = hobby;
		this.enrollDate = enrollDate;
	}

	// getter & setter
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	// toString 오버라이드
	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", password=" + password + ", memberName=" + memberName + ", gender="
				+ gender + ", age=" + age + ", email=" + email + ", phone=" + phone + ", address=" + address
				+ ", hobby=" + hobby + ", enrollDate=" + enrollDate + "]";
	}

}
