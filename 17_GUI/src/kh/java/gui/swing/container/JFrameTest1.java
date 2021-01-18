package kh.java.gui.swing.container;

import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * 맨처음 해야하는 것 : JFrame만들기
 * JFrame객체 생성
 * 방법1 
 * new JFrame()
 * -> 직접 객체 생성하기
 * 방법2 
 * -> JFrame을 상속한 커스텀 클래스 작성后  객체 생성하기
 */

/*
 * 방법 1 : main에 직접 객체를 생성하기
 */
public class JFrameTest1 {
	public static void main(String[] args) {
		
		//JFrame 객체 생성
		JFrame f = new JFrame();
		
		//////////////////////////////////////////////////////////
		//setter들을 이용한 세팅
		
		//setTitle("제목입력") : 제목지정
		f.setTitle("Hello Swing!");
		
		//윈도우 크기,위치 지정 
		//setSize(width(가로), height(세로)) : 가로, 세로 너비 지정
		f.setSize(300, 200);
		
		//setLocation : 윈도우 위치 지정
		//모니터(스크린)의 left&top을 기준으로 어디에 위치시킬지 지정
		//setLocation(x(우측으로 움직임), y(아래쪽으로 움직임))
		//cf. 값이 음수일수도 있으나, 화면 밖에 위치하게 될 것
		f.setLocation(0, 0);

		//스크린 가운데 띄우기
		//f.setLocationRelativeTo(null);
		
		//프로그램은 직접 종료시키기 전까지 윈도우의 x버튼을 눌러도 돌아가고 있음
		//꺼진 것 처럼 보여도, 윈도우 감추기 처리만 된 상태
		//x버튼 -> 프로그램 종료 처리 for x버튼 누를 시 프로그램이 함께 꺼지도록
		//setDefaultCloseOperation메소드의 JFrame의 EXIT_ON_CLOSE 세터 이용
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//자식 컴포넌트 : 컨테이너에 포함된 컴포넌트
		//컨테이너에 자식 컴포넌트 추가
		//객체(JFrame名).add(new 컴포넌트("텍스트 입력"));
//		JLabel label = new JLabel("안녕");
//		f.add(label);
		f.add(new JLabel("안녕!"));
		
		//시각화처리
		//중요 : 무조건 맨 마지막에 작성해야 함
		//cf. 자식 컴포넌트들이 추가되어도 무조건 맨 마지막에 작성!
		//if setVisible을 작성하지 않거나, setVisible작성 후 요소추가하거나, setVisible에 false를 입력하면 나오지 않을 수 있음
		f.setVisible(true);

		
	}
}
