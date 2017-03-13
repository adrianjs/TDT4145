import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Mål {

    //mål
    private String mål, målType;
    private int øvelseId;

    //constructor
    public Mål(mål, måltype, øvelseId) {
        this.mål = mål;
        this.måltype = måltype;
        this.øvelseId = øvelseId;
    }

    //getters
    public String getMål() { return mål; }
    public String getMåltype() { return måltype; }
    public int getØvelseId() { return øvelseId; }

}