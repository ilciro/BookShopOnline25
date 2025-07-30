package laptop.database.primoucacquista.negozio;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.Negozio;

import java.io.IOException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaNegozio {



     public ObservableList<Negozio> getNegozi()throws CsvValidationException, IOException, IdException, ClassNotFoundException {
      return FXCollections.observableArrayList();
     }
     public boolean checkOpen(Negozio  shop) throws CsvValidationException, IOException, ClassNotFoundException {
      return shop.getIsOpen();
     }
     public boolean checkRitiro(Negozio shop) throws IOException, CsvValidationException, ClassNotFoundException {
        return shop.getIsValid();
     }
     public void initializza() throws IOException, SQLException {
         Logger.getLogger("inizializza persistenza negozio").log(Level.INFO," persistenza negozio inizializza");

     }
}
