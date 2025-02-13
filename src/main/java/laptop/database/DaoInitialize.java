package laptop.database;

import laptop.utilities.ConnToDb;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoInitialize {
    //class used for reduce duplication

    private static final String LIBRO = "libro";
    private static final String GIORNALE = "giornale";
    private static final String RIVISTA = "rivista";
    private static final String UTENTE="users";
    private static final String NEGOZIO="negozio";
    private static final String FATTURA="fattura";
    private static final String PAGAMENTO="pagamento";
    private static final String CARTACREDITO="cartacredito";
    private static final String FILESQL="FileSql/";

    private   String query ;
    private  String filePopola = "";



    public void inizializza(String type) throws SQLException,FileNotFoundException {
        String nomeFile;





        switch (type) {
            case LIBRO -> {
                nomeFile = FILESQL + LIBRO + ".sql";
                query = "select count(*) from ISPW.LIBRO;";
                filePopola = FILESQL + "popolaLibro.sql";
            }
            case GIORNALE -> {
                nomeFile = FILESQL + GIORNALE + ".sql";
                query = "select count(*) from ISPW.GIORNALE;";
                filePopola = FILESQL + "popolaGiornale.sql";

            }
            case RIVISTA -> {
                nomeFile = FILESQL + RIVISTA + ".sql";
                query = "select count(*) from ISPW.RIVISTA;";
                filePopola = FILESQL + "popolaRivista.sql";

            }
            case UTENTE -> {
                nomeFile = FILESQL + UTENTE + ".sql";
                query = "select count(*) from ISPW.USERS;";
                filePopola = FILESQL + "popolaUsers.sql";
            }
            case NEGOZIO -> {
                nomeFile = FILESQL + NEGOZIO + ".sql";
                query = "select count(*) from ISPW.NEGOZIO;";
                filePopola = FILESQL + "popolaNegozio.sql";
            }
            case FATTURA ->  nomeFile = FILESQL + FATTURA + ".sql";
            case PAGAMENTO ->   nomeFile = FILESQL + PAGAMENTO + ".sql";
            case CARTACREDITO -> nomeFile = FILESQL + CARTACREDITO + ".sql";

            default -> {
                return;
            }

        }

        //type is libro,giornale,rivista
        ConnToDb.generalConnection();
        //creo tabella


        try (Connection conn = ConnToDb.connectionToDB()) {


            Reader reader = new BufferedReader(new FileReader(nomeFile));
            ScriptRunner sr = new ScriptRunner(conn);
            sr.setSendFullScript(false);
            sr.runScript(reader);


        }
        if (type.equals(FATTURA) || type.equals(PAGAMENTO)|| type.equals(CARTACREDITO)){
            Logger.getLogger("inizializza db pagamento/fattura/cartacredito").log(Level.INFO," tables not pupulated");
        }
        else {

            //vedo se tabella vuoita
            int row = 0;
            try (Connection conn = ConnToDb.connectionToDB();
                 PreparedStatement preQ = conn.prepareStatement(query)) {
                ResultSet rs = preQ.executeQuery();
                if (rs.next())
                    row = rs.getInt(1);
            }
            if (row == 0) {
                try (Connection conn = ConnToDb.connectionToDB()) {
                    Reader reader = new BufferedReader(new FileReader(filePopola));
                    ScriptRunner sr = new ScriptRunner(conn);
                    sr.setSendFullScript(false);
                    sr.runScript(reader);
                }
            }

        }
    }

}
