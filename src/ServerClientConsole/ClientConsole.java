package ServerClientConsole;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import GUIFrame.LoginFrame;
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
public class ClientConsole implements ChatIF // ChatIF �������̽� ȣ��
{
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
  public static Client client;

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * ClientConsole UI ��ü(�ν��Ͻ�) ����
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  
  public ClientConsole(String host, int port) // Client Console ������
  {
    try 
    {
      client = new Client(host, port, this);
    } 
    catch(IOException exception)
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
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
        	client.handleMessageFromClientUI(message);
        }
        else if(message.equals("#quit"))
    	{	
    		client.quit();
    		System.exit(0);
    	}
    	else if(message.equals("#login"))
    	{
    		if(client.isConnected())
    		{
    			System.err.println("logoff ���ֽñ� �ٶ��ϴ�.");
    		}
    		else
    		{
    			System.out.println("������ �����մϴ�.");
    			client = new Client(client.getHost(), client.getPort(), this);
    		}
    	}
    	else if(message.equals("#logoff"))
    	{	
    		client.quit();
    	}
    	else if(message.length() >= 8 && message.substring(0, 8).equals("#sethost"))
    	{	
    		if(client.isConnected())
    		{
    			System.err.println("logoff ���ֽñ� �ٶ��ϴ�.");
    		}
    		else if(message.contains("<") && message.contains(">"))
    		{
    			client.setHost(message.substring(message.indexOf('<') + 1, message.indexOf('>')));
    		}
    		else System.out.println("���ڸ� �־� �ֽñ� �ٶ��ϴ�.");
    	}
    	else if(message.length() >= 8 && message.substring(0, 8).equals("#setport"))
    	{	
    		if(client.isConnected())
    		{
    			System.err.println("logoff ���ֽñ� �ٶ��ϴ�.");
    		}
    		else if(message.contains("<") && message.contains(">"))
    		{
    			client.setPort(Integer.parseInt(
    				message.substring(message.indexOf('<') + 1, message.indexOf('>'))));
    		}
    		else System.out.println("���ڸ� �־� �ֽñ� �ٶ��ϴ�.");
    	}
    	else if(message.equals("#gethost"))
    	{	
    		System.out.println(client.getHost());
    	}
    	else if(message.equals("#getport"))
    	{	
    		System.out.println(client.getPort());
    	}
    	else client.handleMessageFromClientUI(message);
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
	String host = "localhost";
	int port = DEFAULT_PORT;
	
	LoginFrame login = new LoginFrame("Login", 300, 200);
    ClientConsole chat= new ClientConsole(host, port);
    chat.accept();  //Wait for console data
  }
}
//End of ConsoleChat class
