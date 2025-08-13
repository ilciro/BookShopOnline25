package laptop.database.secondouclogin.users;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.DaoInitialize;
import laptop.model.user.TempUser;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaUtente {

    public boolean inserisciUtente(TempUser tu)  { return tu.getId()!=-1;}
    public ObservableList<TempUser> getUserData() { return FXCollections.observableArrayList();}
    public boolean removeUserByIdEmailPwd(TempUser tu)  { return tu.getId()!=-0;
    }



    public void initializza()  {
        getException();

        Logger.getLogger("inizializza persistenza utente").log(Level.INFO,"peristenza utente initialize");
        DaoInitialize daoI=new DaoInitialize();
        daoI.inizializza("utenti");

    }
    private void getException() {
        Logger.getLogger("persistenza utente").log(Level.INFO, "checking files...");
        try {
            if (!Files.exists(Path.of("report/reportUtente.csv"))) throw new CsvValidationException("CSVException utente");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException utente");
            if (!Files.exists(Path.of("memory/serializzazioneUtente.ser"))) throw new ClassNotFoundException("ClassNotFoundException utente");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file utente").log(Level.SEVERE, "exception utente csv :", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database utente").log(Level.SEVERE, "exception utente database :", e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria utente").log(Level.SEVERE, "exception utente memory :", e);
        }
    }
}
