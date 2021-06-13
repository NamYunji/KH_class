package com.kh.java.enum_;

public enum ItemType {
	
	// Enum(내부적으로 처리될 값)
	GLASSES(123), PERFUME(456), COSMETIC(789);

	// 필드 (내부적으로 쓰이는 값들에 대한)
	private int value;
	
	// 생성자
	ItemType(int value) {
		this.value = value;
	}
	
	// 게터
	public int getValue() {
		return this.value;
	}
	
	// 세터
	// valueOf
	// int를 받아서 어떤 상수를 내줘야 하는지를 결정해줌
	public static ItemType valueOf(int value) {
		switch(value) {
			// case 내부적으로 쓰이는 값 : return 외부적으로 쓰이는 값
			case 123 : return GLASSES;
			case 456 : return PERFUME;
			case 789 : return COSMETIC;
			default:
				throw new AssertionError("Unknown ItemType : " + value);
		}
	}
}
