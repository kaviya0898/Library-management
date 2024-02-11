package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.AdminControl;
import controller.HandlingBooks;

public class BorrowRequest {

	Scanner scanner=new Scanner(System.in);
	HandlingBooks books=new HandlingBooks();
	
	public void borrowBook(String userName) throws SQLException {
		
		int choice=0;
		
		String transaction="Borrow";
		do
		{
			System.out.println("Enter the book's name:");
			String keyword = scanner.nextLine().toLowerCase();
			books.borrowBook(userName,keyword,transaction);
			System.out.println("Do you wish to continue?");
			System.out.println("1.YES");
			System.out.println("2.NO");
			
			choice=scanner.nextInt();
			scanner.nextLine();
			
		}while(choice!=2);
		
	}

}
