import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Øvelse {

    //øvelse
    private String navn, beskrivelse;
    private int øktId;

    //utholdenhet
    int lengde, antallMin, puls;
    String gps;

    //styrke
    int belastning, repetisjoner, sett;
    String muskelgruppe;

    //constructer
    public Øvelse (navn, beskrivelse, øktId, type, lengde, antallMin, puls, gps, belastning, repetisjoner, sett, muskelgruppe) {
        this.øktId = øktId;
        this.navn = navn;
        this.beskrivelse = beskrivelse;
        if (type == 'u') {
            this.lengde = lengde;
            this.antallMin = antallMin;
            this.puls = puls;
            this.gps = gps;
        } else if (type == 's') {
            this.belastning = belastning;
            this.repetisjoner = repetisjoner;
            this.sett = sett;
            this.muskelgruppe = muskelgruppe;
        }
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

}