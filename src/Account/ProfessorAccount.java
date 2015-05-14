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

	public void makeAssignment(String topic, String content, String y, String m, String d, String h) {
		
		Assignment newAssignment = new Assignment(this, topic, content, y, m, d, h);
		newAssignment.setSubject(subject);
		assignments.add(newAssignment);
		subject.getAssignments().add(newAssignment);
		/*
		 * To do
		 * Make Notice Here
		 * Yu Dong Kyu, 15.05.13
		 */
	}
	
	public void assignmentAppraisal(String comment, String score, Assignment assignment) {
		assignment.setComment(comment);
		assignment.setScore(Double.parseDouble(score));
		assignment.submitScoredStudentsAssignment(assignment);
	}
	
	public void answerQuestion(Question question, String ans) {
		Answer make = new Answer(this, question, ans);
		answers.add(make);
		question.addAnswer(make);
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
