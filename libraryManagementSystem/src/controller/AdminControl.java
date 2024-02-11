package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.DataBase;

public class AdminControl {

	
	
	Scanner scanner=new Scanner(System.in);
	public boolean adminLoginCredentials(Connection connection,String userName, String password,String check) throws SQLException 
	{
		int userId=0;
  
		if(check=="Admin")
		{
		String query="SELECT * FROM Admin_details WHERE Admin_username=? AND password=?";
		
		PreparedStatement preaparedStatement=connection.prepareStatement(query);
		preaparedStatement.setString(1,userName);
		preaparedStatement.setString(2,password);
		
		ResultSet resultSet=preaparedStatement.executeQuery();
		return resultSet.next();
		}
		else
		{
			String query="SELECT * FROM User_table WHERE User_name=? AND Password=?";
			
			PreparedStatement preaparedStatement=connection.prepareStatement(query);
			preaparedStatement.setString(1,userName);
			preaparedStatement.setString(2,password);
			
			
			ResultSet resultSet=preaparedStatement.executeQuery();
			return resultSet.next();
			
			
			
		}
	}

	public boolean checkUserName(Connection connection,String userName) throws SQLException {
		
		String query="SELECT User_name from User_table WHERE User_name=?";
		PreparedStatement preaparedStatement=connection.prepareStatement(query);
		preaparedStatement.setString(1,userName);
		
		ResultSet resultSet=preaparedStatement.executeQuery();
		return resultSet.next();
		
		
	}

	public void addUser(Connection connection,String name, String userName, String password, String mobileNumber, String address,
			String mailId) throws SQLException {
		
		 String query = "INSERT INTO User_table (name, User_name, Password,Phonenumber, Address, Mailid) VALUES (?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStatement=connection.prepareStatement(query);
	        preparedStatement = connection.prepareStatement(query);

	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, userName);
	        preparedStatement.setString(3, password);
	        preparedStatement.setString(4, mobileNumber);
	        preparedStatement.setString(5, address);
	        preparedStatement.setString(6, mailId);

	        preparedStatement.executeUpdate();
	        
	        String idQuery = "SELECT UserId FROM User_table WHERE User_name=?";
	        PreparedStatement preparedStatement1 = connection.prepareStatement(idQuery);
	        preparedStatement1.setString(1, userName);
	        ResultSet resultSet = preparedStatement1.executeQuery();
	        while (resultSet.next()) 
	        {
	            
	            int userId = resultSet.getInt("UserId");

	            
	            System.out.println("Your Library Cardnumber is: " + userId);
	           
	        }
	}
	public String encryption(String password) {
		String answer ="";
		int k=1;
	    for(char c:password.toCharArray())
	    {
	        k=k%26;
	        int i = c;
	        if(i>=97&&i<=122)
	        {
	        if(i+k<=122)
	        {
	            answer+=(char)(i+k);
	        }
	        else
	        {
	            answer+=(char)(i-(26-k));
	        }
	        }
	        else if(i>=65&&i<=90)
	    {
	        if(i+k<=90)
	        {
	            answer+=(char)(i+k);
	        }else
	        {
	            answer+=(char)(i-(26-k));
	        }
	    }
	        else if(i>='0' && i<='8')
	        {
	        	answer+=(char)(i+k);
	        }
	        else if(i=='9')
	        {
	        	answer+=(char)('0');
	    }
	        else
	        {
	        	answer+=c;
	        }
	    
	    }
		return answer;
		
	}

	public boolean findBookId(int bookId) throws SQLException {
		String query = "SELECT BookId FROM Books_List WHERE BookId=?";
		PreparedStatement preparedStatement = DataBase.DataBaseConnectivity().prepareStatement(query);
		preparedStatement.setInt(1, bookId);

		ResultSet resultSet = preparedStatement.executeQuery();
		return resultSet.next();
	}
	public boolean isValidMobileNumber(String mobileNumber) {
		String pattern = "^[0-9]{10}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(mobileNumber);

        return matcher.matches();
	}
	public boolean isStrongPassword(String password) {
		String pattern = "^(?=.*[A-Z])(?=.*[@$!%*?&]).{8,}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(password);

        return matcher.matches();
	}
	public int findUserId(String userName) throws SQLException {
		int userId=0;
		String nameQuery="SELECT * FROM User_table WHERE User_name=?";
		PreparedStatement preaparedStatementName=DataBase.DataBaseConnectivity().prepareStatement(nameQuery);
		preaparedStatementName.setString(1,userName);
		ResultSet resultSet2=preaparedStatementName.executeQuery();
		if (resultSet2.next()) {
		       userId=resultSet2.getInt("UserId");
		    }
		
		return userId;
	}

	public void changeCredentials(String userName) throws SQLException {
		
		int choice=0;
		int userId=findUserId(userName);
		do
		{
			System.out.println("Which one you want to change?");
			System.out.println("1.User name");
			System.out.println("2.Password");
			System.out.println("3.Mobile number");
			System.out.println("4.Addrress");
			System.out.println("5.Mail ID");
			System.out.println("6.Exit");
			
			choice=scanner.nextInt();
			scanner.nextLine();
			switch(choice)
			{
			case 1:
				boolean isAlreadyExists=false;
				do
				{
					System.out.print("Enter User name:");
					String newUserName=scanner.nextLine();
					String query="SELECT User_name FROM User_table WHERE User_name=?";
					 PreparedStatement preparedStatement= DataBase.DataBaseConnectivity().prepareStatement(query);
				        preparedStatement.setString(1,newUserName);
				        
				        ResultSet resultSet = preparedStatement.executeQuery();
				      // System.out.println(resultSet.next());
				        if(resultSet.next())
				        {
				        	System.out.print("User name already exists..");
				        	System.out.println();
				        	isAlreadyExists=true;
				        }
				        else
				        {
				        	String updateQuery="UPDATE User_table SET User_name=? WHERE UserId=?";
				        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				        	 preparedStatement1.setString(1,newUserName);
				        	 preparedStatement1.setInt(2, userId);
				        	 
				        	 preparedStatement1.executeUpdate();
				        	 System.out.println("User name sucessfully updated");
				        	
				        }
					
				}while(isAlreadyExists==true);
				break;
			case 2:
				boolean isAlreadyPasswordExists=false;
				do
				{
					
					
					boolean isPasswordValid=false;
					do {
						
					
					System.out.print("Enter new password");
					String newPassword=scanner.nextLine();
					
					if(isStrongPassword(newPassword)==false)
					{
						System.out.println("Password is not strong enough try again");
						isPasswordValid=true;
					}
					else
					{
						newPassword=encryption(newPassword);
						//System.out.println(newPassword);
					String query="SELECT User_name FROM User_table WHERE Password=?";
					 PreparedStatement preparedStatement= DataBase.DataBaseConnectivity().prepareStatement(query);
				        preparedStatement.setString(1,newPassword);
				        
				        ResultSet resultSet = preparedStatement.executeQuery();
				        System.out.println(resultSet.next());
				        if(resultSet.next())
				        {
				        	 System.out.print("Password already exists..");
					        	System.out.println();
					        	isAlreadyPasswordExists=true;
				        }
				        else
				        {
				        	//newPassword=encryption(newPassword);
				        	
				  
					        	String updateQuery="UPDATE User_table SET Password=? WHERE UserId=?";
					        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
					        	 preparedStatement1.setString(1,newPassword);
					        	 preparedStatement1.setInt(2, userId);
					        	 
					        	 preparedStatement1.executeUpdate();
					        	 System.out.println("Password sucessfully updated");
				        }
					}
					}while(isPasswordValid==true);
					
				}while(isAlreadyPasswordExists==true);
				break;
			case 3:
				boolean isAlreadyNumberExists=false;
				do
				{
					
					
					boolean isNumberValid=false;
					do {
						
					
					System.out.print("Enter mobile number");
					String newNumber=scanner.nextLine();
					
					if(isValidMobileNumber(newNumber)==false)
					{
						System.out.println("Mobile number is not valid");
						isNumberValid=true;
					}
					else
					{
						
					String query="SELECT User_name FROM User_table WHERE Phonenumber=?";
					 PreparedStatement preparedStatement= DataBase.DataBaseConnectivity().prepareStatement(query);
				        preparedStatement.setString(1,newNumber);
				        
				        ResultSet resultSet = preparedStatement.executeQuery();
				      
				        if(resultSet.next())
				        {
				        	System.out.print("Number already exists..");
				        	System.out.println();
				        	isAlreadyNumberExists=true;
				        }
				        else
				        {
				        	//newPassword=encryption(newPassword);
				        	String updateQuery="UPDATE User_table SET Phonenumber=? WHERE UserId=?";
				        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				        	 preparedStatement1.setString(1,newNumber);
				        	 preparedStatement1.setInt(2, userId);
				        	 
				        	 preparedStatement1.executeUpdate();
				        	 System.out.println("Number sucessfully updated");
				        	
				        }
					}
					}while(isNumberValid==true);
					
				}while(isAlreadyNumberExists==true);
				break;
			case 5:
				boolean isAlreadyMailIdExists=false;
				do
				{
					System.out.print("Enter Mail ID:");
					String newMailId=scanner.nextLine();
					String query="SELECT User_name FROM User_table WHERE Mailid=?";
					 PreparedStatement preparedStatement= DataBase.DataBaseConnectivity().prepareStatement(query);
				        preparedStatement.setString(1,newMailId);
				        
				        ResultSet resultSet = preparedStatement.executeQuery();
				      // System.out.println(resultSet.next());
				        if(resultSet.next())
				        {
				        	System.out.print("Mail Id already exists..");
				        	System.out.println();
				        	isAlreadyMailIdExists=true;
				        }
				        else
				        {
				        	String updateQuery="UPDATE User_table SET Mailid=? WHERE UserId=?";
				        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				        	 preparedStatement1.setString(1,newMailId);
				        	 preparedStatement1.setInt(2, userId);
				        	 
				        	 preparedStatement1.executeUpdate();
				        	 System.out.println("Mail ID sucessfully updated");
				        	
				        }
					
				}while(isAlreadyMailIdExists==true);
				break;
			case 4:
				
				
					System.out.print("Enter Address:");
					String address=scanner.nextLine();
					String query="SELECT User_name FROM User_table WHERE Address=?";
					 PreparedStatement preparedStatement= DataBase.DataBaseConnectivity().prepareStatement(query);
				        preparedStatement.setString(1,address);
				        
				        ResultSet resultSet = preparedStatement.executeQuery();
				      
				        	String updateQuery="UPDATE User_table SET Address=? WHERE UserId=?";
				        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				        	 preparedStatement1.setString(1,address);
				        	 preparedStatement1.setInt(2, userId);
				        	 
				        	 preparedStatement1.executeUpdate();
				        	 System.out.println("Address sucessfully updated");
				        	
				        
					
				
				break;
			}
			
			
		}while(choice!=6);
		
	}

	public boolean checkAdminName(Connection dataBaseConnectivity, String adminName) throws SQLException {
		
		String query="SELECT Admin_username from Admin_details WHERE Admin_username=?";
		PreparedStatement preaparedStatement=DataBase.DataBaseConnectivity() .prepareStatement(query);
		preaparedStatement.setString(1,adminName);
		
		ResultSet resultSet=preaparedStatement.executeQuery();
		return resultSet.next();
		
	}

	public void addAdmin(Connection dataBaseConnectivity, String name, String adminName, String password,
			String mobileNumber, String address, String mailId) throws SQLException {
		
		 String query = "INSERT INTO Admin_details (Admin_name , Admin_username, password,Phonenumber, Address, Mailid) VALUES (?, ?, ?, ?, ?, ?)";
		 PreparedStatement preparedStatement=DataBase.DataBaseConnectivity() .prepareStatement(query);
	        preparedStatement = DataBase.DataBaseConnectivity().prepareStatement(query);

	        preparedStatement.setString(1, name);
	        preparedStatement.setString(2, adminName);
	        preparedStatement.setString(3, password);
	        preparedStatement.setString(4, mobileNumber);
	        preparedStatement.setString(5, address);
	        preparedStatement.setString(6, mailId);

	       preparedStatement.executeUpdate();
	        
	        System.out.println("Admin sucessfully added");
	        
	       
	        
	        
		
	}

	public boolean checkPassword(String userName, String password) throws SQLException {
		
		String query="SELECT password FROM Admin_details WHERE Admin_username=?";
		PreparedStatement preparedStatement=DataBase.DataBaseConnectivity() .prepareStatement(query);
		preparedStatement.setString(1,password);
		ResultSet resultSet=preparedStatement.executeQuery();
		return resultSet.next();
		
		
	}

	public int findAdminId(String userName) throws SQLException {
		
		int adminId=0;
		String nameQuery="SELECT * FROM Admin_details WHERE Admin_username=?";
		PreparedStatement preaparedStatementName=DataBase.DataBaseConnectivity().prepareStatement(nameQuery);
		preaparedStatementName.setString(1,userName);
		ResultSet resultSet2=preaparedStatementName.executeQuery();
		if (resultSet2.next()) {
		      adminId=resultSet2.getInt("AdminId");
		    }
		
		return adminId;
		
	}

	

}
