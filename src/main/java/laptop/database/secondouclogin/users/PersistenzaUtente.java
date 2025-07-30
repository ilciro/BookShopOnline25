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
        return tu.getId()!=-1;}
    public ObservableList<TempUser> getUserData() throws IOException, CsvValidationException, SQLException {

        return FXCollections.observableArrayList();}
    public boolean removeUserByIdEmailPwd(TempUser tu) throws CsvValidationException, IOException, SQLException {
        return tu.getId()!=-0;
    }



    public void initializza() throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {
        Logger.getLogger("inizializza persistenza utente").log(Level.INFO,"peristenza utente initialize");
        DaoInitialize daoI=new DaoInitialize();
        daoI.inizializza("utenti");

    }
}
