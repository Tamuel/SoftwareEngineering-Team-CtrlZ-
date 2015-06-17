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
		
		String message = "<html>새 과제 알림 : " + account.getName() + "교수 " + subject.getName() + "과목<br>" +
						year + "년 " + month + "월 " + date + "일 " + hour + "시 " + minute + "분 " + second + "초</html>";
		
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
		
		String message = "<html><body>과제 제출 알림 : " + account.getName() + "학생 " + subject.getName() + "과목<br>" +
						year + "년 " + month + "월 " + date + "일 " + hour + "시 " + minute + "분 " + second + "초</body></html>";
		
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
		
		String message = "<html>과제 평가 알림 : " + account.getName() + "교수 " + subject.getName() + "과목<br>" +
						year + "년 " + month + "월 " + date + "일 " + hour + "시 " + minute + "분 " + second + "초</html>";
		
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
		
		String message = "<html>새 질문 알림 : " + account.getName() + "학생 " + subject.getName() + "과목<br>" +
						year + "년 " + month + "월 " + date + "일 " + hour + "시 " + minute + "분 " + second + "초</html>";
		
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
			message = "<html>새 답변 알림 : " + account.getName() + "교수 " + subject.getName() + "과목<br>" +
						year + "년 " + month + "월 " + date + "일 " + hour + "시 " + minute + "분 " + second + "초</html>";
		}
		else if(account.isStudent()) {
			message = "<html>새 답변 알림 : " + account.getName() + "학생 " + subject.getName() + "과목<br>" +
					year + "년 " + month + "월 " + date + "일 " + hour + "시 " + minute + "분 " + second + "초</html>";
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
