package Account;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Assignment.*;
import GuiComponent.SimpleTextArea;
import QnA.Question;
import QnA.Answer;

public class Student extends Account{
	private ArrayList<Subject> subjects;
	private ArrayList<Assignment> assignments; // 제출한 과제
	private ArrayList<Assignment> submittedAssignments; // 과제를 어디에 제출했는지
	private ArrayList<Question> questions;
	private ArrayList<Answer> answers;
	
	public Student(String id, String password, String name) {
		
		// 21 MAR 2015 Tuna
		// change 4th parameter (int)0 to PermissionType.STUDENT
		super(id, password, name, PermissionType.STUDENT);
		subjects = new ArrayList<Subject>();
		assignments = new ArrayList<Assignment>();
		submittedAssignments = new ArrayList<Assignment>();
		questions = new ArrayList<Question>();
		answers = new ArrayList<Answer>();
	}

	public void submitAssignment(Assignment whichAssignment, Assignment myAssignment) {
		whichAssignment.addStudentAssignment(this, myAssignment);
		myAssignment.setStudent(this);
		submittedAssignments.add(whichAssignment);
		assignments.add(myAssignment);
	}
	
	public void makeQuestion(Subject subject, String topic, String content) {
		Question make = new Question(this, subject, topic, content);
		questions.add(make);
		subject.addQuestion(make);
	}
	
	public void answerQuestion(Question question, String ans) {
		Answer make = new Answer(this, question, ans);
		answers.add(make);
		question.addAnswer(make);
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
			pl("과목 : " + subejct.getName() + " ");
			for(int j = 0; j < subejct.getAssignments().size(); j++) {
				Assignment assignment = subejct.getAssignments().get(j);
				pl("      " + "과제: " + assignment.getTopic() + " ");
				for(int k = 0; k < assignment.getStudentsAssignment().size(); k++) {
					Assignment sassignment = assignment.getStudentsAssignment().get(k);

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
					pl("              " + "학생 : " + sassignment.getStudent().getName());
					pl("              " + "제출시간 : " + dateFormat.format(sassignment.getDeadline()));
					pl("              " + "제목 : " + sassignment.getTopic());
					pl("              " + "내용 : " + sassignment.getContent());
				}
			}
		}
	}
}
