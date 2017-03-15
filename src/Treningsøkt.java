import java.sql.*;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Treningsøkt {

    private Connection conn;
    private Statement stmt;

    //økt
    private String dato, tidspunkt, formål, tips;
    private String utendørsVær = null;
    private String innendørsLuft = null;
    private int utendørsTemperatur = null;
    private int innendørsTilskuere = null;
    private int varighet;

    //getters
    public String getDato() { return dato; }
    public String getTidspunkt() { return tidspunkt; }
    public int getVarighet() { return varighet; }
    public String getFormål() { return formål; }
    public String getTips() { return tips; }
    public String getUtendørsVær() { return utendørsVær; }
    public int getUtendørsTemperatur() { return utendørsTemperatur; }
    public String getInnendørsLuft() { return innendørsLuft; }
    public int getInnendørsTilskuere() { return innendørsTilskuere; }

    //legg til treningsøkt
    public static void addNyTreningsøkt(Connection conn, Scanner scanner){
        System.out.println("Legg til en treningsøkt");
        System.out.println("Når hadde du treningsøkten? (Dato: yyyy-mm-dd)");
        String dato = scanner.nextLine();
        System.out.println("Tidspunkt? (Tid: tt-mm)");
        String tidspunkt = scanner.nextLine();
        System.out.println("Hva var formålet med økten? (Kan være tom)");
        String formål = scanner.nextLine();
        System.out.println("Noen tips? (Kan være tom)");
        String tips = scanner.nextLine();
        System.out.println("Var du (i)nne eller (u)te?");
        if (scanner.nextLine() == "i") {
            System.out.println("Hvordan var luften innendørs?");
            String innendørsLuft = scanner.nextLine();
            System.out.println("Hvor mange tilskuere?");
            int innendørsTilskuere = scanner.nextInt();
        } else if (scanner.nextLine() == "u") {
            System.out.println("Hvordan var været utendørs?");
            String utendørsVær = scanner.nextLine();
            System.out.println("Hva var temperaturen utendørs?");
            int utendørsTemperatur = scanner.nextInt()
        }

        String øktSql = String.format("INSERT INTO treningsøkt VALUES('%s', '%s', %d, '%s', '%s', '%s', %d, '%s', %d)", getDato(), getTidspunkt(), getVarighet(), getFormål(), getTips(), getInnendørsLuft(), getInnendørsTilskuere(), getUtendørsVær(), getUtendørsTemperatur());

        System.out.println("Er du sikker på at du vil legge til denne treningsøkten? (ja / nei)");
        String godkjenn =  scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(øktSql);
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
    }

}