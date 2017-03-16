import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Treningsøkt {

    //connection
    private Connection conn;

    //økt
    private String formål, tips;
    private int varighet, øktId;
    private Timestamp datoTid;

    //innendørs
    private String innendørsLuft;
    private int innendørsTilskuere;

    //utendørs
    private String utendørsVær;
    private int utendørsTemperatur;

    //constructor
    public Treningsøkt(Connection conn) {
        this.conn = conn;
        this.øktId = getØktIdFromDB(conn);
    }

    //getters
    public Timestamp getDatoTid() { return datoTid; }
    public int getVarighet() { return varighet; }
    public String getFormål() { return formål; }
    public String getTips() { return tips; }
    public String getUtendørsVær() { return utendørsVær; }
    public int getUtendørsTemperatur() { return utendørsTemperatur; }
    public String getInnendørsLuft() { return innendørsLuft; }
    public int getInnendørsTilskuere() { return innendørsTilskuere; }
    public int getØktId() { return øktId; }

    //legg til treningsøkt
    @SuppressWarnings("deprecation")
	public void addNyTreningsøkt(Scanner scanner) throws SQLException {
        System.out.println("Legg til en treningsøkt");
        System.out.println("Når hadde du treningsøkten? (Format: yyyy-mm-dd-hh-mm)");
        String dateTime = scanner.nextLine();
        String[] oppdeltDatoTid = dateTime.split("-");
        int dateYear = Integer.parseInt(oppdeltDatoTid[0]);
        int dateMonth = Integer.parseInt(oppdeltDatoTid[1]);
        int dateDay = Integer.parseInt(oppdeltDatoTid[2]);
        int timeHour = Integer.parseInt(oppdeltDatoTid[3]);
        int timeMinute = Integer.parseInt(oppdeltDatoTid[4]);
        datoTid = new Timestamp(dateYear-1900, dateMonth-1, dateDay, timeHour, timeMinute, 0, 0);
        System.out.println("Hvor lenge varte økten?");
        varighet = Integer.parseInt(scanner.nextLine());
        System.out.println("Hva var formålet med økten? (Kan være tom)");
        formål = scanner.nextLine();
        System.out.println("Noen tips? (Kan være tom)");
        tips = scanner.nextLine();
        System.out.println("Var du (i)nne eller (u)te?");
        String reply = scanner.nextLine();
        if (reply.equals("i")) {
            System.out.println("Hvordan var luften innendørs?");
            innendørsLuft = scanner.nextLine();
            System.out.println("Hvor mange tilskuere?");
            innendørsTilskuere = Integer.parseInt(scanner.nextLine());;
        } else if (reply.equals("u")) {
            System.out.println("Hvordan var været utendørs?");
            utendørsVær = scanner.nextLine();
            System.out.println("Hva var temperaturen utendørs?");
            utendørsTemperatur = Integer.parseInt(scanner.nextLine());
        }

        String øktSql = String.format("INSERT INTO treningsøkt VALUES(%d,'" + getDatoTid() + "', %d, '%s', '%s', '%s', %d, '%s', %d)", getØktId(), getVarighet(), getFormål(), getTips(), getUtendørsVær(), getUtendørsTemperatur(), getInnendørsLuft(), getInnendørsTilskuere());
        
        System.out.println("Er du sikker på at du vil legge til denne treningsøkten? (ja / nei)");
        String godkjenn =  scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(øktSql);
            Øvelse øvelse = new Øvelse(conn);
            øvelse.addØvelse(scanner);
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
    }
    
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
                return øktID;
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

    public void genererStatistikk() {
    	int antallTreningsøkter = 0;
    	double minutter = 0;
        String query = "SELECT øktId FROM Treningsøkt WHERE datotid >= now()-INTERVAL 1 MONTH AND YEAR(datotid)=YEAR(CURDATE())"; // skal vise hvor mange ganger du har trent den siste måneden
        String query2 = "SELECT varighet FROM Treningsøkt WHERE datotid >= now()-INTERVAL 1 MONTH AND YEAR(datotid)=YEAR(CURDATE())"; //Henter antall timer som er brukt på trening siste måned
        try {
            ResultSet rs = getResultSet(conn, query);
            while (rs.next()) {
                antallTreningsøkter++; //antar det må være getInt her fordi jeg henter count(*)?
            }
            ResultSet antallMinutter = getResultSet(conn, query2);
            while(antallMinutter.next()) {
            	minutter += antallMinutter.getInt("varighet"); //antar det samme har
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        double timer = minutter/60;
        System.out.println(String.format("Antall treningsøkter siste måned: " + antallTreningsøkter + "\nAntall timer trent siste måned: " + 
        timer));
    }

}