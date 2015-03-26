package Assignment;
import java.io.Serializable;
import java.util.ArrayList;

import Account.*;
import QnA.Question;

public class Subject implements Serializable{
	private String name;
	private Professor professor;
	private ArrayList<Student> students;
	private ArrayList<Assignment> assignments;
	private Question question;
	
	public Subject(String name) {
		this.name = name;
		this.assignments = new ArrayList<Assignment>();
		this.question = new Question(this);
	}
	
	public Subject(String name, ArrayList<Assignment> assignments) {
		this.assignments = assignments;
		this.name = name;
		this.question = new Question(this);
	}
	
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public void addQuestion(Question question) {
		this.question.addQuestion(question);
	}
	
	public ArrayList<Assignment> getAssignments() {
		return assignments;
	}
	
	public Question getSubjectQuestions() {
		return question;
	}
	
	public String getName() {
		return name;
	}
	
	public Professor getProfessor() {
		return professor;
	}
}
