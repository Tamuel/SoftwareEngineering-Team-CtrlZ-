package GUIFrame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import Account.Account;
import Account.Professor;
import Account.Student;
import Assignment.Subject;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleJFrame;
import GuiComponent.SimpleTextField;

/**
 * 계정을 만드는 프레임 클래스
 * 
 * @author eastern7star
 *
 */
public class MakeAccountFrame extends SimpleJFrame{
	
	private Account accounts;
	
	private LoginFrame loginFrame;
	
	private SimpleTextField idField;
	private SimpleTextField passwordField;
	private SimpleTextField nameField;
	private SimpleTextField subjectField;
	private SimpleButton submitButton;
	private SimpleButton cancleButton;
	
	private int componentHeight = 40;
	private int adjust = -20;
	private int xBorder = 20;
	private int yBorder = 10;
	
	private String subString = "담당 과목 (없으면 학생입니다)";
	
	public MakeAccountFrame(Account accounts, String frameName, int width, int height, LoginFrame loginFrame) {
		super(frameName, width, height);

		this.accounts = accounts;
		this.loginFrame = loginFrame;
		
		idField = new SimpleTextField("ID");
		idField.setSize(WIDTH - xBorder * 2, componentHeight);
		idField.setBackground(new Color(240, 240, 240));
		idField.setLocation(xBorder, componentHeight + adjust);
		
		passwordField = new SimpleTextField("Password");
		passwordField.setSize(WIDTH - xBorder * 2, componentHeight);
		passwordField.setBackground(new Color(240, 240, 240));
		passwordField.setLocation(xBorder, componentHeight * 2 + yBorder + adjust);
		
		nameField = new SimpleTextField("이름");
		nameField.setSize(WIDTH - xBorder * 2, componentHeight);
		nameField.setBackground(new Color(240, 240, 240));
		nameField.setLocation(xBorder, componentHeight * 3 + yBorder * 2 + adjust);

		subjectField = new SimpleTextField(subString);
		subjectField.setSize(WIDTH - xBorder * 2, componentHeight);
		subjectField.setBackground(new Color(240, 240, 240));
		subjectField.setLocation(xBorder, componentHeight * 4 + yBorder * 3 + adjust);
		
		submitButton = new SimpleButton("등록");
		submitButton.setSize((WIDTH - xBorder *3) / 2, componentHeight);
		submitButton.setLocation(xBorder, componentHeight * 5 + yBorder * 4 + adjust);
		submitButton.addActionListener(new submitListener());
		
		cancleButton = new SimpleButton("취소");
		cancleButton.setSize((WIDTH - xBorder *3) / 2, componentHeight);
		cancleButton.setLocation(xBorder * 2 + submitButton.getWidth(), submitButton.getY());
		cancleButton.addActionListener(new cancleListener());
		
		this.add(idField);
		this.add(passwordField);
		this.add(nameField);
		this.add(subjectField);
		this.add(submitButton);
		this.add(cancleButton);
		
		this.getContentPane().setBackground(new Color(255, 255, 255));
		this.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
	}
	
	private class submitListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			/* 교수님 계좌 생성 */
			if(accounts.checkIdRepeated(idField.getText().toString()) && !subjectField.getText().equals(subString)) {
				Subject newSubject = new Subject(subjectField.getText().toString());
				Professor temp = new Professor(idField.getText().toString(), passwordField.getText().toString(), nameField.getText().toString(), newSubject);
				accounts.addAccount(temp);
				loginFrame.setVisible(true);
				visible(false);
			} else if(accounts.checkIdRepeated(idField.getText().toString()) && subjectField.getText().equals(subString)) { /* 학생 계좌 생성 */
				Student temp = new Student(idField.getText().toString(), passwordField.getText().toString(), nameField.getText().toString());
				accounts.addAccount(temp);
				SubjectSelectFrame sbFrame = new SubjectSelectFrame(accounts.getAccounts(), temp, "과목 선택", 300, 300, loginFrame);
				visible(false);
			} else if(!accounts.checkIdRepeated(idField.getText().toString())) {
				idField.setText("");
				idField.setGrayColor("이미 아이디가 존재합니다");
				idField.focusLost(null);
			}
		}
	}
	
	private class cancleListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			LoginFrame login = new LoginFrame("Login", 300, 200);
			visible(false);
		}
	}
	
	public void visible (boolean bool) {
		this.setVisible(bool);
	}
}
