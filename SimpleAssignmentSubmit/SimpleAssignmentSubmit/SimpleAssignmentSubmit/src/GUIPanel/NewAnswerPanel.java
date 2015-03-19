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

import Account.Account;
import Account.Professor;
import Account.Student;
import Assignment.Assignment;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleLabel;
import GuiComponent.SimpleTextArea;
import QnA.Answer;
import QnA.Question;

public class NewAnswerPanel extends JPanel{
	
//	private Answer answer;
	private Account account;
	private Question question;
	
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	private QuestionPanel questionPanel;
	
	private SimpleTextArea content;
	private SimpleTextArea time;
	private SimpleButton submitButton;
	
	private JScrollPane scrollBar;

	private int height = 50;
	private int xBorder = 10;
	private int yBorder = 10;

	private Color backgroundColor = new Color(255, 255, 255);

	public NewAnswerPanel(Account account, Question question, BulletinBoardFrame boardFrame, QuestionPanel questionPanel, TitleBar titleBar) {
		this.account = account;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.questionPanel = questionPanel;
		this.question = question;
		this.setLayout(null);

		this.setSize(questionPanel.getWidth(), height);
		
		content = new SimpleTextArea("");
		content.setSize(this.getWidth() * 10 / 16 - 1, 0);
		content.setLocation(1, 1);
		content.setEditable(true);
	
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		time = new SimpleTextArea( "작성자 - " + account.getName());//, dateFormat.format(new Date()));
		time.setSize(this.getWidth() * 3 / 16 - 1, height - 2);
		time.setLocation(1, 1);
		time.setBackground(new Color(240, 240, 240));
		time.setSmallFont();

		scrollBar = new JScrollPane(content);
		scrollBar.setBorder(null);
		scrollBar.setLocation(time.getWidth() + 1, 1);
		scrollBar.setSize(this.getWidth() * 10 / 16 - 1, height - 2);
		scrollBar.setWheelScrollingEnabled(true);
		
		submitButton = new SimpleButton("답변");
		submitButton.setSize(this.getWidth() * 3 / 16 - 10, height - 2 - 10);
		submitButton.setLocation(scrollBar.getX() + scrollBar.getWidth() + 1 + 5, 1 + 5);
		submitButton.addActionListener(new submit());
		
		this.add(scrollBar);
		this.add(time);
		this.add(submitButton);
	
		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
	}
	

	private class submit implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(account.isProfessor()) {
				((Professor)account).answerQuestion(question, content.getText());
			}else if(account.isStudent()) {
				((Student)account).answerQuestion(question, content.getText());
			}

			boardFrame.addContentPanel(new ContentPanel(question, boardFrame, titleBar));
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
