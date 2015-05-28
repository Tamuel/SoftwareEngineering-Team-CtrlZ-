package Controller;

import Account.ProfessorAccount;
import Assignment.Assignment;
import QnA.Answer;
import QnA.Question;

public class ProfessorAccountController {
	private ProfessorAccount account;
	
	public ProfessorAccountController (ProfessorAccount account) {
		this.account = account;
	}
	public void makeAssignment(String topic, String content, String y, String m, String d, String h) {
		
		Assignment newAssignment = new Assignment(getAccount(), topic, content, y, m, d, h);
		newAssignment.setSubject(getAccount().getSubject());
		getAccount().getAssignments().add(newAssignment);
		getAccount().getSubject().getAssignments().add(newAssignment);
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
		Answer make = new Answer(getAccount(), question, ans);
		getAccount().getAnswers().add(make);
		question.addAnswer(make);
	}

	public ProfessorAccount getAccount() {
		return account;
	}

	public void setAccount(ProfessorAccount account) {
		this.account = account;
	}

}
