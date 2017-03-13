/**
 * Created by Navjot on 13.03.2017.
 */
public class Mål {

    //mål
    private String mål, målType;
    private int øvelseId;

    //constructor
    public Mål(String mål, String målType, int øvelseId) {
        this.mål = mål;
        this.målType = målType;
        this.øvelseId = øvelseId;
    }

    //getters
    public String getMål() { return mål; }
    public String getMåltype() { return målType; }
    public int getØvelseId() { return øvelseId; }

}