import java.sql.*;
import java.util.Scanner;

/**
 * Created by Adrian on 09.03.2017.
 */
public class TUI {

	private static Connection conn;
	private Statement stmt;

	/**
	 * Brukes for å koble til databasen.
	 * @param conn
	 * @return
	 * @throws Exception fordi det er så utrolig mange exceptions som kan throwes at det fyller skjermen
	 */
	public static Connection connect(Connection conn1) throws Exception {
		conn = conn1;
		String userName = "root";
		String password = "root";
		String url = "jdbc:mysql://localhost/tdt4145";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url, userName, password);
		conn.setSchema("tdt4145");
		System.out.print("ree");
		System.out.print(conn.getSchema());
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
				"7. Treningsrapport \n" +
				"8. Avslutt";


		boolean running = true;

		while (running){
			System.out.println(mainPage);
			String input = scanner.nextLine();
			switch (input){
				case ("8"):
					System.out.println("Avslutter programmet..");
					running = false;
					break;
				case ("1"):
					System.out.println("Registrer ny treningsøkt");
					//addNyTreningsøkt()
					break;
				case ("2"):
					System.out.println("Registrer øvelser med mål");
					//addMålToØvelse()
					break;
				case ("3"):
					System.out.println("Tidligere økter og resultater");
					//showTidligereØkter()
					break;
				case ("4"):
					System.out.println("Legg til ny øvelse");
					//addØvelse()
					break;
				case ("5"):
					System.out.println("Legg til ny øvelsesgruppe");
					//makeØvelseGruppe()
					break;
				case ("6"):
					System.out.println("Treningslogg");
					getTreningslogg(conn);
					break;
				case ("7"):
					System.out.println("Generer rapport:");
					Resultatlogg userReport = new Resultatlogg(conn);
					userReport.generateRapport(scanner);
					break;


			}
		}
	}

	/**
	 * Henter øktID for bruk i programmet
	 * @param conn
	 * @return øktID eller 0
	 */
	private static int getØktId(Connection conn){
		String query = "SELECT øktID FROM treningsøkt ORDER BY øktID DESC LIMIT 1";
		try {
			ResultSet rs = getResultSet(conn, query);
			if (rs.next()){
				int øktID = rs.getInt("øktID") + 1;
				System.out.println("ØktID: " + øktID);
				return øktID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Henter øvelseID for bruk i programmet
	 * @param conn
	 * @return øvelseID eller 0
	 */
	private static int getØvelseID(Connection conn){
		String query = "SELECT øvelseID FROM øvelse ORDER BY øvelseID DESC LIMIT 1";
		try {
			ResultSet rs = getResultSet(conn, query);
			if (rs.next()){
				int øvelseID = rs.getInt("øvelseID") + 1;
				System.out.println("ØvelseID: " + øvelseID);
				return øvelseID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Skal hente alle øktene som ligger lagret i databasen, det blir vel rett? Eller?
	 * @param conn
	 */
	private static void showTidligereØkter(Connection conn){
		// kanskje String query = "SELECT * FROM resultatlogg"; ?
		// Hent det som trengs for å vise tidligere økter og resultater
		// bruk gjerne ResultSet, se på get...Id-funksjonene for inspirasjon
	}

	/**
	 * Legger til en ny treningsøkt
	 * Kall inne i funksjonen bruker makeTreningsøkt for å spesifisere
	 * @param conn
	 * @param scanner
	 */
	private static void addNyTreningsøkt(Connection conn, Scanner scanner){
		int øktID = getØktId(conn);
		makeTreningsøkt(conn, scanner, øktID);
	}

	/**
	 * Trenger ganske mye mere arbeid, har lagt til et grunnlag. Kanskje det funker, kanskje ikke.
	 * Ikke vær redd for å endre på noe :p
	 * @param conn
	 * @param scanner
	 * @param øktID
	 */
	private static void makeTreningsøkt(Connection conn, Scanner scanner, int øktID){
		System.out.println("Når var treningen din?");
		// Går det ann å scanne inn et datetime-objekt? Trenger ihvertfall å få inn dato fra brukeren
		// på et fornuftig vis
		// String sql = "INSERT INTO treningsøkt VALUES(" + øktID + ", " + datoen)
	}

	/**
	 * Legger til en øvelse, burde nok brukes i sammenheng med makeTreningsøkt
	 * Kaller på addStyrke og addUtholdenhet
	 * @param conn
	 * @param scanner
	 * @param øktID
	 * @throws SQLException
	 */
	private static void addØvelse(Connection conn, Scanner scanner, int øktID) throws SQLException {
		int øvelseID = getØvelseID(conn);
		System.out.println("Hvilken type øvelse vil du legge til? \n" +
				"(s)tyrke eller (u)tholdenhet?");
		String input = scanner.nextLine();
		switch (input){
			case ("s"):
				addStyrkeØvelse(conn, scanner, øktID, øvelseID);
				break;
			case ("u"):
				addUtholdenhetØvelse(conn, scanner, øktID, øvelseID);
				break;
			default:
				System.out.println("Ikke en gjenkjent type øvelse.");
				break;
		}
	}

	/**
	 * Legger til en styrkeøvelse, med tilhørende felt
	 * @param conn
	 * @param scanner
	 * @param øktID
	 * @param øvelseID
	 * @throws SQLException
	 */
	private static void addStyrkeØvelse(Connection conn, Scanner scanner, int øktID, int øvelseID) throws SQLException {
		System.out.println("Legg til en styrkeøvelse");
		System.out.println("Hva er navnet på øvelsen?");
		String navn = scanner.nextLine();
		System.out.println("Skriv gjerne en beskrivelse av øvelsen (kan være tom)");
		String beskrivelse = scanner.nextLine();
		System.out.println("Hvor mye vektbelastning hadde du? (kg)");
		int belastning = scanner.nextInt();
		System.out.println("Hvor mange repetisjoner kjørte du?");
		int repetisjoner = scanner.nextInt();
		System.out.println("Hvor mange sett gjennomførte du?");
		int sett = scanner.nextInt();
		System.out.println("Hvilken muskelgruppe trente du?");
		String muskel = scanner.nextLine();

		String øvelseSql = String.format("INSERT INTO øvelse VALUES(%d, '%s', '%s', %d)", øvelseID, navn, beskrivelse, øktID);
		String styrkeSql = String.format("INSERT INTO styrke VALUES(%d, %d, %d, %d, '%s')", øvelseID, belastning, repetisjoner, sett, muskel);

		System.out.println("Er du sikker på at du vil legge til denne styrkeøvelsen? (ja / nei)");
		String godkjenn =  scanner.nextLine();
		if (godkjenn.equals("ja")){
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(øvelseSql);
			stmt.executeUpdate(styrkeSql);
		} else {
			System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
		}
	}

	/**
	 * Legger til en utholdenhetsøvelse, med tilhørende felt
	 * Vet ikke helt hvordan puls og gps skal fungere
	 * @param conn
	 * @param scanner
	 * @param øktID
	 * @param øvelseID
	 * @throws SQLException
	 */
	private static void addUtholdenhetØvelse(Connection conn, Scanner scanner, int øktID, int øvelseID) throws SQLException {
		System.out.println("Legg til en utholdenhetsøvelse");
		System.out.println("Hva er navnet på øvelsen?");
		String navn = scanner.nextLine();
		System.out.println("Skriv gjerne en beskrivelse av øvelsen (kan være tom)");
		String beskrivelse = scanner.nextLine();
		System.out.println("Hva var lengden? (m)");
		int lengde = scanner.nextInt();
		System.out.println("Hvor mange minutter brukte du?");
		int minutter = scanner.nextInt();
		//Mulig dette må endres på; vet ikke helt hvordan dette skal løses?
		System.out.println("Hvor høy puls hadde du??");
		int puls = scanner.nextInt();
		System.out.println("Hva var GPS-dataene dine?");
		String gps = scanner.nextLine();
		//Sjekk gjerne over dette ^

		String øvelseSql = String.format("INSERT INTO øvelse VALUES(%d, '%s', '%s', %d)", øvelseID, navn, beskrivelse, øktID);
		String utholdenhetSql = String.format("INSERT INTO utholdenhet VALUES(%d, %d, %d, %d, '%s')", øvelseID, lengde, minutter, puls, gps);

		System.out.println("Er du sikker på at du vil legge til denne utholdenhetsøvelsen? (ja / nei)");
		String godkjenn = scanner.nextLine();
		if (godkjenn.equals("ja")){
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(øvelseSql);
			stmt.executeUpdate(utholdenhetSql);
		} else {
			System.out.println("Avbrutt, ingenting ble lagt til i databasen.");
		}
	}

	/**
	 * Usecase 2.
	 * Creates a report according to the rules set out in usecase two
	 * @param conn
	 *
	 */
	private static void generateReport(Connection conn){

	}

	/**
	 * Skal legge til mål i databasen
	 * Må kanskje ha øktID eller øvelseID som parameter
	 * @param conn
	 * @param scanner
	 */
	private static void addMålToØvelse(Connection conn, Scanner scanner){

	}

	/**
	 * Skal lage grupper av øvelser
	 * Må kanskje ha øvelseID som parameter for å hente øvelser? Er sliten
	 * @param conn
	 * @param scanner
	 */
	private static void makeØvelseGruppe(Connection conn, Scanner scanner){

	}

	/**
	 * Regner med at vi her bare kan hente det som ligger i resultatlogg?
	 * @param conn
	 */
	private static void getTreningslogg(Connection conn){
		String query = "SELECT * FROM resultatlogg";
		try {
			ResultSet rs = getResultSet(conn, query);
			while(rs.next()){
				String resultater = rs.getString("Resultater");
				String beste = rs.getString("Beste");
				String måltype = rs.getString("Måltype");
				int øvelseID = rs.getInt("Øvelse_øvelseID");
				int øktID = rs.getInt("Øvelse_øktID");

				System.out.println(String.format("ØktID: %d \n" +
						"ØvelseID: %d \n" +
						"Resultater: %s \n" +
						"Beste: %s \n" +
						"Måltype: %s \n\n"));


			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Henter ResultSet, eller data fra databasen for bruk i diverse get-operasjoner
	 * @param conn
	 * Koblingen til databasen
	 * @param query
	 * SQL-queryen som brukes for å hente data fra databasen
	 * @return ResultSet
	 * @throws SQLException
	 */
	private static ResultSet getResultSet(Connection conn, String query) throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeQuery(query);
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