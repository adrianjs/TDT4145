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
    public Mål(Connection conn, Statement stmt) {
        this.conn = conn;
        this.stmt = stmt;
    }

    //getters
    public String getKategori() { return kategori; }
    public String getBeskrivelse() { return beskrivelse; }
    public int getSubGruppeId() { return subGruppeId; }
    public int getØvelseId() { return øvelseId; }

}