package com.kh.spring.common.aop.test;

public class AopTest {
	
	Foo foo = new FooProxy(new FooImpl());
	// FooProxy에 FooImpl전달

	public static void main(String[] args) {

		// new AopTest().fooTest();
		// ------------- before -------------
		// say foo o o ooooooooooooooooo!
		// ------------- after -------------
		
		new AopTest().fooNameTest();
		// FOOOOOOOOOOOOO
	}

	private void fooNameTest() {
		String name = foo.getName();
		System.out.println(name);
	}

	private void fooTest() {
		// proxy의 sayHello를 호출한 것
		foo.sayHello();
	}
}
