import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.*;

/**
 * Created by Adrian on 03.03.2017.
 */

//meme gods
//Navjot

public class Application {
	private String url = "jdbc:mysql://localhost:3306/";
	private final String userName = "root";
	private final String password = "root";
	private Connection conn;
	private Statement stmt;
	
	public String connect() throws SQLException {
		conn = (Connection) DriverManager.getConnection(url, userName, password);
		if (conn != null) {
			return "Connected to the database";
		}
		return "Connection failed";
	}
	
	//DETTE FUNKER IKKE ENNÅ, MEN LÆR AV DETTE
	public void addData() throws SQLException {
		System.out.println("Inserting records into the table...");
	    stmt = (Statement) conn.createStatement();
	    
	    String sql = "INSERT INTO Treningsøkt " +
	                 "VALUES (2017-05-05, 2017-05-05, 45, 'formål her', 'tips her', 'vær her', 20, 'luft her', 60)";
	    stmt.executeUpdate(sql);
	    System.out.println("Inserted records into the table...");
	}
	
	//DETTE FUNKER IKKE ENNÅ, MEN LÆR AV DETTE
	public void getData() throws SQLException {
		System.out.println("Getting records from the table...");
		stmt = (Statement) conn.createStatement();
		
		String sql = "SELECT * FROM Treningsøkt";
		ResultSet rs = stmt.executeQuery(sql);
	      while(rs.next()){
	         int id  = rs.getInt("øktID");
	         String formål = rs.getString("formål");
	         String tips = rs.getString("tips");

	         System.out.print("ID: " + id);
	         System.out.print(", Formål: " + formål);
	         System.out.print(", Tips: " + tips);
	      }
	      rs.close();
	}
	
	public static void main(String[] args) throws SQLException {
		Application app = new Application();
		System.out.println(app.connect());
	}
}