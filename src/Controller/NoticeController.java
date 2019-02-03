package Controller;

import java.util.*;

import Account.*;
import Assignment.*;
import QnA.Answer;

public class NoticeController {

	private Account account;
	
	public NoticeController() {
		
	}
	
	public NoticeController(Account account) {
		setAccount(account);
	}
	
	/**
	 * this makes notice for when professor adds new assignment
	 */
	public Notice getNewAssignmentNotice(Subject subject, Date today) {
		int year = today.getYear() + 1900;
		int month = today.getMonth();
		int date = today.getDate();
		int hour = today.getHours();
		int minute = today.getMinutes();
		int second = today.getSeconds();
		
		String message = "<html>�� ���� �˸� : " + account.getName() + "���� " + subject.getName() + "����<br>" +
						year + "�� " + month + "�� " + date + "�� " + hour + "�� " + minute + "�� " + second + "��</html>";
		
		Notice notice = new Notice(message);
		
		return notice;
	}
	
	/**
	 * this makes notice for when student submits assignment
	 */
	public Notice getSubmitAssignmentNotice(Subject subject, Date today) {
		int year = today.getYear() + 1900;
		int month = today.getMonth();
		int date = today.getDate();
		int hour = today.getHours();
		int minute = today.getMinutes();
		int second = today.getSeconds();
		
		String message = "<html><body>���� ���� �˸� : " + account.getName() + "�л� " + subject.getName() + "����<br>" +
						year + "�� " + month + "�� " + date + "�� " + hour + "�� " + minute + "�� " + second + "��</body></html>";
		
		Notice notice = new Notice(message);
		
		return notice;
	}

	/**
	 * this makes notice for when professor appraised assignment
	 */
	public Notice getAppraisalAssignmentNotice(Subject subject, Date today) {
		int year = today.getYear() + 1900;
		int month = today.getMonth();
		int date = today.getDate();
		int hour = today.getHours();
		int minute = today.getMinutes();
		int second = today.getSeconds();
		
		String message = "<html>���� �� �˸� : " + account.getName() + "���� " + subject.getName() + "����<br>" +
						year + "�� " + month + "�� " + date + "�� " + hour + "�� " + minute + "�� " + second + "��</html>";
		
		Notice notice = new Notice(message);
		
		return notice;
	}
	
	/**
	 * this makes notice for when student makes a question
	 */
	public Notice getNewQuestionNotice(Subject subject, Date today) {
		int year = today.getYear() + 1900;
		int month = today.getMonth();
		int date = today.getDate();
		int hour = today.getHours();
		int minute = today.getMinutes();
		int second = today.getSeconds();
		
		String message = "<html>�� ���� �˸� : " + account.getName() + "�л� " + subject.getName() + "����<br>" +
						year + "�� " + month + "�� " + date + "�� " + hour + "�� " + minute + "�� " + second + "��</html>";
		
		Notice notice = new Notice(message);
		
		return notice;
	}
	
	/**
	 * this makes notice for when professor/student answers
	 */
	public Notice getNewAnswerNotice(Subject subject, Date today) {
		int year = today.getYear() + 1900;
		int month = today.getMonth();
		int date = today.getDate();
		int hour = today.getHours();
		int minute = today.getMinutes();
		int second = today.getSeconds();
		
		String message = null;
		
		if(account.isProfessor()) {
			message = "<html>�� �亯 �˸� : " + account.getName() + "���� " + subject.getName() + "����<br>" +
						year + "�� " + month + "�� " + date + "�� " + hour + "�� " + minute + "�� " + second + "��</html>";
		}
		else if(account.isStudent()) {
			message = "<html>�� �亯 �˸� : " + account.getName() + "�л� " + subject.getName() + "����<br>" +
					year + "�� " + month + "�� " + date + "�� " + hour + "�� " + minute + "�� " + second + "��</html>";
		}
		
		Notice notice = new Notice(message);
		
		return notice;
	}
	
	/*
	 * Getters and Setters
	 */
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}