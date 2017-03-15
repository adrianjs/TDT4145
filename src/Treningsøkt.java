import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Created by Navjot on 13.03.2017.
 */
public class Treningsøkt {

    //connection
    private Connection conn;

    //økt
    private String formål, tips;
    private int varighet;
    private LocalDateTime datoTid;

    //innendørs
    private String innendørsLuft;
    private int innendørsTilskuere;

    //utendørs
    private String utendørsVær;
    private int utendørsTemperatur;

    //constructor
    public Treningsøkt(Connection conn) {
        this.conn = conn;
    }

    //getters
    public LocalDateTime getDatoTid() { return datoTid; }
    public int getVarighet() { return varighet; }
    public String getFormål() { return formål; }
    public String getTips() { return tips; }
    public String getUtendørsVær() { return utendørsVær; }
    public int getUtendørsTemperatur() { return utendørsTemperatur; }
    public String getInnendørsLuft() { return innendørsLuft; }
    public int getInnendørsTilskuere() { return innendørsTilskuere; }

    //legg til treningsøkt
    public void addNyTreningsøkt(Scanner scanner) throws SQLException {
        System.out.println("Legg til en treningsøkt");
        System.out.println("Når hadde du treningsøkten? (Format: yyyy-mm-dd-hh-mm)");
        String dateTime = scanner.nextLine();
        String[] oppdeltDatoTid = dateTime.split("-");
        int dateYear = Integer.parseInt(oppdeltDatoTid[0]);
        int dateMonth = Integer.parseInt(oppdeltDatoTid[1]);
        int dateDay = Integer.parseInt(oppdeltDatoTid[2]);
        int timeHour = Integer.parseInt(oppdeltDatoTid[3]);
        int timeMinute = Integer.parseInt(oppdeltDatoTid[4]);
        datoTid = LocalDateTime.of(dateYear, dateMonth, dateDay, timeHour, timeMinute);
        System.out.println("Hvor lenge varte økten?");
        varighet = scanner.nextInt();
        System.out.println("Hva var formålet med økten? (Kan være tom)");
        formål = scanner.nextLine();
        System.out.println("Noen tips? (Kan være tom)");
        tips = scanner.nextLine();
        System.out.println("Var du (i)nne eller (u)te?");
        if (scanner.nextLine() == "i") {
            System.out.println("Hvordan var luften innendørs?");
            innendørsLuft = scanner.nextLine();
            System.out.println("Hvor mange tilskuere?");
            innendørsTilskuere = scanner.nextInt();
        } else if (scanner.nextLine() == "u") {
            System.out.println("Hvordan var været utendørs?");
            utendørsVær = scanner.nextLine();
            System.out.println("Hva var temperaturen utendørs?");
            utendørsTemperatur = scanner.nextInt();
        }

        String øktSql = String.format("INSERT INTO treningsøkt VALUES('%d', %d, '%s', '%s', '%s', %d, '%s', %d)", getDatoTid(), getVarighet(), getFormål(), getTips(), getInnendørsLuft(), getInnendørsTilskuere(), getUtendørsVær(), getUtendørsTemperatur());

        System.out.println("Er du sikker på at du vil legge til denne treningsøkten? (ja / nei)");
        String godkjenn =  scanner.nextLine();
        if (godkjenn.equals("ja")){
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(øktSql);
            System.out.println("Vil du legge til flere øvelser? (ja/nei)");
            String nyØvelse = scanner.nextLine();
            if (nyØvelse.equals("ja")){
                addNyTreningsøkt(scanner);
            } else {
                System.out.println("Takk for denne gang.");
            }
        } else {
            System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
        }
    }

}