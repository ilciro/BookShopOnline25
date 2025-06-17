package laptop.database.primoucacquista.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.primoucacquista.DaoInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class PersistenzaGiornale {

    private static final String DATABASE="src/main/resources/sql/tablePopulate.properties";
    private static final String FILE="report/reportGiornale.csv";
    private static final String MEMORIA="memory/serializzazioneGiornale.ser";
    private static final String DATABASEXCEPTION="file daily sql not exists";
    private static final String FILEXCEPTION="file daily csv not exists";
    private static final String MEMORIAEXCEPTION="class daily not in memory";
    private static final String IDEXCEPTIONMESSAGE=" id  daiy is null or is zero";


    public  boolean inserisciGiornale(Giornale g) throws CsvValidationException, IOException,  ClassNotFoundException {
        if(g.getId()==0) throw new IOException(" file not found or id null");
        if(g.getEditore().isEmpty()) throw new CsvValidationException(" autore insert daily book is null");
        if(g.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo insert book is null");

        return false;}
    public  boolean removeGiornaleById(Giornale g) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        if(g.getId()==0) throw new IOException(" file not found or id null");
        if(g.getEditore().isEmpty()) throw new CsvValidationException(" codice isbn is null");
        if(g.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo is null");
        if(g.getId()<-1) throw new SQLException("removed daily id is wrong");
        return false;}
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        return FXCollections.observableArrayList();
    }
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(g.getId()==0) throw new IOException(" file daily not found or id 0");
        if(g.getEditore().isEmpty()) throw new CsvValidationException(" editor daily is null");
        if(g.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo is null : daily");
        if(g.getId()<=-1) throw new IdException(" id daily is lower than 0");
        return FXCollections.observableArrayList();}
    public void initializza() throws CsvValidationException, IdException, IOException, SQLException, ClassNotFoundException {


        DaoInitialize dI=new DaoInitialize();
        dI.inizializza("giornale");


        }
    public ObservableList<Giornale> getGiornali() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(!Files.exists(Path.of(DATABASE))) throw new IdException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        else throw new IOException(IDEXCEPTIONMESSAGE);
    }

}
