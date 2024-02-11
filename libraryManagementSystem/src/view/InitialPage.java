package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import controller.AdminControl;
import model.DataBase;



public class InitialPage{

	Scanner scanner=new Scanner(System.in);
	AdminControl admin=new AdminControl();
	
	
	public void userScreenShow() throws SQLException, InterruptedException {
		
		System.out.println("\tWELCOME TO PUBLIC LIBRARY.....");
		
		int choice=0;
		
		do
		{
			System.out.println("1.Admin login");
			System.out.println("2.User login");
			System.out.println("3.Exit");
			choice=scanner.nextInt();
			
			if(!(choice>=1 && choice<=3))
			{
				System.out.println("Enter valid options");
			}
			
			
			switch(choice)
			{
			case 1:
				loginAdmin();
				break;
			case 2:
				userLogin();
				break;
			}
			
			
		}while(choice!=3);
		
		
	}

	private void userLogin() throws SQLException {
		UserLogin userlogin=new UserLogin();
		userlogin.proceedUserLogin();
		
	}

	private void loginAdmin() throws SQLException, InterruptedException {
		
		scanner.nextLine();
		System.out.print("Admin's username:");
		String userName=scanner.nextLine();
	    
		System.out.print("Password:");
		String password=scanner.nextLine();
        password=admin.encryption(password);
		try {
			if(admin.adminLoginCredentials(DataBase.DataBaseConnectivity(),userName,password,"Admin"))
			{
				Thread.sleep(2000);
				System.out.println("Login successful......");
				AdminDashBoard admin=new AdminDashBoard();
				admin.functionalities(userName);
			}
			else
			{
				System.out.println("Login failed.Invalid credentials..");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}

	

	

	

	
}
