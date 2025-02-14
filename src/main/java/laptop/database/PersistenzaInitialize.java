package laptop.database;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaInitialize {

    //class used for reduce duplication

    private static final String DATABASEXCEPTION="file book sql not exists";
    private static final String FILEXCEPTION="file book csv not exists";
    private static final String MEMORIAEXCEPTION="class book not in memory";
    private static final String IDEXCEPTIONMESSAGE=" id book is null or is zero";

    public void initializza(String type) throws IOException, CsvValidationException, ClassNotFoundException, SQLException {
        String databasePath="";
        String filePath="";
        String memoriaPath="";
        String popolaDb="";
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
        if(!Files.exists(Path.of(filePath))) throw new CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(memoriaPath))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        if(!Files.exists(Path.of(popolaDb))) throw new SQLException(IDEXCEPTIONMESSAGE);
    }

}
