package kh.java.gui.swing.container.layout;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kh.java.gui.util.MyUtil;

//마찬가지로 JFrame 상속
public class BorderLayoutTest extends JFrame{
	
	//매개변수 생성자 -> 크기, 제목 전달
	public BorderLayoutTest(int w, int h, String title) {
		//공용 static메소드를 만들어서 현재객체, 인자로 전달받은 너비, 높이, 제목까지 전달함
		MyUtil.init(this, w, h, title);

		//패널도 커스터마이징 가능
		//스윙에서 제공하는 클래스를 내 입맛대로 만들기
		//JFrame객체의 기본 레이아웃은 borderlayout
		JPanel north = new BorderPanel("북", Color.cyan);
		JPanel south = new BorderPanel("남", Color.blue);
		JPanel east	 = new BorderPanel("동", Color.gray);
		JPanel west = new BorderPanel("서", Color.orange);
		JPanel center = new BorderPanel("가운데", Color.green);
		//사이즈 지정은 따로 하지 않음. 패널 내 컴포넌트 크기에 따라서 패널 크기 정해짐
		//-> 크기 지정을 해도 적용이 되지 않음
		
		
		//add는 container의 메소드
		add(north, BorderLayout.NORTH);
//		add(south, BorderLayout.SOUTH);
		add(east, BorderLayout.EAST);
		add(west, BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
		//but 동서남북을 무조건 다 써줄 필요 없음
		//-> 떼어내거나 추가하여 본인입맛대로 해주면 됨
		
	}
	
	/**
	 * 내부클래스 (클래스 안의 클래스)
	 *  외부클래스에서 자유롭게 가져다 사용할 수 있음
	 */
	public class BorderPanel extends JPanel {
		
		public BorderPanel(String title, Color c) {
			setBackground(c); //배경색 지정
			JLabel label = new JLabel(title);
			add(label); //현재 JPanel객체에 추가
			
		}
	}
	
	
	public static void main(String[] args) {
		//중요 : setVisible을 맨 마지막에 넣어줌
		new BorderLayoutTest(500, 500, "Border LayoutTest").setVisible(true);
	}

}
