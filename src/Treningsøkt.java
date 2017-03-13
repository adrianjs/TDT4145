/**
 * Created by Navjot on 13.03.2017.
 */
public class Treningsøkt {

    //økt
    private String dato, tidspunkt, formål, tips, utendørsVær, innendørsLuft, navn;
    private int varighet, utendørsTemperatur, innendørsTilskuere, øktId;

    //constructer
    public Treningsøkt (String dato, String tidspunkt, int varighet, String formål, String tips, String utendørsVær, int utendørsTemperatur, String innendørsLuft, int innendørsTilskuere, boolean malFor, int øktId, String navn) {
        this.dato = dato;
        this.tidspunkt = tidspunkt;
        this.varighet = varighet;
        this.formål = formål;
        this.tips = tips;
        this.utendørsVær = utendørsVær;
        this.utendørsTemperatur = utendørsTemperatur;
        this.innendørsLuft = innendørsLuft;
        this.innendørsTilskuere = innendørsTilskuere;
        if (malFor) {
            this.øktId = øktId;
            this.navn = navn;
        }
    }

    //getters
    public String getDato() { return dato; }
    public String getTidspunkt() { return tidspunkt; }
    public int getVarighet() { return varighet; }
    public String getFormål() { return formål; }
    public String getTips() { return tips; }
    public String getUtendørVær() { return utendørsVær; }
    public int getUtendørsTemperatur() { return utendørsTemperatur; }
    public String getInnendørsLuft() { return innendørsLuft; }
    public int getInnendørsTilskuere() { return innendørsTilskuere; }

}