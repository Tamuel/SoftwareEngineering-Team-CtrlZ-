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

public class Answer implements Serializable {
	private StudentAccount student;
	private ProfessorAccount professor;
	private Question question;
	private String content;
	private Date time;
	private int contNum;
	
	public Answer(StudentAccount student, Question question, String ans)
	{
		this.student=student;
		this.question=question;
		this.content=ans;
		this.time = new Date();
		contNum = Account.contNum++;
	}
	
	public Answer(ProfessorAccount professor, Question question, String ans)
	{
		this.professor=professor;
		this.question=question;
		this.content=ans;
		this.time = new Date();
		contNum = Account.contNum++;
	}

	public void setStudent(StudentAccount student)
	{
		this.student=student;
	}
	
	public void setProfessor(ProfessorAccount professor)
	{
		this.professor=professor;
	}
	
	public void setQuestion(Question question)
	{
		this.question=question;
	}
	
	public void setAns(String ans)
	{
		this.content=ans;
	}
	
	public Date getTime() {
		return time;
	}
	
	public ProfessorAccount getProfessor()
	{
		return professor;
	}
	
	public Question getQuestion() {
		return question;
	}
	
	public StudentAccount getStudent() {
		return student;
	}
	
	public String getName() {
		if(professor != null)
			return professor.getName();
		else if(student != null)
			return student.getName();
		
		return "";
	}
	
	public int getContNum() {
		return contNum;
	}

	public String getContent()
	{
		return content;
	}
}