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
import Controller.StudentAccountController;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleTextArea;
import ServerClientConsole.ClientConsole;

public class AssignmentList extends JPanel {

	private Subject subject;
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	private AssignmentPanel assignmentPanel;

	private ArrayList<SimpleButton> assignmentButtons;

	private int assignmentButtonHeight = 80;
	private int assignmentButtonWidth = 250;
	private int xBorder = 10;
	private int yBorder = 10;
	private int height;

	private Color backgroundColor = new Color(250, 250, 250);
	private Color buttonColor = new Color(255, 255, 255);
	private Color fontColor = new Color(100, 100, 100);

	public AssignmentList(int height, Subject subject, BulletinBoardFrame boardFrame, TitleBar titleBar) {
		assignmentButtons = new ArrayList<SimpleButton>();

		this.titleBar = titleBar;
		this.subject = subject;
		this.boardFrame = boardFrame;

		this.setLayout(null);
		this.setBorder(null);
		this.setBackground(backgroundColor);

		paint();
	}

	private class assignmentButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Account account = ClientConsole.client.getAccount();
			if(account.isProfessor()) {
				subject = ((ProfessorAccount)account).getSubject();
			} else {

				StudentAccountController sCon = new StudentAccountController((StudentAccount)ClientConsole.client.getAccount());
				subject = sCon.getSubject(subject.getName());
			}
			
			Assignment assignment = subject.getAssignments().get(
					assignmentButtons.indexOf((SimpleButton) ev.getSource()));

			if (ClientConsole.client.getAccount().isStudent()) {
				ContentPanel contentPanel = new ContentPanel(assignment,
						boardFrame, titleBar);
				boardFrame.addContentPanel(contentPanel);
				titleBar.setAssignmentPath(assignment);
				boardFrame.repaint();
			} else if (ClientConsole.client.getAccount().isProfessor()) {
				ContentPanel contentPanel = new ContentPanel(subject,
						assignment, boardFrame, titleBar);
				boardFrame.addContentPanel(contentPanel);
				titleBar.setAssignmentPath(assignment);
				boardFrame.repaint();
			}
		}
	}
	

	public void paint() {
		subject = getSubject();
		int numOfAssignments = subject.getAssignments().size();
		for (int i = 0; i < numOfAssignments; i++) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy.MM.dd HH:mm:ss");

			SimpleButton temp = new SimpleButton(subject.getAssignments()
					.get(i).getTopic(), dateFormat.format(subject
					.getAssignments().get(i).getDeadline())
					+ " ±îÁö", assignmentButtonWidth, assignmentButtonHeight);
			temp.setLocation(xBorder, yBorder
					+ (assignmentButtonHeight + yBorder) * (numOfAssignments - i - 1));
			temp.addActionListener(new assignmentButtonListener());
			temp.setBackground(buttonColor);
			temp.setFontColor(fontColor, fontColor);
			temp.setBorder(BorderFactory.createLineBorder(new Color(210, 210,
					210), 1));

			if (i == 0)
				this.height = temp.getY() + assignmentButtonHeight + yBorder;

			assignmentButtons.add(temp);
			this.addButton(temp);
		}

		//height -= assignmentButtonHeight / 2;
		if (this.height > height)
			height = this.height;

		this.setSize(xBorder * 2 + assignmentButtonWidth, height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
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

	public void addButton(SimpleButton button) {
		this.add(button);
	}

	public int thisHeight() {
		return this.getHeight();
	}

	public int thisRight() {
		return this.getX() + this.getWidth() + 250;
	}

	public int getButtonHeight() {
		return assignmentButtonHeight;
	}

	public int getButtonWidth() {
		return assignmentButtonWidth;
	}
}
