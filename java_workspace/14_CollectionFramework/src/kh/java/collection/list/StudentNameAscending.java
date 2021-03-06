package kh.java.collection.list;

import java.util.Comparator;

public class StudentNameAscending implements Comparator<Student> {

	// 이름 오름차순 정렬
	// 이름 : String, String은 이미 비교 메소드를 가지고 있음(compareTo)
	@Override
	public int compare(Student s1, Student s2) {
		return s1.getName().compareTo(s2.getName());
	}
}