package kh.java.gui.util;

import javax.swing.JFrame;
import javax.swing.JPanel;

import kh.java.gui.swing.change.panel.CustomPanel;


public class MyUtil {

	//세팅 만들어주기 용  
	//반복적 코드는 init을 호출하여 해결가능
	public static void init(JFrame f, int w, int h, String title) {
		f.setTitle(title);
		f.setSize(w, h);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//창 바꿔주기 용 (어디서 실행할 건지, 현재패널, 끄고 어떤 패널을 표현할지)
	public static void changePanel(JFrame parent, CustomPanel current, JPanel next) {
		//Removes the specified component from the container.
		//This will do nothing if comp is not a child of the JFrame or contentPane.
		//Parameters:comp / the component to be removed
		parent.remove(current);
		//Appends the specified component to the end of this container. 
		//Parameters:comp / the component to be added
		parent.add(next);
		
		//Revalidates the component hierarchy up to the nearest validate root.
		parent.revalidate(); //container하위 계층구조를 새로고침
		//Repaints this component. 
		//If this component is a lightweight component, this method causes a call to this component's paintmethod as soon as possible.
		//Otherwise, this method causesa call to this component's update method as soonas possible. 
		parent.repaint(); //화면 다시 그리기용 method
		
	}

}
