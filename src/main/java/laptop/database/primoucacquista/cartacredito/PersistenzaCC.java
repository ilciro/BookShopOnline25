package laptop.database.primoucacquista.cartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaCC {

    public boolean insCC(CartaDiCredito cc) {
        return cc.getNumeroCC()!=null;

    }
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc)  {

        Logger.getLogger("get cartecredito").log(Level.INFO,"lista cc con nome: ",cc.getNomeUser());

        return FXCollections.observableArrayList();



         }
    public void inizializza()  {
        getException();
        Logger.getLogger("inizializza persistenza cc").log(Level.INFO,"persistenza cc inizializza");
    }


    private void getException()  {
        try {
            if (!Files.exists(Path.of("report/reportCartaCredito.csv"))) throw  new CsvValidationException("persistenza cc CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("persistenza cc SQLException");
        }catch (CsvValidationException e)
        {
            Logger.getLogger("getException persistenca cc csv").log(Level.SEVERE,"persistenza cc csv exception :",e);
        }catch (SQLException e1)
        {
            Logger.getLogger("getException persistenca cc sql").log(Level.SEVERE," persistenza cc sql exception :",e1);

        }

    }
}
