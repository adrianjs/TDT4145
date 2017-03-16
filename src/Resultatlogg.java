import java.sql.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Resultatlogg {

	//connection
	private Connection conn;
	private Statement stmt;

	//resultatlogg
	private String resultater, beste, målType;
	private int øvelseId;

	//constructor
	public Resultatlogg(Connection conn) {
		this.conn = conn;
		this.stmt = stmt;
	}

	//getters
	public String getResultater() { return resultater; }
	public String getBeste() { return beste; }
	public String getMålType() { return målType; }
	public int getØvelseId() { return øvelseId; }

	public void generateRapport(Scanner scanner){
		System.out.println("For hva type trening ønsker å få en rapport over din beste ytelse denne siste uken? (s)tyrke, (d)instanse");
		String trainingType = scanner.nextLine();
		LocalDateTime rightNow = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
		String parsedDate = rightNow.format(formatter);
		if(trainingType.equals("s")){
			trainingType = "Styrke";
		}
		else if(trainingType.equals("d")){
			trainingType = "Distanse";
		}
		String besteSQL = String.format("SELECT resultatlogg.Resultater, resultatlogg.Beste, resultatlogg.Måltype, øvelse.navn, øvelse.beskrivelse, treningsøkt.dato, treningsøkt.varighet FROM resultatlogg INNER JOIN øvelse ON resultatlogg.øvelseID = øvelse.øvelseID INNER JOIN treningsøkt ON treningsøkt.øktID = øvelse.øktID HAVING resultatlogg.Måltype = '%s' AND treningsøkt.dato >= now()-INTERVAL 7 day", trainingType);
		ArrayList<String> resultsArray = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = (stmt.executeQuery(besteSQL));
			int maxVarighet = 0;
			while (rs.next())
			{
				int dank = rs.getInt("varighet");
				if(dank > maxVarighet){
					resultsArray.clear();
					resultsArray.add(rs.getString("Måltype"));
					resultsArray.add(rs.getString("navn"));
					resultsArray.add(rs.getString("Resultater"));
					resultsArray.add(rs.getString("dato"));
					resultsArray.add(rs.getString("varighet"));

				}
			}
		}catch (Exception e){
			System.out.println(e);
			System.out.println("Something went wrong in communicating with the database.");
		}

		System.out.println("Din beste" + trainingType + "trening denne uken var:");
		System.out.println(resultsArray.get(0).toString());
		System.out.println(" Den " + resultsArray.get(3).toString() + " Da brukte du " + resultsArray.get(4).toString() + " min på å gjennomføre " + resultsArray.get(2).toString() + " med " + resultsArray.get(1).toString());
		System.out.println("Press en vilkårlig tast for å fortsette");
		scanner.nextLine();
		return;

	}
}