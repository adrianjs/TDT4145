import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Øvelse {

    //connection
    private Connection conn;
    private Statement stmt;

    //øvelse
    private String navn, beskrivelse;
    private int øktId;

    //utholdenhet
    private int lengde, antallMin, puls;
    private String gps;

    //styrke
    private int belastning, repetisjoner, sett;
    private String muskelgruppe;

    //constructor
    public Øvelse(Connection conn, Statement stmt) {
        this.conn = conn;
        this.stmt = stmt;
        this.øktId = getØktIdFromDB(conn);
    }

    //getters
    public String getNavn() { return navn; }
    public String getBeskrivelse() { return beskrivelse; }
    public int getØktId() { return øktId; }
    public int getLengde() { return lengde; }
    public int getAntallMin() { return antallMin; }
    public int getPuls() { return puls; }
    public String getGPS() { return gps; }
    public int getBelastning() { return belastning; }
    public int getRepetisjoner() { return repetisjoner; }
    public int getSett() { return sett; }
    public String getMuskelgruppe() { return muskelgruppe; }

    private static int getØktIdFromDB(Connection conn){
        String query = "SELECT øktID FROM treningsøkt ORDER BY øktID DESC LIMIT 1";
        try {
            ResultSet rs = getResultSet(conn, query);
            if (rs.next()){
                int øktID = rs.getInt("øktID") + 1;
                System.out.println("ØktID: " + øktID);
                return øktID;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static void addØvelse(Connection conn, Scanner scanner, int øktID) throws SQLException {
        int øvelseID = getØvelseID(conn);
        System.out.println("Hvilken type øvelse vil du legge til? \n" +
                "(s)tyrke eller (u)tholdenhet?");
        String input = scanner.nextLine();
        switch (input){
            case ("s"):
                addStyrkeØvelse(conn, scanner, øktID, øvelseID);
                break;
            case ("u"):
                addUtholdenhetØvelse(conn, scanner, øktID, øvelseID);
                break;
            default:
                System.out.println("Ikke en gjenkjent type øvelse.");
                break;
        }
    }

    private static void addStyrkeØvelse(Connection conn, Scanner scanner, int øktID, int øvelseID) throws SQLException {
        System.out.println("Legg til en styrkeøvelse");
        System.out.println("Hva er navnet på øvelsen?");
        String navn = scanner.nextLine();
        System.out.println("Skriv gjerne en beskrivelse av øvelsen (kan være tom)");
        String beskrivelse = scanner.nextLine();
        System.out.println("Hvor mye vektbelastning hadde du? (kg)");
        int belastning = scanner.nextInt();
        System.out.println("Hvor mange repetisjoner kjørte du?");
        int repetisjoner = scanner.nextInt();
        System.out.println("Hvor mange sett gjennomførte du?");
        int sett = scanner.nextInt();
        System.out.println("Hvilken muskelgruppe trente du?");
        String muskel = scanner.nextLine();

        String øvelseSql = String.format("INSERT INTO øvelse VALUES(%d, '%s', '%s', %d)", øvelseID, navn, beskrivelse, øktID);
        String styrkeSql = String.format("INSERT INTO styrke VALUES(%d, %d, %d, %d, '%s')", øvelseID, belastning, repetisjoner, sett, muskel);

        System.out.println("Er du sikker på at du vil legge til denne styrkeøvelsen? (ja / nei)");
        String godkjenn =  scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(øvelseSql);
            stmt.executeUpdate(styrkeSql);
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
    }

    private static void addUtholdenhetØvelse(Connection conn, Scanner scanner, int øktID, int øvelseID) throws SQLException {
        System.out.println("Legg til en utholdenhetsøvelse");
        System.out.println("Hva er navnet på øvelsen?");
        String navn = scanner.nextLine();
        System.out.println("Skriv gjerne en beskrivelse av øvelsen (kan være tom)");
        String beskrivelse = scanner.nextLine();
        System.out.println("Hva var lengden? (m)");
        int lengde = scanner.nextInt();
        System.out.println("Hvor mange minutter brukte du?");
        int minutter = scanner.nextInt();
        //Mulig dette må endres på; vet ikke helt hvordan dette skal løses?
        System.out.println("Hvor høy puls hadde du??");
        int puls = scanner.nextInt();
        System.out.println("Hva var GPS-dataene dine?");
        String gps = scanner.nextLine();
        //Sjekk gjerne over dette ^

        String øvelseSql = String.format("INSERT INTO øvelse VALUES(%d, '%s', '%s', %d)", øvelseID, navn, beskrivelse, øktID);
        String utholdenhetSql = String.format("INSERT INTO utholdenhet VALUES(%d, %d, %d, %d, '%s')", øvelseID, lengde, minutter, puls, gps);

        System.out.println("Er du sikker på at du vil legge til denne utholdenhetsøvelsen? (ja / nei)");
        String godkjenn = scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(øvelseSql);
            stmt.executeUpdate(utholdenhetSql);
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
    }

}