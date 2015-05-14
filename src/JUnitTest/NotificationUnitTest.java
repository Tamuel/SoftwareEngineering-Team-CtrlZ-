package JUnitTest;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.Permission;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Subject;
import ServerClientConsole.Server;
import client.Client;

public class NotificationUnitTest {

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
	public void before() {
	}
	
	
	@Test
	public void whenMakeAssignment() {
		sender.setAccount(new ProfessorAccount("prof", "1234", "Prof1", subject1));
		receiver.setAccount((new StudentAccount("stud", "1234", "Stud1")).addSubject(subject1));
		
		if(sender.getAccount().getPermission() == Permission.PROFESSOR)
			((ProfessorAccount)sender.getAccount()).makeAssignment("Asssignment1", "contnent", "2015", "10", "20", "12:30");
		
		assertTrue(server.getNotiStack().getSize() == 1);
		assertTrue(( (StudentAccount) receiver.getAccount() ).getSubjects().get(0).haveNotice() == 1);
		/*
		 * haveNotice() : 알림이 있는지 판별, 사용자가 가진 알림의 수를 리턴 
		 */
	}
	
	
}