package controller;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;

import model.DataBase;

public class HandlingBooks {

	public void borrowBook(String userName, String keyword, String transaction) throws SQLException {
		
		int bookId=0;
		int userId=findUserId(userName);
		int availableBooks=0;
		String query="SELECT AvailableBooks,BookId from Books_List WHERE LOWER(BookName) LIKE ?";
		
		String updateAvailableBooks = "UPDATE Books_List SET AvailableBooks = AvailableBooks - 1 WHERE BookId = ?";
		
		PreparedStatement preparedStatementAvailable=DataBase.DataBaseConnectivity().prepareStatement(query);
		preparedStatementAvailable.setString(1,keyword+"%");
		
		ResultSet resultSet=preparedStatementAvailable.executeQuery();
		
		
		while(resultSet.next())
		{
			availableBooks=resultSet.getInt("AvailableBooks");
			bookId=resultSet.getInt("BookId");
		}
		
	    if(availableBooks>=1)
	    {
	    java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        
	    
		
		 java.util.Calendar calendar = java.util.Calendar.getInstance();
		 calendar.setTime(today);
		 calendar.add(java.util.Calendar.DATE,1);
		 java.sql.Date dueDate = new java.sql.Date(calendar.getTimeInMillis());
	    // System.out.println(today+"-----"+dueDate);
	     String updateTransaction = "INSERT INTO Transaction (UserId, BookId, TransactionType, Transaction_Date, Due_date) VALUES (?, ?, ?, ?, ?)";
	    
	         PreparedStatement updateStatement = DataBase.DataBaseConnectivity().prepareStatement(updateTransaction);

	         updateStatement.setInt(1, userId);
	         updateStatement.setInt(2, bookId);
	         updateStatement.setString(3, transaction);
	         updateStatement.setDate(4, today);
	         updateStatement.setDate(5, dueDate);

	        
	         updateStatement.executeUpdate();
	         
	         
	             System.out.println("Book successfully borrowed");
	             System.out.println("Your book ID is:"+bookId);
	      
	     }
	    else
	    {
	    	System.out.println("Book is not available");
	    }
	}
	
	  

	
	private int findUserId(String userName) throws SQLException {
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
	public void returnBooks(String userName, String transaction, int bookId) throws SQLException
	{
		java.sql.Date returnDate = new java.sql.Date(System.currentTimeMillis());
        
		Date transDate = null;
		Date dueDate=null;
		Date returnDates=null;
		double fineAmount=0.0;
		
		int userId=findUserId(userName);
		
		String query="SELECT Transaction_Date,Due_date,Return_date  FROM Transaction WHERE UserId=?";
		PreparedStatement preaparedStatementName=DataBase.DataBaseConnectivity().prepareStatement(query);
		preaparedStatementName.setInt(1,userId);
		ResultSet resultSet=preaparedStatementName.executeQuery();
		
		while(resultSet.next())
		{
			 transDate = resultSet.getDate("Transaction_Date");
			    dueDate = resultSet.getDate("Due_date");
			    returnDates=resultSet.getDate("Return_date");
			    
		}
		LocalDate returnLocalDate = returnDate.toLocalDate();
		LocalDate dueLocalDate = dueDate.toLocalDate();
       // System.out.println(returnLocalDate.isAfter(dueLocalDate));
		if(returnLocalDate.isAfter(dueLocalDate))
		{
		Duration duration = Duration.between(returnLocalDate.atStartOfDay(), dueLocalDate.atStartOfDay());
        long daysDifference = Math.abs(duration.toDays());
		if(daysDifference>0)
		{
			fineAmount=daysDifference*10;
		}
		}
		
		//System.out.println( transDate+"----------"+dueDate);
		
		if(returnDates==null)
		{
		String updateQuery="UPDATE Transaction SET Return_date=?,isPaid=?,fine=? WHERE BookId=?";
		
		PreparedStatement preparedStatement=DataBase.DataBaseConnectivity().prepareStatement(updateQuery);
		//ResultSet resultSetFine=preparedStatement.executeQuery();
		preparedStatement.setDate(1,returnDate);
		preparedStatement.setBoolean(2, false);
		preparedStatement.setDouble(3,fineAmount);
		preparedStatement.setInt(4,bookId);
		//ResultSet resultSetFine=preparedStatement.executeQuery();
		 int rowsAffected = preparedStatement.executeUpdate();
         
         if (rowsAffected > 0) {
        	 
        	
        	 if(fineAmount==0)
        	 {
        		 System.out.println("Book return is queued");
        		 System.out.println("Nil fine amount");
        	 }
        	 else 
        	 {
             System.out.println("Book return is queued");
             System.out.println("Fine amount"+" "+fineAmount +" "+"is pending");
        	 }
            
         } 
//         String updateAvailableBooks = "UPDATE Books_List SET AvailableBooks = AvailableBooks +1 WHERE BookId = ?";
//         PreparedStatement preparedStatementBooks=DataBase.DataBaseConnectivity().prepareStatement(updateAvailableBooks);
// 		preparedStatementBooks.setInt(1,bookId);
// 		preparedStatementBooks.executeUpdate();
 		
		}
		else
		{
			System.out.println("Invalid book ID");
		}
		
	}

	
	}

	
		
	


