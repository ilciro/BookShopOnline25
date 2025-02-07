package laptop.database.cartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaCC {
    public boolean insCC(CartaDiCredito cc) throws IOException, CsvValidationException, ClassNotFoundException {
        boolean status=false;
        if(cc.getNumeroCC()==null) throw new IOException("numero carta sbagliato");
        return status;
    }
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException, ClassNotFoundException, CsvValidationException {
        ObservableList<CartaDiCredito> lista= FXCollections.observableArrayList();
        lista.add(cc);
        return lista;
    }
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        Logger.getLogger("inizializza cc").log(Level.INFO,"initialize cc");
    }
}
