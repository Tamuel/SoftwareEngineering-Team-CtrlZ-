package Controller;

import Account.StudentAccount;
import Assignment.Assignment;
import Assignment.Subject;
import QnA.Answer;
import QnA.Question;

public class StudentAccountController {
	private StudentAccount account;
	
	public StudentAccountController(StudentAccount account) {
		this.account = account;
	}
	

	public void submitAssignment(Assignment whichAssignment, Assignment myAssignment) {
		whichAssignment.addStudentAssignment(getAccount(), myAssignment);
		myAssignment.setStudent(getAccount());
		getAccount().getSubmittedAssignments().add(whichAssignment);
		getAccount().getAssignments().add(myAssignment);
	}
	
	public void makeQuestion(Subject subject, String topic, String content) {
		Question make = new Question(getAccount(), subject, topic, content);
		getAccount().getQuestions().add(make);
		subject.addQuestion(make);
	}
	
	public void answerQuestion(Question question, String ans) {
		Answer make = new Answer(getAccount(), question, ans);
		getAccount().getAnswers().add(make);
		question.addAnswer(make);
	}

	public StudentAccount getAccount() {
		return account;
	}


	public void setAccount(StudentAccount account) {
		this.account = account;
	}
	
}
