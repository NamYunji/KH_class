package kh.java.gui.swing.container;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//컴포넌트들끼리를 묶어줄 수 있는 것 : JPanel
//패널도 컨테이너임

//또한 JFrame을 상속해야함
public class JPanelTest extends JFrame{

	//JPanel에 해당하는 객체를 만든다는 것 : 화면에 보여주기 위한 컴포넌트를 추가하는 등의 설정을 하는 것
	public JPanelTest() {
		
		//JFrame세팅
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//배치담당 매니저 : 레이아웃 매니저가 있음 -> 그것을 고려해서 추가해야 함
		//JFrame의 기본 LayoutManager객체는 BorderLayout이다
//	(	setLayout(new BorderLayout());  ) 생략되어 있음
		
		//null layout : layout manager객체를 사용하지 않고 직접 좌표를 지정 -> 보다 직관적임
		//레이아웃을 null로 설정하고, 밑에서 각 컴포넌트들의 사이즈, 위치 지정
		setLayout(null);
		
		/////////////////////////////////////////////////////////
		
		//자식 컴포넌트를 JFrame에 추가
		
		//JPanel1 객체 생성
		JPanel panel1 = new JPanel();
		
		//JPanel 세팅작업
		//바탕 색상 지정
		//방법1. 색상명 입력
		panel1.setBackground(Color.MAGENTA);
		//방법2. 색상에 맞는 rgb넘버를 찾아서 대입하기
		panel1.setBackground(new Color(0,255,0));
		panel1.setBounds(100, 50, 300, 400);
		
		//객체만 만든다고 해서 실행이 되는 것이 아님
		//각각 부모컨테이너에 추가해주는 작업 필요
		//JLable -> JPanel -> JFrame에 추가
		
		///////////////////////////////
		//라벨 추가
		JLabel label1 = new JLabel("panel1");		
		//panel에 label추가
		panel1.add(label1);
		
		///////////////////////////////
		//JPanel1 객체 생성 & 세팅작업		
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.gray);
		panel2.setBounds(400, 50, 300, 400);
		JLabel label2 = new JLabel("panel2");
		panel2.add(label2);
		
		///////////////////////////////
		
		//frame에 panel 추가
		add(panel1);		
		add(panel2);
		//BorderLayout으로 위치 지정해주고, 추가해주기
		//but 하나하나 따져가면서 하기 힘듦
//		add(panel1, BorderLayout.NORTH);
		
		/////////////////////////////////////////////////////////
		
		setVisible(true);

	}
	
	//생성자 호출
	public static void main(String[] args) {
		new JPanelTest();
	}

}
