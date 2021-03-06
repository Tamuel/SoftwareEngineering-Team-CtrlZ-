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
import javax.swing.JTextArea;

import common.ProtocolType;
import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Assignment;
import Assignment.Subject;
import Controller.ProfessorAccountController;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleLabel;
import GuiComponent.SimpleTextArea;
import GuiComponent.SimpleTextField;
import ServerClientConsole.ClientConsole;

public class NewAssignmentPanel extends JPanel{
	
	private Subject subject;
	private Assignment assignment;
	
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	
	private SimpleTextField topic;
	private SimpleTextArea content;
	private SimpleTextField deadlineYear;
	private SimpleTextField deadlineMonth;
	private SimpleTextField deadlineDay;
	private SimpleTextField deadlineHour;
	private SimpleButton makeAssignment;
	
	private JScrollPane scrollBar;

	private int topicHeight = 40;
	private int xBorder = 10;
	private int yBorder = 10;

	private Color backgroundColor = new Color(255, 255, 255);

	public NewAssignmentPanel(Subject subject, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.subject = subject;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.setLayout(null);

		this.setSize(boardFrame.getContentWidth() - xBorder * 2, (boardFrame.getHeight() - yBorder * 3 - titleBar.getHeight()) / 2);
		
		topic = new SimpleTextField("과제");
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
		topic.setEditable(true);
	
		deadlineYear = new SimpleTextField("년");
		deadlineYear.setSize((this.getWidth() * 2 / 5) / 4 + 6, topicHeight - 1);
		deadlineYear.setLocation(topic.getWidth() + 1, 1);
		deadlineYear.setBackground(new Color(240, 240, 240));
//		deadlineYear.setSmallFont();
		deadlineYear.setEditable(true);
		
		deadlineMonth = new SimpleTextField("월");
		deadlineMonth.setSize((this.getWidth() * 2 / 5) / 4 - 2, topicHeight - 1);
		deadlineMonth.setLocation(deadlineYear.getWidth() + deadlineYear.getX() + 1, 1);
		deadlineMonth.setBackground(new Color(240, 240, 240));
//		deadlineMonth.setSmallFont();
		deadlineMonth.setEditable(true);
		
		deadlineDay = new SimpleTextField("일");
		deadlineDay.setSize((this.getWidth() * 2 / 5) / 4 - 2, topicHeight - 1);
		deadlineDay.setLocation(deadlineMonth.getWidth() + deadlineMonth.getX() + 1, 1);
		deadlineDay.setBackground(new Color(240, 240, 240));
//		deadlineDay.setSmallFont();
		deadlineDay.setEditable(true);
		
		deadlineHour = new SimpleTextField("시");
		deadlineHour.setSize((this.getWidth() * 2 / 5) / 4 - 2, topicHeight - 1);
		deadlineHour.setLocation(deadlineDay.getWidth() + deadlineDay.getX() + 1, 1);
		deadlineHour.setBackground(new Color(240, 240, 240));
//		deadlineHour.setSmallFont();
		deadlineHour.setEditable(true);

		makeAssignment = new SimpleButton("과제 생성");
		makeAssignment.setSize(90, 30);
		makeAssignment.setLocation(this.getWidth() - makeAssignment.getWidth() - 5, this.getHeight() - makeAssignment.getHeight() - 5);
		makeAssignment.addActionListener(new SubmitButtonListener());
		
		content = new SimpleTextArea("");
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - makeAssignment.getHeight() - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));
		content.setEditable(true);

		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - makeAssignment.getHeight() - 10);
		scrollBar.setWheelScrollingEnabled(true);
		
		this.add(topic);
		this.add(scrollBar);
		this.add(deadlineYear);
		this.add(deadlineMonth);
		this.add(deadlineDay);
		this.add(deadlineHour);
		
		this.add(makeAssignment);
		
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
		this.setBackground(backgroundColor);
	}
	
	/* 과제 수정 시 */
	public NewAssignmentPanel(Assignment assignment, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		this.assignment = assignment;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.subject = assignment.getSubject();
		this.setLayout(null);

		this.setSize(boardFrame.getContentWidth() - xBorder * 2, (boardFrame.getHeight() - yBorder * 3 - titleBar.getHeight()) / 2);
		
		topic = new SimpleTextField(assignment.getTopic());
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
		topic.setEditable(true);
		
		Date deadline = assignment.getDeadline();
		Calendar cal = Calendar.getInstance( );
		cal.setTime(deadline);

		deadlineYear = new SimpleTextField(cal.get(Calendar.YEAR) + "");
		deadlineYear.setSize((this.getWidth() * 2 / 5) / 4 + 6, topicHeight - 1);
		deadlineYear.setLocation(topic.getWidth() + 1, 1);
		deadlineYear.setBackground(new Color(240, 240, 240));
		deadlineYear.setEditable(true);
		
		deadlineMonth = new SimpleTextField((cal.get(Calendar.MONTH) + 1) + "");
		deadlineMonth.setSize((this.getWidth() * 2 / 5) / 4 - 2, topicHeight - 1);
		deadlineMonth.setLocation(deadlineYear.getWidth() + deadlineYear.getX() + 1, 1);
		deadlineMonth.setBackground(new Color(240, 240, 240));
		deadlineMonth.setEditable(true);
		
		deadlineDay = new SimpleTextField(cal.get(Calendar.DATE) + "");
		deadlineDay.setSize((this.getWidth() * 2 / 5) / 4 - 2, topicHeight - 1);
		deadlineDay.setLocation(deadlineMonth.getWidth() + deadlineMonth.getX() + 1, 1);
		deadlineDay.setBackground(new Color(240, 240, 240));
		deadlineDay.setEditable(true);
		
		deadlineHour = new SimpleTextField(cal.get(Calendar.HOUR_OF_DAY) + "");
		deadlineHour.setSize((this.getWidth() * 2 / 5) / 4 - 2, topicHeight - 1);
		deadlineHour.setLocation(deadlineDay.getWidth() + deadlineDay.getX() + 1, 1);
		deadlineHour.setBackground(new Color(240, 240, 240));
		deadlineHour.setEditable(true);

		makeAssignment = new SimpleButton("수정 완료");
		makeAssignment.setSize(90, 30);
		makeAssignment.setLocation(this.getWidth() - makeAssignment.getWidth() - 5, this.getHeight() - makeAssignment.getHeight() - 5);
		makeAssignment.addActionListener(new editButtonListener());
		
		content = new SimpleTextArea(assignment.getContent());
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - makeAssignment.getHeight() - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));
		content.setEditable(true);

		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - makeAssignment.getHeight() - 10);
		scrollBar.setWheelScrollingEnabled(true);
		
		this.add(topic);
		this.add(scrollBar);
		this.add(deadlineYear);
		this.add(deadlineMonth);
		this.add(deadlineDay);
		this.add(deadlineHour);
		
		this.add(makeAssignment);
		
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
		this.setBackground(backgroundColor);
	}
	
	private class editButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Date temp;
			String stringDate = deadlineYear.getText().toString() + "/" + deadlineMonth.getText().toString() + "/"
								+ deadlineDay.getText().toString() + "/" + deadlineHour.getText().toString();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
			try {
				temp = dateFormat.parse(stringDate);
				Assignment tempAssignment = new Assignment(topic.getText(), content.getText(), temp);
				if(ClientConsole.client.getAccount().isProfessor()) {
					try
					{
						ClientConsole.client.sendToServer(ProtocolType.EDIT_ASSIGNMENT, assignment.getContNum() + ":" +
								topic.getText() + ":" + content.getText() + ":" +
								deadlineYear.getText().toString() + ":" + deadlineMonth.getText().toString() + ":" +
								deadlineDay.getText().toString() + ":" + deadlineHour.getText().toString());
					}
					catch(Exception ex)
					{
						System.err.println(ex.toString());
					}
				}
				
				ContentPanel contentPanel = new ContentPanel(subject, assignment, boardFrame, titleBar);
				boardFrame.addContentPanel(contentPanel);
	
				AssignmentList assignmentList = new AssignmentList(thisHeight(), subject, boardFrame, titleBar);
				boardFrame.addAssignmentPanel(assignmentList);
				titleBar.setAssignmentPath(assignment);
				boardFrame.repaint();
			} catch (Exception ex) {System.err.println("시간이 제대로 입력되지 않았습니다!");}
		}
	}

	private class SubmitButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(ClientConsole.client.getAccount().isProfessor()) {
				try
				{
					ClientConsole.client.setMsgReceive(false);
					ClientConsole.client.sendToServer(ProtocolType.MAKE_ASSIGNMENT, ClientConsole.client.getAccount().getId() + ":" +
							topic.getText() + ":" + content.getText() + ":" +
							deadlineYear.getText().toString() + ":" + deadlineMonth.getText().toString() + ":" +
							deadlineDay.getText().toString() + ":" + deadlineHour.getText().toString());
					
					while(!ClientConsole.client.isMsgReceive()) {
						Thread.sleep(100);
					}
					ClientConsole.client.setMsgReceive(false);
				}
				catch(Exception ex)
				{
					System.err.println(ex.toString());
				}
			}
			
			AssignmentList assignmentList = new AssignmentList(thisHeight(), subject, boardFrame, titleBar);
			titleBar.setAssignmentPath(subject);

			boardFrame.addAssignmentPanel(assignmentList);
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
