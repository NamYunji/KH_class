package com.kh.spring.board.model.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {

	private int no;
	private int boardNo;
	private String originalFileName;
	private String renamedFileName;
	private Date uploadDate;
	private int downloadCount;
	private boolean status; // status --- 'Y', 'N'
	// 1과 0이면 자동으로 형변환되지만, Y,N은 자동형변환되지 않기 때문에 typeHandler 필요
	
}
