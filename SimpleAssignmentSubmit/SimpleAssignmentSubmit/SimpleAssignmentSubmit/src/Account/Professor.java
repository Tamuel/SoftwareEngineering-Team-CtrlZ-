package Account;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import Assignment.Assignment;
import Assignment.Subject;
import QnA.Answer;
import QnA.Question;

public class Professor extends Account {
	private Subject subject;
	private ArrayList<Assignment> assignments;
	private ArrayList<Answer> answers;

	public Professor(String id, String password, String name, Subject subject) {
		super(id, password, name, 1);
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
	}
	
	public void assignmentApprasal(String comment, String score, Assignment assignment) {
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
