package laptop.database.libro;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class PersistenzaLibro {
     public  boolean inserisciLibro(Libro l) throws CsvValidationException, IOException, ClassNotFoundException {return true;}
     public  boolean removeLibroById(Libro l) throws CsvValidationException, IOException, ClassNotFoundException {return false;}
    public  ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.observableArrayList();}
     public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.observableArrayList();}
    public void initializza() throws IOException, CsvValidationException, ClassNotFoundException, SQLException { Logger.getLogger("Persistenza Libro").log(Level.INFO,"initialize persistenza libro");}
    public ObservableList<Libro> getLibri() throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.emptyObservableList();}



}
