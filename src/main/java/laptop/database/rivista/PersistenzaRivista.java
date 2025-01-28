package laptop.database.rivista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaRivista {
    public  boolean inserisciRivista(Rivista r) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {return true;}
    public  boolean removeRivistaById(Rivista r) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {return false;}
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.emptyObservableList();}
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.emptyObservableList();}
    public void initializza() throws IOException, CsvValidationException, SQLException, ClassNotFoundException {Logger.getLogger("Persistenza rivista").log(Level.INFO,"initialize persistenza rivista");}
    public ObservableList<Rivista> getRiviste() throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.emptyObservableList();}
}
