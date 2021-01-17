package kh.java.gui.swing.container;

import javax.swing.JFrame;

/*
 * 방법2 : JFrame을 상속한 커스텀 클래스 작성后  객체 생성하기 (추천!)
 */

//jframe클래스 상속

public class JFrameTest2 extends JFrame {

	//생성자 안에서 필요한 처리들을 해줌
	public JFrameTest2() {
//		setSize(300, 200);
//		setLocation(200, 200);
		// setBounds(x,y,width,height);
		// setLocation(x,y), setSize(width, height) 코드를 한번에 쓸 수 있음
		setBounds(200, 200, 300, 200);
		//리사이즈 여부 설정
		//(사용자가 윈도우 크기를 조정할 수 있도록 하는것
		//setResizable(true); : 리사이즈 가능
		//setResizable(false); : 리사이즈 불가
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		}

	//생성자 한번만 호출해 놓으면
	//객체생성을 하고, <객체.메소드명>의 메소드 호출과정을 거치지 않아도
	//그 생성자 안에서 JFrame내 메소드를 모두 내것처럼 사용 가능
	public static void main(String[] args) {
		new JFrameTest2();
	}
}
