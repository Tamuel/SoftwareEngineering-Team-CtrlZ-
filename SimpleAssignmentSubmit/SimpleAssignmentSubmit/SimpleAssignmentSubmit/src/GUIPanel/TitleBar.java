package GUIPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objectSave.ObjectSaveSingleton;
import Account.Account;
import Assignment.Assignment;
import Assignment.Subject;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleLabel;

public class TitleBar extends JPanel{
	private Account account;
	
	private SimpleLabel nameField;
	private SimpleLabel pathField;
	private SimpleButton closeWindow;

	public TitleBar(BulletinBoardFrame boardFrame, int height, Account account) {
		this.account = account;
		
		this.setLayout(null);
		this.setSize(boardFrame.getWidth(), height);
		this.setBackground(new Color(255, 255, 255));
		
		String title = "  " + account.getId() + " | " + account.getName();
		if(account.isProfessor())
			title += " 교수님";
		else
			title += " 학생";
		nameField = new SimpleLabel(title);
		nameField.setSize(this.getWidth() / 4, this.getHeight());
		nameField.setLocation(0, 0);
		nameField.setHorizontalAlignment(SwingConstants.LEFT);
		nameField.setForeground(new Color(255, 132, 0));
		//nameField.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(180, 180, 180)));
		
		pathField = new SimpleLabel("");
		pathField.setSize(this.getWidth() / 2 - 60, this.getHeight());
		pathField.setLocation(this.getWidth() / 2, 0);
		pathField.setSmallFont();
		pathField.setForeground(new Color(255, 132, 0));
		
		closeWindow = new SimpleButton("Close");
		closeWindow.setSize(60, this.getHeight() - 1);
		closeWindow.setLocation(pathField.getX() + pathField.getWidth(), 0);
		closeWindow.setBackground(new Color(255, 255, 255));
		closeWindow.setForeground(new Color(150, 150, 150));
		closeWindow.addActionListener(new CloseWindow());

		this.add(nameField);
		this.add(pathField);
		this.add(closeWindow);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180, 180, 180)));
	}

	private class CloseWindow implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			ObjectSaveSingleton.getInstance().saveAccounts();
			System.exit(0);
		}
	}
	

	public void setQuestionPath(Subject subject) {
		pathField.setText("질문 게시판 > " + subject.getProfessor().getName() + " 교수님 > " + subject.getName());
	}
	
	public void setAssignmentPath(Subject subject) {
		pathField.setText("과제 게시판 > " + subject.getProfessor().getName() + " 교수님 > " + subject.getName());
	}
	
	public void setAssignmentPath(Assignment assignment) {
		pathField.setText("과제 게시판 > " + assignment.getProfessor().getName() + " 교수님 > " + assignment.getSubject().getName() + " > " + assignment.getTopic());
	}
}
