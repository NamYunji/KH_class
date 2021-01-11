package kh.java.gui.swing.container;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//컴포넌트들끼리를 묶어줄 수 있는 것 : JPanel
//패널도 컨테이너임

/*
 * 800*500 가운데 위치시키기
 */
public class JPanelTest extends JFrame{

	public JPanelTest() {
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JFrame의 기본 LayoutManager객체는 BorderLayout이다
		//배치담당 매니저 : 레이아웃 매니저가 있음 -> 그것을 고려해서 추가해야 함
//	(	setLayout(new BorderLayout());  ) 생략되어 있음
		//null layout : layout manager객체를 사용하지 않고 직접 좌표를 지정
		setLayout(null);
		
		//자식 컴포넌트 추가
		JPanel panel1 = new JPanel();
		//색상명 입력하기
//		panel1.setBackground(Color.MAGENTA);
		//색상에 맞는 rgb넘버를 찾아서 대입하기
		panel1.setBackground(new Color(0,255,0));
		JLabel label1 = new JLabel("panel1");
		panel1.setBounds(100, 50, 300, 400);
		//add메소드 필요
		panel1.add(label1);
		
		
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.gray);
		JLabel label2 = new JLabel("panel2");
		panel2.setBounds(400, 50, 300, 400);
		panel2.add(label2);
		
		
		
		//프레임에 패널 추가
//		add(panel1, BorderLayout.NORTH);
		add(panel1);		
		add(panel2);
		
		setVisible(true);

	}
	
	public static void main(String[] args) {
		new JPanelTest();
	}

}
