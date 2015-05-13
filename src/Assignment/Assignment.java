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
 * ������ ����, �����ϱ� ���� Ŭ����
 * @author eastern7star
 *
 */
public class Assignment implements Serializable{
	private String topic;
	private String content;
	private ProfessorAccount professor;
	private StudentAccount student;
	private Subject subject;
	private Date deadline;
	private Assignment scoredStudentsAssignment;
	private Double score;
	private boolean isScored;
	private ArrayList<Assignment> studentsAssignment; // �л���� ������ ����
	private ArrayList<String> attachedDocument; // ÷�� ������ ���� ��θ� ����
	private String comment;
	
	/* ������ ������ ���� �� ������ */
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
		} catch (Exception ex) {System.err.println("�ð��� ����� �Էµ��� �ʾҽ��ϴ�!");}

		studentsAssignment = new ArrayList<Assignment>();
		attachedDocument = new ArrayList<String>();
	}
	
	/* �л��� ������ ���� �� ������ */
	public Assignment(String topic, String content, Date date) {
		this.topic = topic;
		this.content = content;
		this.deadline = date;
		this.setScored(false);
		attachedDocument = new ArrayList<String>();
	}
	
	/**
	 * ������ �ִ� ������ �л��� ��������
	 * @param student
	 * @param assignment
	 * @author eastern7star
	 */
	public void addStudentAssignment(StudentAccount student, Assignment assignment) {
		assignment.setStudent(student);
		studentsAssignment.add(assignment);
	}
	
	/**
	 * ������ ���ߴٰ� ǥ������ ����
	 * �л��� ������ �ִ� "�򰡵� �л� ����"�� ���� �Ҵ�
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
	
	public void makeStatistic() {	
	}
}
