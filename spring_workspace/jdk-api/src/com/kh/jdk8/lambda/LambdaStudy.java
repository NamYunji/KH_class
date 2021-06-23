package com.kh.jdk8.lambda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Lambda 표현식
 * 자바 : 객체를 이용해서 메모리에 데이터를 쌓고 그걸 입출력하면서 프로그래밍 진행
 * java 함수형 프로그래밍
 * java에서 함수형프로그래밍을 지원하기 위한 api(jdk8부터 사용가능)
 * 
 * 객체지향 프로그래밍
 * : 상태(속성, 정적임) - field, 행동 - method을 객체로 관리하는 것
 * 상태를 제어하기 위해 method를 사용하는 경우가 많고, 직접적으로 상태 제어가 안될때 메소드를 이용해서 처리하기도 함
 * 객체안에서 사용되는 함수 : 메소드
 * <->
 * 함수형 프로그래밍
 * 상태값을 가지지 않음. 모든 것이 함수 안에서 끝남
 * 특징 :
 * - 상태를 관리하지 않기 때문에, 함수를 값으로써 취급함
 * 		-> 함수를 매개인자로 전달할 수도 있고, 리턴값으로 반환할 수도 있음
 * - 상태관리를 하지 않고, 모든 것을 불변으로 처리함
 * 
 * 자바에서 메소드는 독립적으로 존재할 수 없음
 * : 인자로 전달되거나, 리턴되거나 모두 불가능
 * -> 객체를 통해서만 전달 가능
 * 
 * lambda 표현식 또한 이런 제한이 적용됨
 * 
 */
public class LambdaStudy {

	public static void main(String[] args) {
		LambdaStudy study = new LambdaStudy();
//		study.test0();
//		study.test1();
//		study.test2();
//		study.test3();
//		study.test4();
//		study.test5();
		study.test6();
	}

	private void test6() {
		List<Integer> list = new ArrayList<Integer>() {
			{
				for(int i = 0; i < 10; i++)
					add(i);
			}
		};
		// System.out.println(list);
		
		// 특정 요소 제거
		list.removeIf(n -> n % 2 == 0);
		
		// 요소 대체
		// UnaryOperator : 매개변수와 리턴타입이 같을 경우 Function 대신 사용 가능
		list.replaceAll(n -> n * 100);
		
		// 모든 요소 순회
		list.forEach(System.out::println);
	}

	/**
	 * 메소드 참조 Method Reference
	 * 람다식을 더 간결히 표현한 문법
	 * 베이스가 되는 함수형 인터페이스에 따라 동일한 메소드참조도 기능이 달라질 수 있다.
	 * 
	 * 1. static : Integer.parseInt("123") -> Integer::parseInt
	 * 2. non-static : "홍길동".equals(name) -> String::equals (두개의 인자를 받아서 처리)
	 * 3. 특정 객체의 메소드 사용 : str::eqlas (한개의 인자를 받아서 처리)
	 * 4. 생성자 참조 : new Person() -> Person::new (함수형 인터페이스에 따라 여러 생성자 호출 가능)
	 * 
	 * cf. 메소드 참조의 조건
	 * 전달한 값이 있다면 그 값을 변경 없이 그대로 사용한다는 전제!
	 * ex. upperCase로 바꾼다음에 진행하고자 하면 사용할 수 없음
	 */
	private void test5() {
		// 람다식 사용
		// Consumer<String> printer = s -> System.out.println(s);
		// 메소드 참조 사용
		Consumer<String> printer = System.out::println;
		printer.accept("홍길동");
		
		// 1. static
		// String을 전달하고 Integer 리턴
		// Function<String, Integer> intParser = s -> Integer.parseInt(s);
		Function<String, Integer> intParser = Integer::parseInt;
		int num = intParser.apply("1234567");
		System.out.println("num = " + (num + 1));
		
		/////////////////////////////////////////////////////////////////////////////
		
		// 2. non-static
		// 문자열의 길이를 구하는 람다식 - 메소드 참조
		// String을 전달하고 Integer 리턴
		
		// 이전 방식
		String name = "아라비카";
		System.out.println(name.length());

		// 람다식 사용
		// Function<String ,Integer> strLength = s -> s.length();
		// 메소드 참조 사용
		Function<String ,Integer> strLength = String::length;
		System.out.println(strLength.apply(name));
		
		// equals
		// String 두개를 받아서 같은지 아닌지를 boolean형으로 리턴
		// 람다식 사용
		// BiFunction (매개변수 두개인 function)
		// BiFunction<String ,String ,Boolean> strEquals = (s1, s2) -> s1.equals(s2);
		
		// 메소드 참조
		BiFunction<String ,String ,Boolean> strEquals = String::equals;
		System.out.println(strEquals.apply(name, "아라비카")); // true
		System.out.println(strEquals.apply(name, "ㅋㅋ")); // false
		
		/////////////////////////////////////////////////////////////////////////////
		
		// 3. 특정객체 기준으로 메소드 참조
		// 클래스 이름이 아닌 객체이름이 앞에 나옴
		String title = "소나기";
		
		// 람다식 사용
		// Predicate<String> equalToTitle = s -> title.equals(s);
		
		// 메소드 참조 사용
		Predicate<String> equalToTitle = title::equals;
		
		System.out.println(equalToTitle.test("소나기")); // true
		System.out.println(equalToTitle.test("장마")); // false
		
		/////////////////////////////////////////////////////////////////////////////

		// 4. 생성자 메소드 참조
		
		// 람다식 사용
		// Supplier<Person> personConstr = () -> new Person();
		
		// 메소드 참조 사용
		Supplier<Person> personConstr = Person::new;
		System.out.println(personConstr.get()); // LambdaStudy.Person(name=null, age=0)
				
		// 람다식 사용
		// BiFunction<String, Integer, Person> personConstr2 = (name_, age) -> new Person(name_, age);

		// 메소드 참조 사용
		BiFunction<String, Integer, Person> personConstr2 = Person::new;
		
		System.out.println(personConstr2.apply("홍길동", 22)); // LambdaStudy.Person(name=홍길동, age=22)
		
		// 람다식 사용
		// Function<String, Person> personConstr3 = (name_) -> new Person(name_);

		// 메소드 참조 사용
		Function<String, Person> personConstr3 = Person::new;
		System.out.println(personConstr3.apply("이순신")); // LambdaStudy.Person(name=이순신, age=0)
	}

	// 생성자 메소드 참조를 위한 내부 클래스
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@RequiredArgsConstructor // (non null이 붙은 필드만 가지고 생성자를 따로 만들어줌)
	class Person {
		@NonNull
		private String name;
		private int age;
	}
	
	/**
	 * 실습문제
	 */
	private void test4() {
		// 현재 시간 출력 람다식
		Runnable displayNow = () -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sdf.format(new Date()));
		};
		displayNow.run(); // 2021-06-22 15:46:16
		
		// 로또 (1~45사이의 중복없는 난수 Set리턴) 생성 람다식
		Supplier<Set<Integer>> lottoPublisher = () -> {
			Set<Integer> lotto = new TreeSet<>();
			while(lotto.size() < 6) {
				lotto.add(new Random().nextInt(45) + 1);
			}
			return lotto;
		};
		for(int i = 0; i < 5; i++) {
			Set<Integer> lotto = lottoPublisher.get();
			System.out.println(i + 1 + " : " + lotto);
		}
		// 환율 계산기 람다식 : 원화 입력시 달러값을 리턴
		// 1 달러 : 1,100원이다
		Function<Integer, Double> wonDollarCalc = won -> {
			double rate = 1100;
			return won / rate;
		};
		System.out.println(wonDollarCalc.apply(3000));
	}

	/**
	 * jdk가 제공하는 함수형 인터페이스 사용
	 * 람다식을 사용하려면 인터페이스가 필요한데 매번 인터페이스 작성하기 귀찮으니까 jdk가 자주 사용하는 인터페이스를 만들어둠
	 * 함수형 인터페이스를 제공할 수 있는 이유 : 메소드 시그니쳐가 비슷하기 때문
	 * - 제네릭을 사용해서 람다식 작성 타임에 매개변수나 리턴타입이 결정되도록 함
	 * 
	 * 함수형 인터페이스의 종류
	 * 1. java.lang.Runnable : 매개변수 없음 | 리턴값 없음 | 실행메소드 : run():void
	 * 2. java.util.funtion.Supplier<R> : 매개변수 없음 | 리턴값 R | 실행메소드 : get():R
	 * 3. java.util.function.Consumer<T> : 매개변수 T | 리턴값 없음 | 실행메소드 : accept(T):void
	 * 4. java.util.function.Function<T,R> : 매개변수 T | 리턴값 R | 실행메소드 : apply(T):R
	 * 5. java.util.function.Predicate<T> : 매개변수 T | 리턴값 boolean | 실행메소드 : test(T):boolean
	 */
	private void test3() {
		// Runnable : 호출하면 어떤 일처리를 하는 것 말고는 주는것도 받는것도 없음
		Runnable r = () -> {
			for(int i = 0; i < 10; i++) {
				System.out.println(new Date());
			}
		};
		r.run();
		
		// Supplier : 매개변수 없고, 리턴값이 있음
		// 제공자 -> get으로 가져올 수 있음
		// Supplier<리턴값의 타입>
		Supplier<Long> supplier = () -> new Date().getTime();
		System.out.println(supplier.get());
		
		// 1부터 100사이의 난수 하나 리턴
		Supplier<Integer> supplier2 = () -> new Random().nextInt(100) + 1;
		System.out.println(supplier2.get());
		
		// Consumer : 매개변수 있고, 리턴값이 없음
		// 소비자 -> 무언갈 취하기만 하고 리턴값을 돌려주지 않음
		// 매개변수가 하나라면 소괄호 생략 가능
		Consumer<String> consumer = name -> System.out.println("이름 : " + name);
		consumer.accept("홍길동"); // 이름 : 홍길동
		
		// 현재시각을 특정포맷으로 출력
		Consumer<Date> printTime = date -> {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			System.out.println(sdf.format(date));
		};
		printTime.accept(new Date()); // 2021-06-21
		
		// Function : 매개변수 있고, 리턴값도 있음
		// str를 하나 받아서 str.length()를 리턴
		Function<String, Integer> function = str -> str.length();
		System.out.println(function.apply("안녕하세요")); // 5
		System.out.println(function.apply("hello")); // 5
		System.out.println(function.apply("bye bye")); // 7
		
		// Predicate : 매개변수 있고, 리턴타입이 Boolean
		// 논리형으로 검사할 때 유용
		Predicate<Integer> predicate = n -> n % 3 == 00 && n % 5 == 0;
		int num = supplier2.get(); // 난수 가져옴
		if(predicate.test(num)) {
			System.out.println("3의 배수 && 5의 배수 : " + num);
		} else {
			System.out.println("꽝!");
		}
	}

	/**
	 * 함수형 인터페이스
	 * - lambda식의 조건
	 * 추상메소드가 딱 하나인 인터페이스를 베이스로 람다식을 작성할 수 있다.
	 * 		(인터페이스가 먼저 존재해야 함.)
	 * - 추상메소드 문법검사
	 * @FunctionalInterface 어노테이션 사용하면 문법오류를 컴파일타임에 확인 가능
	 */
	private void test2() {
		Foo max = (a, b) -> a > b ? a : b;
		// max.process() 추상메소드 호출
		System.out.println(max.process(80, 77)); // 80
		
		Foo min = (a, b) -> a < b ? a : b;
		System.out.println(min.process(80, 77)); // 77
		
		Foo sum = (a, b) -> a + b;
		System.out.println(sum.process(80, 77)); // 157
		
		// 인터페이스를 통해 문자열 이름과 나이를 받아 출력가능한 lambda식 작성
		// 1. 인터페이스 선언 (Hoo)
		// 2. 람다식 작성
		// 함수처럼 보이지만 하나의 객체이다
		Hoo printPerson = (name, age) -> System.out.printf("이름 : %s, 나이 : %d", name, age);
		// 3. 람다식 호출
		printPerson.print("홍길동", 20); // 이름 : 홍길동, 나이 : 20
	}
	
	@FunctionalInterface
	interface Hoo {
		void print(String s, int n);
	}

	@FunctionalInterface
	interface Foo {
		int process(int a, int b);
		// int process(int a); // Invalid '@FunctionalInterface' annotation; LambdaStudy.Foo is not a functional interface
		// @FunctionalInterface -> 추상메소드가 두개이기 때문에 문법오류
	}
	/**
	 * lambda 표현식 사용
	 * 메소드만 전달 또는 변수에 저장이 불가능하므로, 인터페이스를 통해 처리해야 한다
	 * 
	 * cf. 조건
	 * lambda식 사용시에는 인터페이스의 추상메소드가 단 하나만 존재해야한다
	 * 보기에는 메소드만 전달하는 것 같지만, 실제로는 객체가 전달됨
	 */
	private void test1() {
		// 오버라이드할 메소드를 바로 적음
		// 방식1
//		Pita pita = (int a, int b) -> {
//			return Math.sqrt(a * a + b * b);
//		};
		// 방식2 : 축약형
		// return절이 한줄이라면 생략할 수 있음
		// Pita pita = (int a, int b) -> Math.sqrt(a * a + b * b);
		// 매개변수 선언부의 자료형 또한 생략 가능
		Pita pita = (a, b) -> Math.sqrt(a * a + b * b);
		// but 자료형이 뭔지도 모르고 return타입도 모르는데?
		// 추상메소드가 하나가 아니라면 뭘 오버라이드한지 헷갈리니까 아예 허용하지 않음
		double c = pita.calc(100, 30);
		System.out.println("빗변 = " + c); // 빗변 = 104.4030650891055
	}

	/**
	 * 피타고라스의 정의
	 * 빗변 제곱 = 밑변제곱 + 높이제곱
	 */
	private void test0() {
		// 익명클래스 : 객체선언과 생성을 동시에 처리
		// 인터페이스를 new연산자와 함께 사용
		Pita pita = new Pita() {
			@Override
			public double calc(int a, int b) {
				return Math.sqrt(a * a + b * b);
			}
		};
		
		double c = pita.calc(100, 30);
		System.out.println("빗변 = " + c); // 빗변 = 104.4030650891055
	}
	
	/**
	 * 빗변을 구하는 추상메소드 선언
	 */
	interface Pita {
		double calc(int a, int b);
	}
	
	// 함수 하나가 필요할 뿐인데, 객체를 만들고, 객체를 통해 메소드 호출

}
