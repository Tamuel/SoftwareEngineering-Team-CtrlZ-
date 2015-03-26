package ServerClientConsole;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import common.*;

import server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 * 
 * 이 Class 는 기능성 향상을 위해 AbstractServer를 override 함.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
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
  public EchoServer(int port, ChatIF chatUI) 
  {
    super(port);
	this.chatUI = chatUI;
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   * 
   * 이 메소드는 client 에서 받은 모든 메세지를 핸들링 한다.
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
   * Client와 연결되었을 때 메세지 출력
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
   * Server가 중지되었을 때 출력
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
	  System.out.println("클라이언트가 연결되었습니다.");
  }
  
  @Override
  synchronized protected void clientException(
		    ConnectionToClient client, Throwable exception) {
	  System.out.println("클라이언트 연결이 해제되었습니다.");
  }
  
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println("클라이언트 연결을 해제하였습니다.");
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
