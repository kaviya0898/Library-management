package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.DataBase;

public class AdminBookDetails {

	Scanner scanner=new Scanner(System.in);
	
	public void searchBooks() throws SQLException {
		

		int choice=0;
		
		do
		{
			System.out.println("Enter the book's name");
			String keyword = scanner.nextLine().toLowerCase();
			
			String query="SELECT * from Books_List WHERE LOWER(BookName) LIKE ?";
			
			PreparedStatement preparedStatement=DataBase.DataBaseConnectivity().prepareStatement(query);
			preparedStatement.setString(1,keyword+"%");
			
			ResultSet resultSet=preparedStatement.executeQuery();
			if (resultSet.next()) 
			{
				System.out.printf("%-20s %-15s %-15s %-15s", "Book title", "Rack number", "Column number", "Availability");
				System.out.println();
				System.out.println("----------------------------------------------------------------");


			    do 
			    {
			       
			        String bookTitle = resultSet.getString("BookName");
			       // System.out.println(resultSet.getString("BookName"));
			        int rackNumber = resultSet.getInt("RackNumber");
			        int columnNumber = resultSet.getInt("ColumnNumber");
			       

			        System.out.printf("%-20s %-15s %-15s %-15s", bookTitle, rackNumber, columnNumber,resultSet.getInt("AvailableBooks"));
			        System.out.println();
			        System.out.println("----------------------------------------------------------------");
			    } while (resultSet.next());
			    
			} 
			else 
			{
			    System.out.println("No matching books found.");
			}
			System.out.println("Do you wish to continue?");
			System.out.println("1.YES");
			System.out.println("2.NO");
			
			choice=scanner.nextInt();
			scanner.nextLine();
			
			
		}while(choice!=2);
		
	}

	public void addBookDetails() throws SQLException {
		
		int choice=0;
		do
		{
			System.out.println("1.Add existing book");
			System.out.println("2.Add new books");
			System.out.println("3.Exit");
			int bookChoice=scanner.nextInt();
			
			switch(bookChoice)
			{
			case 1:
				addExistingBook();
				break;
			case 2:
				addNewBook();
				break;
			}
		}while(choice!=3);
		
		
	}

	private void addNewBook() {
		
		
		
	}

	private void addExistingBook() throws SQLException {
		
		boolean isBookName=false;
		scanner.nextLine();
		do
		{
		System.out.print("Enter book's name:");
		String bookName=scanner.nextLine();
		
		if(isBookPresent(bookName))
		{
			System.out.print("Input the count:");
			int count=scanner.nextInt();
			String updateQuery = "UPDATE Books_list SET AvailableBooks = AvailableBooks + ? WHERE BookName LIKE ?";
			PreparedStatement updateStatement = DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
            isBookName=true;
			updateStatement.setInt(1, count);
			updateStatement.setString(2, bookName + "%");
			updateStatement.executeUpdate();
			System.out.println("Books added sucessfully.");
		}
		else
		{
			System.out.println("Invalid book.");
		}
		}while(isBookName==false);
			
		
	}

	private boolean isBookPresent(String bookName) throws SQLException {
		
		String checkQuery = "SELECT BookName FROM Books_List WHERE BookName LIKE ?";
		PreparedStatement updateStatement = DataBase.DataBaseConnectivity().prepareStatement(checkQuery);

		updateStatement.setString(1, bookName + "%");
		ResultSet resultSet = updateStatement.executeQuery();

		return resultSet.next();

	}
	

}
