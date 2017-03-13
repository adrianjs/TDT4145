import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Resultatlogg {

    //resultatlogg
    private String resultater, beste, måltype;
    private int øvelseId;

    //constructor
    public ResultatLogg(resultater, beste, måltype, øvelseId) {
        this.resultater = resultater;
        this.beste = beste;
        this.måltype = måltype;
        this.øvelseId = øvelseId;
    }

    //getters
    public String getResultater() { return resultater; }
    public String getBeste() { return beste; }
    public String getMåltype() { return måltype; }
    public int getØvelseId() { return øvelseId; }

}