package Controller;

import java.util.*;

import Account.*;
import Assignment.*;
import QnA.Answer;

public class NoticeController {

	private Account account;
	
	public NoticeController() {
		
	}
	
	/**
	 * this makes notice for when professor add new assignment
	 */
	public void newAssignmentNotice(Subject subject) {
		Iterator it = subject.getAssignments().iterator();
		StudentAccount studentAccount = null;
		
		while(it.hasNext()) {
			studentAccount = (StudentAccount)((Assignment)it.next()).getStudent();
			
			String message = "�� ���� �˸� : " + subject.getProfessor().getName() + "���� " + subject.getName() + "����\n" +
			subject.;
			
			Notice newNotice = new Notice();
		}
	}
}
