package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import model.DataBase;

public class LibraryAdminTransaction {

	Scanner scanner = new Scanner(System.in);

	public void fineCollection() throws SQLException {

		boolean isUserId = false;
       
		do {
			System.out.println("Enter User ID:");
			int userId = scanner.nextInt();

			String idQuery = "SELECT UserId from Transaction WHERE UserId=?";
			PreparedStatement preparedStatementId = DataBase.DataBaseConnectivity().prepareStatement(idQuery);
			preparedStatementId.setInt(1, userId);
			ResultSet resultSet = preparedStatementId.executeQuery();

			if (resultSet.next()) {
				isUserId=true;
				boolean isBookId = false;
				do {
					System.out.println("Enter Book ID:");
					int bookId = scanner.nextInt();

					String bookQuery = "SELECT BookId from Transaction WHERE BookId=? AND UserId=?";
					PreparedStatement preparedStatementBook = DataBase.DataBaseConnectivity()
							.prepareStatement(bookQuery);
					preparedStatementBook.setInt(1, bookId);
					preparedStatementBook.setInt(2, userId);
					ResultSet resultSetBook = preparedStatementBook.executeQuery();

					if (resultSetBook.next()) {
						isBookId = false;
						double fineAmount = 0.0;
						String fineQuery = "SELECT fine From Transaction WHERE UserId=? AND BookId=?";
						PreparedStatement preparedStatementFine = DataBase.DataBaseConnectivity()
								.prepareStatement(fineQuery);
						preparedStatementFine.setInt(1, userId);
						preparedStatementFine.setInt(2, bookId);
						ResultSet resultSetFine = preparedStatementFine.executeQuery();
						while (resultSetFine.next()) {

							fineAmount = resultSetFine.getDouble("fine");
						}
						//System.out.println(fineAmount);
						if (fineAmount > 0) 
						{
						  System.out.println("Fine amount is"+fineAmount);
						  System.out.print("Enter the fine amount:");
						  double currentFineAmount=scanner.nextDouble();
						  
						  
						  String transactionUpdate="UPDATE Transaction SET isPaid=? WHERE UserID=? BookId=?";
						  PreparedStatement preparedStatementUpdate = DataBase.DataBaseConnectivity()
									.prepareStatement(transactionUpdate);
						  preparedStatementUpdate.setBoolean(1,true);
						  preparedStatementUpdate.setInt(2,userId);
						  preparedStatementUpdate.setInt(1,bookId);
						  
						  int resultSetTrans=preparedStatementUpdate.executeUpdate();
						  if(resultSetTrans>0)
						  {
							  System.out.println("Fine amount sucessfully updated");
						  }
                          updateBooksTable(bookId);
						} 
						else 
						{
							System.out.println("No fine amount pending.");
							updateBooksTable(bookId);
							isUserId=true;
						}

					} else {
						System.out.println("Invalid Book ID for specific user");
						isBookId = true;
					}
				} while (isBookId == true);

			} else {
				System.out.println("Invalid User Id.Try again.");
			}

		} while (isUserId == false);

	}

	private void updateBooksTable(int bookId) throws SQLException {
      String updateAvailableBooks = "UPDATE Books_List SET AvailableBooks = AvailableBooks +1 WHERE BookId = ?";
      PreparedStatement preparedStatementBooks=DataBase.DataBaseConnectivity().prepareStatement(updateAvailableBooks);
		preparedStatementBooks.setInt(1,bookId);
		preparedStatementBooks.executeUpdate();
		
	}

	public void fineHistory() throws SQLException {
		
		
	    int choice=0;
		do
		{
			System.out.println("Enter User ID:");
			int userId = scanner.nextInt();

			String idQuery = "SELECT UserId from Transaction WHERE UserId=?";
			PreparedStatement preparedStatementId = DataBase.DataBaseConnectivity().prepareStatement(idQuery);
			preparedStatementId.setInt(1, userId);
			ResultSet resultSet = preparedStatementId.executeQuery();
			
			if(resultSet.next())
			{
				String query="SELECT UserId,BookId, Transaction_Date,Due_date,Return_date,isPaid,fine FROM Transaction WHERE UserId=?";
				PreparedStatement preparedStatement = DataBase.DataBaseConnectivity().prepareStatement(query);
				preparedStatement.setInt(1, userId);
				ResultSet resultSetTransaction = preparedStatement.executeQuery();
				
				if(resultSetTransaction.next())
				{
					
					
					System.out.printf("%10s %10s %15s %15s %15s %10s %10s%n",
					         "User ID", "Book ID", "Transaction Date", "Due Date", "Return Date", "Payment", "Fineamount");
					while (resultSetTransaction.next()) {
					    String payment="Payment pending";
					    String book="";
					    int bookId=resultSetTransaction.getInt("BookId");
					    String bookName="SELECT BookName from Books_list WHERE BookId=?";
					    PreparedStatement preparedStatementBook = DataBase.DataBaseConnectivity().prepareStatement(bookName);
						preparedStatementBook.setInt(1, bookId);
						ResultSet resultSetBook= preparedStatementBook.executeQuery();
						while (resultSetBook.next()) 
						{
							book=resultSetBook.getString("BookName");
						}
					    String transactionDate = resultSetTransaction.getString("Transaction_Date");
					    String dueDate = resultSetTransaction.getString("Due_date");
					    String returnDate = resultSetTransaction.getString("Return_date");
					    boolean isPaid = resultSetTransaction.getBoolean("isPaid");
					    if(isPaid==true)
					    {
					    	payment="Payment cleared";
					    }
					    double fine = resultSetTransaction.getDouble("fine");

					    System.out.printf("%10d %10s %15s %15s %15s %10s %10.2f%n",
			                    userId, book, transactionDate, dueDate, returnDate, payment, fine);
					}
				}
				else
				{
					System.out.println("No transaction found");
				}
			}
			else
			{
				System.out.println("Invalid User Id.Try again.");
			}
           
			System.out.println("Do you wish to continue?");
			System.out.println("1.yes");
			System.out.println("2.No");
			 choice=scanner.nextInt();
		}while(choice!=2);
		
	}

}
