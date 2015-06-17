package GUIFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import common.ProtocolType;

import Account.Account;
import Assignment.Notice;
import GuiComponent.SimpleButton;
import GuiComponent.SimpleJFrame;
import GuiComponent.SimpleLabel;
import ServerClientConsole.ClientConsole;

public class NoticeFrame extends SimpleJFrame {
	private Account account;
	
	private ArrayList<Notice> notices;
	private JScrollPane scrollPane;
	private JPanel noticesListPanel;
	private SimpleButton okayButton;
	
	private int okayButtonHeight = 30;
	private int noticeLabelHeight = 100;
	private int noticeLabelBorderHeight = 10;

	public NoticeFrame(String frameName, int width, int height) {
		super(frameName, width, height);
		setLayout(null);
		account = ClientConsole.client.getAccount();
		notices = account.getNotices();
		
		okayButton = new SimpleButton("»Æ¿Œ");
		okayButton.setSize(width, okayButtonHeight);
		okayButton.setLocation(0, height - okayButtonHeight);
		okayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				account.getNotices().clear();
				
				try {
					ClientConsole.client.sendToServer(ProtocolType.CLEAR_NOTICE,
							ClientConsole.client.getAccount().getId());
				}
				catch(Exception ex) {
					
				}
				
				exit();
			}
			
		});
		add(okayButton);
		
		noticesListPanel = new JPanel();
		noticesListPanel.setSize(width - 30, noticeLabelHeight * notices.size()
				+ noticeLabelBorderHeight * (notices.size() + 1));
		noticesListPanel.setPreferredSize(new Dimension(width, noticeLabelHeight * notices.size()
				+ noticeLabelBorderHeight * (notices.size() + 1)));
		noticesListPanel.setLocation(0, 0);
		noticesListPanel.setBackground(Color.WHITE);
		noticesListPanel.setLayout(null);
		
		int notiSize = notices.size();
		for(int i = 0; i < notiSize; i++) {
			SimpleLabel temp = new SimpleLabel(notices.get(i).getMessage());
			temp.setBorder(new LineBorder(new Color(200, 200, 200), 1));
			temp.setSize(width - 30, noticeLabelHeight);
			temp.setLocation(5, (notiSize - i - 1) * (noticeLabelHeight + noticeLabelBorderHeight));
			noticesListPanel.add(temp);
		}

		scrollPane = new JScrollPane();
		
		scrollPane.setLocation(0, okayButtonHeight);
		scrollPane.setSize(width, height - okayButtonHeight * 2);
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setViewportView(noticesListPanel);
		scrollPane.setHorizontalScrollBar(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		scrollPane.setBackground(Color.WHITE);
		
		add(scrollPane);
	}

	public void exit() {
		this.dispose();
	}
}
