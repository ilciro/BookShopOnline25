package laptop.database.users;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.user.TempUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

public class PersistenzaUtente {
    private static final String DATABASE="FileSql/users.sql";
    private static final String FILE="report/reportUtenti.csv";
    private static final String MEMORIA="memory/serializzazioneUtenti.ser";
    private static final String DATABASEXCEPTION="file sql not exists";
    private static final String FILEXCEPTION="file csv not exists";
    private static final String MEMORIAEXCEPTION="class not in memory";
    private static final String IDEXCEPTIONMESSAGE=" id is null or is zero";
    public boolean inserisciUtente(TempUser tu) throws IOException, CsvValidationException, IdException, ClassNotFoundException {
        if(!Files.exists(Path.of(DATABASE))) throw new IOException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        if(tu.getId()<=0) throw new IdException(IDEXCEPTIONMESSAGE);
        return false;}
    public ObservableList<TempUser> getUserData() throws IOException, CsvValidationException, SQLException {
        if(!Files.exists(Path.of(DATABASE))) throw new SQLException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new IOException(MEMORIAEXCEPTION);
        return FXCollections.observableArrayList();}
    public boolean removeUserByIdEmailPwd(TempUser tu) throws CsvValidationException, IOException, SQLException {
        if(tu.getId()<=0) throw new SQLException("id in db is wrong");
        if(tu.getEmailT().isEmpty()) throw new CsvValidationException(FILEXCEPTION);
        if(tu.getPasswordT().isEmpty()) throw new IOException("password memory is empty");
        return false;}
    public List<TempUser> userList(TempUser tu) throws CsvValidationException, IOException, SQLException {
        if(tu.getId()<=0) throw new SQLException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new IOException(MEMORIAEXCEPTION);
        return FXCollections.observableArrayList();}
    public void initializza() throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {
        if(!Files.exists(Path.of(DATABASE))) throw new IOException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        if(!Files.exists(Path.of("FileSql/popolaUser.sql"))) throw new SQLException("file fo populate db users not found!");
        else throw new IdException(IDEXCEPTIONMESSAGE);
    }
}
