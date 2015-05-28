package ServerClientConsole;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import objectSave.ObjectSaveSingleton;
import Account.Account;
import client.*;
import common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole
 * 
 * 이 Class 는 Display() 메소드를 이용하여 채팅 UI를 제공한다.
 * 주의: 여기있는 몇몇 코드들은 ServerConsole 에 Clone 으로 있다.
 * 
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ServerConsole implements ChatIF // ChatIF 인터페이스 호출
{
	static private Account accounts = ObjectSaveSingleton.getInstance().getAccounts();
	
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   * DEFAULT_PORT : 포트 번호, 이 번호로 커넥트
   */
  final public static int DEFAULT_PORT = 9000;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   * ChatClient 객체 생성
   */
  Server server;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * ClientConsole UI 객체(인스턴스) 생성
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ServerConsole(int port) // Server Console 생성자
  {
	  server = new Server(port, this);
		try 
		{
			server.listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients! port : " + port);
		}
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   * 
   * 이 메소드는 console 창에 입력할 때까지 대기한다. 
   * console 창에서의 입력 client의 message handler로 보낸다.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;
      
      while (true) // 무한 반복하면서 console 입력을 받아들임.
      {
        message = fromConsole.readLine();
       
        if(!message.startsWith("#"))
        {
        	server.sendToAllClients(message);
        }
        else
        {
        	if(message.equals("#quit"))
        	{
        		server.close();
        		System.exit(0);
        	}
        	else if(message.equals("#stop"))
        	{
        		server.stopListening();
        	}
        	else if(message.equals("#close"))
        	{
        		server.close();
        	}
        	else if(message.length() >= 8 && message.substring(0, 8).equals("#setport"))
        	{	
        		if(server.isListening())
        		{
        			System.err.println("서버를 멈춰 주시 바랍니다.");
        		}
        		else if(message.contains("<") && message.contains(">"))
        		{
        			server.setPort(Integer.parseInt(
        				message.substring(message.indexOf('<') + 1, message.indexOf('>'))));
        		}
        		else System.out.println("인자를 넣어 주시기 바랍니다.");
        	}
        	else if(message.equals("#start"))
        	{
        		if(server.isListening())
        		{
        			System.err.println("서버가 가동중입니다.");
        		}
        		else server.listen();
        	}
        	else if(message.equals("#getport"))
        	{
        		System.out.println(server.getPort());
        	}
          
         }
        
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   * 
   * Display()는 응답하는 메세지를 출력
   * 
   * @param message The string to be displayed.
   */
  
  public void display(String message) 
  {
	  System.out.println("SERVER MSG> " + message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   * 
   * 
   * 
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 9000
    }
	
    ServerConsole sv = new ServerConsole(port);
    sv.accept();
  }
}
//End of ConsoleChat class
