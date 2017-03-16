import java.sql.*;
import java.util.Scanner;

/**
 * Created by Adrian on 09.03.2017.
 */
public class TUI {

    /**
     * Brukes for å koble til databasen.
     * @param conn
     * @return
     * @throws Exception fordi det er så utrolig mange exceptions som kan throwes at det fyller skjermen
     */
    public static Connection connect(Connection conn) throws Exception {
        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/tdt4145";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(url, userName, password);
        if (conn != null){
            System.out.println("Tilkoblet til databasen.");
            return conn;
        } else {
            System.out.println("Tilkobling mislyktes.");
            return conn;
        }
    }

    /**
     * The meat 'n potatoes av prosjektet.
     * Er selve hoved-UI'et, som gjerne kan endres på.
     * Switch virket som det beste, er åpen for innspill
     * @param conn
     * @throws SQLException
     */
    public static void runUI(Connection conn) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String mainPage = " == Hovedside == \n " +
                "Vennligst velg en av disse kategoriene: \n" +
                "1. Registrer ny treningsøkt \n" +
                "2. Registrer mål for øvelser \n" +
                "3. Tidligere økter og resultater \n" +
                "4. Legg til nye øvelser \n" +
                "5. Lag ny øvelsesgruppe \n" +
                "6. Treningslogg \n" +
                "7. Legg til resultatlogg \n" +
                "8. Treningsrapport \n" +
                "9. Avslutt";

        boolean running = true;
        while (running){
            System.out.println(mainPage);
            String input = scanner.nextLine();
            switch (input){
                case ("9"):
                    System.out.println("Avslutter programmet..");
                    running = false;
                    break;
                case ("1"):
                    System.out.println("Registrer ny treningsøkt");
                    Treningsøkt økt = new Treningsøkt(conn);
                    økt.addNyTreningsøkt(scanner);
                    break;
                case ("2"):
                    System.out.println("Registrer øvelser med mål");
                    Mål mål = new Mål(conn);
                    mål.getAlleØkter(scanner);
                    break;
                case ("3"):
                    System.out.println("Tidligere økter og resultater");
                    //showTidligereØkter()
                    break;
                case ("4"):
                    System.out.println("Legg til ny øvelse");
                    Øvelse øvelse = new Øvelse(conn);
                    øvelse.getAlleØkter(scanner);
                    //velg treningsøkt først
                    break;
                case ("5"):
                    System.out.println("Legg til ny øvelsesgruppe");
                    Gruppe gruppe = new Gruppe(conn);
                    gruppe.makeGruppe(scanner);
                    break;
                case ("6"):
                    System.out.println("Generer treningslogg:");
                    Treningsøkt treningsøkt = new Treningsøkt(conn);
                    treningsøkt.genererStatistikk();
                    break;
                case ("7"):
                    System.out.println("Legg til resultatlogg");
                    Resultatlogg resultatlogg = new Resultatlogg(conn);
                    resultatlogg.getAlleØkter(scanner);
                    //velg øvelse først
                    break;
                case ("8"):
                    System.out.println("Generer rapport:");
                    Resultatlogg userReport = new Resultatlogg(conn);
                    userReport.generateRapport(scanner);
                    break;
                }
            }
        }

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = connect(conn);
            runUI(conn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
}
