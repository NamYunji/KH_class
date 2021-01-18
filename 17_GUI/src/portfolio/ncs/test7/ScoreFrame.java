package portfolio.ncs.test7;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ScoreFrame extends JFrame {
	
	JTextField javaScore;
	JTextField sqlScore;
	JTextField total;
	JTextField average;
	
	
	public ScoreFrame() {
		setLayout(null);
		setTitle("문제7");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JLabel label = new JLabel("점수를 입력하세요");
		label.setBounds(50, 0, 400, 100);
		Font font = new Font("굴림", Font.BOLD, 45);

		JLabel scrLabel = new JLabel("자바 : ");
		scrLabel.setBounds(10, 100, 50, 50);
		
		javaScore = new JTextField();
		javaScore.setBounds(50, 100, 150, 50);
		
		JLabel sqlLabel = new JLabel("SQL : ");
		sqlLabel.setBounds(260, 100, 50, 50);
		
		sqlScore = new JTextField();
		sqlScore.setBounds(300, 100, 150, 50);
		
		
		JButton calcBtn = new JButton("계산하기");
		calcBtn.setBounds(180, 190, 140, 50);
		
		JLabel tLabel = new JLabel("총점 : ");
		tLabel.setBounds(10, 280, 150, 50);
		
		total = new JTextField();
		total.setBounds(50, 280, 150, 50);
		
		JLabel avgLabel = new JLabel("평균 : ");
		avgLabel.setBounds(260, 280, 50, 50);
		
		average = new JTextField();
		average.setBounds(300, 280, 150, 50);
		
		calcBtn.addActionListener(new MyActionListener());
		

		label.setFont(font);
		add(label);
		add(javaScore);
		add(scrLabel);
		add(sqlScore);
		add(sqlLabel);
		add(calcBtn);
		add(tLabel);
		add(total);
		add(avgLabel);
		add(average);	
		
	}
	
	
	
	class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int java = Integer.parseInt(javaScore.getText());
			int sql = Integer.parseInt(sqlScore.getText());

			String t = Integer.toString(java + sql);
			total.setText(t);
			
			String avg = Integer.toString((java + sql) /2);
			average.setText(avg);
		}
		
	}



}
