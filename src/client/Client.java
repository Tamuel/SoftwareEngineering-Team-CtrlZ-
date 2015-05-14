// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import Account.Account;
import client.*;
import common.*;

import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class Client extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI;
  Account account;

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * ChatClient 媛앹껜 �깮�꽦.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public Client(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   * 
   * �꽌踰꾩뿉�꽌 硫붿꽭吏�瑜� 諛쏆븘�뱾�씤�떎.
   * 
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * Client UI�뿉�꽌 硫붿꽭吏�瑜� handling �븳�떎.
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      sendToServer(message); // Server濡� message �쟾�넚
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  @Override
  protected void connectionClosed() {
	  System.out.println("�꽌踰꾩� �뿰寃곗쓣 �걡�뿀�뒿�땲�떎.");
  }

  @Override
  protected void connectionException(Exception exception) {
	  System.out.println("�꽌踰꾧� �뿰寃곗쓣 �걡�뿀�뒿�땲�떎.");
	  System.exit(1);
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
  }
  
  public Account getAccount() {
	  return account;
  }
  
  public void setAccount(Account account) {
	  this.account = account;
  }
}
//End of ChatClient class
