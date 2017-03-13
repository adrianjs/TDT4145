/**
 * Created by Navjot on 13.03.2017.
 */
public class Resultatlogg {

    //resultatlogg
    private String resultater, beste, målType;
    private int øvelseId;

    //constructor
    public Resultatlogg(String resultater, String beste, String målType, int øvelseId) {
        this.resultater = resultater;
        this.beste = beste;
        this.målType = målType;
        this.øvelseId = øvelseId;
    }

    //getters
    public String getResultater() { return resultater; }
    public String getBeste() { return beste; }
    public String getMålType() { return målType; }
    public int getØvelseId() { return øvelseId; }

}