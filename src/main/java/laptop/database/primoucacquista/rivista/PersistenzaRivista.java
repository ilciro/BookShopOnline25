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


    public  boolean inserisciRivista(Rivista r){
        return r.getId()!=0;
    }
    public  boolean removeRivistaById(Rivista r)  {
        return r.getId()!=-1;}

    public ObservableList<Raccolta> retrieveRaccoltaData()  {

        return FXCollections.observableArrayList();

    }
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r)  {

        return FXCollections.observableArrayList();
    }
    public void initializza() {
        getException();


        Logger.getLogger("inizializza persistenza rivista").log(Level.INFO,"persistenza rivista inizializza");

    }
    public ObservableList<Rivista> getRiviste()  {
     return FXCollections.observableArrayList();
    }

    private void getException() {
        Logger.getLogger("persistenza rivista").log(Level.INFO, "checking files...");

        try {
            if (!Files.exists(Path.of("report/reportRivista.csv"))) throw new CsvValidationException("CSVException rivista");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException rivista");
            if (!Files.exists(Path.of("memory/serializzazioneRivista.ser"))) throw new ClassNotFoundException("ClassNotFoundException rivista");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file rivista").log(Level.SEVERE, "exception csv rivista :{0}", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database rivista").log(Level.SEVERE, "exception database rivista :{0}", e);

        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria rivista").log(Level.SEVERE, "exception memory  rivista:{0}", e);

        }
    }
}
