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
		myAssignment.setProfessor(whichAssignment.getProfessor());
		myAssignment.setSubject(whichAssignment.getSubject());
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
	
	public Subject getSubject(String name) {
		for(int i = 0; i < account.getSubjects().size(); i++)
			if(account.getSubjects().get(i).getName().equals(name))
				return account.getSubjects().get(i);
		return null;
	}
	
	public Question getQuestion(int question_contNum) {
		for(int i = 0; i < account.getQuestions().size(); i++)
			if(account.getQuestions().get(i).getContNum() == question_contNum)
				return account.getQuestions().get(i);
		
		return null;
	}
	
	public Assignment getAssignment(int assignment_contNum) {
		for(int i = 0; i < account.getAssignments().size(); i++)
			if(account.getAssignments().get(i).getContNum() == assignment_contNum)
				return account.getAssignments().get(i);
		
		return null;
	}

	public StudentAccount getAccount() {
		return account;
	}


	public void setAccount(StudentAccount account) {
		this.account = account;
	}
	
}
