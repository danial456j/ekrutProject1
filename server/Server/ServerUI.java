package Server;

import javafx.application.Application;
import javafx.stage.Stage;
import jdbc.DBController;
import logic.Client;
import logic.Subscriber;
import serverGUI.ServerPortFrameController;

import java.util.ArrayList;
import java.util.Vector;
//import gui.ServerPortFrameController;
import Server.EchoServer;

public class ServerUI extends Application {
	final public static int DEFAULT_PORT = 5555;
	public static Vector<Subscriber> subscribers=new Vector<Subscriber>();
	

	public static void main( String args[] ) throws Exception
	   {   
		 launch(args);
	  } // end main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub				  		
		ServerPortFrameController aFrame = new ServerPortFrameController(); // create StudentFrame
		 
		aFrame.start(primaryStage);
	}
	public static  EchoServer sv;
	public static void runServer(String p)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }
	        catch(Throwable t)
	        {
	        	
	        	System.out.println("ERROR - Could not connect!");
	        	DBController.msgFromDb+="\nServer-ERROR - Could not connect!";
	        }
	    	
	         sv = new EchoServer(port);
	        
	        try 
	        {
	          sv.listen(); //Start listening for connections
	          DBController.msgFromDb+="\nserver is listening to clients";
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	          DBController.msgFromDb+="\nServer- Could not listen for clients!";
	        }
	}
	public void closeServer() {
		 sv.stopListening();
		 DBController.msgFromDb="server stopped listening to clients";
	}
}
