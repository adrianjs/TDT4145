import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Mål {

    //connection
    private Connection conn;

    //mål
    private String mål, målType;
    private int målId, øvelseId, øktId;

    //constructor
    public Mål(Connection conn) {
        this.conn = conn;
        this.målId = getMålIdFromDB(conn);
    }

    //getters
    public String getMål() { return mål; }
    public String getMåltype() { return målType; }
    public int getØvelseId() { return øvelseId; }
    public int getØktId() { return øktId; }
    public int getMålId() { return målId; }

    /**
     * Skal legge til mål i databasen
     * Må kanskje ha øktID eller øvelseID som parameter
     * @param conn
     * @param scanner
     */
    public void addMålToØvelse(Scanner scanner) throws SQLException {
        System.out.println("Legg til et mål for en øvelse");

        System.out.println("Hva er målet?");
        mål = scanner.nextLine();
        System.out.println("Hva slags type mål er det?");
        målType = scanner.nextLine();

        String målSql = String.format("INSERT INTO mål VALUES(%d, '%s', '%s', %d)", getMålId(), getMål(), getMåltype(), getØvelseId());

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
     * Henter målId for bruk i programmet
     * @param conn
     * @return målId eller 0
     */
    public int getMålIdFromDB(Connection conn){
        String query = "SELECT målId FROM mål ORDER BY målId DESC LIMIT 1";
        try {
            ResultSet rs = getResultSet(conn, query);
            if (rs.next()){
                int målId = rs.getInt("målId") + 1;
                return målId;
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

    public void getAlleØkter(Scanner scanner) {
        System.out.println("Velg deg en treningsøkt");
        //print ut alle treningsøkter og deres id
        System.out.println("Skriv inn ID til treningsøkten din");
        øktId = Integer.parseInt(scanner.nextLine()));
        getAlleØvelser(scanner);
    }

    public void getAlleØvelser(Scanner scanner) {
        System.out.println("Velg deg en øvelse");
        //print ut alle øvelser og deres id
        System.out.println("Skriv inn ID til øvelsen din");
        øvelseId = Integer.parseInt(scanner.nextLine()));
        addMålToØvelse(scanner);
    }

}