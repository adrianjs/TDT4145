import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Gruppe {

    //gruppe
    private String kategori, beskrivelse;
    private int subGruppeId, øvelseId;

    //constructor
    public Gruppe(kategori, beskrivelse, subGruppeId, øvelseId) {
        this.kategori = kategori;
        this.beskrivelse = beskrivelse;
        this.subGruppeId = subGruppeId;
        this.øvelseId = øvelseId;
    }

    //getters
    public String getKategori() { return kategori; }
    public String getBeskrivelse() { return beskrivelse; }
    public int getSubGruppeId() { return subGruppeId; }
    public int getØvelseId() { return øvelseId; }

}