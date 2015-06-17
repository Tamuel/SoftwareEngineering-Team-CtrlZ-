package JUnitTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.Permission;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Assignment;
import Assignment.Subject;
import ServerClientConsole.Server;
import client.Client;

public class NotificationAndServerClientUnitTest {

	Server server;
	Client sender;
	Client receiver;
	Subject subject1;
	
	@BeforeClass
	public void startServer() throws IOException {
		server.setPort(9000);
		server.setTimeout(100);
		server.listen();
		
		sender.run();
		sender.openConnection();
		
		receiver.run();
		receiver.openConnection();
		
		subject1 = new Subject("Subject1");
	}
	
	@Before
	public void beforeNotiTest() {
	}
	
	
	@Test
	public void whenMakeAssignment() {
		ProfessorAccount prof1 = new ProfessorAccount("prof", "1234", "Prof1", subject1);
		StudentAccount stud1 = new StudentAccount("stud", "1234", "Stud1");
		stud1.addSubject(subject1);
		sender.setAccount(prof1);
		receiver.setAccount(stud1);
		
		if(sender.getAccount().isProfessor())
			((ProfessorAccount)sender.getAccount()).makeAssignment("Asssignment1", "contnent", "2015", "10", "20", "12:30");
		
		assertTrue(server.getNotiStack().getSize() == 1);
		assertTrue(receiver.getAccount().haveNotice() == 1);
	}
	
	@Test
	public void whenSubmitAssignment() {
		ProfessorAccount prof1 = new ProfessorAccount("prof", "1234", "Prof1", subject1);
		StudentAccount stud1 = new StudentAccount("stud", "1234", "Stud1");
		stud1.addSubject(subject1);
		
		sender.setAccount(prof1);
		receiver.setAccount(stud1);
		
		if(sender.getAccount().isProfessor())
			((ProfessorAccount)sender.getAccount()).makeAssignment("Asssignment1", "contnent", "2015", "10", "20", "12:30");
		
		if(receiver.getAccount().isStudent())
			((StudentAccount)receiver.getAccount()).submitAssignment(subject1.getAssignments().get(0), new Assignment("Asssignment1", "contnent", new Date()));
		
		Assert.assertEquals(1, server.getNotiStack().getSize());
		Assert.assertEquals(1, sender.getAccount().haveNotice());
	}
	
	@Test
	public void whenAssignmentAppraisal() {
		ProfessorAccount prof1 = new ProfessorAccount("prof", "1234", "Prof1", subject1);
		StudentAccount stud1 = new StudentAccount("stud", "1234", "Stud1");
		stud1.addSubject(subject1);
		
		sender.setAccount(prof1);
		receiver.setAccount(stud1);
		
		if(sender.getAccount().isProfessor())
			((ProfessorAccount)sender.getAccount()).makeAssignment("Asssignment1", "contnent", "2015", "10", "20", "12:30");
		
		if(receiver.getAccount().isStudent())
			((StudentAccount)receiver.getAccount()).submitAssignment(subject1.getAssignments().get(0), new Assignment("Asssignment1", "contnent", new Date()));
		
		if(sender.getAccount().isProfessor())
			((ProfessorAccount)sender.getAccount()).assignmentAppraisal("Good", "100", stud1.getAssignments().get(0));

		Assert.assertEquals(1, server.getNotiStack().getSize());
		Assert.assertEquals(1, sender.getAccount().haveNotice());
		Assert.assertEquals(2, receiver.getAccount().haveNotice());
	}
	
	@Test
	public void whenAnswerQuestion() {
		ProfessorAccount prof1 = new ProfessorAccount("prof", "1234", "Prof1", subject1);
		StudentAccount stud1 = new StudentAccount("stud", "1234", "Stud1");
		stud1.addSubject(subject1);
		
		sender.setAccount(prof1);
		receiver.setAccount(stud1);
		
		((StudentAccount)receiver.getAccount()).makeQuestion(subject1, "I have question", "Who am I?");
		((ProfessorAccount)sender.getAccount()).answerQuestion(subject1.getSubjectQuestions().getQuestions().get(0), "You");
		
		Assert.assertEquals(1, receiver.getAccount().haveNotice());
	}
	
	@After
	public void afterNotiTest()	{
	}
	
	
}