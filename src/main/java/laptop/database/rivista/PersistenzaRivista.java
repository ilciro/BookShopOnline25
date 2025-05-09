package laptop.database.rivista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class PersistenzaRivista {
    private static final String DATABASE="FileSql/rivista.sql";
    private static final String FILE="report/reportRivista.csv";
    private static final String MEMORIA="memory/serializzazioneRivista.ser";
    private static final String DATABASEXCEPTION="file magazine sql not exists";
    private static final String FILEXCEPTION="file magazine csv not exists";
    private static final String MEMORIAEXCEPTION="class magazine not in memory";
    private static final String IDEXCEPTIONMESSAGE=" id magazine is null or is zero";

    public  boolean inserisciRivista(Rivista r) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        if(r.getId()==0) throw new IOException(" file not found or id null");
        if(r.getAutore().isEmpty()) throw new CsvValidationException(" codice isbn insert book is null");
        if(r.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo insert book is null");
        if(r.getId()<0) throw new SQLException("id rivista is lower than 0");
        return true;
    }
    public  boolean removeRivistaById(Rivista r) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        if(r.getId()==0) throw new IOException(" file not found or id null");
        if(r.getAutore().isEmpty()) throw new CsvValidationException(" codice isbn is null");
        if(r.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo is null");
        if(r.getEditore().isEmpty()) throw new SQLException(" editore is null");
        return false;}

    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        PersistenzaInitialize pI=new PersistenzaInitialize();
        return pI.retrieveRaccoltaData("rivista");

    }
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(r.getId()==0) throw new IOException(" file not found or id 0");
        if(r.getAutore().isEmpty()) throw new CsvValidationException(" codice isbn is null");
        if(r.getEditore().isEmpty()) throw new ClassNotFoundException("class not found or titolo is null");
        if(r.getId()<=-1) throw new IdException(" id magazine is lower than 0");
        return FXCollections.observableArrayList();
    }
    public void initializza() throws IOException, CsvValidationException, SQLException, ClassNotFoundException, IdException {
        PersistenzaInitialize pI=new PersistenzaInitialize();
        pI.initializza("rivista");
    }
    public ObservableList<Rivista> getRiviste() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(!Files.exists(Path.of(DATABASE))) throw new IdException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        else throw new IOException(IDEXCEPTIONMESSAGE);
    }
}
