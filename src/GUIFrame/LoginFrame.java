package GUIFrame;

import Account.*;
import Assignment.Assignment;
import Assignment.Subject;
import GuiComponent.*;
import QnA.Question;
import ServerClientConsole.ClientConsole;
import ServerClientConsole.Protocol;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import common.ProtocolType;

import objectSave.ObjectSaveSingleton;

public class LoginFrame extends SimpleJFrame{
	
	private SimpleTextField idField;
	private SimpleTextField passwordField;
	
	static private Account account;
	
	private SimpleButton loginButton;
	private SimpleButton joinButton;

	private int componentHeight = 40;
	private int xBorder = 20;
	private int yBorder = 10;
	
	public LoginFrame(String frameName, int width, int height) {
		super(frameName, width, height);
		
		// Component 추가
		idField = new SimpleTextField("ID");
		idField.setSize(WIDTH - xBorder * 2, componentHeight);
		idField.setBackground(new Color(240, 240, 240));
		idField.setLocation(xBorder, componentHeight);
		
		passwordField = new SimpleTextField("Password");
		passwordField.setSize(WIDTH - xBorder * 2, componentHeight);
		passwordField.setBackground(new Color(240, 240, 240));
		passwordField.setLocation(xBorder, componentHeight * 2 + yBorder);
		
		Action listener = new Action();
		
		loginButton = new SimpleButton("Login");
		loginButton.setSize((WIDTH - xBorder *3) / 2, componentHeight);
		loginButton.setLocation(xBorder, componentHeight * 3 + yBorder * 2);
		loginButton.addActionListener(listener);
		
		joinButton = new SimpleButton("Join");
		joinButton.setSize((WIDTH - xBorder *3) / 2, componentHeight);
		joinButton.setLocation(xBorder * 2 + loginButton.getWidth(), loginButton.getY());
		joinButton.addActionListener(listener);
		
		this.add(idField);
		this.add(passwordField);
		this.add(loginButton);
		this.add(joinButton);
		
		this.getContentPane().setBackground(new Color(255, 255, 255));
		this.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
	}
	
	public void checkAccount()
	{
		try
		{
			while(ClientConsole.client.isMsgReceive() == false)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			ClientConsole.client.setMsgReceive(false);
			if(ClientConsole.client.getAccount() != null) {
				makeBulletinBoard(ClientConsole.client.getAccount());
				visible(false);
			}
		}
		catch(Exception ex)
		{
			System.err.println("Login Fail");
		}
	}
	
	private class Action implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			if(ev.getSource().equals(loginButton)) {
				try
				{
					ClientConsole.client.sendToServer(ProtocolType.LOGIN, idField.getText() + ":" + passwordField.getText());
				}
				catch(Exception ex)
				{
					System.err.println(ex.toString());
				}
				checkAccount();
			}
			else if(ev.getSource().equals(joinButton)) {
				MakeAccountFrame join = new MakeAccountFrame("Join", 300, 275, getThis());
				visible(false);
			}
		}
	}
	
	public LoginFrame getThis() {
		return this;
	}
	
	public void visible (boolean bool) {
		this.setVisible(bool);
	}
	
	public void makeBulletinBoard(Account account) {
		BulletinBoardFrame board = new BulletinBoardFrame("과제 제출", 1100, 630);
	}

	public static void main(String args[]) {
		LoginFrame login = new LoginFrame("Login", 300, 200);
	}
}
