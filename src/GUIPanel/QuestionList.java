package GUIPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Assignment;
import Assignment.Subject;
import Controller.AccountController;
import Controller.ProfessorAccountController;
import Controller.StudentAccountController;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleTextArea;
import QnA.Answer;
import QnA.Question;
import ServerClientConsole.ClientConsole;

public class QuestionList extends JPanel{
	
	private Subject subject;
	private Question thisQuestion;
	
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	private ContentPanel qnaPanel;
	
	
	private ArrayList<SimpleButton> questionButtons;

	private int questionButtonHeight = 80;
	private int questionButtonWidth = 250;
	private int xBorder = 10;
	private int yBorder = 10;
	private int height;

	private Color backgroundColor = new Color(250, 250, 250);
	private Color buttonColor = new Color(255, 255, 255);
	private Color fontColor = new Color(100, 100, 100);
	
	public QuestionList(int height, Subject subject, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		questionButtons = new ArrayList<SimpleButton>();
		
		this.titleBar = titleBar;
		this.subject = subject;
		this.boardFrame = boardFrame;

		this.setLayout(null);
		this.setBackground(backgroundColor);
		this.setBorder(null);

		paint();
	}
	
	public void paint() {
		subject = getSubject();
		int numOfQuestions = subject.getSubjectQuestions().getQuestions().size();
		for(int i = 0; i < numOfQuestions; i++) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			
			SimpleButton temp = new SimpleButton(subject.getSubjectQuestions().getQuestions().get(i).getTopic(),
					subject.getSubjectQuestions().getQuestions().get(i).getStudent().getName(),
					dateFormat.format(subject.getSubjectQuestions().getQuestions().get(i).getTime()) + "�� �ۼ�",
					questionButtonWidth, questionButtonHeight);
			temp.setLocation(xBorder, yBorder + (questionButtonHeight + yBorder) * (numOfQuestions - i - 1));
			temp.addActionListener(new questionButtonListener());
			temp.setBackground(buttonColor);
			temp.setFontColor(fontColor, fontColor, fontColor);
			temp.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
			
			if(i == 0)
				this.height = temp.getY() + questionButtonHeight + yBorder;
			
			questionButtons.add(temp);
			this.addButton(questionButtons.get(i));
		}
		
		height -= questionButtonHeight / 2;
		
		if(this.height > height)
			height = this.height;
		
		this.setSize(xBorder * 2 + questionButtonWidth, height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}

	private class questionButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			removeQuestionPanel();
			Account account = ClientConsole.client.getAccount();
			if(account.isProfessor()) {
				subject = ((ProfessorAccount)account).getSubject();
			} else {

				StudentAccountController sCon = new StudentAccountController((StudentAccount)ClientConsole.client.getAccount());
				subject = sCon.getSubject(subject.getName());
			}
			
			thisQuestion = subject.getSubjectQuestions().getQuestions().get(questionButtons.indexOf((SimpleButton)ev.getSource()));
			
			qnaPanel = new ContentPanel(thisQuestion, boardFrame, titleBar);

			boardFrame.addContentPanel(qnaPanel);
			boardFrame.repaint();
		}
	}
	
	public Subject getSubject() {
		if(ClientConsole.client.getAccount().isProfessor())
			return ((ProfessorAccount)ClientConsole.client.getAccount()).getSubject();
		else {
			StudentAccountController sCon = new StudentAccountController(
					(StudentAccount)ClientConsole.client.getAccount());
			return sCon.getSubject(subject.getName());
		}
	}

	public void removeQuestionPanel() {
		if(qnaPanel != null)
			boardFrame.remove(qnaPanel);
	}

	public void addButton(SimpleButton button) {
		this.add(button);
	}
	
	public int thisHeight() {
		return this.getHeight();
	}
	
	public int thisRight() {
		return this.getX() + this.getWidth();
	}
}
