package laptop.database.cartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaCC {
    public boolean insCC(CartaDiCredito cc) throws IOException, CsvValidationException, ClassNotFoundException {
        boolean status=false;
        if(cc.getNumeroCC()==null)
                throw new IOException("numero carta sbagliato");
        return status;
    }
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException, ClassNotFoundException, CsvValidationException, IdException {return FXCollections.emptyObservableList();}
    public void inizializza() throws IOException, ClassNotFoundException {
        Logger.getLogger("inizializza cc").log(Level.INFO,"initialize cc");
    }
}
