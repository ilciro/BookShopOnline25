package laptop.database.primoucacquista.libro;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.DaoInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public  class PersistenzaLibro {

     public  boolean inserisciLibro(Libro l) throws CsvValidationException, IOException, ClassNotFoundException { return l.getId()!=-1 ;}

     public  boolean removeLibroById(Libro l) throws CsvValidationException, IOException, ClassNotFoundException { return l.getId()!=-0; }

    public  ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException { return FXCollections.observableArrayList();}

    public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) throws CsvValidationException, IOException, IdException, ClassNotFoundException { return FXCollections.observableArrayList();}

    public void initializza() throws IOException, CsvValidationException, ClassNotFoundException, SQLException, IdException {
      DaoInitialize dI=new DaoInitialize();
        dI.inizializza("libro");


    }
    public ObservableList<Libro> getLibri() throws IOException, ClassNotFoundException, CsvValidationException, IdException { return FXCollections.observableArrayList();}



}
