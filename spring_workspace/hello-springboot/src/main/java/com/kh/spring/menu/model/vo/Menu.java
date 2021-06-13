package com.kh.spring.menu.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

	private int id;
	private String restaurant;
	private String name;
	private int price;
	private MenuType type; // kr, ch, jp
	private String taste; // mild, hot
	
	// type이나 tase의 경우, 보통 상수(final)을 통해 처리함
	// 상수보다 더 좋은 것 : enum
}