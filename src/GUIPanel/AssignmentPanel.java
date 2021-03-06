package GUIPanel;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import common.ProtocolType;

import Account.Account;
import Account.StudentAccount;
import Assignment.Assignment;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleLabel;
import GuiComponent.SimpleTextArea;
import ServerClientConsole.ClientConsole;

public class AssignmentPanel extends JPanel{
	
	private Assignment assignment;
	
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	private StudentAssignmentPanel myAssignmentPanel;
	private ContentPanel contentPanel;
	
	private SimpleTextArea topic;
	private SimpleTextArea content;
	private SimpleTextArea deadline;
	private SimpleButton makeAssignment;
	
	private JScrollPane scrollBar;

	private boolean editable;
	private int topicHeight = 40;
	private int xBorder = 10;
	private int yBorder = 10;

	private Color backgroundColor = new Color(255, 255, 255);

	public AssignmentPanel(Assignment assignment, BulletinBoardFrame boardFrame, ContentPanel contentPanel, TitleBar titleBar) {
		this.assignment = assignment;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.contentPanel = contentPanel;
		this.editable = false;
		this.setLayout(null);

		this.setSize(boardFrame.getContentWidth() - xBorder * 2, (boardFrame.getHeight() - yBorder * 3 - titleBar.getHeight()) / 2);
		
		topic = new SimpleTextArea(assignment.getTopic());
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		deadline = new SimpleTextArea("제출 " + dateFormat.format(assignment.getDeadline()) + " 까지");
		deadline.setSize(this.getWidth() * 2 / 5, topicHeight - 1);
		deadline.setLocation(topic.getWidth() + 1, 1);
		deadline.setBackground(new Color(240, 240, 240));
		deadline.setSmallFont();
		
		if(ClientConsole.client.getAccount().isStudent()) {
			makeAssignment = new SimpleButton("과제 작성");
			makeAssignment.addActionListener(new SubmitButtonListener());
		} else if(ClientConsole.client.getAccount().isProfessor()) {
			makeAssignment = new SimpleButton("과제 수정");
			makeAssignment.addActionListener(new EditButtonListener());
		}
		makeAssignment.setSize(90, 30);
		makeAssignment.setLocation(this.getWidth() - makeAssignment.getWidth() - 5, this.getHeight() - makeAssignment.getHeight() - 5);
		
		content = new SimpleTextArea(assignment.getContent());
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - makeAssignment.getHeight() - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));

		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - makeAssignment.getHeight() - 10);
		scrollBar.setWheelScrollingEnabled(true);
		scrollBar.getVerticalScrollBar().setUnitIncrement(10);
		
		this.add(topic);
		this.add(scrollBar);
		this.add(deadline);
		
		this.add(makeAssignment);
		
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
		this.setBackground(backgroundColor);
	}

	private class SubmitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			// 제출 안한 과제일 경우
			if(ClientConsole.client.getAccount().isStudent() && !assignment.isSubmitted(((StudentAccount)ClientConsole.client.getAccount()))) {
				contentPanel.addUnsubmittedStudentAssignmentPanel();
			}
			boardFrame.repaint();
		}
	}
	
	private class EditButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			NewAssignmentPanel naPanel = new NewAssignmentPanel(assignment, boardFrame, titleBar);
			ContentPanel contentPanel = new ContentPanel(naPanel, boardFrame, titleBar);
			boardFrame.addContentPanel(contentPanel);
			titleBar.setAssignmentPath(assignment);
			boardFrame.repaint();
		}
	}

	public int thisHeight() {
		return this.getHeight();
	}

	public int thisX() {
		return this.getX();
	}
	
	public int thisBottom() {
		return this.getY() + this.getHeight() + yBorder;
	}
}
