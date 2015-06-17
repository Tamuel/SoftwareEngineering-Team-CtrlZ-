package ServerClientConsole;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import objectSave.ObjectSaveSingleton;
import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Assignment;
import Assignment.Notice;
import Assignment.Subject;
import Controller.AccountController;
import Controller.ProfessorAccountController;
import Controller.StudentAccountController;
import GUIFrame.SubjectSelectFrame;
import common.*;
import server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 * 
 * 이 Class 는 기능성 향상을 위해 AbstractServer를 override 함.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class Server extends AbstractServer {
	// Class variables *************************************************

	private Account accounts = ServerConsole.accounts;
	
	private ArrayList<Notice> noticies;
	
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 9000;
	

	ChatIF chatUI;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 * 
	 * @param port
	 *            The port number to connect on.
	 */
	public Server(int port, ChatIF chatUI) {
		super(port);
		this.chatUI = chatUI;
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 * 
	 * 이 메소드는 client 에서 받은 모든 메세지를 핸들링 한다.
	 * 
	 * @param msg
	 *            The message received from the client.
	 * @param client
	 *            The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Protocol proc = (Protocol) msg;
		System.out.println("Proc received: " + proc.getProcKind() + " " + proc.getData().toString() + " from " + client);

		Account account = null;
		Assignment assignment = null;
		AccountController aCon = new AccountController(accounts);
		StudentAccountController sCon;
		ProfessorAccountController pCon;
		Calendar time = Calendar.getInstance();
		
		switch(proc.getProcKind())
		{
		case LOGIN:
			System.out.println("Search Account " + proc.getID() + " " + proc.getPW());
			
			if ((account = aCon.searchAccount(proc.getID(), proc.getPW())) != null) {// && !account.isOnLine()) {
				System.out.println("Send to Client [LOGIN_ACCEPT]");
				account.setOnLine(true);
				account.setClientAddress(client.getInetAddress());
				this.sendToClient(client, new Protocol(ProtocolType.LOGIN_ACCEPT, account));
			}
			else {
				System.out.println("Send to Client [LOGIN_FAIL]");
				this.sendToClient(client, new Protocol(ProtocolType.LOGIN_FAIL, account));
			}
			break;
			
		case JOIN:
			String subString = "담당 과목 (없으면 학생입니다)";
			System.out.println("Search Account for new account " + proc.getID() + " " + proc.getPW());
			
			/* 교수님 계좌 생성 */
			if(aCon.checkIdRepeated(proc.getID()) && !proc.getSubject().equals(subString)) {
				Subject newSubject = new Subject(proc.getSubject());
				ProfessorAccount temp = new ProfessorAccount(proc.getID(), proc.getPW(), proc.getName(), newSubject);
				accounts.addAccount(temp);
				ObjectSaveSingleton.getInstance().saveAccounts();
				this.sendToClient(client, new Protocol(ProtocolType.JOIN_ACCEPT, temp));
			}
			else if(aCon.checkIdRepeated(proc.getID()) && proc.getSubject().equals(subString)) { /* 학생 계좌 생성 */
				StudentAccount temp = new StudentAccount(proc.getID(), proc.getPW(), proc.getName());
				accounts.addAccount(temp);
				ObjectSaveSingleton.getInstance().saveAccounts();
				this.sendToClient(client, new Protocol(ProtocolType.JOIN_ACCEPT, temp));
			}
			else if(!aCon.checkIdRepeated(proc.getID())) {
				this.sendToClient(client, new Protocol(ProtocolType.ID_EXIST, ""));
			}
			break;
			
		case ADD_SUBJECT:
			System.out.println("Add " + proc.getSubject() + " to " + proc.getID() + " " + client);
			((StudentAccount)aCon.searchAccountByID(proc.getID())).addSubject(aCon.searchSubject(proc.getName(), proc.getSubject()));
			ObjectSaveSingleton.getInstance().saveAccounts();
			break;
			
		case REQUEST_ACCOUNT_LIST:
			System.out.println("Send account list to client " + client);
			this.sendToClient(client, new Protocol(ProtocolType.ACCOUNT_LIST, accounts));
			break;
			
		case MAKE_ASSIGNMENT:
			System.out.println("Make new assignment " + proc.getID() + " " + proc.getTopic() + " " + client);
			account = aCon.searchAccountByID(proc.getID());
			pCon = new ProfessorAccountController((ProfessorAccount)account);
			pCon.makeAssignment(proc.getTopic(), proc.getContent(), proc.getYear(), proc.getMonth(), proc.getDay(), proc.getHour());
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* 모든 클라이언트에게 전송 */
			this.sendToAllClients(new Protocol(ProtocolType.SET_REFRESH, ((ProfessorAccount)account).getSubject().getName()));
			
			break;
			
		case EDIT_ASSIGNMENT:
			System.out.println("Edit assignment " + proc.getContNum() + " " + proc.getTopic() + " " + client);
			assignment = aCon.getProfAssignment(proc.getContNum());
			
			Date temp;
			String stringDate = proc.getYear() + "/" + proc.getMonth() + "/"
								+ proc.getDay() + "/" + proc.getHour();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH");
			try {
				temp = dateFormat.parse(stringDate);
				assignment.setTopic(proc.getTopic());
				assignment.setContent(proc.getContent());
				assignment.setDeadline(temp);
			}
			catch(Exception ex) {
				System.err.println(ex.toString());
			}
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* 모든 클라이언트에게 전송 */
			this.sendToAllClients(new Protocol(ProtocolType.SET_REFRESH, assignment.getProfessor().getSubject().getName()));
			
			break;
			
		case SUBMIT_ASSIGNMENT:
			System.out.println("Submit assignment " + proc.getID() + " " + proc.getContNum() + " " + proc.getTopic() + " " + client);
			account = aCon.searchAccountByID(proc.getID());
			assignment = aCon.getProfAssignment(proc.getContNum());
			sCon = new StudentAccountController((StudentAccount)account);
			sCon.submitAssignment(assignment, new Assignment(proc.getTopic(), proc.getContent(), new Date()));
			((StudentAccount)account).printAllAssignment();
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* 모든 클라이언트에게 전송 */
			this.sendToAllClients(new Protocol(ProtocolType.SET_REFRESH, assignment.getProfessor().getSubject().getName()));
			
			break;
			
		case STUDENT_EDIT_ASSIGNMENT:
			System.out.println("Edit assignment " + proc.getContNum() + " " + proc.getTopic() + " " + client);
			assignment = aCon.getStudAssignment(proc.getSubject(), proc.getContNum());
			assignment.setTopic(proc.getTopic());
			assignment.setContent(proc.getContent());
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* 모든 클라이언트에게 전송 */
			this.sendToAllClients(new Protocol(ProtocolType.SET_REFRESH, assignment.getProfessor().getSubject().getName()));
			
			break;
			
			/*
			 * 2015.06.17, Tuna Park
			 * - this occurs when client(professor) comments, scoring and send it to the server
			 */
		case APPRAISAL_ASSIGNMENT:
			System.out.println("Make Comment " + proc.getContNum() + " " + proc.getSubject() + " " + client);
			
			account = aCon.searchAccountByID(proc.getID());
			assignment = aCon.getStudAssignment(proc.getSubject(), proc.getContNum());
			
			pCon = new ProfessorAccountController(((ProfessorAccount)account));
			pCon.assignmentAppraisal(proc.getContent(), proc.getScore(), assignment);
			
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* 모든 클라이언트에게 전송 */
			this.sendToAllClients(new Protocol(ProtocolType.SET_REFRESH, assignment.getProfessor().getSubject().getName()));
			
			break;
			
			/*
			 * 2015.06.17, Tuna Park
			 * - this occurs when client(student) adds new question and send it to the server
			 */
		case MAKE_QUESTION:
			System.out.println("Make Question " + proc.getContNum() + " " + proc.getTopic() + " " + client);
			
			account = aCon.searchAccountByID(proc.getID());
			Subject subject = aCon.searchSubject(proc.getName(), proc.getSubject());
			sCon = new StudentAccountController((StudentAccount)account);
			sCon.makeQuestion(subject, proc.getTopic(), proc.getContent());
			
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* 모든 클라이언트에게 전송 */
			this.sendToAllClients(new Protocol(ProtocolType.SET_REFRESH, subject.getName()));
			
			break;
			
			/*
			 * 2015.06.17, Tuna Park
			 * - this occurs when client adds new answer and send it to the server
			 */
		case MAKE_ANSWER:
			System.out.println("Make Answer " + proc.getContNum() + " " + proc.getSubject() + " " + client);
			
			account = aCon.searchAccountByID(proc.getID());
			subject = aCon.searchSubject(proc.getName(), proc.getSubject());

			if(account.isProfessor()) {
				pCon = new ProfessorAccountController((ProfessorAccount)account);
				pCon.answerQuestion(aCon.getQuestion(subject, proc.getContNum()), proc.getContent());
			}else if(account.isStudent()) {
				sCon = new StudentAccountController((StudentAccount)account);
				sCon.answerQuestion(aCon.getQuestion(subject, proc.getContNum()), proc.getContent());
			}
			
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* 모든 클라이언트에게 전송 */
			this.sendToAllClients(new Protocol(ProtocolType.SET_REFRESH, subject.getName()));
			
			break;
			
		case DELETE_ANSWER:
			System.out.println("Delete Answer " + proc.getSubject() + " " + proc.getContNum() + " " + + proc.getContNum2() + " " + client);
			
			account = aCon.searchAccountByID(proc.getID());
			subject = aCon.searchSubject(proc.getName(), proc.getSubject());

			if(account.isProfessor()) {
				((ProfessorAccount)account).getAnswers().remove(aCon.getAnswer(subject, proc.getContNum(), proc.getContNum2()));
			}
			else if(account.isStudent()) {
				((StudentAccount)account).getAnswers().remove(aCon.getAnswer(subject, proc.getContNum(), proc.getContNum2()));
			}
			
			aCon.getQuestion(subject, proc.getContNum2()).getAnswers().remove(aCon.getAnswer(subject, proc.getContNum(), proc.getContNum2()));
			
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* 모든 클라이언트에게 전송 */
			this.sendToAllClients(new Protocol(ProtocolType.SET_REFRESH, subject.getName()));
			
			break;
			
		case NEED_REFRESH:
			account = aCon.searchAccountByID(proc.getID());
			System.out.println("Refresh " + account.getId() + " " + client);
			this.sendToClient(client, new Protocol(ProtocolType.REFRESH, account));
			break;
			
		case QUIT:
			System.out.println("Client Quit " + " " + client);
			aCon.searchAccountByID(proc.getID()).setOnLine(false);
			break;
			
		default:
			break;
		}
	}

	/**
	 * This method overrides the one in the superclass. Called
	 * 
	 * Client와 연결되었을 때 메세지 출력
	 * 
	 * when the server starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port "
				+ getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called
	 * 
	 * Server가 중지되었을 때 출력
	 * 
	 * when the server stops listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		System.out.println("클라이언트가 연결되었습니다.");
	}

	@Override
	synchronized protected void clientException(ConnectionToClient client,
			Throwable exception) {
		System.out.println("클라이언트 연결이 해제되었습니다.");
	}

	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.println("클라이언트 연결을 해제하였습니다.");
	}

	@Override
	public void sendToAllClients(Object msg) {
		Thread[] clientThreadList = getClientConnections();
		for (int i = 0; i < clientThreadList.length; i++) {
			try {
				((ConnectionToClient) clientThreadList[i]).sendToClient(msg);

			} catch (Exception ex) {
			}
		}
	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the server instance (there
	 * is no UI in this phase).
	 *
	 * @param args
	 *            [0] The port number to listen on. Defaults to 5555 if no
	 *            argument is entered.
	 */

}
// End of EchoServer class
