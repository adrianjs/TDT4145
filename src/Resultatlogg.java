import java.sql.*;
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
    private int øvelseId, resultatloggId;

    //constructor
    public Resultatlogg(Connection conn) {
        this.conn = conn;
        this.stmt = stmt;
        this.resultatloggId = getResultIdFromDB(conn);
    }

    //getters
    public String getResultater() { return resultater; }
    public String getBeste() { return beste; }
    public String getMålType() { return målType; }
    public int getØvelseId() { return øvelseId; }
    public int getResultId() { return resultatloggId; }

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

        String målSql = String.format("INSERT INTO resultatlogg VALUES(%d, '%s', '%s', '$s', %d)", getResultId(), getResultater(), getBeste(), getMålType(), getØvelseId());

        System.out.println("Er du sikker på at du vil legge til dette målet? (ja / nei)");
        String godkjenn =  scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(målSql);
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

}