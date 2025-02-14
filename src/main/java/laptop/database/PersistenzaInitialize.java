package laptop.database;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import org.codehaus.plexus.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PersistenzaInitialize {

    //class used for reduce duplication

    private static final String DATABASEXCEPTION="file book sql not exists";
    private static final String FILEXCEPTION="file book csv not exists";
    private static final String MEMORIAEXCEPTION="class book not in memory";
    private static final String IDEXCEPTIONMESSAGE=" id book is null or is zero";


    public void initializza(String type) throws IOException, CsvValidationException, ClassNotFoundException, IdException {

        String []paths=new String[4];

        paths[0]="FileSql/"+type+".sql";
        //solo prima lettere
        String appoggio= StringUtils.capitalizeFirstLetter(type);
        paths[1]="report/report"+appoggio+".csv";
        paths[2]="memory/serializzazione"+appoggio+".ser";
        paths[3]="FileSql/popola"+appoggio+".sql";

        if(!Files.exists(Path.of(paths[0]))) throw new IOException(DATABASEXCEPTION);
        if(!Files.exists(Path.of(paths[1]))) throw new  CsvValidationException(FILEXCEPTION);
        if(!Files.exists(Path.of(paths[2]))) throw new ClassNotFoundException(MEMORIAEXCEPTION);
        if(!Files.exists(Path.of(paths[3]))) throw new IdException(IDEXCEPTIONMESSAGE);
        }

    public ObservableList<Raccolta> retrieveRaccoltaData(String type) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
       initializza(type);
        return FXCollections.observableArrayList();


    }



}
