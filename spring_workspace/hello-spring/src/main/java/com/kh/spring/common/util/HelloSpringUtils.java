package com.kh.spring.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloSpringUtils {

	/**
	 * RenamedFileName 만들기
	 * ex. test.jpg
	 * -> beginIndex : 4
	 * -> ext : .jpg
	 */
	public static String getRenamedFilename(String originalFilename) {
		// 1. 확장자 추출
		// 확장자 시작 인덱스
		int beginIndex = originalFilename.lastIndexOf(".");
		// 시작인덱스부터 확장자 가져오기
		String ext = originalFilename.substring(beginIndex);
		
		// 2. 년월일_난수 format
		// 년월일 format
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
		// 난수 format
		DecimalFormat df = new DecimalFormat("000"); // 정수부 3자리
		
		// 문자열 합치기 (날짜 + 난수 + 확장자)
		return sdf.format(new Date()) + df.format(Math.random() * 1000) + ext;
	}
	/*
	* Pagebar Section : 숫자에 맞게 html 작성
	*  [parameter로 넘어옴]
	*  1. cPage 현재페이지
	*  2. limit (numPerPage) 페이지당 컨텐츠 수 10
	*  3. totalContents 총 컨텐츠
	*  4. url 이동할 주소 /spring/board/boardList.do
	*  [메소드 안에서 만듦]
	*  5. totalPage (전체 페이지 수) - 마지막 페이지 때 pageNo가 totalPage를 넘어가는 것을 방지
	* 	   -> totalContents와 numPerPage를 알면, tatalPage를 계산
	*  6. pageBarSize (페이지바에 표시할 페이지 개수) -> 지정
	*  7. pageStart ~ pageEnd (pageNo의 범위) -> 계산
	*  8. pageNo (페이지 넘버를 출력할 증감변수)
	*/
	public static String getPageBar(int totalContents, int cPage, int limit, String url) {

		// 리터럴이 계속해서 변경될 경우, StringBuilder사용하기
		// StringBuilder는 값이 바뀌면 실제 문자열을 수정할 수 있음
		// String의 경우는 실제 문자열을 변경하는게 아니라, 값이 바뀔때마다 문자열을 생성해내야 해서 메모리 낭비
		StringBuilder pageBar = new StringBuilder();
		
		/////////////////////////////////////////////////////////////////////////////////
		
		// 1. 준비물
		
		final int pageBarSize = 5;
		final int totalPage = (int)Math.ceil((double)totalContents / limit); // 나머지까지 페이지 처리 -> 올림
		// 105 -> 105/10 -> 10.5 -> 11.0 -> 11
		/**
		 * pageStart
		 * 1p - 1 2 3 4 5 --> 1 (0 * pageBarSize + 1)
		 * 2p - 6 7 8 9 10 --> 6 (1 * pageBarSize + 1)
		 * 3p - 11 12 13 14 15 --> 11 (2 * pageBarSize + 1)
		 */
		final int pageStart = ((cPage - 1) / pageBarSize) * pageBarSize + 1;
		final int pageEnd = pageStart + pageBarSize - 1;
		// 증감변수는 pageStart부터 시작
		int pageNo = pageStart;
		
		// cPage속성 추가전 키워드 작업
		// url에 ?가 이미 있는지 없는지 여부를 따짐
		// cPage이외에도 다른 사용자 입력값이 있는 경우를 대비
		// /mvc/admin/memberFinder?type=id&kw=abc일 경우 &cPage=
		url += "?cPage=";

		/////////////////////////////////////////////////////////////////////////////////
		
		// 2. 영역에 따라 처리
		
		// 이전파트, pageNo파트, 다음파트로 나누어서 처리
		// 이전 1 2 3 4 5 다음
		// 이전 6 7 8 9 10 다음
		// 이전 11
		
		pageBar.append("<nav aria-label=\"Page navigation example\">\r\n"
				+ "  <ul class=\"pagination justify-content-center\">");
		
		// [1. 이전영역]
		if(pageNo == 1) {
			// 이전버튼 비활성화 : disabled값을 주기
			pageBar.append("<li class=\"page-item disabled\">\r\n"
					+ "      <a class=\"page-link\" href=\"#\" aria-label=\"Previous\">\r\n"
					+ "        <span aria-hidden=\"true\">&laquo;</span>\r\n"
					+ "        <span class=\"sr-only\">Previous</span>\r\n"
					+ "      </a>\r\n"
					+ "    </li>");
		} else {
			// 이전버튼 활성화
			// pageNo가 6 또는 11일 때 -> 5페이지, 10페이지로 감
			pageBar.append("<li class=\"page-item \">\r\n"
					+ "      <a class=\"page-link\" href=\"" + url + (pageNo - 1) + "\" aria-label=\"Previous\">\r\n"
					+ "        <span aria-hidden=\"true\">&laquo;</span>\r\n"
					+ "        <span class=\"sr-only\">Previous</span>\r\n"
					+ "      </a>\r\n"
					+ "    </li>");
		}
		
		// [2. pageNo영역]
		// 1, 6, 11로 시작
		// 1 ~ 5, 6 ~ 10, 11 ~ 12
		while(pageNo <= pageEnd && pageNo <= totalPage) {
			if(pageNo == cPage) {
				// 현재페이지 - 링크비활성화 : active이용하기
				pageBar.append("<li class=\"page-item active\">\r\n"
						+ "      <a class=\"page-link\" href=\"#\">"
						+ pageNo 
						+ "<span class=\"sr-only\">(current)</span></a>\r\n"
						+ "    </li>");
			}
			else {
				// 현재페이지가 아닌 경우 - 링크활성화
				pageBar.append("<li class=\"page-item\"><a class=\"page-link\" href=\"" + url + pageNo + "\">"
						+ pageNo
						+ "</a></li>");
			}
			pageNo++;
			// pageEnd를 마치거나, totalPage(last페이지)까지 다하면 탈출
		}
		
		// [3. 다음영역]
		if(pageNo > totalPage) {
			// 다음버튼 비활성화 : disabled값을 주기
			// 12페이지까지만 있다면, 위에서 13일때 탈출하니까, 마지막 페이지가 포함된 페이지바인 경우 next를 만들지 않음
			pageBar.append("<li class=\"page-item disabled\">\r\n"
					+ "      <a class=\"page-link\" href=\"#\" aria-label=\"Next\">\r\n"
					+ "        <span aria-hidden=\"true\">&raquo;</span>\r\n"
					+ "        <span class=\"sr-only\">Next</span>\r\n"
					+ "      </a>\r\n"
					+ "    </li>");
		}
		else {
			// 다음버튼 활성화
			// 이미 1이 더해진채로 탈툴하니까 +1 필요없음
			pageBar.append("<li class=\"page-item \">\r\n"
					+ "      <a class=\"page-link\" href=\"" + url + pageNo + "\" aria-label=\"Next\">\r\n"
					+ "        <span aria-hidden=\"true\">&raquo;</span>\r\n"
					+ "        <span class=\"sr-only\">Next</span>\r\n"
					+ "      </a>\r\n"
					+ "    </li>");
		}
		
		pageBar.append("</ul>\r\n"
				+ "</nav>");
		
		
		// StringBuilder 객체는 넘길 수가 없어서 toString 호출해서 넘기기
		return pageBar.toString();
	}
}
