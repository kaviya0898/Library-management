package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {

	public static Connection DataBaseConnectivity() throws SQLException
	{
		
       Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LibraryManagement", "root", "Password08");
      
       return connection;
	}
}

