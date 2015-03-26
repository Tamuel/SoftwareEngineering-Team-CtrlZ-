package GUIFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Account.Account;
import Account.Professor;
import Account.Student;
import Assignment.Subject;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleJFrame;
import GuiComponent.SimpleTextField;

/**
 * 계정을 생성하는 과정에서 수강하는 과목을 선택하는 프레임 클래스
 * 
 * @author eastern7star
 *
 */
public class SubjectSelectFrame extends SimpleJFrame{
	
	private Student student;
	
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
	
	public SubjectSelectFrame(ArrayList<Account> accounts, Student student, String frameName, int width, int height, LoginFrame loginFrame) {
		super(frameName, width, height);

		this.student = student;
		this.loginFrame = loginFrame;

		subjectButtons = new ArrayList<SimpleButton>();
		mySubject = new ArrayList<Subject>();
		
		height = this.getHeight();
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setSize(this.getWidth() - 30, height);
		panel.setLayout(null);
		
		int i = 0;
		Iterator iter = accounts.iterator();
		while(iter.hasNext()) {
			Account temp = (Account)iter.next();
			if(temp.isProfessor()) {
				SubjectButton tempButton = new SubjectButton(((Professor)temp).getSubject().getName(), ((Professor)temp).getName(),
						WIDTH - xBorder * 4, componentHeight, ((Professor)temp).getSubject());
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
		
		submitButton = new SimpleButton("확인");
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
			student.getSubjects().addAll(mySubject);

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
