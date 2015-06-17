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
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Assignment;
import GUIFrame.BulletinBoardFrame;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleLabel;
import GuiComponent.SimpleTextArea;
import QnA.Answer;
import QnA.Question;
import ServerClientConsole.ClientConsole;

public class AnswerPanel extends JPanel{
	
	private Answer answer;
	
	private BulletinBoardFrame boardFrame;
	private TitleBar titleBar;
	
	private SimpleTextArea content;
	private SimpleTextArea time;
	private SimpleButton delete;
	
	private JScrollPane scrollBar;

	private int height = 50;
	private int xBorder = 10;
	private int yBorder = 10;

	private Color backgroundColor = new Color(255, 255, 255);

	public AnswerPanel(Answer answer, BulletinBoardFrame boardFrame, ContentPanel contentPanel, TitleBar titleBar) {
		this.answer = answer;
		this.boardFrame = boardFrame;
		this.titleBar = titleBar;
		this.setLayout(null);

		this.setSize(contentPanel.getWidth() - xBorder * 2 - 18, height);
	
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		time = new SimpleTextArea( "ÀÛ¼ºÀÚ - " + answer.getName(), dateFormat.format(answer.getTime()));
		time.setSize(this.getWidth() * 5 / 16 - 1, height - 2);
		time.setLocation(1, 1);
		time.setBackground(new Color(240, 240, 240));
		time.setSmallFont();
		
		if((ClientConsole.client.getAccount().isStudent() && ((StudentAccount)ClientConsole.client.getAccount()).getAnswers().indexOf(answer) != -1) ||
		   (ClientConsole.client.getAccount().isProfessor() && ((ProfessorAccount)ClientConsole.client.getAccount()).getAnswers().indexOf(answer) != -1)) {
			content = new SimpleTextArea(answer.getContent());
			content.setSize(this.getWidth() * 10 / 16 - 1, 0);
			content.setLocation(1, 1);
	
			scrollBar = new JScrollPane(content);
			scrollBar.setBorder(null);
			scrollBar.setLocation(time.getWidth() + 1, 1);
			scrollBar.setSize(this.getWidth() * 10 / 16 - 1, height - 2);
			scrollBar.setWheelScrollingEnabled(true);
			
			delete = new SimpleButton("x");
			delete.setBackground(null);
			delete.setForeground(new Color(255, 0, 0));
			delete.setSize(this.getWidth() * 1 / 16 - 1, height / 2);
			delete.setLocation(scrollBar.getWidth() + scrollBar.getX() + 1, this.getHeight() / 4 + 1);
			delete.addActionListener(new deleteListener());
			this.add(delete);
		} else {
			content = new SimpleTextArea(answer.getContent());
			content.setSize(this.getWidth() * 11 / 16 - 1, 0);
			content.setLocation(1, 1);
	
			scrollBar = new JScrollPane(content);
			scrollBar.setBorder(null);
			scrollBar.setLocation(time.getWidth() + 1, 1);
			scrollBar.setSize(this.getWidth() * 11 / 16 - 1, height - 2);
			scrollBar.setWheelScrollingEnabled(true);
		}
		
		this.add(scrollBar);
		this.add(time);
	
		this.setBackground(backgroundColor);
		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
	}
	
	private class deleteListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			
			System.out.println("answer contNum : " + answer.getContNum());
			
			try
			{
				ClientConsole.client.sendToServer(ProtocolType.DELETE_ANSWER,
						ClientConsole.client.getAccount().getId() + ":" +
						answer.getQuestion().getSubject().getName() + ":" + 
						answer.getContNum() + ":" + // for finding answer
						answer.getQuestion().getContNum() + ":" + // for finding question
						answer.getQuestion().getSubject().getProfessor().getName());
				
				System.out.println();
			}
			catch(Exception ex)
			{
				System.err.println(ex.toString());
			}
			
			/*if(ClientConsole.client.getAccount().isStudent())
				((StudentAccount)ClientConsole.client.getAccount()).getAnswers().remove(((StudentAccount)ClientConsole.client.getAccount()).getAnswers().indexOf(answer));
			else if(ClientConsole.client.getAccount().isProfessor())
				((ProfessorAccount)ClientConsole.client.getAccount()).getAnswers().remove(((ProfessorAccount)ClientConsole.client.getAccount()).getAnswers().indexOf(answer));
			
			answer.getQuestion().getAnswers().remove(answer.getQuestion().getAnswers().indexOf(answer));*/
			
			if(!boardFrame.getSeeWholeAnswer()) {
				boardFrame.addContentPanel(new ContentPanel(answer.getQuestion(), boardFrame, titleBar));
				boardFrame.repaint();
			}else {
				ContentPanel maPanel = new ContentPanel(boardFrame, ((ProfessorAccount)ClientConsole.client.getAccount()).getAnswers(), titleBar);
				boardFrame.addContentPanel(maPanel);
				boardFrame.setSeeWholeAnswer(true);
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
