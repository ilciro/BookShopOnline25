package laptop.database.primoucacquista.rivista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.primoucacquista.DaoInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class PersistenzaRivista {
    private static final String DATABASE="src/main/resources/sql/tablePopulate.properties";
    private static final String FILE="report/reportRivista.csv";
    private static final String MEMORIA="memory/serializzazioneRivista.ser";
    private static final String DATABASEXCEPTION="file magazine sql not exists";
    private static final String FILEXCEPTION="file magazine csv not exists";
    private static final String MEMORIAEXCEPTION="class magazine not in memory";
    private static final String IDEXCEPTIONMESSAGE="id magazine is null or is zero";

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
        return FXCollections.observableArrayList();

    }
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(r.getId()==0) throw new IOException(" file magazine not found or id 0");
        if(r.getAutore().isEmpty()) throw new CsvValidationException(" magazine author is null");
        if(r.getEditore().isEmpty()) throw new ClassNotFoundException("magazine editor is null");
        if(r.getId()<=-1) throw new IdException(" id magazine is lower than 0");
        return FXCollections.observableArrayList();
    }
    public void initializza() throws CsvValidationException, IdException, IOException ,SQLException,ClassNotFoundException{

        if(!Files.exists(Path.of(DATABASE))) throw new SQLException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        DaoInitialize dI=new DaoInitialize();
        dI.inizializza("rivista");
    }
    public ObservableList<Rivista> getRiviste() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(!Files.exists(Path.of(DATABASE))) throw new IdException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        else throw new IOException(IDEXCEPTIONMESSAGE);
    }
}
