package GUIPanel;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import common.ProtocolType;

import Account.*;
import Assignment.Assignment;
import Assignment.Subject;
import Controller.ProfessorAccountController;
import Controller.StudentAccountController;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleLabel;
import GuiComponent.SimpleTextArea;
import GuiComponent.SimpleTextField;
import ServerClientConsole.ClientConsole;

public class StudentAssignmentPanel extends JPanel{
	
	private Assignment assignment;
	
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	
	private SimpleTextField topic;
	private SimpleTextArea content;
	private SimpleTextArea deadline;
	private SimpleButton submit;
	
	private SimpleTextArea comment;
	private SimpleTextField score;

	private JScrollPane scrollBar;
	private JScrollPane commentScrollBar;

	private int topicHeight = 40;
	private int xBorder = 10;
	private int yBorder = 10;

	private Color backgroundColor = new Color(255, 255, 255);

	public StudentAssignmentPanel(Assignment assignment, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.assignment = assignment;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.setLayout(null);

		this.setSize(boardFrame.getContentWidth() - xBorder * 2, (boardFrame.getHeight() - 30 - yBorder * 3 - titleBar.getHeight()) / 2);
		topic = new SimpleTextField(assignment.getTopic());
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
		topic.setEditable(true);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date today = new Date();
		deadline = new SimpleTextArea(dateFormat.format(today.getTime()));
		deadline.setSize(this.getWidth() * 2 / 5, topicHeight - 1);
		deadline.setLocation(topic.getWidth() + 1, 1);
		deadline.setBackground(new Color(240, 240, 240));
		deadline.setSmallFont();

		submit = new SimpleButton("제출");
		submit.setSize(70, 30);
		submit.setLocation(this.getWidth() - submit.getWidth() - 5, this.getHeight() - submit.getHeight() - 5);
		submit.addActionListener(new SubmitButtonListener());
		
		content = new SimpleTextArea();
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - submit.getHeight() - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));
		content.setEditable(true);

		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - submit.getHeight() - 10);
		scrollBar.setWheelScrollingEnabled(true);
		scrollBar.setViewportView(content);
		
		this.add(topic);
		this.add(scrollBar);
		this.add(deadline);
		
		this.add(submit);

		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
	}
	
	public StudentAssignmentPanel(StudentAccount student, int indexOfAssignment, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.assignment = student.getAssignments().get(indexOfAssignment);
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.setLayout(null);

		this.setSize(boardFrame.getContentWidth() - xBorder * 2, (boardFrame.getHeight() - 30 - yBorder * 3 - titleBar.getHeight()) / 2);
		topic = new SimpleTextField(assignment.getTopic());
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
		topic.setEditable(true);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date today = assignment.getDeadline();
		deadline = new SimpleTextArea("제출 일자 " + dateFormat.format(today.getTime()));
		deadline.setSize(this.getWidth() * 2 / 5, topicHeight - 1);
		deadline.setLocation(topic.getWidth() + 1, 1);
		deadline.setBackground(new Color(240, 240, 240));
		deadline.setSmallFont();

		submit = new SimpleButton("수정");
		submit.setSize(70, 30);
		submit.setLocation(this.getWidth() - submit.getWidth() - 5, this.getHeight() - submit.getHeight() - 5);
		submit.addActionListener(new EditButtonListener());
		
		content = new SimpleTextArea(assignment.getContent());
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - submit.getHeight() - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));
		content.setEditable(true);

		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - submit.getHeight() - 10);
		scrollBar.setWheelScrollingEnabled(true);
		scrollBar.setViewportView(content);
		
		this.add(topic);
		this.add(scrollBar);
		this.add(deadline);
		
		this.add(submit);

		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
	}
	
	/* 교수가 학생들이 제출한 모든 과제를 볼 때의 생성자  */
	public StudentAssignmentPanel(Subject subject, Assignment assignment, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.assignment = assignment;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.setLayout(null);

		this.setSize(boardFrame.getContentWidth() - xBorder * 2, (boardFrame.getHeight() - 30 - yBorder * 3 - titleBar.getHeight()) / 2);
		SimpleTextArea topic = new SimpleTextArea(assignment.getTopic());
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
		topic.setEditable(false);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date today = assignment.getDeadline();
		deadline = new SimpleTextArea("작성자 - " + assignment.getStudent().getName(), "제출 일자 " + dateFormat.format(today.getTime()));
		deadline.setSize(this.getWidth() * 2 / 5, topicHeight - 1);
		deadline.setLocation(topic.getWidth() + 1, 1);
		deadline.setBackground(new Color(240, 240, 240));
		deadline.setSmallFont();

		submit = new SimpleButton("평가");
		submit.setSize(70, 30);
		submit.setLocation(this.getWidth() - submit.getWidth() - 5, this.getHeight() - submit.getHeight() - 5);
		submit.addActionListener(new ScoreButtonListener());
		
		content = new SimpleTextArea(assignment.getContent());
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - submit.getHeight() - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));
		content.setEditable(false);
		
		comment = new SimpleTextArea("");
		comment.setSize(this.getWidth() * 4 / 5 - 45, topicHeight - 1);
		comment.setLocation(1, 1);
		comment.setMargin(new Insets(10, 10, 10, 10));
		comment.setEditable(true);
		
		score = new SimpleTextField("");
		score.setSize(submit.getWidth() - 10, submit.getHeight());
		score.setLocation(submit.getX() - score.getWidth() - xBorder, submit.getY());
		score.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
		score.setMargin(new Insets(10, 10, 10, 10));
		score.setEditable(true);
		
		if(assignment.isScored()) {
			comment.setText(assignment.getScoredAssignment().getComment());
			score.setText(assignment.getScoredAssignment().getScore() + "");
		} else if(!assignment.isScored()) {
			comment.setText("코멘트");
			score.setText("점수");
		}
		
		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - submit.getHeight() - 10);
		scrollBar.setWheelScrollingEnabled(true);
		
		commentScrollBar = new JScrollPane(comment);
		commentScrollBar.setLocation(2, scrollBar.getY() + scrollBar.getHeight() + 1);
		commentScrollBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
		commentScrollBar.setSize(this.getWidth() * 4 / 5 - 45, topicHeight - 3);
		commentScrollBar.setWheelScrollingEnabled(true);
		
		this.add(topic);
		this.add(score);
		this.add(scrollBar);
		this.add(deadline);
		this.add(commentScrollBar);
		
		this.add(submit);

		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
	}
	
	/* 평가된 과제 표시 */
	public StudentAssignmentPanel(Assignment assignment, Double sc, String cm, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.assignment = assignment;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.setLayout(null);

		this.setSize(boardFrame.getContentWidth() - xBorder * 2, (boardFrame.getHeight() - 30 - yBorder * 3 - titleBar.getHeight()) / 2);
		SimpleTextArea topic = new SimpleTextArea(assignment.getTopic());
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
		topic.setEditable(false);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		Date today = assignment.getDeadline();
		deadline = new SimpleTextArea("작성자 - " + assignment.getStudent().getName(), "제출 일자 " + dateFormat.format(today.getTime()));
		deadline.setSize(this.getWidth() * 2 / 5, topicHeight - 1);
		deadline.setLocation(topic.getWidth() + 1, 1);
		deadline.setBackground(new Color(240, 240, 240));
		deadline.setSmallFont();

		submit = new SimpleButton("평가");
		submit.setSize(70, 30);
		submit.setLocation(this.getWidth() - submit.getWidth() - 5, this.getHeight() - submit.getHeight() - 5);
		
		content = new SimpleTextArea(assignment.getContent());
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - submit.getHeight() - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));
		content.setEditable(false);
		
		comment = new SimpleTextArea(cm);
		comment.setSize(this.getWidth() * 4 / 5 - 45, topicHeight - 1);
		comment.setLocation(1, 1);
		comment.setMargin(new Insets(10, 10, 10, 10));
		comment.setEditable(false);
		
		SimpleTextArea score = new SimpleTextArea(sc + "점");
		score.setSize(submit.getWidth() - 10, submit.getHeight());
		score.setLocation(submit.getX() - score.getWidth() - xBorder, submit.getY() - 5);
		score.setMargin(new Insets(10, 10, 10, 10));
		score.setEditable(false);
		
		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - submit.getHeight() - 10);
		scrollBar.setWheelScrollingEnabled(true);
		
		commentScrollBar = new JScrollPane(comment);
		commentScrollBar.setLocation(2, scrollBar.getY() + scrollBar.getHeight() + 1);
		commentScrollBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
		commentScrollBar.setSize(this.getWidth() * 4 / 5 - 45, topicHeight - 3);
		commentScrollBar.setWheelScrollingEnabled(true);
		
		this.add(topic);
		this.add(score);
		this.add(scrollBar);
		this.add(deadline);
		this.add(commentScrollBar);
		
//		this.add(submit);

		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
	}
	
	private class ScoreButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(!assignment.isScored()) {
				ProfessorAccountController pCon = new ProfessorAccountController(((ProfessorAccount)ClientConsole.client.getAccount()));
				pCon.assignmentAppraisal(comment.getText().toString(), score.getText().toString(), assignment);
			}
			System.out.println(assignment.getScoredAssignment().getScore() + " " + assignment.getScoredAssignment().getComment());
		}
	}
		
	private class EditButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(ClientConsole.client.getAccount().isStudent()) {
				try
				{
					ClientConsole.client.sendToServer(ProtocolType.STUDENT_EDIT_ASSIGNMENT, assignment.getSubject().getName() + ":" + assignment.getContNum() + ":" + 
								topic.getText() + ":" + content.getText());
				}
				catch(Exception ex)
				{
					System.err.println(ex.toString());
				}
			}
		}
	}
	
	private class SubmitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(getStudent() != null) {
				if(ClientConsole.client.getAccount().isStudent()) {
					try
					{
						ClientConsole.client.sendToServer(ProtocolType.SUBMIT_ASSIGNMENT, getStudent().getId() + ":" + assignment.getContNum() + ":" + 
									topic.getText() + ":" + content.getText());
					}
					catch(Exception ex)
					{
						System.err.println(ex.toString());
					}
				}
//				StudentAccountController sCon = new StudentAccountController(getStudent());
//				sCon.submitAssignment(assignment, myAssignment);
//				getStudent().printAllAssignment();

				ContentPanel contentPanel = new ContentPanel(assignment, boardFrame, titleBar);
				boardFrame.addContentPanel(contentPanel);
				titleBar.setAssignmentPath(assignment);
				boardFrame.repaint();
			}
		}
	}
	
	public StudentAccount getStudent() {
		if(ClientConsole.client.getAccount().isStudent())
			return (StudentAccount)ClientConsole.client.getAccount();
		else
			return null;
	}
}
