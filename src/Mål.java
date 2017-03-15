import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Mål {

    //connection
    private Connection conn;
    private Statement stmt;

    //mål
    private String mål, målType;
    private int øvelseId;

    //constructor
    public Mål(Connection conn, Statement stmt) {
        this.conn = conn;
        this.stmt = stmt;
    }

    //getters
    public String getMål() { return mål; }
    public String getMåltype() { return målType; }
    public int getØvelseId() { return øvelseId; }

}