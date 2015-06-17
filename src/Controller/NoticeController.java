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
			
			String message = "새 과제 알림 : " + subject.getProfessor().getName() + "교수 " + subject.getName() + "과목\n" +
			subject.;
			
			Notice newNotice = new Notice();
		}
	}
}
