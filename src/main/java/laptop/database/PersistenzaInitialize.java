package laptop.database;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaInitialize {

    //class used for reduce duplication

    private static final String DATABASEXCEPTION="file book sql not exists";
    private static final String FILEXCEPTION="file book csv not exists";
    private static final String MEMORIAEXCEPTION="class book not in memory";
    private static final String IDEXCEPTIONMESSAGE=" id book is null or is zero";
    private String databasePath="";
    private String filePath="";
    private String memoriaPath="";
    private String popolaDb="";


    public void initializza(String type) throws IOException, CsvValidationException, ClassNotFoundException, IdException {

       tipo(type);
        }

    public ObservableList<Raccolta> retrieveRaccoltaData(String type) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
      tipo(type);
        return FXCollections.observableArrayList();


    }

    private void tipo(String type) throws IOException, CsvValidationException, ClassNotFoundException, IdException {

        switch (type)
        {
            case "libro"->
            {
                databasePath="FileSql/libro.sql";
                filePath="report/reportLibro.csv";
                memoriaPath="memory/serializzazioneLibro.ser";
                popolaDb="FileSql/popolaLibro.sql";
            }
            case "giornale"->
            {
                databasePath="FileSql/giornale.sql";
                filePath="report/reportGiornale.csv";
                memoriaPath="memory/serializzazioneGiornale.ser";
                popolaDb="FileSql/popolaGiornale.sql";
            }
            case "rivista"->{
                databasePath="FileSql/rivista.sql";
                filePath="report/reportRivista.csv";
                memoriaPath="memory/serializzazioneRivista.ser";
                popolaDb="FileSql/popolaRivista.sql";
            }
            default -> Logger.getLogger("initialize persistenza").log(Level.SEVERE," persistenct error");

        }
        if(!Files.exists(Path.of(databasePath))) throw new IOException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(filePath))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(memoriaPath))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        if(!Files.exists(Path.of(popolaDb))) throw new IdException(IDEXCEPTIONMESSAGE);
    }

}
