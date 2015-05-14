package ServerClientConsole;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import Account.Account;
import Assignment.Assignment;
import Assignment.Subject;
import QnA.Question;
import common.*;
import server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 * 
 * �씠 Class �뒗 湲곕뒫�꽦 �뼢�긽�쓣 �쐞�빐 AbstractServer瑜� override �븿.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class Server extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 9000;
 
  ChatIF chatUI;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   * 
   * @param port The port number to connect on.
   */
  public Server(int port, ChatIF chatUI) 
  {
    super(port);
	this.chatUI = chatUI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   * 
   * �씠 硫붿냼�뱶�뒗 client �뿉�꽌 諛쏆� 紐⑤뱺 硫붿꽭吏�瑜� �빖�뱾留� �븳�떎.
   * 
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * 
   * Client�� �뿰寃곕릺�뿀�쓣 �븣 硫붿꽭吏� 異쒕젰
   * 
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * 
   * Server媛� 以묒��릺�뿀�쓣 �븣 異쒕젰
   * 
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  @Override
  protected void clientConnected(ConnectionToClient client) {
	  System.out.println("�겢�씪�씠�뼵�듃媛� �뿰寃곕릺�뿀�뒿�땲�떎.");
  }
  
  @Override
  synchronized protected void clientException(
		    ConnectionToClient client, Throwable exception) {
	  System.out.println("�겢�씪�씠�뼵�듃 �뿰寃곗씠 �빐�젣�릺�뿀�뒿�땲�떎.");
  }
  
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println("�겢�씪�씠�뼵�듃 �뿰寃곗쓣 �빐�젣�븯���뒿�땲�떎.");
  }  
  
  @Override
  public void sendToAllClients(Object msg)
  {
    Thread[] clientThreadList = getClientConnections();
    chatUI.display(msg.toString());
    for (int i=0; i<clientThreadList.length; i++)
    {
      try
      {
        ((ConnectionToClient)clientThreadList[i]).sendToClient(msg);
        
      }
      catch (Exception ex) {}
    }
  }
  
  
  public void getAccount() {
	  
  }
  
  public void setAccount() {
	  
  }
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */

}
//End of EchoServer class
