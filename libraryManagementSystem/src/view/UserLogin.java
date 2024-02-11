package view;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.AdminControl;
import model.DataBase;

public class UserLogin {

	Scanner scanner=new Scanner(System.in);
	AdminControl admin=new AdminControl();
	public void proceedUserLogin() throws SQLException {
		
		//scanner.nextLine();
		int choice=0;
		do
		{
			System.out.println("1.Sign in");
			System.out.println("2.Sign up");
			System.out.println("3.Exit");
			
			choice=scanner.nextInt();
			switch(choice)
			{
			case 1:
				signIn();
				break;
			case 2:
				signUp();
				break;
			}
			
			
			
		}while(choice!=3);
		
		
		
		
	}
	private void signUp() throws SQLException {
		
		scanner.nextLine();
		System.out.print("Enter Name:");
		String name=scanner.nextLine();
		boolean isUserExists=true;
		boolean isPasswordValid=true;
		boolean isNumberValid=true;
		String userName=scanner.nextLine();
		String password="";
		String mobileNumber="";
		String address="";
		String mailId="";
		do
		{
			System.out.print("Enter userName:");
			userName=scanner.nextLine();
			if(admin.checkUserName(DataBase.DataBaseConnectivity(), userName))
			{
				System.out.println("User name already exists..");
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
						password=encryption(password);
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
	
		admin.addUser(DataBase.DataBaseConnectivity(),name,userName,password,mobileNumber,address,mailId);
		
		
		
	}
	
	
	private void signIn() {
		scanner.nextLine();
		System.out.print("User name:");
		String userName=scanner.nextLine();
	    
		System.out.print("Password:");
		String password=scanner.nextLine();
		
		String encryptedPassword=encryption(password);
		//System.out.println(encryptedPassword);

		try {
			if(admin.adminLoginCredentials(DataBase.DataBaseConnectivity(),userName,encryptedPassword,"user"))
			{
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				System.out.println("Login successful......");
				
				UserDashBoard dashBoard=new UserDashBoard();
				dashBoard.printDashBoard(userName);
				
			}
			else
			{
				System.out.println("Login failed.Invalid credentials..");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}
	private String encryption(String password) {
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
	

}
