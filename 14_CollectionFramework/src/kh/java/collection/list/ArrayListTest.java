package kh.java.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * list : 저장된 순서를 기억해줌. 중복 허용 set : 중복 허용 x, 저장된 순서에 관심 없음 map : key와 value (cf.
 * key의 特征 : 중복 허용 x , value는 중복 여부와 상관x)
 */

public class ArrayListTest {
	public static void main(String[] args) {
		ArrayListTest a = new ArrayListTest();
//		a.test0();
//		a.test1();
//		a.test2();
//		a.test3();
//		a.test4();
//		a.test5();
		a.test6();
	}

	/*
	 * 커스텀 클래스 정렬하기
	 */
	private void test6() {
		List<Student> list = new ArrayList<>();
		list.add(new Student(3, "세종대왕"));
		list.add(new Student(2, "신사임당"));
		list.add(new Student(5, "이황"));
		list.add(new Student(4, "장영실"));
		list.add(new Student(1, "홍길동"));
		// list -> 저장해준 순서대로 가지고 있음
//		System.out.println(list);

		// 기본정렬 : 학번 오름차순으로
		// 내부적으로 comparable student를 구현 필요
		// 방법1
//		Collections.sort(list); 
		// 방법2 (기본정렬인 경우 comparator 객체는 不必要
//		list.sort(null);
//		System.out.println(list);

		// 학번 내림차순
		Collections.sort(list, Collections.reverseOrder());
		System.out.println(list);

		// 추가적인 정렬기준 생성
		// 1. 이름 오름차순
//		Comparator<Student> comp = new StudentNameAscending();
//		Collections.sort(list, comp);
//		System.out.println(list);
		// 1. 이름 내림차순
		Comparator<Student> comp = new StudentNameAscending().reversed();
		Collections.sort(list, comp);
		System.out.println(list);

	}

	/*
	 * 정렬 1. Comparable인터페이스 기본정렬(한가지) / 클래스에서 직접 구현. compareTo메소드를 오버라이딩 2.
	 * Comparator인터페이스 기본정렬 外에 추가적으로 정렬기준을 제시해야 할 때 별도의 comparator클래스를 작성
	 */
	private void test5() {
		// list로만 정렬기능을 구현함
		List<String> list = new ArrayList<String>();
		list.add("허허허");
		list.add("나나나~");
		list.add("가버려");
		list.add("다나가");

		// 정렬메소드 호출
		// 요소로 취하는 string이 이미 정렬기준을 만들어놨기 때문
//		Collections.sort(list); //기본정렬기준(사전등재순. 가나다순)에 따라 정렬
		// 사전등재역순
		// 별도의 정렬기준 객체를 만들어줘야함
		Comparator<String> comp = Collections.reverseOrder();
		// 정렬할 때, 이것보고 정렬해~
		Collections.sort(list, comp);

		System.out.println(list);
	}

	private void test4() {
		// 객체할당부의 generic 타입은 생략 가능 (jdk 1.7이상에서만 사용가능)
//		List<Integer> list = new ArrayList<Integer>();
		List<Integer> list = new ArrayList<>();

		// 추가
		list.add(5);
		list.add(2);
		list.add(3);
		list.add(1);
		list.add(4);
		// 중간에 끼워넣기
		// (1 : int, 100 : new Integer(100)으로 auto-boxing
		list.add(1, 100); // 1번지에 100요소 추가
		// 삭제
		// 1. index로 삭제 (ex. 3번지의 3을 삭제)
//		list.remove(3);
		// 2. 동등객체로 삭제 (ex. 숫자 100을 찾아서 지워라)
		// 만약 100내용이 여러개 있을 때, 맨 앞 100만 지움
		// 동등객체 : 객체가 가진 값이 동일한 객체 (equals가 동일한 객체)
		list.remove(new Integer(100));
		// 수정 : 해당인덱스의 객체를 제공된 매개인자로 대체
		list.set(1, 888);
		// 다른 collection객체 추가
		List<Integer> another = new ArrayList<>();
		another.add(7);
		another.add(8);
		another.add(9);
		list.addAll(another);
		// 매개인자 요소를 포함하고 있는가를 확인 (동등객체임을 인정받아야 함)
		System.out.println(list.contains(9)); // 9 : not int 9 but Integer9
		// 매개인자 요소가 몇번지에 있는가를 확인 : int
		System.out.println(list.indexOf(9));
		// 존재하지 않는 값을 확인한다면 -1을 리턴
		// why? -1 : 음수 -> 번지수로 사용될 수 없는 숫자이기 때문
		System.out.println(list.indexOf(900));
		// 모든 요소를 열람 : 기존for문, for each문, iterator방식 다 가능
		// iterator객체 만들기
		// 요소들을 아래로 순서대로 줄세워놓고, pointer가 있어서,
		// 다음 것이 있으면(hasNext), true리턴, 또 커서(pointer)가 다음 것으로 이동
		// 맨마지막은 다음것이 없으니, false를 리턴해서 반복문을 빠져나옴
		Iterator<Integer> iter = list.iterator();
		while (iter.hasNext()) {
			Integer i = iter.next();
			System.out.println(i);
		}
		// 모든 요소 삭제
		// 요소들을 삭제했으므로 empty -> true
		list.clear();
		// 리스트가 비어있는가?
		// 텅비어있으면, true
		System.out.println(list.isEmpty());
		// list 출력
		System.out.println(list);
	}

	// test0을 ArrayList사용버전으로 변경하기
	private void test3() {
		ArrayList<Student> list = new ArrayList<Student>();
		list.add(new Student(1, "홍길동"));
		list.add(new Student(2, "신사임당"));
		list.add(new Student(3, "세종대왕"));
		// 1. 마지막에 학생2명 추가
		list.add(new Student(4, "장영실"));
		list.add(new Student(5, "이황"));
		// 2. 중간의 학생1명 삭제
//		list.remove(2);
		// 객체를 전달해 삭제하려면, 전달된 객체와 저장된 객체의 동등성을 인정받아야 한다
		// 두 객체의 equals비교 결과가 true여야 한다
		// 두 객체의 equals비교 결과가 true가 나오도록 equals를 오버라이딩 해야 한다
		System.out.println((new Student(3, "세종대왕").equals(new Student(3, "세종대왕"))));
		System.out.println(list.remove(new Student(3, "세종대왕")));
		// hashCode값 출력
		System.out.println(new Student(3, "세종대왕").hashCode());
		System.out.println(new Student(3, "세종대왕").hashCode());
		// 3. 중간에 학생1명 추가 : new Student(6, "뺑덕어멈")
		list.add(0, new Student(6, "뺑덕어멈"));
		for (Student s : list) {
			System.out.println(s);
		}
	}

	// Generics : 타입제한의 역할
	// 기본형을 사용할 수 없음
	// int => Integer
	// double => Double
	private void test2() {
		// List is a raw type.
		// References to generic type List<E> should be parameterized
		// Lise<E> : type parameter (타입변수)
		//경고 표시 : 타입을 지정해줘야 하는데, 지정해주지 않으면 object로 처리됨, 타입을 구체적으로 써주지 않았기 때문
		List list1 = new ArrayList();
		list1.add("안녕!");
		list1.add(123);
		// 요소는 기본적으로 object라서 자식객체들의 다형성을 담을 수 있다
		// 리턴타입은 object
		Object e1 = list1.get(0);
		Object e2 = list1.get(1);
		System.out.println(((String) e1).length());

		// 다형성 적용
		// 종류별로 담음 -> generics
		// <String> 타입변수 지정 : 요소의 타입 제한
		// <E> : generic class : 타입제한
		List<String> list2 = new ArrayList<String>();
		list2.add("안녕");
		// The method add(int, String) in the type List<String>
		// is not applicable for the arguments (int)
		// -> String이 아닌 요소는 추가할 수 없음
//		list2.add(123);
		// 리턴타입이 String으로 변함
		String s1 = list2.get(0);
		// 형변환 없이 s1.length메소드를 호출 가능
		System.out.println(s1.length());

		// Wrapper Class
		List<Integer> list3 = new ArrayList<Integer>();
		list3.add(31); // 31 -> new Integer(31) : auto-boxing
		// 리턴타입은 Integer인데 int타입이므로 자동으로 변경되었음
		int num = list3.get(0); // new Integer(31) -> 31 : auto-unboxing
	}

	/*
	 * java.util.List (인터페이스) java.util.ArrayList라는 대표적인 구현클래스를 사용하기
	 */
	private void test1() {
		ArrayList list1 = new ArrayList();
		List list2 = new ArrayList();
		Collection list3 = new ArrayList();

		// 맨마지막에 요소 추가 : add
		list1.add("안녕");
		list1.add(123);
		list1.add(123.456);
		list1.add(true);
		list1.add(new Date());
		list1.add(new Student(1, "고주몽"));
		list1.add(123);

		// toString이 override되어있음
		// 저장된 순서유지, 중복허용
		System.out.println(list1);

		// 저장된 요소의 개수
		System.out.println(list1.size());

		// 삭제 : remove
		list1.remove(3);

		// 중간에 요소추가
		list1.add(3, false);

		// 반복문을 통해 요소에 접근 가능 : 인덱스 사용가능
		for (int i = 0; i < list1.size(); i++) {
			System.out.println(i + " : " + list1.get(i));
		}

	}

//배열의 문제점

	// 배열의 문제점
	private void test0() {

		// 학생객체 3명의 정보 저장
		Student[] arr = new Student[3];
		arr[0] = new Student(1, "홍길동");
		arr[1] = new Student(2, "신사임당");
		arr[2] = new Student(3, "세종대왕");

		// 학생정보 출력
//		System.out.println(Arrays.toString(arr));

		// 1. 학생 2명 추가
		// but arr크기 변경 불가 -> 배열 새로 만들어야 함
		// 여러명 추가할 경우를 위하여, 배열 크기 넉넉히 잡음 -> 메모리 낭비
		Student[] arr2 = new Student[10];
		// 배열 복사
		System.arraycopy(arr, 0, arr2, 0, arr.length);
		arr2[3] = new Student(4, "장영실");
		arr2[4] = new Student(5, "이황");
		// 학생 정보 출력
//		System.out.print(Arrays.toString(arr2));

		// 2. 중간의 학생 1명 삭제
//		arr2[2] = null;
		// 중간에 null값으로 구멍이 나버림..
//		System.out.print(Arrays.toString(arr2));
		// 데이터는 중간의 빈자리 없이 관리해야 한다
		// 구멍난 자리만큼 뒤의 데이터를 하나씩 땡겨와주는 작업 필요
//		arr2[2] = null;
		arr2[2] = arr2[3];
		arr2[3] = arr2[4];
		arr2[4] = null;
//		System.out.print(Arrays.toString(arr2));

		// 중간에 학생 1명 추가 : new Student(6, "뺑덕어멈");
		arr2[4] = arr2[3];
		arr2[3] = arr2[2];
		arr2[2] = arr2[1];
		arr2[1] = arr2[0];
		arr2[0] = new Student(6, "뺑덕어멈");
		System.out.print(Arrays.toString(arr2));
		// 너무 귀찮잖아!! CollectionList가 해결해줄거얌!
	}
}
