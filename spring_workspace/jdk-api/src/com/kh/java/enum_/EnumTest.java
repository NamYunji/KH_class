package com.kh.java.enum_;

import lombok.AllArgsConstructor;
import lombok.Data;

public class EnumTest {
	
	// 상수 선언
	public static final int COLOR_BLACK = 0;
	public static final int COLOR_RED = 1;
	public static final int COLOR_BLUE = 2;
	
	public static final int ITEM_OUTER = 0;
	public static final int ITEM_INNER = 1;
	public static final int ITEM_PANTS = 2;
	
	public static void main(String[] args) {
		EnumTest et = new EnumTest();
		et.test1();
		et.test2();
		et.test3();
	}

	/**
	 * 상수의 한계
	 * : 내부적으로 처리되느 값에 대한 검증기능이 없다
	 * ONLY 내부적으로 처리할 값을 가독성 좋게 (코드 가독성), 타이핑 실수를 방지하는 (코드 전달력) 기능만 있음
	 */
	private void test1() {
		// 상수를 통한 person객체 세팅
		Person p1 = new Person("홍길동", COLOR_BLACK); 
		// Person p2 = new Person("신사임당", COLOR_RED); // EnumTest.Person(name=신사임당, color=1)
		Person p2 = new Person("신사임당", ITEM_INNER); // EnumTest.Person(name=신사임당, color=1)
		// 무엇에 대한 값인지 티가 안남
		// Person객체가 필요한 건 color값인데 다른 상수를 끼워넣는다 해도 오류 없이 작동함
		
		// COLOR_BLACK, COLOR_RED를 썼지만 내부적으로 지정된 값(0, 1)이 할당됨
		System.out.println(p1); 
		System.out.println(p2); 
	}
	
	@Data
	@AllArgsConstructor
	class Person {
		private String name;
		private int color;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Enum
	 * 값에 대한 타입검증기능 (enum의 장점)
	 * + 코드 가독성, 오타예방 등의 기능 (상수의 장점)
	 * 
	 * enum은 enum명.상수명으로 접근한다.
	 * - namespace를 통해서만 사용할 수 있다.
	 */
	private void test2() {
		Car car1 = new Car("소나타", CarColor.BLACK);
		Car car2 = new Car("그랜져", CarColor.WHITE);
		System.out.println(car1); // EnumTest.Car(name=소나타, color=BLACK)
		System.out.println(car2); // EnumTest.Car(name=그랜져, color=WHITE)

		// 다른 enum타입을 쓴다면?
		// Car car3 = new Car("소나타", Color.BLACK);
		// The constructor EnumTest.Car(String, Color) is undefined
		// Color클래스를 받는 생성자가 없다는 오류!
		// -> 값에 대한 검증기능이 있다!
	}
	
	@Data
	@AllArgsConstructor
	class Car {
		private String name;
		// enum 필드 : enum클래스를 타입으로 적음
		private CarColor color;
	}
	
	///////////////////////////////////////////////////////////////////////////
	
	/** 
	 * enum도 내부값을 갖고 처리할 수 있다.
	 * 
	 * ex. MenuType.KR = "kr"
	 */
	private void test3() {
		Item item1 = new Item("젠틀몬스터", ItemType.GLASSES);
		Item item2 = new Item("르라보", ItemType.PERFUME);
		Item item3 = new Item("젠틀몬스터", ItemType.COSMETIC);
		
		System.out.println(item1);
		System.out.println(item2);
		System.out.println(item3);
		// 출력은 원래 쓰이는 값이 나옴
		// EnumTest.Item(name=젠틀몬스터, itemType=GLASSES)
		// EnumTest.Item(name=르라보, itemType=PERFUME)
		// EnumTest.Item(name=젠틀몬스터, itemType=COSMETIC)
		
		// getter
		// 내부적 값이 필요하다면?
		// getValue() 호출
		System.out.println(item1.getItemType().getValue()); // 123
		System.out.println(item2.getItemType().getValue()); // 456
		System.out.println(item3.getItemType().getValue()); // 789
		
		// setter
		// 값을 가지고 어떤 enum인지 알고 싶다면?
		// valueOf() 호출
		// {"name" : "젠틀몬스터", "itemType" : 123}
		Item item4 = new Item("젠틀몬스터", ItemType.valueOf(123));
		System.out.println(item4);
		// EnumTest.Item(name=젠틀몬스터, itemType=GLASSES)
	
	}
	
	@Data
	@AllArgsConstructor
	class Item {
		private String name;
		private ItemType itemType;
	}
}
