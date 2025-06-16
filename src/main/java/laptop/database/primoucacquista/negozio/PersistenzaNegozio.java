package laptop.database.primoucacquista.negozio;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.Negozio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;


public class PersistenzaNegozio {

    private static final String DATABASE="src/main/resources/sql/tablePopulate.properties";

     public ObservableList<Negozio> getNegozi( )throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        if(!Files.exists(Path.of("report/reportNegozio.csv"))) throw new CsvValidationException(" file csv not exists");
        if(!Files.exists(Path.of(DATABASE))) throw new IdException(" file not exists");
        if(!Files.exists(Path.of("memory/serializzazioneNegozio.ser"))) throw new ClassNotFoundException(" class in memory not found");
        else throw new IOException("file not found");
     }
     public boolean checkOpen(Negozio  shop) throws CsvValidationException, IOException, ClassNotFoundException {
         if(shop.getNome().isEmpty()) throw new IOException(" name is null");
         if(Boolean.FALSE.equals(shop.getIsOpen())) throw new CsvValidationException(" shop is closed");
         if (shop.getId()==0) throw new IOException("id is empty");
         if (shop.getId()<=0) throw new ClassNotFoundException("id is lower 0");
         return false;
     }
     public boolean checkRitiro(Negozio shop) throws IOException, CsvValidationException, ClassNotFoundException {
         if(shop.getNome().isEmpty()) throw new IOException(" name is null");
         if(Boolean.FALSE.equals(shop.getIsValid())) throw new CsvValidationException(" shop is closed");
         if (shop.getId()==0) throw new IOException("id is empty");
         if (shop.getId()<=0) throw new ClassNotFoundException("id is lowe 0");
         return false;
     }
     public void initializza() throws IOException, SQLException {
         if(!Files.exists(Path.of("report/reportNegozio.csv"))||(!Files.exists(Path.of("memory/serializzazioneNegozio.ser")))) throw new IOException(" files not exists");
         if(!Files.exists(Path.of(DATABASE)))  throw new SQLException("file sql not exists");
     }
}
