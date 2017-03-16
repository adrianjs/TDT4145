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
    public Gruppe(Connection conn) {
        this.conn = conn;
    }

    //getters
    public String getKategori() { return kategori; }
    public String getBeskrivelse() { return beskrivelse; }
    public int getSubGruppeId() { return subGruppeId; }
    public int getØvelseId() { return øvelseId; }

    public void makeGruppe(Scanner scanner){
        System.out.println("Hva vil du kalle gruppen?");
        kategori = scanner.nextLine();
        System.out.println("Gi en beskrivelse av øvelsene i gruppen: (kan være tom)");
        beskrivelse = scanner.nextLine();

    }

}