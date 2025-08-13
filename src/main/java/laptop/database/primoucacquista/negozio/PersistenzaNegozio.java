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



     public ObservableList<Negozio> getNegozi() {
         return FXCollections.observableArrayList();
     }
     public boolean checkOpen(Negozio  shop)  {

         return shop.getIsOpen();
     }
     public boolean checkRitiro(Negozio shop)  {

         return shop.getIsValid();
     }
     public void initializza() {
         getException();

         Logger.getLogger("inizializza persistenza negozio").log(Level.INFO," persistenza negozio inizializza");

     }
    private void getException()  {
        Logger.getLogger("persistenza negozio").log(Level.INFO,"checking files...");

        try {
            if (!Files.exists(Path.of("report/reportNegozio.csv")))throw  new CsvValidationException("CSVException shop");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw  new SQLException("SQLException shop");
            if(!Files.exists(Path.of("memory/serializzazioneNegozio.ser"))) throw new ClassNotFoundException("ClassNotFoundException shop");
        }catch (CsvValidationException e)
        {
            Logger.getLogger("exception modalita file negozio").log(Level.SEVERE,"exception csv shop :",e);
        }
        catch (SQLException e)
        {
            Logger.getLogger("exception modalita database negozio").log(Level.SEVERE,"exception database shop :",e);

        }
        catch (ClassNotFoundException e)
        {
            Logger.getLogger("exception modalita memoria negozio").log(Level.SEVERE,"exception memory shop :",e);

        }

    }
}
