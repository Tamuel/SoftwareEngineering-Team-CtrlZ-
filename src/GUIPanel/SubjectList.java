package GUIPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Assignment;
import Assignment.Subject;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import ServerClientConsole.ClientConsole;

public class SubjectList extends JPanel{

	private Account account;
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	private AssignmentList assignmentList;
	private QuestionList questionList;
	private Subject tempSubject;
	
	private ArrayList<SimpleButton> subjectButtons;
	private SimpleButton assignmentButton;
	private SimpleButton questionButton;

	private int subjectButtonHeight = 100;
	private int subjectButtonWidth = 200;
	private int xBorder = 10;
	private int yBorder = 10;
	private int height;
	
	private Color backgroundColor = new Color(250, 250, 250);
	private Color buttonColor = new Color(255, 255, 255);
	private Color fontColor = new Color(100, 100, 100);
	private Color clickColor = new Color(123, 170, 218);
	
	public SubjectList(int height, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		subjectButtons = new ArrayList<SimpleButton>();
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.account = ClientConsole.client.getAccount();
		
		this.setLayout(null);
		
		assignmentButton = new SimpleButton("과제", "게시판", subjectButtonHeight, subjectButtonWidth / 2);
		assignmentButton.setBackground(new Color(255, 255, 255, 100));
		assignmentButton.setFontColor(fontColor, fontColor);
		assignmentButton.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
		assignmentButton.setLocation(-subjectButtonWidth, 0);
		assignmentButton.addActionListener(new assignmentListener());
		
		questionButton = new SimpleButton("질문", "게시판", subjectButtonHeight, subjectButtonWidth / 2);
		questionButton.setBackground(new Color(255, 255, 255, 100));
		questionButton.setFontColor(fontColor, fontColor);
		questionButton.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
		questionButton.setLocation(-subjectButtonWidth, 0);
		questionButton.addActionListener(new questionListener());
		
		this.add(questionButton);
		this.add(assignmentButton);

		if(account.isStudent()) {
			// subjectButtons
			for(int i = 0; i < ((StudentAccount)account).getSubjects().size(); i++) {
				SimpleButton temp = new SimpleButton(((StudentAccount)account).getSubjects().get(i).getName(),
						((StudentAccount)account).getSubjects().get(i).getProfessor().getName() + " 교수님",
						subjectButtonWidth, subjectButtonHeight);
				temp.setLocation(xBorder, yBorder + (subjectButtonHeight + yBorder) * i);
				temp.addActionListener(new chooseListener());
				temp.setBackground(buttonColor);
				temp.setFontColor(fontColor, fontColor);
				temp.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
				
				if(i == ((StudentAccount)account).getSubjects().size() - 1)
					height = temp.getY() + subjectButtonHeight + yBorder;

				subjectButtons.add(temp);
				this.addButton(subjectButtons.get(i));
			}				
		}
		
		if(account.isProfessor()) {
			// subjectButton
			SimpleButton temp = new SimpleButton(((ProfessorAccount)account).getSubject().getName(),
					subjectButtonWidth, subjectButtonHeight);
			temp.setLocation(xBorder, yBorder);
			temp.addActionListener(new chooseListener());
			temp.setBackground(buttonColor);
			temp.setForeground(fontColor);
			temp.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));

			subjectButtons.add(temp);
			this.addButton(subjectButtons.get(0));
		}
		
		if(this.height > height)
			height = this.height;
		
		this.setSize(subjectButtonWidth + xBorder * 2, height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
		this.setBackground(backgroundColor);
		this.setBorder(null);
	}
	
	private class chooseListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(account.isStudent())
				tempSubject = ((StudentAccount)account).getSubjects().get(subjectButtons.indexOf((SimpleButton)ev.getSource()));
			else if(account.isProfessor())
				tempSubject = ((ProfessorAccount)account).getSubject();
			assignmentButton.setLocation(((SimpleButton)ev.getSource()).getX(), ((SimpleButton)ev.getSource()).getY());
			questionButton.setLocation(assignmentButton.getX() + assignmentButton.getWidth(), ((SimpleButton)ev.getSource()).getY());
//			((SimpleButton)ev.getSource()).setLocation(-subjectButtonWidth, 0);
		}
	}

	private class assignmentListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
//			if(assignmentList != null) {
//				assignmentList.removeAssignmentPanel();
//				boardFrame.remove(assignmentList);
//			}
//			if(questionList != null) {
//				questionList.removeQuestionPanel();
//				boardFrame.remove(questionList);
//			}
			
			if(account.isStudent()) {
				assignmentList = new AssignmentList(thisHeight(), tempSubject, boardFrame, titleBar);
				titleBar.setAssignmentPath(tempSubject);
			} else if(account.isProfessor()) {
				assignmentList = new AssignmentList(thisHeight(), tempSubject, boardFrame, titleBar);
				titleBar.setAssignmentPath(tempSubject);
			}
			
			assignmentButton.setLocation(-subjectButtonWidth, 0);
			questionButton.setLocation(-subjectButtonWidth, 0);
			
			boardFrame.addAssignmentPanel(assignmentList);
			boardFrame.repaint();
		}
	}
	
	private class questionListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
//			if(questionList != null) {
//				questionList.removeQuestionPanel();
//				boardFrame.remove(questionList);
//			}
//			if(assignmentList != null) {
//				assignmentList.removeAssignmentPanel();
//				boardFrame.remove(assignmentList);
//			}
			
			if(account.isStudent()) {
				questionList = new QuestionList(thisHeight(), tempSubject, boardFrame, titleBar);
				titleBar.setQuestionPath(tempSubject);
			} else if(account.isProfessor()) {
				questionList = new QuestionList(thisHeight(), tempSubject, boardFrame, titleBar);
				titleBar.setQuestionPath(tempSubject);
			}
			
			assignmentButton.setLocation(-subjectButtonWidth, 0);
			questionButton.setLocation(-subjectButtonWidth, 0);
			
			boardFrame.addQuestionPanel(questionList);
			boardFrame.repaint();
		}
	}

	public void addButton(SimpleButton button) {
		this.add(button);
	}

	public int thisRight() {
		return this.getX() + this.getWidth();
	}
	
	public int thisHeight() {
		return this.getHeight();
	}
}
