package laptop.database.secondouclogin.users;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.DaoInitialize;
import laptop.exception.IdException;
import laptop.model.user.TempUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaUtente {

    public boolean inserisciUtente(TempUser tu) throws IOException, CsvValidationException, IdException, ClassNotFoundException {

       getException();
       return tu.getId()!=-1;}
    public ObservableList<TempUser> getUserData() throws IOException, CsvValidationException, SQLException {

        return FXCollections.observableArrayList();}
    public boolean removeUserByIdEmailPwd(TempUser tu) throws CsvValidationException, IOException, SQLException {
        getException();

        return tu.getId()!=-0;
    }



    public void initializza() throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {
        getException();

        Logger.getLogger("inizializza persistenza utente").log(Level.INFO,"peristenza utente initialize");
        DaoInitialize daoI=new DaoInitialize();
        daoI.inizializza("utenti");

    }
    private void getException() {
        Logger.getLogger("persistenza pagamento totale").log(Level.INFO, "checking files...");

        try {
            if (!Files.exists(Path.of("report/reportUtente.csv"))) throw new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException");
            if (!Files.exists(Path.of("memory/serializzazioneUtente.ser"))) throw new ClassNotFoundException("ClassNotFoundException");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file").log(Level.SEVERE, "exception csv :{0}", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database").log(Level.SEVERE, "exception database :{0}", e);

        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE, "exception memory :{0}", e);

        }
    }
}
