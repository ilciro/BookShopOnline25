package laptop.database.primoucacquista.libro;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.primoucacquista.DaoInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import org.codehaus.plexus.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public  class PersistenzaLibro {
    private static final String DATABASE="src/main/resources/sql/tablePopulate.properties";
    private static final String FILE="report/reportLibro.csv";
    private static final String MEMORIA="memory/serializzazioneLibro.ser";
    private static final String DATABASEXCEPTION="file book sql not exists";
    private static final String CSVEXCEPTION="file book csv not exists";
    private static final String MEMORIAEXCEPTION="class book not in memory";
    private static final String IDEXCEPTIONMESSAGE="id book is null or is zero";


     public  boolean inserisciLibro(Libro l) throws CsvValidationException, IOException, ClassNotFoundException {

         if(l.getId()==0) throw new IOException(" file not found or id null");
         if(l.getCodIsbn().isEmpty()) throw new CsvValidationException(" codice isbn insert book is null");
         if(l.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo insert book is null");
         return true;}

     public  boolean removeLibroById(Libro l) throws CsvValidationException, IOException, ClassNotFoundException {
         if(l.getId()==0) throw new IOException(" file not found or id null");
         if(l.getCodIsbn().isEmpty()) throw new CsvValidationException(" codice isbn is null");
         if(l.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo is null");

         return false;}
    public  ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        return FXCollections.observableArrayList();

    }
     public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) throws CsvValidationException, IOException, IdException, ClassNotFoundException {

         if(l.getId()==0) throw new IOException(" file not found or id 0");
         if(l.getCodIsbn().isEmpty()) throw new CsvValidationException(" codice isbn is null");
         if(l.getTitolo().isEmpty()) throw new ClassNotFoundException("class not found or titolo is null");
         if(l.getId()<=0) throw new IdException(" id book is lower than 0");
         return FXCollections.observableArrayList();

     }
    public void initializza() throws IOException, CsvValidationException, ClassNotFoundException, SQLException, IdException {
      DaoInitialize dI=new DaoInitialize();
        dI.inizializza("libro");


    }
    public ObservableList<Libro> getLibri() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(!Files.exists(Path.of(DATABASE))) throw new IdException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(FILE))) throw new  CsvValidationException(CSVEXCEPTION);
        if(!Files.exists(Path.of(MEMORIA))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        else throw new IOException(IDEXCEPTIONMESSAGE);
     }



}
