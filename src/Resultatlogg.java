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
    private int øvelseId;

    //constructor
    public Resultatlogg(Connection conn, Statement stmt) {
        this.conn = conn;
        this.stmt = stmt;
    }

    //getters
    public String getResultater() { return resultater; }
    public String getBeste() { return beste; }
    public String getMålType() { return målType; }
    public int getØvelseId() { return øvelseId; }

}