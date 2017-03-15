import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Øvelse {

    //connection
    private Connection conn;

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
    public Øvelse(Connection conn) {
        this.conn = conn;
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

    /**
     * Henter øktID for bruk i programmet
     * @param conn
     * @return øktID eller 0
     */
    public int getØktIdFromDB(Connection conn){
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

    /**
     * Legger til en øvelse, burde nok brukes i sammenheng med makeTreningsøkt
     * Kaller på addStyrke og addUtholdenhet
     * @param scanner
     * @throws SQLException
     */
    public void addØvelse(Scanner scanner) throws SQLException {
        System.out.println("Hvilken type øvelse vil du legge til? \n" +
                "(s)tyrke eller (u)tholdenhet?");
        String input = scanner.nextLine();
        switch (input){
            case ("s"):
                addStyrkeØvelse(scanner, øktId);
                break;
            case ("u"):
                addUtholdenhetØvelse(scanner, øktId);
                break;
            default:
                System.out.println("Ikke en gjenkjent type øvelse.");
                break;
        }
    }

    /**
     * Legger til en styrkeøvelse, med tilhørende felt
     * @param scanner
     * @param øktID
     * @throws SQLException
     */
    private void addStyrkeØvelse(Scanner scanner, int øktID) throws SQLException {
        System.out.println("Legg til en styrkeøvelse");
        System.out.println("Hva er navnet på øvelsen?");
        navn = scanner.nextLine();
        System.out.println("Skriv gjerne en beskrivelse av øvelsen (kan være tom)");
        beskrivelse = scanner.nextLine();
        System.out.println("Hvor mye vektbelastning hadde du? (kg)");
        belastning = scanner.nextInt();
        System.out.println("Hvor mange repetisjoner kjørte du?");
        repetisjoner = scanner.nextInt();
        System.out.println("Hvor mange sett gjennomførte du?");
        sett = scanner.nextInt();
        System.out.println("Hvilken muskelgruppe trente du?");
        muskelgruppe = scanner.nextLine();

        String øvelseSql = String.format("INSERT INTO øvelse VALUES('%s', '%s', %d)", getNavn(), getBeskrivelse(), getØktId());
        String styrkeSql = String.format("INSERT INTO styrke VALUES(%d, %d, %d, '%s')", getBelastning(), getRepetisjoner(), getSett(), getMuskelgruppe());

        System.out.println("Er du sikker på at du vil legge til denne styrkeøvelsen? (ja / nei)");
        String godkjenn =  scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(øvelseSql);
            stmt.executeUpdate(styrkeSql);
            ekstraØvelse(scanner);
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
    }

    /**
     * Legger til en utholdenhetsøvelse, med tilhørende felt
     * Vet ikke helt hvordan puls og gps skal fungere
     * @param scanner
     * @param øktID
     * @throws SQLException
     */
    private void addUtholdenhetØvelse(Scanner scanner, int øktID) throws SQLException {
        System.out.println("Legg til en utholdenhetsøvelse");
        System.out.println("Hva er navnet på øvelsen?");
        navn = scanner.nextLine();
        System.out.println("Skriv gjerne en beskrivelse av øvelsen (kan være tom)");
        beskrivelse = scanner.nextLine();
        System.out.println("Hva var lengden? (m)");
        lengde = scanner.nextInt();
        System.out.println("Hvor mange minutter brukte du?");
        antallMin = scanner.nextInt();
        //Mulig dette må endres på; vet ikke helt hvordan dette skal løses?
        System.out.println("Hvor høy puls hadde du??");
        puls = scanner.nextInt();
        System.out.println("Hva var GPS-dataene dine?");
        gps = scanner.nextLine();
        //Sjekk gjerne over dette ^

        String øvelseSql = String.format("INSERT INTO øvelse VALUES('%s', '%s', %d)", getNavn(), getBeskrivelse(), getØktId());
        String utholdenhetSql = String.format("INSERT INTO utholdenhet VALUES(%d, %d, %d, '%s')", getLengde(), getAntallMin(), getPuls(), getGPS());

        System.out.println("Er du sikker på at du vil legge til denne utholdenhetsøvelsen? (ja / nei)");
        String godkjenn = scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(øvelseSql);
            stmt.executeUpdate(utholdenhetSql);
            ekstraØvelse(scanner);
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
    }

    private void ekstraØvelse(Scanner scanner){
        System.out.println("Ønsker du å legge til en ekstra øvelse?");
        String godkjenn = scanner.nextLine();
        while (godkjenn.equals("ja")){
            addØvelse(scanner);
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
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