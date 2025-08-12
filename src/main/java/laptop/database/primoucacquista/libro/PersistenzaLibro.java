package laptop.database.primoucacquista.libro;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public  class PersistenzaLibro {

     public  boolean inserisciLibro(Libro l) throws CsvValidationException, IOException, ClassNotFoundException {
         getException();
         return l.getId()!=-1 ;}

     public  boolean removeLibroById(Libro l) throws CsvValidationException, IOException, ClassNotFoundException {
         getException();

         return l.getId()!=-0; }

    public  ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        getException();
        return FXCollections.observableArrayList();}

    public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        getException();

        Logger.getLogger("lista  persistenza libro").log(Level.INFO,"lista libro by id .{0}",l.getId());

        return FXCollections.observableArrayList();}

    public void initializza() throws  CsvValidationException, IdException, IOException, ClassNotFoundException {
        getException();


        Logger.getLogger("inizializza persistenza libro").log(Level.INFO," persistenza libro inizializza");



    }
    public ObservableList<Libro> getLibri() throws IOException, ClassNotFoundException, CsvValidationException, IdException { return FXCollections.observableArrayList();}


    private void getException()  {
        Logger.getLogger("persistenza libro libro").log(Level.INFO,"checking files...");

        try {
            if (!Files.exists(Path.of("report/reportLibro.csv")))throw  new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw  new SQLException("SQLException");
            if(!Files.exists(Path.of("memory/serializzazioneLibro.ser"))) throw new ClassNotFoundException("ClassNotFoundException");
        }catch (CsvValidationException e)
        {
            Logger.getLogger("exception modalita file").log(Level.SEVERE,"exception csv :{0}",e);
        }
        catch (SQLException e)
        {
            Logger.getLogger("exception modalita database").log(Level.SEVERE,"exception database :{0}",e);

        }
        catch (ClassNotFoundException e)
        {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE,"exception memory :{0}",e);

        }

    }

}
