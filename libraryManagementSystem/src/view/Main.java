package view;

import java.sql.SQLException;

import model.DataBase;

public class Main {

	public static void main(String[] args) throws SQLException, InterruptedException {
		
		DataBase data=new DataBase();
		data.DataBaseConnectivity();
		InitialPage user=new InitialPage();
		user.userScreenShow();

	}

}
