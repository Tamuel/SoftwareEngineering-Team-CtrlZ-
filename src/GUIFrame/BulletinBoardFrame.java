package GUIFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument.Content;

import Account.*;
import Assignment.*;
import GUIPanel.AnswerPanel;
import GUIPanel.AssignmentList;
import GUIPanel.ContentPanel;
import GUIPanel.NewAssignmentPanel;
import GUIPanel.NewQuestionPanel;
import GUIPanel.QuestionList;
import GUIPanel.StudentAssignmentPanel;
import GUIPanel.SubjectList;
import GUIPanel.TitleBar;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleJFrame;
import QnA.Question;
import ServerClientConsole.ClientConsole;

public class BulletinBoardFrame extends SimpleJFrame{
	
	private Subject subject;
	
	private TitleBar titleBar;
	
	private JScrollPane subjectScrollPane;
	private JScrollPane assignmentScrollPane;
	private JScrollPane contentPane;
	private SimpleButton menuButton;
	
	private boolean seeWholeAnswer = false;

	private int scrollBarWidth = 18;
	private int titleBarHeight = 30;
	private int assignmentButtonHeight = 80;
	private int assignmentButtonWidth = 250;
	private int xBorder = 10;
	
	public BulletinBoardFrame(String frameName, int width, int height) {
		super(frameName, width, height);
		
		titleBar = new TitleBar(this, titleBarHeight, ClientConsole.client.getAccount());
		titleBar.setLocation(0, 0);
		
		menuButton = new SimpleButton("", assignmentButtonWidth + 2 * xBorder + scrollBarWidth, assignmentButtonHeight / 2);
		menuButton.setVisible(false);
		this.add(menuButton);
		
		SubjectList subjectList = new SubjectList(this.getHeight() - titleBarHeight, this, titleBar);
		
		subjectScrollPane = new JScrollPane();
		assignmentScrollPane = new JScrollPane();
		contentPane = new JScrollPane();
		
		subjectScrollPane.setLocation(0, titleBarHeight);
		subjectScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));
		subjectScrollPane.setSize(subjectList.getWidth() + scrollBarWidth, this.HEIGHT - titleBar.getHeight());
		subjectScrollPane.setWheelScrollingEnabled(true);
		subjectScrollPane.setViewportView(subjectList);
		
		assignmentScrollPane.setLocation(subjectScrollPane.getWidth(), titleBarHeight + menuButton.getHeight());
		assignmentScrollPane.setBorder(null);
		assignmentScrollPane.setSize(xBorder * 2 + assignmentButtonWidth + scrollBarWidth, this.HEIGHT - titleBar.getHeight() - menuButton.getHeight());
		assignmentScrollPane.setWheelScrollingEnabled(true);
		
		contentPane.setLocation(assignmentScrollPane.getX() + assignmentScrollPane.getWidth(), titleBarHeight);
		contentPane.setBorder(null);
		contentPane.setSize(this.getWidth() - (assignmentScrollPane.getX() + assignmentScrollPane.getWidth()), this.HEIGHT - titleBar.getHeight());
		contentPane.setWheelScrollingEnabled(true);
//		contentPane.setLayout(null);
		
		menuButton.setLocation(subjectScrollPane.getWidth(), titleBar.getHeight());
		menuButton.addActionListener(new menuListener());
		
		this.add(titleBar);
		this.add(subjectScrollPane);
		this.add(assignmentScrollPane);
		this.add(contentPane);
		
		this.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1)); // frame에 테두리 적용
		this.getContentPane().setBackground(Color.WHITE);//new Color(235, 235, 235));
		this.setVisible(true);
	}
	
	private class menuListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(menuButton.getText().equals("내가 올린 과제")) {
				ContentPanel stPanel = new ContentPanel(((StudentAccount)ClientConsole.client.getAccount()).getAssignments(), getFrame(), titleBar);
				addContentPanel(stPanel);
				titleBar.setAssignmentPath(subject);
			} else if(menuButton.getText().equals("과제 생성")) {
				NewAssignmentPanel naPanel = new NewAssignmentPanel(subject, thisFrame(), titleBar);
				ContentPanel contentPanel = new ContentPanel(naPanel, thisFrame(), titleBar);
				addContentPanel(contentPanel);
				titleBar.setAssignmentPath(subject);
			} else if(menuButton.getText().equals("질문 올리기")) {
				NewQuestionPanel nqPanel = new NewQuestionPanel(ClientConsole.client.getAccount(), subject, thisFrame(), titleBar);
				ContentPanel contentPanel = new ContentPanel(nqPanel, thisFrame(), titleBar);
				addContentPanel(contentPanel);
			} else if(menuButton.getText().equals("나의 답변")) {
				ContentPanel maPanel = new ContentPanel(getFrame(), ((ProfessorAccount)ClientConsole.client.getAccount()).getAnswers(), titleBar);
				addContentPanel(maPanel);
				seeWholeAnswer = true;
			}
		}
	}
	
	public BulletinBoardFrame thisFrame() {
		return this;
	}
	
	public void addAssignmentPanel(AssignmentList assignmentPanel) {
		if(ClientConsole.client.getAccount().isStudent())
			menuButton.setText("내가 올린 과제");
		else if(ClientConsole.client.getAccount().isProfessor())
			menuButton.setText("과제 생성");
		
		menuButton.setVisible(true);
		assignmentScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));
		assignmentScrollPane.setViewportView(assignmentPanel);
		
		this.subject = assignmentPanel.getSubject();
		seeWholeAnswer = false;
		
		Runnable doScroll = new Runnable() {
			public void run() {
				assignmentScrollPane.getVerticalScrollBar().setValue(0);
			}
		};
		SwingUtilities.invokeLater(doScroll);
	}
	
	public void addQuestionPanel(QuestionList questionPanel) {
		if(ClientConsole.client.getAccount().isStudent())
			menuButton.setText("질문 올리기");
		else if(ClientConsole.client.getAccount().isProfessor())
			menuButton.setText("나의 답변");
		
		menuButton.setVisible(true);
		
		this.subject = questionPanel.getSubject();
		seeWholeAnswer = false;
		
		assignmentScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(200, 200, 200)));
		assignmentScrollPane.setViewportView(questionPanel);
		
		Runnable doScroll = new Runnable() {
			public void run() {
				assignmentScrollPane.getVerticalScrollBar().setValue(0);
			}
		};
		SwingUtilities.invokeLater(doScroll);
	}
	
	public void addContentPanel(JPanel content) {
		contentPane.setViewportView(content);
		seeWholeAnswer = false;
		
		Runnable doScroll = new Runnable() {
			public void run() {
				contentPane.getVerticalScrollBar().setValue(0);
			}
		};
		SwingUtilities.invokeLater(doScroll);
	}
	
	public BulletinBoardFrame getFrame() {
		return this;
	}
	
	public boolean getSeeWholeAnswer() {
		return seeWholeAnswer;
	}

	public void setSeeWholeAnswer(boolean s) {
		seeWholeAnswer = s;
	}
	
	public int getContentWidth() {
		return contentPane.getWidth() - scrollBarWidth;
	}
}
