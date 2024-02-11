package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.DataBase;

public class BrowseBooks {

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
			        String availability="";
					if (resultSet.getInt("AvailableBooks")>=1)
						availability = "Available";
					else
						availability = "Not available";

			        System.out.printf("%-20s %-15s %-15s %-15s", bookTitle, rackNumber, columnNumber, availability);
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
	

}
