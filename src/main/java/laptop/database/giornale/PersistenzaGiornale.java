package laptop.database.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaGiornale {
    public  boolean inserisciGiornale(Giornale g) throws CsvValidationException, IOException,  ClassNotFoundException {return true;}
    public  boolean removeGiornaleById(Giornale g) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {return false;}
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.emptyObservableList();}
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.emptyObservableList();}
    public void initializza() throws IOException, CsvValidationException, SQLException, ClassNotFoundException { Logger.getLogger("Persistenza giornale").log(Level.INFO,"initialize persistenza giornale");}
    public ObservableList<Giornale> getGiornali() throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.emptyObservableList();}

}
