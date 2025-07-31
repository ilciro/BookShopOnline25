package laptop.database.primoucacquista.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.DaoInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaGiornale {



    public  boolean inserisciGiornale(Giornale g) throws CsvValidationException, IOException,  ClassNotFoundException {


        return g.getId()!=0;}
    public  boolean removeGiornaleById(Giornale g) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {

        return g.getId()!=-1;}
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        return FXCollections.observableArrayList();
    }
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) throws CsvValidationException, IOException, IdException, ClassNotFoundException {

        return FXCollections.observableArrayList();}
    public void initializza() throws CsvValidationException, IdException, IOException, SQLException, ClassNotFoundException {

        Logger.getLogger("inizializza persistenza giornale").log(Level.INFO," persistenza giornale inizializza");



    }
    public ObservableList<Giornale> getGiornali() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
       return FXCollections.observableArrayList();
    }

}
