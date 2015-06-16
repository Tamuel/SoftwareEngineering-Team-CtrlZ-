package QnA;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Account.*;
import Assignment.*;

public class Question implements Serializable {
	private StudentAccount student;
	private Subject subject;
	private String attachedDocument;
	private ArrayList<Question> questions;
	private ArrayList<Answer> answers;
	private String topic;
	private String content;
	private Date time;
	private int contNum;
	
	public Question(Subject subject) {
		this.subject = subject;
		questions = new ArrayList<Question>();
	}
	
	public Question(StudentAccount student, Subject subject, String topic, String content) {
		this.student = student;
		this.subject = subject;
		this.topic = topic;
		this.content = content;
		
		contNum = Account.contNum++;
		
		answers = new ArrayList<Answer>();
		
		try {
			time = new Date();
		} catch (Exception ex) {}
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}
	
	public void setStudent(StudentAccount student) {
		this.student=student;
	}
	
	public void setTopic(String topic) {
		this.topic=topic;
	}
	
	public void setContent(String content) {
		this.content=content;
	}
	
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public String getContent() {
		return content;
	}
	
	public StudentAccount getStudent() {
		return student;
	}
	
	public Subject getSubject() {
		return subject;
	}
	
	public Date getTime() {
		return time;
	}
	
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	
	public ArrayList<Question> getQuestions()
	{
		return questions;
	}
}
