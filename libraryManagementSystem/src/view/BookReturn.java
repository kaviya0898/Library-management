package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.AdminControl;
import controller.HandlingBooks;

public class BookReturn {

	Scanner scanner=new Scanner(System.in);
	HandlingBooks books=new HandlingBooks();
	AdminControl admin=new AdminControl();
	public void returnBooks(String userName) throws SQLException {
		
          int choice=0;
          int bookId=0;
		  boolean isCorrect=true;
		String transaction="Return";
		do
		{
			do
			{
			System.out.print("Enter the book's ID:");
			bookId=scanner.nextInt();
			if(admin.findBookId(bookId)==false)
			{
				System.out.println("Book ID is not present");
				isCorrect=false;
			}
			else
			{
				books.returnBooks(userName,transaction,bookId);
			}
			}while(isCorrect==false);
			
			System.out.println("Do you wish to continue?");
			System.out.println("1.YES");
			System.out.println("2.NO");
			
			choice=scanner.nextInt();
			scanner.nextLine();
			
		}while(choice!=2);
		
		
	}

}
