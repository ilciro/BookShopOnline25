package laptop.database.primoucacquista.rivista;

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


    public  boolean inserisciRivista(Rivista r) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {

        return r.getId()!=0;
    }
    public  boolean removeRivistaById(Rivista r) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
              return r.getId()!=-1;}

    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        return FXCollections.observableArrayList();

    }
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        return FXCollections.observableArrayList();
    }
    public void initializza() throws CsvValidationException, IdException, IOException ,SQLException,ClassNotFoundException{

        Logger.getLogger("inizializza persistenza rivista").log(Level.INFO,"persistenza rivista inizializza");

    }
    public ObservableList<Rivista> getRiviste() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
     return FXCollections.observableArrayList();
    }
}
