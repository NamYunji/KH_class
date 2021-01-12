package kh.java.gui.swing.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kh.java.gui.util.MyUtil;

/*
 * Event : 컴퓨터(프로그램)과 사용자간의 상호작용
 * event -> os에 의해 감지됨 -> jvm -> event분배쓰레드 -> 각 component의 event handler메소드 호출
 * 1. 저수준 이벤트
 * -key event
 * -mouse event
 * -focus event(입력을 기다리는 상태)
 * -window event
 * 2. 고수준 semantic event (component 별처리)
 * -action event
 * -item event
 * -adjustment event
 * 
 * 
 */
public class LowLevelEventTest1 extends JFrame{

	public LowLevelEventTest1(int w, int h, String title) {
		MyUtil.init(this, w, h, title);
	
		JPanel panel = new JPanel();
		JLabel label = new JLabel("패널");
		panel.add(label);
		
		//컴포넌트에 이벤트핸들러 객체 바인딩
//		MyMouseListener listener = new MyMouseListener();
//		panel.addMouseListener(listener);
//		panel.addMouseWheelListener(listener);
//		panel.addMouseMotionListener(listener);
		
		panel.addMouseListener(new Mymouselister2());

		
		add(panel);
	}
	//원하는 것만 구현 가능
	//MouseAdapter클래스
	//public abstract class MouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener {
	//+ 모든 것을 override하여 구현해놓음 -> 컴파일 오류 나지 않을테니, 원하는 것만 구현하면 됨
	//---> 직접적으로 쓰지 않고, MouseAdapter를 상속하여 사용함, 미리 모든 것을 상속해놓고, 원하는 것만 오버라이드하여 사용
	public class Mymouselister2 extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked!" + e);
		}
	}
	
	public class MyMouseListener implements 
		MouseListener, MouseWheelListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("mouseClicked!" + e);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("mousePressed!");
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("mouseReleased!");
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("mouseEntered!");
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("mouseExited!");
			
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			System.out.println("mouseWheelMoved!");
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("mouseDragged!");
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println(e.getX() + " , " + e.getY());
		}
		
	}
	

	public static void main(String[] args) {
		new LowLevelEventTest1(300, 200, "저수준 이벤트").setVisible(true);
	}

}