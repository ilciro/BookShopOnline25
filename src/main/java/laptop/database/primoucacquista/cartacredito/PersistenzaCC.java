package laptop.database.primoucacquista.cartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaCC {
    public boolean insCC(CartaDiCredito cc) throws IOException, CsvValidationException, ClassNotFoundException {
        return cc.getNumeroCC()!=null;

    }
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException,  CsvValidationException {
      return FXCollections.observableArrayList();



         }
    public void inizializza()throws IOException, ClassNotFoundException, SQLException {
        Logger.getLogger("inizializza persistenza cc").log(Level.INFO,"persistenza cc inizializza");
    }
}
