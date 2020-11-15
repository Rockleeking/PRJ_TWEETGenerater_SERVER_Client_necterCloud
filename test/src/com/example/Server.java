package com.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class Server 
{
	static ArrayList<Integer> h=new ArrayList<Integer>();
	
    static final int ASCII_SIZE = 256; 
	static int bill=0;
	static Random rand = new Random();
	private static Socket serverClient;
	private static DataOutputStream out;
	private static DataInputStream dis;
	public Server(Socket inSocket) throws IOException {
		serverClient = inSocket;
		out=new DataOutputStream(serverClient.getOutputStream());
		dis=new DataInputStream( serverClient.getInputStream());
	}
	public static void main(String[] args) throws SQLException, InterruptedException, IOException 
	{
		Connection con= DriverManager.getConnection("jdbc:mysql:///mydb","root","root");
		menuCard();
	}
	protected static void menuCard() throws InterruptedException, SQLException, IOException 
	{
		out.writeUTF("------------------------------------------------------------------------");
		out.writeUTF("\n");
		out.writeUTF("         ACTION"+"                                "+"      PRICE\n");
		out.writeUTF("1. SEARCH TWEET FROM ID"+"                             "+"  50\n");
		out.writeUTF("2. NUMBER OF TWEETS CONTAINING SPECIFIC WORDS"+"       "+"  100\n");
		out.writeUTF("3. NUMBER OF TWEETS FOR A SPECIFIC AIRLINE"+"          "+"  100\n");
		out.writeUTF("4. MOST FREQUENT CHARACTER USED IN A SPECIFIC TWEET"+" "+"  80\n");
		out.writeUTF("5. GENERATE YOUR BILL\n");
		out.writeUTF("6. SEE STATUS OF YOUR QUERY\n");
		out.writeUTF("\n");
		out.writeUTF("-----------------------------------------------------------------------\n");
		out.writeUTF("\n");
		out.writeUTF("ENTER NUMBER 1-5 FOR SELECTING ANY ONE\n");
		int option=Integer.parseInt(dis.readUTF());
		out.writeUTF("The option you entered is: "+option+"\n");
		int min = 125;
	    int max = 1567;
	    int random =0;
	    switch(option)
		{
		case 1:
			out.writeUTF("\n");
			random=rand.nextInt(12345);
			out.writeUTF("YOUR QUERY ID IS:"+random+"\n");
			out.writeUTF("Enter the Tweet ID you want to display:\n");
			h.add(random);
			bill+=50;
			String id=dis.readUTF();
			displayTweetDet(id,random);
			againMenu();
			break;
		case 2:
			out.writeUTF("\n");
			random=rand.nextInt(12345);
			out.writeUTF("YOUR QUERY ID IS:"+random+"\n");
			h.add(random);
			out.writeUTF("Enter the Word and we will \nDisplay the number of tweets with that word:\n");
			bill+=100;
			String word=dis.readUTF();
			numberTweetDet(word,random);
			againMenu();
			break;
		case 3:
			out.writeUTF("\n");
			random=rand.nextInt(12345);
			out.writeUTF("YOUR QUERY ID IS:"+random+"\n");
			h.add(random);
			out.writeUTF("Enter the Airline and we will \nDisplay the number of tweets of that Airline:\n");
			bill+=100;
			String airline=dis.readUTF();
			numberAirlineDet(airline,random);
			againMenu();
			break;
		case 4:
			out.writeUTF("\n");

			random=rand.nextInt(12345);
			out.writeUTF("YOUR QUERY ID IS:"+random+"\n");
			h.add(random);
			out.writeUTF("Enter the Tweet ID and we will \nDisplay the most frequent character in that Tweet:\n");
			bill+=80;
			String id1=dis.readUTF();
			mostFreq(id1,random);
			againMenu();
			break;
		case 5:
			out.writeUTF("\n");
			out.writeUTF("YOUR BILL IS :"+bill+"\n");
			out.writeUTF("Your Query IDs are:\n");
			
			 for (Integer num : h) { 		      
		           System.out.print(num+"   "); 		
		      }
			out.writeUTF("\n");
			out.writeUTF("THANK YOU FOR VISITING ! :)\n");
			out.writeUTF("\n");

			out.writeUTF("----------------------------------------------------------------\n");
			break;
		case 6:

			out.writeUTF("\n");

			out.writeUTF("----------------------------------------------------------------\n");
			out.writeUTF("What do you want to do? Enter the number to choose:\n");
			out.writeUTF("1. EXECUTE THE QUERY\n 2. DELETE THE QUERY\n 3. SEE THE STATUS\n");
			int ch=Integer.parseInt(dis.readUTF());
			out.writeUTF("Enter the Query ID:\n");
			int qid=Integer.parseInt(dis.readUTF());
			statusQuery(ch,qid);
			out.writeUTF("----------------------------------------------------------------\n");
			againMenu();
			break;
			
		default:
			out.writeUTF("\n");
			out.writeUTF("YOUR BILL IS :"+bill+"\n");
			out.writeUTF("Your Query IDs are:\n");
			
			 for (Integer num : h) { 		      
		           out.writeUTF(num+"   "); 		
		      }
			out.writeUTF("\n");
			out.writeUTF("THANK YOU FOR VISITING ! :)\n");
			out.writeUTF("\n");

			out.writeUTF("----------------------------------------------------------------\n");
			break;
		}
	}
	private static void statusQuery(int ch, int qid){
		Connection con;
		try
		{

			con = DriverManager.getConnection("jdbc:mysql:///mydb","root","root");
			switch(ch)
			{
			case 1:

				Statement st= con.createStatement();
				ResultSet rs1= st.executeQuery("select count(*) from query where query_id="+qid+";");
					if(rs1.next())
					{
						int res=rs1.getInt(1);
						if(res==1)
						{
							out.writeUTF("Correct Query-id Entered!\n");
							out.writeUTF("Executing query!\n");
							ResultSet rs4=st.executeQuery("select result from query where query_id="+qid+";");
							if(rs4.next())
							{
								String result=rs4.getString(1);
								out.writeUTF("RESULT:  "+result+"\n");
							}
						}
						else
						{
							out.writeUTF("INCORRECT QUERY ID ENTERED!!\n");
							againMenu();
						}
					}
					else
					{
						
					}
			case 2:

				Statement st1= con.createStatement();
				ResultSet rs2= st1.executeQuery("select count(*) from query where query_id="+qid+";");
				
				if(rs2.next())
				{
					int res=rs2.getInt(1);
					if(res==1)
					{
						out.writeUTF("Correct Query-id Entered!\n");
						out.writeUTF("Wait Until we Delete the Query!\n");
						PreparedStatement ps= con.prepareStatement("delete from query where query_id="+qid+";");
						int i=ps.executeUpdate();
						if(i!=0)
						{
							out.writeUTF("QUERY ID "+qid+" IS DELETED\n");
						}
					}
					else
					{
						out.writeUTF("INCORRECT QUERY ID ENTERED!!\n");
						againMenu();
					}
				}
				else
				{
					
				}
			case 3:

				Statement st3= con.createStatement();
				ResultSet rs3= st3.executeQuery("select count(*) from query where query_id="+qid+";");
				
			if(rs3.next())
			{
				int res=rs3.getInt(1);
				if(res==1)
				{
					out.writeUTF("Correct Query-id Entered!\n");
					out.writeUTF("Wait Until We Give the Status the Query!\n");
					ResultSet rsa=st3.executeQuery("select status from query where query_id="+qid+";");
					if(rsa.next())
					{
						int result=rsa.getInt(1);
						if(result==1)
							out.writeUTF("The statement has been executed already.\n");
						else
							out.writeUTF("The statement is under processing!\n");
						out.writeUTF("RESULT:  "+result+"\n");
					}				
				}
				else
				{
					out.writeUTF("INCORRECT QUERY ID ENTERED!!\n");
					againMenu();
				}
			}
			else
			{
				
			}
				out.writeUTF("\n");
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private static void numberAirlineDet(String airline, int random) throws IOException 
	{
		
		out.writeUTF("\n");
		out.writeUTF("Airline Entered:"+airline+"\n'");
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql:///mydb","root","root");
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery("select count(*) from tweets where airline like '%"+airline+"%';");
			if (rs.next())  
			{
				out.writeUTF("Frequency of the Airline is:"+rs.getString(1));  
				PreparedStatement ps= con.prepareStatement("insert into query values(?,?,"+1+")");
				ps.setInt(1, random);
				ps.setString(2, rs.getString(1));
				ps.executeUpdate();
				rs.close();
			}
			else  
			{
				out.writeUTF("Invalid Tweet ID Entered\n");
				out.writeUTF("ENTER AGAIN!!\n");
				menuCard();
			}
			
			con.close();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	private static void mostFreq(String id1, int random) throws IOException 
	{
		out.writeUTF("Tweet ID:"+id1+"\n");
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql:///mydb","root","root");
			
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery("select text_tweet from minitweets1 where tweet_id='"+id1+"'");
			if (rs.next())  
			{
		
				String tweet=rs.getString(1);  
				char ch=getMaxOccuringChar(tweet);
				out.writeUTF("The maximum occurring character is:"+ch);
				PreparedStatement ps= con.prepareStatement("insert into query values(?,?,"+1+")");
				ps.setInt(1, random);
				ps.setString(2,Character.toString(ch));
				ps.executeUpdate();
				rs.close();
			}
			else  
			{
				
				ResultSet rs1= st.executeQuery("select tweet_id,text_tweet,airline,name_airline from tweets where tweet_id='"+id1+"'");
				if(rs1.next())
				{
					PreparedStatement ps= con.prepareStatement("insert into minitweets1(tweet_id,text_tweet,airline,name_tweet) values(?,?,?,?)");
					ps.setString(1, rs1.getString(1));
					ps.setString(2, rs1.getString(2));
					ps.setString(3, rs1.getString(3));
					ps.setString(4, rs1.getString(4));
					rs1.close();
					int i=ps.executeUpdate();
					if(i!=0)
					{
						ResultSet rs2= st.executeQuery("select text_tweet from minitweets1 where tweet_id='"+id1+"'");
						if (rs2.next())  
						{
				
							String str=rs2.getString(1);  
							char ch=getMaxOccuringChar(str);
							out.writeUTF("The maximum occurring character is:"+ch+"\n"); 
							PreparedStatement ps3= con.prepareStatement("insert into query values(?,?,"+1+")");
							ps3.setInt(1, random);
							ps3.setString(2,Character.toString(ch));
							ps3.executeUpdate();
							rs2.close();
						}
					}
					else
					{
						out.writeUTF("INVALID TWEET ID ENTERED!!\n");
					}
					
				}
				else
				{
					out.writeUTF("Invalid Tweet ID Entered\n");
					out.writeUTF("ENTER AGAIN!!\n");
					menuCard();
				}
				con.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

		
	public static char getMaxOccuringChar(String str) 
    { 
		str = str.replaceAll("\\s", ""); 
		HashMap<Integer, Integer> freq = new HashMap<Integer, Integer>();
		for(int i = 0; i < str.length(); i ++) {
			int key = (int)str.charAt(i);
			if(freq.containsKey(key)) {
                freq.put(key, freq.get(key) + 1);
            }
            else {
                freq.put(key, 1);
            }
		}
		int maxfreq = Integer.MIN_VALUE;
		char maxfreqchar = ' ';
		for(int i = 0; i < str.length(); i ++) {
			int key = (int)str.charAt(i);
			int frequency = freq.get(key);
			if(frequency > maxfreq) {
				maxfreq = frequency;
				maxfreqchar = str.charAt(i);
			}
		}
		return maxfreqchar;
	}
      
	private static void numberTweetDet(String word, int random) throws IOException 
	{
		out.writeUTF("\n");
		out.writeUTF("Word Entered:"+word+"\n");
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql:///mydb","root","root");
			
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery("select count(*) from tweets where text_tweet like '%"+word+"%';");
			if (rs.next())  
			{
				out.writeUTF("Frequency of the word is:"+rs.getString(1));  
				PreparedStatement ps= con.prepareStatement("insert into query values(?,?,"+1+")");
				ps.setInt(1, random);
				ps.setString(2,rs.getString(1));
				ps.executeUpdate();
				
				rs.close();
			}
			else  
			{
				out.writeUTF("Invalid Tweet ID Entered\n");
				out.writeUTF("ENTER AGAIN!!\n");
				menuCard();
			}
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		
	}
	private static void againMenu() throws InterruptedException, IOException {
		out.writeUTF("\n");
		out.writeUTF("-----------------------------------------------------------");
		out.writeUTF("Do you want to run any other query?\n Enter 1 for YES, 2 for NO:\n");
		int op=Integer.parseInt(dis.readUTF());
		
		if(op==2)
		{
			out.writeUTF("\n");
			out.writeUTF("YOUR BILL IS :"+bill);
			out.writeUTF("Your Query IDs are:");
			
			 for (Integer num : h) { 		      
		           System.out.print(num+"   "); 		
		      }
			out.writeUTF("THANK YOU FOR VISITING ! :)");
			out.writeUTF("\n");

			out.writeUTF("----------------------------------------------------------------");
			}
		else {
			
		}
		
	}
	private static void displayTweetDet(String id, int random) throws IOException {
		out.writeUTF("Tweet ID:"+id+"\n");
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql:///mydb","root","root");
			
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery("select text_tweet from minitweets1 where tweet_id='"+id+"'");
			if (rs.next())  
			{
				out.writeUTF("Tweet is:"+rs.getString(1)+"\n");  
				PreparedStatement ps= con.prepareStatement("insert into query values(?,?,"+1+")");
				ps.setInt(1, random);
				ps.setString(2,rs.getString(1));
				ps.executeUpdate();
				
				rs.close();
			}
			else  
			{
				ResultSet rs1= st.executeQuery("select tweet_id,text_tweet,airline,name_airline from tweets where tweet_id='"+id+"'");
				if(rs1.next())
				{
					PreparedStatement ps= con.prepareStatement("insert into minitweets1(tweet_id,text_tweet,airline,name_tweet) values(?,?,?,?)");
					ps.setString(1, rs1.getString(1));
					ps.setString(2, rs1.getString(2));
					ps.setString(3, rs1.getString(3));
					ps.setString(4, rs1.getString(4));
					rs1.close();
					int i=ps.executeUpdate();
					if(i!=0)
					{
						ResultSet rs2= st.executeQuery("select text_tweet from minitweets1 where tweet_id='"+id+"'");
						if (rs2.next())  
						{
							out.writeUTF("Tweet is:"+rs2.getString(1)+"\n");  
							PreparedStatement ps2= con.prepareStatement("insert into query values(?,?,"+1+")");
							ps2.setInt(1, random);
							ps2.setString(2,rs2.getString(1));
							ps2.executeUpdate();
							
						}
						rs2.close();
					}
					else
					{
						out.writeUTF("INVALID TWEET ID ENTERED!!\n");
					}
					
				}
				else
				{
					out.writeUTF("Invalid Tweet ID Entered\n");
					out.writeUTF("ENTER AGAIN!!\n");
					menuCard();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected static void loginOrReg() throws InterruptedException 
	{
		
		try {
			out.writeUTF("Welcome to the server\n");
		out.writeUTF("----------------------------------------------------------------\n");
		out.writeUTF("ENTER 1 FOR LOGIN\n2 FOR REGISTRATION:\n");
		int option=Integer.parseInt(dis.readUTF());
		switch(option)
		{
		case 1:
			out.writeUTF("--------------------------------------------------\n");
			out.writeUTF("USER LOGIN\n");
			login();
			break;
		case 2: 
			out.writeUTF("--------------------------------------------------\n");
			out.writeUTF("USER REGISTRATION\n");
			register();
			break;
		default:
			out.writeUTF("ENTER A VALID OPTION\n");
			out.writeUTF("--------------------------------------------------\n");
			loginOrReg();
			break;
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void register() throws InterruptedException, IOException {
		out.writeUTF("Enter First Name:\n");
		String fname=dis.readUTF();
		out.writeUTF("Enter Last Name:\n");
		String lname=dis.readUTF();
		out.writeUTF("Enter your Email Address:\n");
		String mail=dis.readUTF();
		out.writeUTF("Enter your Age:\n");
		int age=Integer.parseInt(dis.readUTF());
		out.writeUTF("Set your Password:\n");
		String pass=dis.readUTF();
		out.writeUTF("Re-type your Password:\n");
		String pass1=dis.readUTF();
		if(pass.equals(pass1))
		{
			Connection con;
			try 
			{
				con = DriverManager.getConnection("jdbc:mysql:///mydb","root","root");
				PreparedStatement ps= con.prepareStatement("insert into users(firstname,lastname,email,age,pass) values(?,?,?,?,?)");
				ps.setString(1, fname);
				ps.setString(2, fname);
				ps.setString(3, mail);
				ps.setInt(4, age);
				ps.setString(5, pass);
				int i=ps.executeUpdate();
				if(i!=0)
				{
					out.writeUTF("Saved Successfully\n");
					Statement st= con.createStatement();
					ResultSet rs= st.executeQuery("select id from users where email='"+mail+"'");
					while(rs.next())
					{
						out.writeUTF("Your UserID is:"+rs.getInt(1)+"\n");
						out.writeUTF("REGISTRATION SUCCESSFUL!!\n");
						out.writeUTF("--------------------------------------------------\n");
						loginOrReg();

					}
					
				}
				else
					out.writeUTF("REGISTRATION UNSUCCESSFUL\n");
				
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}	
			
		}
		else
		{
			out.writeUTF("The passwords did not match!! Enter again!!\n");
			out.writeUTF("\n");
			register();
		}
		
	}

	private static void login() throws InterruptedException, IOException {
		out.writeUTF("Enter your USER-ID\n");
		
		int id=Integer.parseInt(dis.readUTF());
		out.writeUTF("Enter your password:\n");
		String pass= dis.readUTF();
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql:///mydb","root","root");
			
			Statement st= con.createStatement();
			ResultSet rs= st.executeQuery("select * from users where id="+id+" and pass='"+pass+"'");
			if (rs.next())  
			{
				out.writeUTF("\n");
				out.writeUTF("Welcome::: "+id+"\n");  
				out.writeUTF("\n");
				Server r=new Server(serverClient);
				out.writeUTF("-------------------------------------------------------------\n");
				out.writeUTF("   REDIRECTING TO MENU CARD\n");
				out.writeUTF("-------------------------------------------------------------\n");
				Server.menuCard();
			}
			else  
			{
				out.writeUTF("Invalid user name and password\n");  
				out.writeUTF("Login Again!!\n");
				login();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
