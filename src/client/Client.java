// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import Account.Account;
import Account.ProfessorAccount;
import Account.StudentAccount;
import GUIFrame.LoginFrame;
import ServerClientConsole.ClientConsole;
import ServerClientConsole.Protocol;
import client.*;
import common.*;

import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class Client extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	private Account account = null;
	private Account accountList = null;
	

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * ChatClient 객체 생성.
	 *
	 * @param host
	 *            The server to connect to.
	 * @param port
	 *            The port number to connect on.
	 * @param clientUI
	 *            The interface type variable.
	 */

	public Client(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 * 
	 * 서버에서 메세지를 받아들인다.
	 * 
	 * @param msg
	 *            The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		System.out.print("Receive from server ");
		Protocol proc = (Protocol)msg;
		System.out.println(proc.getProcKind() + " " + proc.getData());
		setMsgReceive(true);
		
		switch(proc.getProcKind()) {
		case LOGIN_ACCEPT:
			setAccount((Account)proc.getData());
			System.out.println("계좌가 존재합니다 " + account.getName());
			break;
			
		case LOGIN_FAIL:
			setAccount(null);
			System.out.println("로그인 실패");
			break;
			
		case JOIN_ACCEPT:
			setAccount((Account)proc.getData());
			System.out.println("계좌를 생성했습니다 " + account.getName());
			break;
			
		case ACCOUNT_LIST:
			setAccountList((Account)proc.getData());
			System.out.println("계좌 리스트를 가져왔습니다");
			break;
			
		case SET_REFRESH:
			if(account.isStudent())
			{
				for(int i = 0; i < ((StudentAccount)account).getSubjects().size(); i++)
					if(((StudentAccount)account).getSubjects().get(i).getName().equals(proc.getSubject()))
					{
						try {
							sendToServer(ProtocolType.NEED_REFRESH, account.getId());
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					}
			}
			else
			{
				if(((ProfessorAccount)account).getSubject().getName().equals(proc.getSubject()))
				{
					try {
						sendToServer(ProtocolType.NEED_REFRESH, account.getId());
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
			break;
			
		case REFRESH:
			setAccount((Account)proc.getData());
			System.out.println("계좌를 새로고침 했습니다 ");
			break;
			
		case ID_EXIST:
			break;
			
		default:
			break;
		}
		
		System.out.println("# of Notice : " + account.getNotices().size());
	}
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccountList() {
		return accountList;
	}

	public void setAccountList(Account accountList) {
		this.accountList = accountList;
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * Client UI에서 메세지를 handling 한다.
	 *
	 * @param message
	 *            The message from the UI.
	 */
	public void handleMessageFromClientUI(String message) {
		try {
			sendToServer(ProtocolType.ELSE, message); // Server로 message 전송
		} catch (IOException e) {
			clientUI.display("Could not send message to server.  Terminating client.");
			quit();
		}
	}

	@Override
	protected void connectionClosed() {
		System.out.println("서버와 연결을 끊었습니다.");
	}

	@Override
	protected void connectionException(Exception exception) {
		System.out.println("서버가 연결을 끊었습니다.");
		System.exit(1);
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
	}
}
// End of ChatClient class
