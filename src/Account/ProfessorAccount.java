package Account;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import common.Permission;

import Assignment.Assignment;
import Assignment.Subject;
import QnA.Answer;
import QnA.Question;

public class ProfessorAccount extends Account {
	private Subject subject;
	private ArrayList<Assignment> assignments;
	private ArrayList<Answer> answers;

	public ProfessorAccount(String id, String password, String name, Subject subject) {
		
		// 21 MAR 2015 Tuna
		// change 4th parameter (int)1 to PermissionType.PROFESSOR
		super(id, password, name, Permission.PROFESSOR);
		this.subject = subject;
		assignments = new ArrayList<Assignment>();
		answers = new ArrayList<Answer>();
		
		subject.setProfessor(this);
	}

	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(ArrayList<Assignment> assignments) {
		this.assignments = assignments;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public Subject getSubject() {
		return subject;
	}
}
