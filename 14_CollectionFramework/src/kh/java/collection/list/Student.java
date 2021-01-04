package kh.java.collection.list;

import java.util.Objects;

public class Student implements Comparable<Student> {

	private int no;
	private String name;

	public Student() {
		super();
	}

	public Student(int no, String name) {
		super();
		this.no = no;
		this.name = name;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [no=" + no + ", name=" + name + "]";
	}

	/*
	 * 필드값이 동일하면 true가 나올 수 있도록, 재작성 그 필드 : no, name equals & hashCode는 동시에 오버라이딩한다
	 * equals 결과가 true라면, hashCode값도 동일해야 한다 (동일한 객체처럼 작동할 수 있도록)
	 */

	// override의 rule : equals에 사용한 필드를 똑같이 이용해서 hashCode를 생성할 것
	@Override
	public int hashCode() {
		// hashCode 생성메소드 (jdk 1.7부터 추가됨)
		return Objects.hash(no, name);

	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Student))
			return false;
		// 형변환
		Student other = (Student) o;

		// 값이 다르면 바로 return false
		if (no != other.no)
			return false;

		// name과 equals를 비교
		if (!name.contentEquals(other.name))
			return false;

		return true;
	}

	// 기본정렬 : 학번 오름차순
	// 리턴타입이 양수 음수 0이 리턴될 수 있도록 해줘야 함
	// 양수 : 자리유지
	// 0 : 자리유지
	// 음수 : 자리바꿈
	@Override
	public int compareTo(Student other) {
		return this.no - other.no;
	}

}