import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Resultatlogg {
    //connection
    private Connection conn;

    //resultatlogg
    private String resultater, beste, målType;
    private int øvelseId, resultatloggId, øktId;

    //constructor
    public Resultatlogg(Connection conn) {
        this.conn = conn;
        this.resultatloggId = getResultIdFromDB(conn);
    }

    //getters
    public String getResultater() { return resultater; }
    public String getBeste() { return beste; }
    public String getMålType() { return målType; }
    public int getØvelseId() { return øvelseId; }
    public int getResultId() { return resultatloggId; }
    public int getØktId() { return øktId; }

    /**
     * Skal legge til resultatlogg i databasen
     * Må kanskje ha idResultatLogg eller øvelseID som parameter
     * @param conn
     * @param scanner
     */
    public void addResultatloggToØvelse(Scanner scanner) throws SQLException {
        System.out.println("Legg til et resultatlogg for en øvelse");

        System.out.println("Hva ble resultatet?");
        resultater = scanner.nextLine();
        System.out.println("Hva er rekorden?");
        beste = scanner.nextLine();
        System.out.println("Hva slags type mål er det?");
        målType = scanner.nextLine();

        String målSql = String.format("INSERT INTO resultatlogg VALUES(%d, '%s', '%s', '%s', %d)", getResultId(), getResultater(), getBeste(), getMålType(), getØvelseId());

        System.out.println("Er du sikker på at du vil legge til dette målet? (ja / nei)");
        String godkjenn =  scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(målSql);
            ekstraResultatlogg(scanner);
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
    }

    /**
     * Henter idResultatLogg for bruk i programmet
     * @param conn
     * @return idResultatLogg eller 0
     */
    public int getResultIdFromDB(Connection conn){
        String query = "SELECT idResultatLogg FROM resultatlogg ORDER BY idResultatLogg DESC LIMIT 1";
        try {
            ResultSet rs = getResultSet(conn, query);
            if (rs.next()){
                int idResultatLogg = rs.getInt("idResultatLogg") + 1;
                return idResultatLogg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Henter øvelseID for bruk i programmet
     * @param conn
     * @return øvelseID eller 0
     */
    public int getØvelseIDFromDB(Connection conn){
        String query = "SELECT øvelseID FROM øvelse ORDER BY øvelseID DESC LIMIT 1";
        try {
            ResultSet rs = getResultSet(conn, query);
            if (rs.next()){
                int øvelseID = rs.getInt("øvelseID");
                return øvelseID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Henter ResultSet, eller data fra databasen for bruk i diverse get-operasjoner
     * @param conn
     * Koblingen til databasen
     * @param query
     * SQL-queryen som brukes for å hente data fra databasen
     * @return ResultSet
     * @throws SQLException
     */
    private static ResultSet getResultSet(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
    
    private void ekstraResultatlogg(Scanner scanner) throws SQLException{
        System.out.println("Ønsker du å legge til en ekstra resultatlogg? (ja / nei)");
        String godkjenn = scanner.nextLine();
        while (godkjenn.equals("ja")){
        	addResultatloggToØvelse(scanner);
        }
        System.out.println("Avbrutt, ingen flere resultater.");
    }

    public void getAlleØkter(Scanner scanner) throws SQLException {
        System.out.println("Velg deg en treningsøkt");
        String query = "SELECT øktId, datotid, formål FROM treningsøkt";
        try {
            ResultSet rs = getResultSet(conn, query);
            while (rs.next()){
                System.out.println(rs.getInt("øktId") + ", " + rs.getDate("datotid") + ", " + rs.getString("formål"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Skriv inn ID til treningsøkten din");
        øktId = Integer.parseInt(scanner.nextLine());
        getAlleØvelser(scanner);
    }

    public void getAlleØvelser(Scanner scanner) throws SQLException {
        System.out.println("Velg deg en øvelse");
        String query = "SELECT øvelseId, navn FROM øvelse WHERE øktId=" + øktId;
        try {
            ResultSet rs = getResultSet(conn, query);
            while (rs.next()){
                System.out.println(rs.getInt("øvelseId") + ", " + rs.getString("navn"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Skriv inn ID til øvelsen din");
        øvelseId = Integer.parseInt(scanner.nextLine());
        addResultatloggToØvelse(scanner);
    }
  
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