import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Gruppe {

    //connection
    private Connection conn;

    //gruppe
    private String kategori, beskrivelse;
    private int subGruppeId, øvelseId, gruppeId;

    //constructor
    public Gruppe(Connection conn) {
        this.conn = conn;
        this.gruppeId = getGruppeIDFromDB(conn);
    }

    //getters
    public String getKategori() { return kategori; }
    public String getBeskrivelse() { return beskrivelse; }
    public int getSubGruppeId() { return subGruppeId; }
    public int getØvelseId() { return øvelseId; }
    public int getGruppeId() { return gruppeId; }


    public void makeGruppe(Scanner scanner) throws SQLException {
        System.out.println("Lag en ny treningsgruppe");

        System.out.println("Hva vil du kalle gruppen?");
        kategori = scanner.nextLine();
        System.out.println("Gi en beskrivelse av øvelsene i gruppen: (kan være tom)");
        beskrivelse = scanner.nextLine();
        System.out.println("Hvilken øvelse vil du legge i gruppen?");
        getAlleØvelser(scanner);

        String gruppeSql = String.format("INSERT INTO gruppe VALUES(%d, '%s', '%s', %d, %d)", getGruppeId(), getKategori(), getBeskrivelse(), null, getØvelseId());

        System.out.println("Er du sikker på at du vil legge til denne gruppen? (ja/nei)");
        String godkjenn = scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(gruppeSql);
            flereGrupper(scanner);
        } else {
            System.out.println("Avbrutt, ingen grupper ble lagt til i databasen.");
        }
    }


    private void flereGrupper(Scanner scanner) throws SQLException {
        System.out.println("Vil du legge til flere grupper? (ja/nei)");
        String godkjenn = scanner.nextLine();
        while (godkjenn.equals("ja")){
            makeGruppe(scanner);
        }
        System.out.println("Avbrutt, ingen flere grupper.");
        return;
    }


    public void getAlleØvelser(Scanner scanner) throws SQLException {
        System.out.println("Velg deg en øvelse");
        String query = "SELECT øvelseId, navn FROM øvelse";
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
    }
    
    /**
     * Henter gruppeID for bruk i programmet
     * @param conn
     * @return gruppeID eller 0
     */
    public int getGruppeIDFromDB(Connection conn){
        String query = "SELECT gruppeID FROM gruppe ORDER BY gruppeID DESC LIMIT 1";
        try {
            ResultSet rs = getResultSet(conn, query);
            if (rs.next()){
                int gruppeID = rs.getInt("gruppeID") + 1;
                return gruppeID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private static ResultSet getResultSet(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }




}