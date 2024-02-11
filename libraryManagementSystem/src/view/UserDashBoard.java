package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import controller.AdminControl;
import model.DataBase;

public class UserDashBoard {

	Scanner scanner=new Scanner(System.in);
	BrowseBooks browse=new BrowseBooks();
	BorrowRequest request=new BorrowRequest();
	AdminControl admin=new AdminControl();
	
	public void printDashBoard(String userName) throws SQLException 
	{
		int choice=0;
		do
		{
			System.out.println("1.Browse books");
			System.out.println("2.Borrow request");
			System.out.println("3.Book return");
			System.out.println("4.Change credentials");
			System.out.println("5.Borrowing history");
			System.out.println("6.Exit");
			choice=scanner.nextInt();
			
			switch(choice)
			{
			case 1:
				browseBooks();
				break;
			case 2:
				borrowRequest(userName);
				break;
			case 3:
				bookReturn(userName);
				break;
			case 4:
				changeCredentials(userName);
				break;
			case 5:
				borrowingHistory(userName);
				break;
			
			}
			
		}while(choice!=6);
		
		
	}

	private void borrowingHistory(String userName) throws SQLException {
		
		int userId=admin.findUserId(userName);
		System.out.println();
		String query="select bl.BookName,tr.BookId,tr.TransactionType,tr.Transaction_Date,tr.Due_date,tr.Return_date,tr.fine,tr.isPaid from Books_List bl join Transaction tr on bl.BookId = tr.BookId WHERE UserId=?";
		PreparedStatement preparedStatement = DataBase.DataBaseConnectivity().prepareStatement(query);
		preparedStatement.setInt(1, userId);
		
		ResultSet resultSet=preparedStatement.executeQuery();
		
		if(resultSet.next()==false)
		{
			System.out.println("No transactions yet");
		}
		else
		{
		System.out.println("User name:"+userName+"                    "+"User ID:"+userId);
		System.out.println();
		System.out.printf("%10s %20s %25s %15s %15s %15s %n","Book ID","Book name","Issue date","Due date","Return date","Fine amount");
		System.out.println();
		
		
		while(resultSet.next())
		{
			String bookReturnDate=resultSet.getDate("Return_date")==null?"Pending":resultSet.getDate("Return_date")+"";
			double fineAmount=resultSet.getDouble("fine")==0?0.0:resultSet.getDouble("fine");
			//String paymentDate=resultSet.getDate("Payment_Date")==null?"--":resultSet.getDate("Payment_Date")+"";
			
			System.out.printf("%10s %20s %25s %15s %15s %15s %n",
					resultSet.getInt("BookId"),
					resultSet.getString("BookName"),
					resultSet.getDate("Transaction_Date"),
					resultSet.getDate("Due_date"),
					bookReturnDate,fineAmount);
			System.out.println();
		}
		System.out.println();
		
		
		
	}
	}

	private void changeCredentials(String userName) throws SQLException {
		admin.changeCredentials(userName);
		
	}

	private void bookReturn(String userName) throws SQLException {
		
		BookReturn bookreturn=new BookReturn();
		bookreturn.returnBooks(userName);
		
	}

	private void borrowRequest(String userName) throws SQLException {
		
		request.borrowBook(userName);
	}

	private void browseBooks() throws SQLException {
		
		
		browse.searchBooks();
		
	}

}
