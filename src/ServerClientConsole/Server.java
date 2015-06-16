package ServerClientConsole;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import objectSave.ObjectSaveSingleton;
import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import Assignment.Notice;
import Assignment.Subject;
import Controller.AccountController;
import Controller.ProfessorAccountController;
import GUIFrame.SubjectSelectFrame;
import common.*;
import server.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 * 
 * �� Class �� ��ɼ� ����� ���� AbstractServer�� override ��.
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
	 * �� �޼ҵ�� client ���� ���� ��� �޼����� �ڵ鸵 �Ѵ�.
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
		AccountController aCon = new AccountController(accounts);
		
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
			String subString = "��� ���� (������ �л��Դϴ�)";
			System.out.println("Search Account for new account " + proc.getID() + " " + proc.getPW());
			
			/* ������ ���� ���� */
			if(aCon.checkIdRepeated(proc.getID()) && !proc.getSubject().equals(subString)) {
				Subject newSubject = new Subject(proc.getSubject());
				ProfessorAccount temp = new ProfessorAccount(proc.getID(), proc.getPW(), proc.getName(), newSubject);
				accounts.addAccount(temp);
				ObjectSaveSingleton.getInstance().saveAccounts();
				this.sendToClient(client, new Protocol(ProtocolType.JOIN_ACCEPT, temp));
			}
			else if(aCon.checkIdRepeated(proc.getID()) && proc.getSubject().equals(subString)) { /* �л� ���� ���� */
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
			ProfessorAccountController pCon = new ProfessorAccountController((ProfessorAccount)account);
			pCon.makeAssignment(proc.getTopic(), proc.getContent(), proc.getYear(), proc.getMonth(), proc.getDay(), proc.getHour());
			ObjectSaveSingleton.getInstance().saveAccounts();
			
			/* ��� Ŭ���̾�Ʈ���� ���� */
			this.sendToAllClients(new Protocol(ProtocolType.MAKE_ASSIGNMENT_REFRESH, ((ProfessorAccount)account).getSubject().getName()));
			
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
	 * Client�� ����Ǿ��� �� �޼��� ���
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
	 * Server�� �����Ǿ��� �� ���
	 * 
	 * when the server stops listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		System.out.println("Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");
	}

	@Override
	synchronized protected void clientException(ConnectionToClient client,
			Throwable exception) {
		System.out.println("Ŭ���̾�Ʈ ������ �����Ǿ����ϴ�.");
	}

	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.println("Ŭ���̾�Ʈ ������ �����Ͽ����ϴ�.");
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
