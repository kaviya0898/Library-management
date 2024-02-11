package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import controller.AdminBookDetails;
import controller.AdminControl;

import controller.LibraryAdminCredentials;
import controller.LibraryAdminTransaction;
import model.DataBase;

public class AdminDashBoard {

	Scanner scanner = new Scanner(System.in);
	LibraryAdminCredentials admin = new LibraryAdminCredentials();
    LibraryAdminTransaction transaction=new LibraryAdminTransaction();
    AdminBookDetails bookDetails=new AdminBookDetails();
    
	public void functionalities(String userName) throws SQLException {
		int choice = 0;

		do {
			System.out.println("1.Catalog books");
			System.out.println("2.Handle transaction");
			System.out.println("3.Admin actions");
			System.out.println("4.Exit");
			System.out.println();
			System.out.println("Enter your choice");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				catalogBooks();
				break;
			case 2:
				handleTransaction();
				break;
			case 3:
				adminActions(userName);
				break;
			}
		} while (choice != 4);

	}

	private void adminActions(String userName) throws SQLException {
		int choice = 0;

		do {
			System.out.println("1.Add admin");
			System.out.println("2.Delete admin");
			System.out.println("3.Change credentials");
			System.out.println("4.View Admin details");
			System.out.println("5.Exit");
			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				admin.addAdmin();
				break;
			case 2:
				admin.deleteAdmin(userName);
				break;
			case 3:
				admin.changeCredentials(userName);
				break;
			case 4:
				admin.viewAdmins();
				break;

			}

		} while (choice != 5);

	}

	private void handleTransaction() throws SQLException {
		int choice = 0;
		do {
			System.out.println("1.Fine payment");
			System.out.println("2.Fine history");
			System.out.println("3.Exit");
            choice=scanner.nextInt();
			switch (choice) {
			case 1:
				transaction.fineCollection();
				break;
			case 2:
				transaction.fineHistory();
				break;
			}

		} while (choice != 3);

	}

	private void catalogBooks() throws SQLException {

		int choice = 0;
		do {
			System.out.println("1.Add book");
			System.out.println("2.Delete book");
			System.out.println("3.Update book details");
			System.out.println("4.View book details");
			System.out.println("5.Exit");
			
			

			choice = scanner.nextInt();
			switch (choice) {
			case 1:
				addBook();
				break;
			case 2:
				// deleteBook();
				break;
			case 3:
				updateBook();
				break;
			case 4:
				viewBooks();
				break;
			
			}

		} while (choice != 7);

	}

	private void addBook() throws SQLException {
		
		bookDetails.addBookDetails();
		
	}

	private void updateBook() throws SQLException {
		
       int choice=0;
		
		do
		{
		System.out.println("Enter the book's name");
		String keyword = scanner.nextLine().toLowerCase();
		
		String query="SELECT * from Books_List WHERE LOWER(BookName) LIKE ?";
		
		PreparedStatement preparedStatement=DataBase.DataBaseConnectivity().prepareStatement(query);
		preparedStatement.setString(1,keyword+"%");
		
		ResultSet resultSet=preparedStatement.executeQuery();
		
		choice=scanner.nextInt();
		System.out.println("Do you want to continue?");
		System.out.println("1.Yes");
		System.out.println("2.No");
		
		}while(choice!=2);
		
		
	}

	private void viewBooks() throws SQLException {
		
		bookDetails.searchBooks();
		
	}

}
