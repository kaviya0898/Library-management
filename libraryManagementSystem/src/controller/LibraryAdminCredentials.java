package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.DataBase;

public class LibraryAdminCredentials {

	Scanner scanner=new Scanner(System.in);
	AdminControl admin=new AdminControl();
	
	public void addAdmin() throws SQLException {
		
		//scanner.nextLine();
		System.out.print("Enter Name:");
		String name=scanner.nextLine();
		boolean isUserExists=true;
		boolean isPasswordValid=true;
		boolean isNumberValid=true;
		String adminName=scanner.nextLine();
		String password="";
		String mobileNumber="";
		String address="";
		String mailId="";
		do
		{
			System.out.print("Enter userName:");
			adminName=scanner.nextLine();
			if(admin.checkAdminName(DataBase.DataBaseConnectivity(), adminName))
			{
				System.out.println("Admin name already exists..");
				isUserExists=false;
			}
			else
			{
				
				
				 do
				 {
					 System.out.print("Enter password:");
					 password=scanner.nextLine();
					 if(admin.isStrongPassword(password))
					 {
						 isPasswordValid=true;
						password=admin.encryption(password);
						do
						{
							System.out.print("Enter mobile number:");
							mobileNumber=scanner.next();
							if(admin.isValidMobileNumber(mobileNumber))
							{
								isNumberValid=true;
								scanner.nextLine();
								System.out.print("Enter Address:");
								address=scanner.nextLine();
								System.out.print("Enter Mail-id:");
								mailId=scanner.next();
							}
							else
							{
								System.out.println("Invalid mobile number");
								isNumberValid=false;
							}
						}while(isNumberValid==false);
					 }
					 else
					 {
						 isPasswordValid=false;
						 System.out.println("Password is not strong enough..");
					 }
				 }while(isPasswordValid==false);
				
			}
		}while(isUserExists==false);
	
		admin.addAdmin(DataBase.DataBaseConnectivity(),name,adminName,password,mobileNumber,address,mailId);
		
		
		
	}

	public void deleteAdmin(String userName) throws SQLException {
		
		boolean isPasswordCorrect=true;
		
		do
		{
			System.out.println("Enter your password:");
			String password=scanner.nextLine();
			
			if(admin.checkPassword(userName,password))
			{
				System.out.println("Admin account is deleted");
			}
			else
			{
				System.out.println("Enter correct password");
				isPasswordCorrect=false;
			}
		}while(isPasswordCorrect==false);
		
	}

	public void changeCredentials(String userName) throws SQLException {
		int choice=0;
		int adminId=admin.findAdminId(userName);
		//System.out.println(adminId);
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
					String query="SELECT Admin_username FROM Admin_details WHERE Admin_username=?";
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
				        	String updateQuery="UPDATE Admin_details SET Admin_username=? WHERE adminId=?";
				        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				        	 preparedStatement1.setString(1,newUserName);
				        	 preparedStatement1.setInt(2, adminId);
				        	 
				        	 preparedStatement1.executeUpdate();
				        	 System.out.println("User name sucessfully updated");
				        	
				        }
					
				}while(isAlreadyExists==true);
				break;
			case 2:
				boolean isAlreadyPasswordExists = false;

				do {
				    boolean isPasswordValid = false;

				    do {
				        System.out.print("Enter new password:");
				        String newPassword = scanner.nextLine();

				        if (!admin.isStrongPassword(newPassword)) {
				            System.out.println("Password is not strong enough. Please try again.");
				            isPasswordValid = true;
				        } else {
				            newPassword = admin.encryption(newPassword);

				            String query = "SELECT Admin_username FROM Admin_details WHERE password=?";
				            PreparedStatement preparedStatement = DataBase.DataBaseConnectivity().prepareStatement(query);
				            preparedStatement.setString(1, newPassword);

				            ResultSet resultSet = preparedStatement.executeQuery();

				            if (!resultSet.next()) {
				                String updateQuery = "UPDATE Admin_details SET password=? WHERE AdminId=?";
				                PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				                preparedStatement1.setString(1, newPassword);
				                preparedStatement1.setInt(2, adminId);

				                preparedStatement1.executeUpdate();
				                System.out.println("Password successfully updated");
				            } else {
				                System.out.println("Password already exists. Please choose a different one.");
				                isAlreadyPasswordExists = true;
				            }
				        }
				    } while (isPasswordValid);

				} while (isAlreadyPasswordExists);

				break;
			case 3:
				boolean isAlreadyNumberExists=false;
				do
				{
					
					
					boolean isNumberValid=false;
					do {
						
					
					System.out.print("Enter mobile number");
					String newNumber=scanner.nextLine();
					
					if(admin.isValidMobileNumber(newNumber)==false)
					{
						System.out.println("Mobile number is not valid");
						isNumberValid=true;
					}
					else
					{
						
					String query="SELECT Admin_username FROM Admin_details WHERE Phonenumber=?";
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
				        	String updateQuery="UPDATE Admin_details SET Phonenumber=? WHERE AdminId=?";
				        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				        	 preparedStatement1.setString(1,newNumber);
				        	 preparedStatement1.setInt(2, adminId);
				        	 
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
					String query="SELECT Admin_username FROM Admin_details WHERE Mailid=?";
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
				        	String updateQuery="UPDATE Admin_details SET Mailid=? WHERE AdminId=?";
				        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				        	 preparedStatement1.setString(1,newMailId);
				        	 preparedStatement1.setInt(2, adminId);
				        	 
				        	 preparedStatement1.executeUpdate();
				        	 System.out.println("Mail ID sucessfully updated");
				        	
				        }
					
				}while(isAlreadyMailIdExists==true);
				break;
			case 4:
				
				
					System.out.print("Enter Address:");
					String address=scanner.nextLine();
					String query="SELECT Admin_username FROM Admin_details WHERE Address=?";
					 PreparedStatement preparedStatement= DataBase.DataBaseConnectivity().prepareStatement(query);
				        preparedStatement.setString(1,address);
				        
				        ResultSet resultSet = preparedStatement.executeQuery();
				      
				        	String updateQuery="UPDATE Admin_details SET Address=? WHERE AdminId=?";
				        	 PreparedStatement preparedStatement1 = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
				        	 preparedStatement1.setString(1,address);
				        	 preparedStatement1.setInt(2, adminId);
				        	 
				        	 preparedStatement1.executeUpdate();
				        	 System.out.println("Address sucessfully updated");
				        	
				        
					
				
				break;
			}
			
			
		}while(choice!=6);
		
		
	}

	public void viewAdmins() throws SQLException {
		
		String query="SELECT * FROM Admin_details";
		PreparedStatement preparedStatement= DataBase.DataBaseConnectivity().prepareStatement(query);
		 ResultSet resultSet = preparedStatement.executeQuery();
		 
		 System.out.printf("%-10s %-20s %-30s %-16s %-12s %-30s %-30s\n", 
				    "AdminId", "Admin_name", "Admin_username", "Password", "Phonenumber", "Address", "Mailid");

				while (resultSet.next()) 
				{
				    System.out.printf("%-10d %-20s %-30s %-16s %-12s %-30s %-30s\n", 
				        resultSet.getInt("AdminId"), resultSet.getString("Admin_name"),
				        resultSet.getString("Admin_username"), resultSet.getString("password"),
				        resultSet.getString("Phonenumber"), resultSet.getString("Address"),
				        resultSet.getString("Mailid"));
				}
	}

}
