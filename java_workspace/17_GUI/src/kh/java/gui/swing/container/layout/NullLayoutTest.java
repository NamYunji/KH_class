package kh.java.gui.swing.container.layout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kh.java.gui.util.MyUtil;

public class NullLayoutTest extends JFrame{
	
	//a lightweight component that allows the editing of a single line of text.
	private JTextField id;
	//a lightweight component that allows the editing of a single line of text where the view indicates something was typed,
	//but does not show the original characters.
	private JPasswordField pwd;
	
	
	public NullLayoutTest(int w, int h, String title) {
		MyUtil.init(this, w, h, title);
		
		//layout 무효화 (nulllayout을 쓰겠다)
		setLayout(null);
		
		JLabel labelId = new JLabel("아이디 : ");
		labelId.setBounds(50, 100, 60, 50);
		
		id = new JTextField();
		id.setBounds(110, 100, 200, 50);
		
		JLabel labelPwd = new JLabel("비번 : ");
		labelPwd.setBounds(50, 160, 60, 50);
		
		//노출되지 않기 위함
		pwd = new JPasswordField();
		pwd.setBounds(110, 160, 200, 50);		

		//버튼만들기
		JButton btn = new JButton("로그인");
		btn.setBounds(330, 100, 100, 110);

		//실제 사용자가 있는지 비교하여 확인한 후 -> 아이디, 비번이 맞으면 로그인 시켜줌
		//사용자가 로그인버튼을 클릭시, 입력한 아이디, 패스워드를 가져오기
		//event (사용자가 어플리케이션에서 취하는 액션,
		//  ex. 버튼클릭, 키보드 입력, 커서 움직임 등등 어플리케이션에서 하는 모든 것)
		//->
		//event handling : 이벤트를 처리하는 것
		//->
		//1.이벤트 처리객체 생성(클래스 작성)
		//2.btn컴포넌트와 연결(binding)
		
		
		//addActionListener : Adds an ActionListener to the button.
		//Parameters: the ActionListener to be added
		btn.addActionListener(new LoginBtnListener());		
		
		add(labelId);
		add(id);
		add(labelPwd);
		add(pwd);
		add(btn);		

			}
	
    /**
     * 1.이벤트처리객체 생성(클래스 작성)
     * JButton용 action event handler클래스 작성
     * 이벤트가 발생한지 아닌지 감지해주는 역할
     * event handler
     * event listner
     */
    public class LoginBtnListener implements ActionListener {
        /**
         * JButton의 ActionEvent가 발생시 자동호출되는 메서드
         */
        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.println("버튼을 클릭하셨습니다.");
            //사용자 입력 id, password 가져오기
            System.out.println("id : " + id.getText());
            //new String(pwd.getPassword() -> CHAR배열로 가져온 후, 문자열로 보기 위해 다시 String객체에..
            System.out.println("password : " + new String(pwd.getPassword()));
            
        }
    }
    
    public static void main(String[] args) {
        new NullLayoutTest(500, 500, "로그인").setVisible(true);
    }
}
