package test03.ncs.test3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTest {
	public static void main(String[] args) {
		//Calendar클래스는 추상클래스 -> new에 사용못함 -> getInstance메소드를 통해 객체 생성
		//Calendar.getInstance 는 현재시간을 기준으로 calendar객체를 만든다
//		Calendar today = Calendar.getInstance();
		//Calendar의 후손(파생) 클래스임
		Calendar td = new GregorianCalendar();
		int year = td.get(Calendar.YEAR);
		int month = td.get(Calendar.MONTH + 1);
		int date = td.get(Calendar.DATE);
		int week = td.get(Calendar.DAY_OF_WEEK);
		
		Calendar b = new GregorianCalendar(1987, 4, 27);
		long millis1 = b.getTimeInMillis();
		Date bday = new Date(millis1);
		
		//calendar to date
		long mills2 = td.getTimeInMillis();
		Date today = new Date(mills2);

		long millis3 = today - bday;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 M월 d일");
		String sdfToday = sdf.format(today);
		System.out.println(sdfToday);
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy년 M월 d일 E요일");
		String sdf22 = sdf.format(bday);
		System.out.println(sdf22);

	}

}
