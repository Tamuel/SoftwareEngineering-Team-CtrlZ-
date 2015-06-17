package GUIPanel;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import common.ProtocolType;
import Account.Account;
import Account.StudentAccount;
import Assignment.Assignment;
import Assignment.Subject;
import Controller.StudentAccountController;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleLabel;
import GuiComponent.SimpleTextArea;
import GuiComponent.SimpleTextField;
import QnA.Question;
import ServerClientConsole.ClientConsole;

public class NewQuestionPanel extends JPanel{
	
	private Account account;
	private Question question;
	private Subject subject;
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	
	private SimpleTextField topic;
	private SimpleTextArea content;
	private SimpleTextArea time;
	private SimpleButton makeAnswer;
	
	private JScrollPane scrollBar;

	private int topicHeight = 40;
	private int xBorder = 10;
	private int yBorder = 10;

	private Color backgroundColor = new Color(255, 255, 255);

	public NewQuestionPanel(Account account, Subject subject, BulletinBoardFrame boardFrame, TitleBar titleBar){
		this.account = account;
		this.subject = subject;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.setLayout(null);

		this.setSize(boardFrame.getContentWidth() - xBorder * 2, (boardFrame.getHeight() - yBorder * 3 - titleBar.getHeight()) / 2);
		topic = new SimpleTextField("질문 제목");
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
		topic.setEditable(true);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		time = new SimpleTextArea("작성자 - " + account.getName(), dateFormat.format(new Date()));
		time.setSize(this.getWidth() * 2 / 5, topicHeight - 1);
		time.setLocation(topic.getWidth() + 1, 1);
		time.setBackground(new Color(240, 240, 240));
		time.setSmallFont();

		makeAnswer = new SimpleButton("질문 올리기");
		makeAnswer.setSize(90, 30);
		makeAnswer.setLocation(this.getWidth() - makeAnswer.getWidth() - 5, this.getHeight() - makeAnswer.getHeight() - 5);
		makeAnswer.addActionListener(new addQuestion());
		
		content = new SimpleTextArea("");
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - makeAnswer.getHeight() - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));
		content.setEditable(true);

		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - makeAnswer.getHeight() - 10);
		scrollBar.setWheelScrollingEnabled(true);
		
		this.add(topic);
		this.add(scrollBar);
		this.add(time);
		
		this.add(makeAnswer);
		
		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
	}

	private class addQuestion implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			//StudentAccountController sCon = new StudentAccountController((StudentAccount)account);
			//sCon.makeQuestion(subject, topic.getText(), content.getText());
			//boardFrame.addQuestionPanel(new QuestionList(thisHeight(), subject, boardFrame, titleBar));
			//boardFrame.repaint();
			
			/*
			 * 2015.06.17, Tuna Park
			 * - Add server-clients COMMS func.
			 */
			if(ClientConsole.client.getAccount().isStudent()) {
				try
				{
					ClientConsole.client.sendToServer(ProtocolType.MAKE_QUESTION,
							ClientConsole.client.getAccount().getId() + ":" +
							subject.getName() + ":" + 
							topic.getText() + ":" +
							content.getText() + ":" +
							subject.getProfessor().getName());
					
					ClientConsole.client.setMsgReceive(false);
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
			
			boardFrame.addQuestionPanel(new QuestionList(thisHeight(), subject, boardFrame, titleBar));
			boardFrame.repaint();
		}
	}
	
	/**
	 * this returns student account if it's instance of StudentAccount
	 * @return StudentAccount : student
	 */
	public StudentAccount getStudent() {
		if(ClientConsole.client.getAccount().isStudent())
			return (StudentAccount)ClientConsole.client.getAccount();
		else
			return null;
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
