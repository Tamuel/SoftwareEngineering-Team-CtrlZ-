package Account;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import common.Permission;
import Assignment.*;
import GuiComponent.SimpleTextArea;
import QnA.Question;
import QnA.Answer;

public class StudentAccount extends Account implements Serializable {
	private ArrayList<Subject> subjects;
	private ArrayList<Assignment> assignments; // Assignments which Student have
	private ArrayList<Assignment> submittedAssignments; // Submitted Assignments which Student have
	private ArrayList<Question> questions;
	private ArrayList<Answer> answers;
	
	public StudentAccount(String id, String password, String name) {
		
		// 21 MAR 2015 Tuna
		// change 4th parameter (int)0 to PermissionType.STUDENT
		super(id, password, name, Permission.STUDENT);
		subjects = new ArrayList<Subject>();
		assignments = new ArrayList<Assignment>();
		submittedAssignments = new ArrayList<Assignment>();
		questions = new ArrayList<Question>();
		answers = new ArrayList<Answer>();
	}

	public ArrayList<Assignment> getSubmittedAssignments() {
		return submittedAssignments;
	}

	public void setSubmittedAssignments(ArrayList<Assignment> submittedAssignments) {
		this.submittedAssignments = submittedAssignments;
	}

	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}

	public void setAssignments(ArrayList<Assignment> assignments) {
		this.assignments = assignments;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}
	
	public ArrayList<Assignment> getWhichAssignmentSubmitted() {
		return submittedAssignments;
	}
	
	public ArrayList<Question> getQuestions() {
		return questions;
	}
	
	public ArrayList<Subject> getSubjects() {
		return subjects;
	}
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	
	public void addSubject(Subject subject) {
		subjects.add(subject);
	}

	public void pl(String a) {
		System.out.println(a);
	}

	public void printAllAssignment() {
		pl("--------------------------------------------------");
		for(int i = 0; i < this.getSubjects().size(); i++) {
			Subject subejct = this.getSubjects().get(i);
			pl("占쏙옙占쏙옙 : " + subejct.getName() + " ");
			for(int j = 0; j < subejct.getAssignments().size(); j++) {
				Assignment assignment = subejct.getAssignments().get(j);
				pl("      " + "占쏙옙占쏙옙: " + assignment.getTopic() + " ");
				for(int k = 0; k < assignment.getStudentsAssignment().size(); k++) {
					Assignment sassignment = assignment.getStudentsAssignment().get(k);

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					pl("              " + "占싻삼옙 : " + sassignment.getStudent().getName());
					pl("              " + "占쏙옙占쏙옙챨占� : " + dateFormat.format(sassignment.getDeadline()));
					pl("              " + "占쏙옙占쏙옙 : " + sassignment.getTopic());
					pl("              " + "占쏙옙占쏙옙 : " + sassignment.getContent());
				}
			}
		}
	}
}
