package laptop.database.primoucacquista.libro;

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

     public  boolean inserisciLibro(Libro l) throws CsvValidationException, IOException, ClassNotFoundException { return l.getId()!=-1 ;}

     public  boolean removeLibroById(Libro l) throws CsvValidationException, IOException, ClassNotFoundException { return l.getId()!=-0; }

    public  ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException { return FXCollections.observableArrayList();}

    public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        Logger.getLogger("lista  persistenza libro").log(Level.INFO,"lista libro by id .{0}",l.getId());

        return FXCollections.observableArrayList();}

    public void initializza() throws  CsvValidationException, IdException, IOException, ClassNotFoundException {

        Logger.getLogger("inizializza persistenza libro").log(Level.INFO," persistenza libro inizializza");



    }
    public ObservableList<Libro> getLibri() throws IOException, ClassNotFoundException, CsvValidationException, IdException { return FXCollections.observableArrayList();}



}
