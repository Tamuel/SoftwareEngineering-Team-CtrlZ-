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
import ServerClientConsole.ClientConsole;

public class AssignmentList extends JPanel{
	
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
		
		for(int i = 0; i < subject.getAssignments().size(); i++) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			
			SimpleButton temp = new SimpleButton(subject.getAssignments().get(i).getTopic(),
					dateFormat.format(subject.getAssignments().get(i).getDeadline()) + " ����",
					assignmentButtonWidth, assignmentButtonHeight);
			temp.setLocation(xBorder, yBorder + (assignmentButtonHeight + yBorder) * i);
			temp.addActionListener(new assignmentButtonListener());
			temp.setBackground(buttonColor);
			temp.setFontColor(fontColor, fontColor);
			temp.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
			
			if(i == subject.getAssignments().size() - 1)
				this.height = temp.getY() + assignmentButtonHeight + yBorder;
			
			assignmentButtons.add(temp);
			this.addButton(assignmentButtons.get(i));
		}
		
		height -= assignmentButtonHeight / 2;
		if(this.height > height)
			height = this.height;
		
		this.setBackground(backgroundColor);
		this.setBorder(null);
		this.setSize(xBorder * 2 + assignmentButtonWidth, height);
		this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
	}

	private class assignmentButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			Assignment assignment = subject.getAssignments().get(assignmentButtons.indexOf((SimpleButton)ev.getSource()));
			
			if(ClientConsole.client.getAccount().isStudent()) {
				ContentPanel contentPanel = new ContentPanel(assignment, boardFrame, titleBar);
				boardFrame.addContentPanel(contentPanel);
				titleBar.setAssignmentPath(assignment);
				boardFrame.repaint();
			} else if(ClientConsole.client.getAccount().isProfessor()) {
				ContentPanel contentPanel = new ContentPanel(subject, assignment, boardFrame, titleBar);
				boardFrame.addContentPanel(contentPanel);
				titleBar.setAssignmentPath(assignment);
				boardFrame.repaint();
			}
		}
	}
	
	public Subject getSubject() {
		return subject;
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
