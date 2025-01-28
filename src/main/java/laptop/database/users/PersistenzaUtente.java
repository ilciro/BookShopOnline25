package laptop.database.users;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.user.TempUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaUtente {
    public boolean inserisciUtente(TempUser tu) throws IOException, CsvValidationException, IdException, ClassNotFoundException { return false;}
    public ObservableList<TempUser> getUserData() throws IOException, CsvValidationException, SQLException {return FXCollections.observableArrayList();}
    public boolean removeUserByIdEmailPwd(TempUser u) throws CsvValidationException, IOException, SQLException {return false;}
    public List<TempUser> userList(TempUser u) throws CsvValidationException, IOException, SQLException {return null;}
    public void initializza() throws IOException, CsvValidationException, IdException, ClassNotFoundException {Logger.getLogger("inizializza").log(Level.INFO,"initialize");}
}
