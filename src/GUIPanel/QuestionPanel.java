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

import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Assignment;
import Controller.AccountController;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleLabel;
import GuiComponent.SimpleTextArea;
import QnA.Question;
import ServerClientConsole.ClientConsole;

public class QuestionPanel extends JPanel{
	
	private Question question;
	
	private ContentPanel qnaPanel;
	
	private SimpleTextArea topic;
	private SimpleTextArea content;
	private SimpleTextArea time;
	private SimpleButton makeAnswer;
	
	private JScrollPane scrollBar;
	
	private boolean editable;

	private int buttonHeight = 30;
	private int topicHeight = 40;
	private int xBorder = 10;
	private int yBorder = 10;

	private Color backgroundColor = new Color(255, 255, 255);

	public QuestionPanel(Question question, ContentPanel qnaPanel, BulletinBoardFrame boardFrame) {
		this.question = question;
		this.qnaPanel = qnaPanel;
		this.editable = false;
		this.setLayout(null);

		this.setSize(qnaPanel.getWidth() - xBorder * 2 - 18, (qnaPanel.getHeight() - yBorder * 3) / 2);
		topic = new SimpleTextArea(question.getTopic());
		topic.setSize(this.getWidth() * 3 / 5 - 2, topicHeight - 1);
		topic.setLocation(1, 1);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		time = new SimpleTextArea("작성자 - " + question.getStudent().getName(), dateFormat.format(question.getTime()));
		time.setSize(this.getWidth() * 2 / 5, topicHeight - 1);
		time.setLocation(topic.getWidth() + 1, 1);
		time.setBackground(new Color(240, 240, 240));
		time.setSmallFont();
		
		AccountController aCon = new AccountController(ClientConsole.client.getAccount());

		if(ClientConsole.client.getAccount().isStudent() && aCon.getQuestion(question.getSubject(), question.getContNum()) != null) {
			makeAnswer = new SimpleButton("질문 수정");
			makeAnswer.setSize(90, buttonHeight);
			makeAnswer.setLocation(this.getWidth() - makeAnswer.getWidth() - 5, this.getHeight() - makeAnswer.getHeight() - 5);
			makeAnswer.addActionListener(new editListener());
			this.add(makeAnswer);
		}
		
		content = new SimpleTextArea(question.getContent());
		content.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - buttonHeight - 25);
		content.setLocation(1, topicHeight);
		content.setMargin(new Insets(10, 10, 10, 10));

		scrollBar = new JScrollPane(content);
		scrollBar.setLocation(1, topicHeight);
		scrollBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(180, 180, 180)));
		scrollBar.setSize(this.getWidth() - 2, this.getHeight() - topicHeight - buttonHeight - 10);
		scrollBar.setWheelScrollingEnabled(true);
		
		this.add(topic);
		this.add(scrollBar);
		this.add(time);
		
		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
	}
	

	private class editListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(!editable) {
				editable = true;
				((SimpleButton)ev.getSource()).setText("수정 완료");
				topic.setEditable(true);
				content.setEditable(true);
			} else if(editable) {
				editable = false;
				((SimpleButton)ev.getSource()).setText("질문 수정");
				topic.setEditable(false);
				content.setEditable(false);
				question.setTopic(topic.getText().toString());
				question.setContent(content.getText().toString());
			}
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
