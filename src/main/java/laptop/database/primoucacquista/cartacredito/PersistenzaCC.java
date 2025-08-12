package laptop.database.primoucacquista.cartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaCC {

    public boolean insCC(CartaDiCredito cc) throws IOException, CsvValidationException, ClassNotFoundException {
        getException();
        return cc.getNumeroCC()!=null;

    }
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) throws IOException,  CsvValidationException {

        getException();
        Logger.getLogger("get cartecredito").log(Level.INFO,"lista cc con nome: {0}",cc.getNomeUser());

        return FXCollections.observableArrayList();



         }
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        getException();
        Logger.getLogger("inizializza persistenza cc").log(Level.INFO,"persistenza cc inizializza");
    }


    private void getException()  {
        Exception e1 = new Exception();
        try {
            if (!Files.exists(Path.of("report/reportCartaCredito.csv"))) e1 = new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) e1 = new SQLException("SQLException");
            throw e1;
        }catch (Exception e)
        {
            Logger.getLogger("getException").log(Level.SEVERE,"exception :{0}",e);
        }

    }
}
