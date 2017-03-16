import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Gruppe {

    //connection
    private Connection conn;
    private Statement stmt;

    //gruppe
    private String kategori, beskrivelse;
    private int subGruppeId, øvelseId;

    //constructor
    public Gruppe(Connection conn) {
        this.conn = conn;
    }

    //getters
    public String getKategori() { return kategori; }
    public String getBeskrivelse() { return beskrivelse; }
    public int getSubGruppeId() { return subGruppeId; }
    public int getØvelseId() { return øvelseId; }


    public void makeGruppe(Scanner scanner) throws SQLException {
        System.out.println("Lag en ny treningsgruppe");

        System.out.println("Hva vil du kalle gruppen?");
        kategori = scanner.nextLine();
        System.out.println("Gi en beskrivelse av øvelsene i gruppen: (kan være tom)");
        beskrivelse = scanner.nextLine();
        System.out.println("Hvilken øvelse vil du legge i gruppen?");
        getAlleØvelser(scanner);

        String gruppeSql = String.format("INSERT INTO gruppe VALUES('%s', '%s', %d)", getKategori(), getBeskrivelse(), getØvelseId());

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
        System.out.println("Vil du legge til flere grupper?");
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


    private static ResultSet getResultSet(Connection conn, String query) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }




}