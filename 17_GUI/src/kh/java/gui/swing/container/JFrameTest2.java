package kh.java.gui.swing.container;

import javax.swing.JFrame;

//방법2. jframe메소드 상속
public class JFrameTest2 extends JFrame {

	public JFrameTest2() {
//		setSize(300, 200);
//		setLocation(200, 200);
		// setLocation(x,y), setSize(width, height) 코드를 한번에 쓸 수 있음
		setBounds(200, 200, 300, 200);
		//리사이즈 방지
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		}

	public static void main(String[] args) {
		new JFrameTest2();
	}
}
