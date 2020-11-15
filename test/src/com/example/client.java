package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

public class Client 
{
	static Scanner sc=new Scanner(System.in);
	
	public static void main(String[] args) throws ClassNotFoundException,SQLException, InterruptedException
	{
		try{
			//INITILISE THE SERVER SOCKET CONNECTION 
		      ServerSocket server=new ServerSocket(9098);
		      //COUNTER FOR NUMBER OF CLIENT CONNECTION
		      int counter=0;
		      System.out.println("Server Started ....");
		      while(true){
		        //ACCEPT CONNECTION
		        Socket serverClient=server.accept();  //server accept the client connection request
		        System.out.print("Client accepted");
		        //INITIALISE AND START SERVER THEREAD TO HANDLE CLIENT REQUEST
		        Server r=new Server(serverClient);
				r.loginOrReg();
		      }
		    }//illegal argument exceptions 
	    catch(IllegalArgumentException iae){
    		System.out.println("The argument from commandline is proucing the following error message:" +iae.getMessage());
    	}
    //socket and network exceptions
    catch(SocketException ce){
    		System.out.println("There is socket error and is proucing the following error message:" +ce.getMessage());
    	}
    //input/output exceptions
    catch(IOException ipe){
    		System.out.println("There is Input/output error and is proucing the following error message:" +ipe.getMessage());
    	}
    //other exceptions
    catch(Exception ex){
      System.out.println("This error says:" +ex.getMessage());
      
    }

		
	}

}
