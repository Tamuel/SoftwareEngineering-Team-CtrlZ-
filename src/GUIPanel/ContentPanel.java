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
import Account.StudentAccount;
import Assignment.Assignment;
import Assignment.Subject;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleTextArea;
import QnA.Answer;
import QnA.Question;
import ServerClientConsole.ClientConsole;

public class ContentPanel extends JPanel{
	
	private StudentAccount student;
	
	private Subject subject;
	private Question thisQuestion;
	private Assignment thisAssignment;
	
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	private QuestionPanel questionPanel;
	private AssignmentPanel assignmentPanel;
	private ArrayList<AnswerPanel> answerPanels;

	private int height;
	private int arrangeHeight;
	private int xBorder = 10;
	
	private Color backgroundColor = new Color(255, 255, 255);

	public ContentPanel(Question question, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		answerPanels = new ArrayList<AnswerPanel>();
		thisQuestion = question;
		
		height = boardFrame.getHeight() - titleBar.getHeight();

		this.setLayout(null);
		this.setSize(boardFrame.getContentWidth() + 20, height);

		questionPanel = new QuestionPanel(thisQuestion, this, boardFrame);
		questionPanel.setLocation(xBorder, xBorder);
		this.add(questionPanel);
		
		NewAnswerPanel naPanel = new NewAnswerPanel(ClientConsole.client.getAccount(), question, boardFrame, questionPanel, titleBar);
		naPanel.setLocation(questionPanel.getX(), questionPanel.getY() + questionPanel.getHeight() + xBorder);
		this.add(naPanel);
		
		for(int i = thisQuestion.getAnswers().size() - 1; i >= 0; i--) {
			AnswerPanel make = new AnswerPanel(thisQuestion.getAnswers().get(i), boardFrame, this, titleBar);
			make.setLocation(questionPanel.getX(),
					questionPanel.getY() + questionPanel.getHeight() + xBorder * (thisQuestion.getAnswers().size() - i + 1) + make.getHeight() * (thisQuestion.getAnswers().size() - i));
			answerPanels.add(make);
			this.add(make);
			if(i == 0)
				arrangeHeight = make.getY() + make.getHeight() + xBorder;
		}
		
		if(arrangeHeight > height)
			height = arrangeHeight;

		this.setBorder(null);
		this.setBackground(backgroundColor);
		this.setSize(boardFrame.getContentWidth(), height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}

	/* 학생이 과제를 클릭했을 때 */
	public ContentPanel(Assignment assignment, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.student = ((StudentAccount)ClientConsole.client.getAccount());
		answerPanels = new ArrayList<AnswerPanel>();
		thisAssignment = assignment;
		
		height = boardFrame.getHeight() - titleBar.getHeight();

		this.setLayout(null);
		this.setSize(boardFrame.getContentWidth() + 20, height);

		assignmentPanel = new AssignmentPanel(assignment, boardFrame, this, titleBar);
		assignmentPanel.setLocation(xBorder, xBorder);
		this.add(assignmentPanel);
		

		if(ClientConsole.client.getAccount().isStudent() && assignment.isSubmitted(((StudentAccount)ClientConsole.client.getAccount()))) {
			addSubmittedStudentAssignmentPanel(((StudentAccount)ClientConsole.client.getAccount())
					.getWhichAssignmentSubmitted().indexOf(assignment));
		}
		
		/* 평가된 과제가 있을 경우 */
		if(ClientConsole.client.getAccount().isStudent() &&
				student.getWhichAssignmentSubmitted().indexOf(assignment) != -1 &&
				student.getAssignments().get(student.getWhichAssignmentSubmitted().indexOf(assignment)).getScoredAssignment() != null) {
			addScoredStudentAssignmentPanel();
		}
		
		arrangeHeight = assignmentPanel.getY() + assignmentPanel.getHeight() * 3 + xBorder * 2;
		
		if(arrangeHeight > height)
			height = arrangeHeight;

		this.setBorder(null);
		this.setBackground(backgroundColor);
		this.setSize(boardFrame.getContentWidth(), height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}
	
	public ContentPanel(BulletinBoardFrame boardFrame, ArrayList<Answer> answer, TitleBar titleBar) {
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		answerPanels = new ArrayList<AnswerPanel>();
		
		height = boardFrame.getHeight() - titleBar.getHeight();

		this.setLayout(null);
		this.setSize(boardFrame.getContentWidth() + 20, height);

		for(int i = answer.size() - 1; i >= 0; i--) {
			AnswerPanel make = new AnswerPanel(answer.get(i), boardFrame, this, titleBar);
			make.setLocation(xBorder, xBorder * (answer.size() - i) + make.getHeight() * (answer.size() - i - 1));
			answerPanels.add(make);
			this.add(make);
			if(i == 0)
				arrangeHeight = make.getY() + make.getHeight() + xBorder;
		}
		
		if(arrangeHeight > height)
			height = arrangeHeight;

		this.setBorder(null);
		this.setBackground(backgroundColor);
		this.setSize(boardFrame.getContentWidth(), height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}
	
	public ContentPanel(ArrayList<Assignment> assignments, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		height = boardFrame.getHeight() - titleBar.getHeight();

		this.setLayout(null);
		this.setSize(boardFrame.getContentWidth() + 20, height);

		for(int i = assignments.size() - 1; i >= 0; i--) {
			StudentAssignmentPanel stPanel = new StudentAssignmentPanel(((StudentAccount)ClientConsole.client.getAccount()), i, boardFrame, titleBar);
			stPanel.setLocation(xBorder, (assignments.size() - i) * xBorder + (assignments.size() - i - 1) * stPanel.getHeight());
			
			if(i == 0)
				arrangeHeight = stPanel.getY() + stPanel.getHeight() + xBorder; 
			this.add(stPanel);
		}
		
		if(arrangeHeight > height)
			height = arrangeHeight;

		this.setBorder(null);
		this.setBackground(backgroundColor);
		this.setSize(boardFrame.getContentWidth(), height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}
	
	/* 교수 계정으로 로그인 했을 때 과제에 있는 학생이 제출한 모든 과제를 같이 보여줌 */
	public ContentPanel(Subject subject, Assignment assignment, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		height = boardFrame.getHeight() - titleBar.getHeight();

		this.setLayout(null);
		this.setSize(boardFrame.getContentWidth() + 20, height);

		assignmentPanel = new AssignmentPanel(assignment, boardFrame, this, titleBar);
		assignmentPanel.setLocation(xBorder, xBorder);
		this.add(assignmentPanel);
		
		for(int i = assignment.getStudentsAssignment().size() - 1; i >= 0; i--) {
			StudentAssignmentPanel stPanel = new StudentAssignmentPanel(subject, assignment.getStudentsAssignment().get(i), boardFrame, titleBar);
			stPanel.setLocation(xBorder, (assignment.getStudentsAssignment().size() - i + 2) * xBorder + (assignment.getStudentsAssignment().size() - i) * stPanel.getHeight() + 5);
			
			if(i == 0)
				arrangeHeight = stPanel.getY() + stPanel.getHeight() + xBorder; 
			this.add(stPanel);
		}
		
		if(arrangeHeight > height)
			height = arrangeHeight;

		this.setBorder(null);
		this.setBackground(backgroundColor);
		this.setSize(boardFrame.getContentWidth(), height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}
	
	public ContentPanel(JPanel panel, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		answerPanels = new ArrayList<AnswerPanel>();
		
		height = boardFrame.getHeight() - titleBar.getHeight();

		this.setLayout(null);
		this.setSize(boardFrame.getContentWidth() + 20, height);

		panel.setLocation(xBorder, xBorder);
		this.add(panel);
		
		if(arrangeHeight > height)
			height = arrangeHeight;

		this.setBorder(null);
		this.setBackground(backgroundColor);
		this.setSize(boardFrame.getContentWidth(), height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}

	public void addSubmittedStudentAssignmentPanel(int index) {
		StudentAssignmentPanel stPanel = new StudentAssignmentPanel(((StudentAccount)ClientConsole.client.getAccount()), index,boardFrame, titleBar);
		stPanel.setLocation(assignmentPanel.getX(), assignmentPanel.getY() + assignmentPanel.getHeight() + xBorder);
		this.add(stPanel);
	}
	
	public void addUnsubmittedStudentAssignmentPanel() {
		StudentAssignmentPanel stPanel = new StudentAssignmentPanel(thisAssignment, boardFrame, titleBar);
		stPanel.setLocation(assignmentPanel.getX(), assignmentPanel.getY() + assignmentPanel.getHeight() + xBorder);
		this.add(stPanel);
	}
	
	public void addScoredStudentAssignmentPanel() {
		Assignment temp = student.getAssignments().get(student.getWhichAssignmentSubmitted().indexOf(thisAssignment)).getScoredAssignment();
		StudentAssignmentPanel stPanel = new StudentAssignmentPanel(temp, temp.getScore(), temp.getComment(), boardFrame, titleBar);
		stPanel.setLocation(assignmentPanel.getX(), assignmentPanel.getY() + assignmentPanel.getHeight() * 2 + xBorder);
		this.add(stPanel);
	}
}
