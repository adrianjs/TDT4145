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
    private int øvelseId;

    //constructor
    public Mål(Connection conn) {
        this.conn = conn;
    }

    //getters
    public String getMål() { return mål; }
    public String getMåltype() { return målType; }
    public int getØvelseId() { return øvelseId; }

    /**
     * Skal legge til mål i databasen
     * Må kanskje ha øktID eller øvelseID som parameter
     * @param conn
     * @param scanner
     */
    private static void addMålToØvelse(Scanner scanner){

    }

}