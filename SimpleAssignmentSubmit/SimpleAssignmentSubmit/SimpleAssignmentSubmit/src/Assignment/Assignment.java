package Assignment;

import Account.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Assignment implements Serializable{
	private String topic;
	private String content;
	private Professor professor;
	private Student student;
	private Subject subject;
	private Date deadline;
	private Assignment scoredStudentsAssignment;
	private Double score;
	private boolean isScored;
	private ArrayList<Assignment> studentsAssignment;
	private ArrayList<String> attachedDocument; // 첨부 파일의 절대 경로를 저장
	private String comment;
	
	/* 교수가 과제를 만들 때 생성자 */
	public Assignment(Professor professor, String topic, String content,
			String deadlineYear, String deadlineMonth, String deadlineDay, String deadlineHour) {
		this.topic = topic;
		this.content = content;
		this.professor = professor;
		this.setScored(false);
		String stringDate = deadlineYear + "/" + deadlineMonth + "/" + deadlineDay + "/" + deadlineHour;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
		try {
			deadline = dateFormat.parse(stringDate);
		} catch (Exception ex) {System.err.println("시간이 제대로 입력되지 않았습니다!");}

		studentsAssignment = new ArrayList<Assignment>();
		attachedDocument = new ArrayList<String>();
	}
	
	/* 학생이 과제를 제출 시 생성자 */
	public Assignment(String topic, String content, Date date) {
		this.topic = topic;
		this.content = content;
		this.deadline = date;
		this.setScored(false);
		attachedDocument = new ArrayList<String>();
	}
	
	public void addStudentAssignment(Student student, Assignment assignment) {
		assignment.setStudent(student);
		studentsAssignment.add(assignment);
	}
	
	public void submitScoredStudentsAssignment(Assignment assignment) {
		assignment.setScored(true);
		scoredStudentsAssignment = assignment;
	}
	
	public Assignment getScoredAssignment() {
		return scoredStudentsAssignment;
	}
	
	public ArrayList<Assignment> getStudentsAssignment() {
		return studentsAssignment;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public Subject getSubject() {
		return subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	
	public Student getStudent() {
		return student;
	}
	
	public Double getScore() {
		return score;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setScore(Double score) {
		this.score = score;
	}
	
	public void setComment(String text) {
		this.comment = text;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public void setTopic(String text) {
		this.topic = text;
	}
	
	public void setContent(String text) {
		this.content = text;
	}
	
	public void setDeadline(Date date) {
		this.deadline = date;
	}
	
	public void setScored(boolean a) {
		isScored = a;
	}
	
	public boolean isScored() {
		return isScored;
	}
	
	public boolean isSubmitted(Student student) {
		if(student.getWhichAssignmentSubmitted().indexOf(this) != -1)
			return true;
		else
			return false;
	}
	
	private void saveData() {
	}
	
	public void makeStatistic() {	
	}
}
