package laptop.database.primoucacquista.negozio;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.Negozio;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaNegozio {



     public ObservableList<Negozio> getNegozi()throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        getException();
         return FXCollections.observableArrayList();
     }
     public boolean checkOpen(Negozio  shop) throws CsvValidationException, IOException, ClassNotFoundException {
         getException();

         return shop.getIsOpen();
     }
     public boolean checkRitiro(Negozio shop) throws IOException, CsvValidationException, ClassNotFoundException {
         getException();

         return shop.getIsValid();
     }
     public void initializza() throws IOException, SQLException {
         getException();

         Logger.getLogger("inizializza persistenza negozio").log(Level.INFO," persistenza negozio inizializza");

     }
    private void getException()  {
        Logger.getLogger("persistenza negozio").log(Level.INFO,"checking files...");

        try {
            if (!Files.exists(Path.of("report/reportNegozio.csv")))throw  new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw  new SQLException("SQLException");
            if(!Files.exists(Path.of("memory/serializzazioneNegozio.ser"))) throw new ClassNotFoundException("ClassNotFoundException");
        }catch (CsvValidationException e)
        {
            Logger.getLogger("exception modalita file").log(Level.SEVERE,"exception csv :{0}",e);
        }
        catch (SQLException e)
        {
            Logger.getLogger("exception modalita database").log(Level.SEVERE,"exception database :{0}",e);

        }
        catch (ClassNotFoundException e)
        {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE,"exception memory :{0}",e);

        }

    }
}
