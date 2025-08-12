package laptop.database.primoucacquista.rivista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaRivista {


    public  boolean inserisciRivista(Rivista r) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        getException();
        return r.getId()!=0;
    }
    public  boolean removeRivistaById(Rivista r) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        getException();

        return r.getId()!=-1;}

    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        getException();

        return FXCollections.observableArrayList();

    }
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) throws CsvValidationException, IOException, IdException, ClassNotFoundException {

        getException();
        return FXCollections.observableArrayList();
    }
    public void initializza() throws CsvValidationException, IdException, IOException ,SQLException,ClassNotFoundException{
        getException();


        Logger.getLogger("inizializza persistenza rivista").log(Level.INFO,"persistenza rivista inizializza");

    }
    public ObservableList<Rivista> getRiviste() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
     return FXCollections.observableArrayList();
    }

    private void getException() {
        Logger.getLogger("persistenza rivista").log(Level.INFO, "checking files...");

        try {
            if (!Files.exists(Path.of("report/reportRivista.csv"))) throw new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException");
            if (!Files.exists(Path.of("memory/serializzazioneRivista.ser"))) throw new ClassNotFoundException("ClassNotFoundException");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file").log(Level.SEVERE, "exception csv :{0}", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database").log(Level.SEVERE, "exception database :{0}", e);

        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE, "exception memory :{0}", e);

        }
    }
}
