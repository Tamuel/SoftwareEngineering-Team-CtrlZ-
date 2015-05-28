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
 * �� Class �� Display() �޼ҵ带 �̿��Ͽ� ä�� UI�� �����Ѵ�.
 * ����: �����ִ� ��� �ڵ���� ServerConsole �� Clone ���� �ִ�.
 * 
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ServerConsole implements ChatIF // ChatIF �������̽� ȣ��
{
	static private Account accounts = ObjectSaveSingleton.getInstance().getAccounts();
	
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   * DEFAULT_PORT : ��Ʈ ��ȣ, �� ��ȣ�� Ŀ��Ʈ
   */
  final public static int DEFAULT_PORT = 9000;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   * ChatClient ��ü ����
   */
  Server server;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * ClientConsole UI ��ü(�ν��Ͻ�) ����
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ServerConsole(int port) // Server Console ������
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
   * �� �޼ҵ�� console â�� �Է��� ������ ����Ѵ�. 
   * console â������ �Է� client�� message handler�� ������.
   */
  public void accept() 
  {
    try
    {
      BufferedReader fromConsole = 
        new BufferedReader(new InputStreamReader(System.in));
      String message;
      
      while (true) // ���� �ݺ��ϸ鼭 console �Է��� �޾Ƶ���.
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
        			System.err.println("������ ���� �ֽ� �ٶ��ϴ�.");
        		}
        		else if(message.contains("<") && message.contains(">"))
        		{
        			server.setPort(Integer.parseInt(
        				message.substring(message.indexOf('<') + 1, message.indexOf('>'))));
        		}
        		else System.out.println("���ڸ� �־� �ֽñ� �ٶ��ϴ�.");
        	}
        	else if(message.equals("#start"))
        	{
        		if(server.isListening())
        		{
        			System.err.println("������ �������Դϴ�.");
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
   * Display()�� �����ϴ� �޼����� ���
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
