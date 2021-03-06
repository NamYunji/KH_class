package kh.java.collection.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HashSetTest {
	/**
	 * hashSet의 계층구조 Collection - Set - HashSet - 중복 허용하지 않음 - 저장된 순서 보장 X
	 * 
	 * (조회할 때 마다 순서가 바뀌지는 않지만, 내부적인 자기만의 순서대로 정렬하는 것)
	 * 
	 * LinkedHashSet - 저장된 순서 유지 - 중복 허용 x TreeSet - 저장된 순서 유지 + 오름차순 정렬 지원 - 중복 허용
	 * X
	 * 
	 */

	public static void main(String[] args) {
		HashSetTest h = new HashSetTest();
//		h.test1();
//		h.test2();
//		h.test3();
//		h.test4();
		h.test5();

	}

	/**
	 * 실습문제 로또 숫자 출력하기 1 ~ 45 사이의 중복없는 난수 6개를 오름차순 정렬해서 출력 중복없음 -> Set 오름차순 정렬 ->
	 * TreeSet
	 */
	private void test5() {
		Set<Integer> lotto = new TreeSet<>();
		while (lotto.size() < 6)
			lotto.add((int) (Math.random() * 45 +1));
		System.out.println(lotto);
	}

	/*
	 * LinkedHashSet TreeSet
	 */
	private void test4() {
		Set<Integer> set1 = new LinkedHashSet<>();
		set1.add(34);
		set1.add(25);
		set1.add(100);
		set1.add(1);
		System.out.println(set1);

		// 오름차순 정렬
		Set<Integer> set2 = new TreeSet<>(set1);
		System.out.println(set2);
	}

	/**
	 * 커스텀 클래스 중복처리
	 */
	private void test3() {
		Set<Person> set = new HashSet<>();
		set.add(new Person("홍길동"));
		set.add(new Person("신사임당"));
		// 동일한 "홍길동"이 어떻게 처리될까?
		set.add(new Person("홍길동"));

		System.out.println(set);

	}

	/**
	 * list <-> set 바꿔서 많이 씀 List -> Set : 중복을 제거하기 위함
	 * 
	 * Set -> List : 순서필요 (정렬)
	 */
	private void test2() {
		// list는 중복에 관심 없음. 저장된 순서가 중요
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(1);
		list.add(3);
		list.add(2);
		System.out.println(list);

		// 중복 제거하고 싶으면 -> set으로 변환
		Set<Integer> set = new HashSet<>(list);
		System.out.println(set);

		// 내림차순 정렬 -> list로 변환
		List<Integer> list2 = new ArrayList<>(set);
		// 방법1
		list2.sort(Collections.reverseOrder());
		// 방법2
//	Collections.sort(list, Collections.reverseOrder());
		System.out.println(list2);
	}

	private void test1() {
		HashSet<Integer> set1 = new HashSet();
		Set<String> set2 = new HashSet<String>();
		Collection<Double> set3 = new HashSet<>();

		set2.add("안녕");
//	set2.add(123); // 제네릭 덕분에 다른 타입의 요소는 추가 불가
		set2.add("hello world");
		set2.add("ㅋㅋㅋ");
		set2.add("ㅋㅋㅋ"); // 중복 허용 x

		// 저장된 요소 개수 확인 : size 메소드 사용
		System.out.println(set2.size());
//	System.out.println(set2);

		// but 인덱스를 통한 요소가져오기는 지원하지 않음
//	set2.get(2);
		// 가져오려면, 반복문을 통한 전체 열람만 가능
		// 일반 for문은 사용할 수 없음 (why? 인덱스 사용이 불가능하기 때문)
		// -> index사용 없이 쓸 수 있는 것만 가능

		// 1. for each문 가능
		for (String s : set2)
			System.out.println(s);

		// 2. iterator객체 이용
		Iterator<String> iter = set2.iterator();
		while (iter.hasNext()) {
			String s = iter.next();
			System.out.println(s);
		}

		// 삭제 : remove메소드
		// 동등한 객체를 찾아 삭제 (equals & hashCode overriding 필수)
		// 마찬가지로 인덱스 사용한 삭제 x
		set2.remove("ㅋㅋㅋ");
		// 전체 삭제 : clear메소드
		set2.clear();
		// 요소가 존재하지 않는지 (비어있는지) 검사 : isEmpty메소드
		set2.isEmpty();
		// index로 할 수 있는 기능을 제외하면 arraylist와 동일
		System.out.println(set2);
	}

}
