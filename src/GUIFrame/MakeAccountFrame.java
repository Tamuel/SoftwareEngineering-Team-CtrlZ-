package GUIFrame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Subject;
import Controller.AccountController;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleJFrame;
import GuiComponent.SimpleTextField;
import ServerClientConsole.ClientConsole;
import ServerClientConsole.Protocol;

public class MakeAccountFrame extends SimpleJFrame{
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
	
	public MakeAccountFrame(String frameName, int width, int height, LoginFrame loginFrame) {
		super(frameName, width, height);

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

	public void checkAccount()
	{
		while(ClientConsole.client.isMsgReceive() == false)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(ClientConsole.client.getAccount().getId() + " " + ClientConsole.client.getAccount().getName());
		
		if(ClientConsole.client.getAccount().isProfessor()) {
			BulletinBoardFrame board = new BulletinBoardFrame("과제 제출", 1100, 630, ClientConsole.client.getAccount());
			ClientConsole.client.setMsgReceive(false);
			visible(false);
		} else if(ClientConsole.client.getAccount().isStudent()) {
			ClientConsole.client.setMsgReceive(false);
			SubjectSelectFrame sbFrame = new SubjectSelectFrame((StudentAccount)ClientConsole.client.getAccount(), "과목 선택", 300, 300, loginFrame);
			ClientConsole.client.setMsgReceive(false);
			visible(false);
		}
	}
	
	private class submitListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			try
			{
				ClientConsole.client.sendToServer(new Protocol("[JOIN]", idField.getText() + ":" + passwordField.getText() + ":" + nameField.getText() + ":" + subjectField.getText()));
			}
			catch(Exception ex)
			{
				System.err.println(ex.toString());
			}
		
			checkAccount();
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
