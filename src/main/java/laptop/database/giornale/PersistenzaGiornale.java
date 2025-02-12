package laptop.database.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class PersistenzaGiornale {

    private static final String DATABASE="FileSql/giornale.sql";
    private static final String FILE="report/reportGiornale.csv";
    private static final String MEMORIA="memory/serializzazioneGiornale.ser";
    private static final String DATABASEXCEPTION="file sql not exists";
    private static final String FILEXCEPTION="file csv not exists";
    private static final String MEMORIAEXCEPTION="class not in memory";
    private static final String IDEXCEPTIONMESSAGE=" id is null or is zero";


    public  boolean inserisciGiornale(Giornale g) throws CsvValidationException, IOException,  ClassNotFoundException {
        if(g.getId()==0) throw new IOException(" file not found or id null");
        if(g.getEditore().isEmpty()) throw new CsvValidationException(" autore insert daily book is null");
        if(g.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo insert book is null");

        return false;}
    public  boolean removeGiornaleById(Giornale g) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        if(g.getId()==0) throw new IOException(" file not found or id null");
        if(g.getEditore().isEmpty()) throw new CsvValidationException(" codice isbn is null");
        if(g.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo is null");
        if(g.getId()<0) throw new SQLException("removed id is wrong");
        return false;}
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(!Files.exists(Path.of(DATABASE))) throw new IOException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        else throw new IdException(IDEXCEPTIONMESSAGE);
    }
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(g.getId()==0) throw new IOException(" file not found or id 0");
        if(g.getEditore().isEmpty()) throw new CsvValidationException(" codice isbn is null");
        if(g.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo is null");
        if(g.getId()<0) throw new IdException(" id is lower than 0");
        return FXCollections.observableArrayList();}
    public void initializza() throws IOException, CsvValidationException, SQLException, ClassNotFoundException {

        if(!Files.exists(Path.of("FileSql/giornale.sql"))) throw new IOException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        else throw new SQLException(IDEXCEPTIONMESSAGE);
        }
    public ObservableList<Giornale> getGiornali() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(!Files.exists(Path.of(DATABASE))) throw new IdException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        else throw new IOException(IDEXCEPTIONMESSAGE);
    }

}
