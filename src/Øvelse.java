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

    private static int getØktId(Connection conn){
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

}