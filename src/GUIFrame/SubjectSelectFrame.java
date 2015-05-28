package GUIFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import common.ProtocolType;

import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Subject;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleJFrame;
import GuiComponent.SimpleTextField;
import ServerClientConsole.ClientConsole;
import ServerClientConsole.Protocol;

public class SubjectSelectFrame extends SimpleJFrame{
	
	
	private LoginFrame loginFrame;
	
	private ArrayList<SimpleButton> subjectButtons;
	private ArrayList<Subject> mySubject;
	private JScrollPane scrollBar;
	private SimpleButton submitButton;
	
	private int componentHeight = 60;
	private int xBorder = 10;
	private int yBorder = 10;
	private int height;
	
	private Color selected = new Color(230, 230, 230);
	
	public SubjectSelectFrame(String frameName, int width, int height, LoginFrame loginFrame) {
		super(frameName, width, height);

		this.loginFrame = loginFrame;

		subjectButtons = new ArrayList<SimpleButton>();
		mySubject = new ArrayList<Subject>();
		
		height = this.getHeight();
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setSize(this.getWidth() - 30, height);
		panel.setLayout(null);
		

		try
		{
			ClientConsole.client.sendToServer(ProtocolType.REQUEST_ACCOUNT_LIST, "");
		}
		catch(Exception ex)
		{
			System.err.println(ex.toString());
		}
		System.out.println("Send To Server Reuqest Account List");
		
		while(ClientConsole.client.isMsgReceive() == false)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Leceive from Server Account List");
		

		Account accounts = ClientConsole.client.getAccountList();
		
		int i = 0;
		Iterator iter = accounts.getAccounts().iterator();
		while(iter.hasNext()) {
			Account temp = (Account)iter.next();
			if(temp.isProfessor()) {
				SubjectButton tempButton = new SubjectButton(((ProfessorAccount)temp).getSubject().getName(), ((ProfessorAccount)temp).getName(),
						WIDTH - xBorder * 4, componentHeight, ((ProfessorAccount)temp).getSubject());
				tempButton.setLocation(xBorder * 2, componentHeight * i + yBorder * (i + 1));
				tempButton.addActionListener(new selectListener());
				tempButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(180, 180, 180)));
				tempButton.setBackground(Color.WHITE);
				tempButton.setFontColor(new Color(120, 120, 120), new Color(120, 120, 120));
				subjectButtons.add(tempButton);
				panel.add(tempButton);
				
				if(tempButton.getY() + tempButton.getHeight() + yBorder > height)
					height = tempButton.getY() + tempButton.getHeight() + yBorder;
				i++;
			}
		}
		
		panel.setSize(this.getWidth() - 30, height);
		panel.setPreferredSize(new Dimension(panel.getWidth(), panel.getHeight()));
		
		scrollBar = new JScrollPane(panel);
		scrollBar.setSize(this.getWidth(), this.getHeight() - 70);
		scrollBar.setLocation(0, 0);
		scrollBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(180, 180, 180)));
		
		submitButton = new SimpleButton("»Æ¿Œ");
		submitButton.setSize(this.getWidth() - xBorder * 2, 50);
		submitButton.setLocation(xBorder, scrollBar.getY() + scrollBar.getHeight() + 10);
		submitButton.addActionListener(new submitListener());
		
		this.add(submitButton);
		this.add(scrollBar);
		
		this.getContentPane().setBackground(new Color(255, 255, 255));
		this.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
	}
	
	private class SubjectButton extends SimpleButton {
		private Subject subject;
		private boolean selected = false;
		
		public SubjectButton(String text, String text2, int width, int height, Subject subject) {
			super(text, text2, width, height);
			this.subject = subject;
		}
		
		public void setSelected(boolean a) {
			this.selected = a;
		}
		
		public boolean isSelected() {
			return selected;
		}
		
		public Subject getSubject() {
			return subject;
		}
	}

	private class submitListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			for(int i = 0; i < mySubject.size(); i++) {
				try {
					ClientConsole.client.sendToServer(ProtocolType.ADD_SUBJECT, ClientConsole.client.getAccount().getId() + ":" 
							+ mySubject.get(i).getName() + ":" + mySubject.get(i).getProfessor().getName());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			
			LoginFrame login = new LoginFrame("Login", 300, 200);
			visible(false);
		}
	}
	
	private class selectListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			SubjectButton thisButton = (SubjectButton)ev.getSource();
			if(!thisButton.isSelected()) {
				thisButton.setBackground(selected);
				thisButton.setSelected(true);
				mySubject.add(thisButton.getSubject());
			} else if(thisButton.isSelected()) {
				thisButton.setBackground(Color.WHITE);
				thisButton.setSelected(false);
				mySubject.remove(thisButton.getSubject());
			}
		}
	}
	
	public void visible (boolean bool) {
		this.setVisible(bool);
	}
}
