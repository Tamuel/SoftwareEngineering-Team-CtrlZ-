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
/**
 * 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙, 占쏙옙占쏙옙占싹깍옙 占쏙옙占쏙옙 클占쏙옙占쏙옙
 * @author eastern7star
 *
 */
public class Assignment implements Serializable {
	private String topic;
	private String content;
	private ProfessorAccount professor;
	private StudentAccount student;
	private Subject subject;
	private Date deadline;
	private Assignment scoredStudentsAssignment;
	private Double score;
	private boolean isScored;
	private ArrayList<Assignment> studentsAssignment; // 학생들이 제출한 과제
	private ArrayList<String> attachedDocument; // 첨부 파일
	private String comment;
	private int contNum;
	
	/* 교수님이 새로운 과제를 만들 때 사용하는 생성자 */
	public Assignment(ProfessorAccount professor, String topic, String content,
			String deadlineYear, String deadlineMonth, String deadlineDay, String deadlineHour) {
		this.topic = topic;
		this.content = content;
		this.professor = professor;
		this.setScored(false);
		String stringDate = deadlineYear + "/" + deadlineMonth + "/" + deadlineDay + "/" + deadlineHour;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
		try {
			deadline = dateFormat.parse(stringDate);
		} catch (Exception ex) {System.err.println("占시곤옙占쏙옙 占쏙옙占쏙옙占� 占쌉력듸옙占쏙옙 占십았쏙옙占싹댐옙!");}
		
		contNum = Account.contNum++;

		studentsAssignment = new ArrayList<Assignment>();
		attachedDocument = new ArrayList<String>();
	}
	
	/* 학생이 과제를 제출할 때 사용하는 생성자 */
	public Assignment(String topic, String content, Date date) {
		this.topic = topic;
		this.content = content;
		this.deadline = date;
		this.setScored(false);
		attachedDocument = new ArrayList<String>();
		
		contNum = Account.contNum++;
	}
	
	/**
	 * 占쏙옙占쏙옙占쏙옙 占쌍댐옙 占쏙옙占쏙옙占쏙옙 占싻삼옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙
	 * @param student
	 * @param assignment
	 * @author eastern7star
	 */
	public void addStudentAssignment(StudentAccount student, Assignment assignment) {
		assignment.setStudent(student);
		studentsAssignment.add(assignment);
	}
	
	/**
	 * 占쏙옙占쏙옙占쏙옙 占쏙옙占쌩다곤옙 표占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
	 * 占싻삼옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쌍댐옙 "占쏠가듸옙 占싻삼옙 占쏙옙占쏙옙"占쏙옙 占쏙옙占쏙옙 占쌀댐옙
	 * @param assignment
	 * @author eastern7star
	 */
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
	
	public ProfessorAccount getProfessor() {
		return professor;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	
	public StudentAccount getStudent() {
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
	
	public void setStudent(StudentAccount student) {
		this.student = student;
	}
	
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public void setProfessor(ProfessorAccount professor) {
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
	
	public boolean isSubmitted(StudentAccount student) {
		if(student.getWhichAssignmentSubmitted().indexOf(this) != -1)
			return true;
		else
			return false;
	}
	
	private void saveData() {
	}
	
	public int getContNum() {
		return contNum;
	}

	public void makeStatistic() {	
	}
}
