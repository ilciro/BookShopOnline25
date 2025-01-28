package laptop.database.pagamento;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Pagamento;
import laptop.model.user.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaPagamento {
    public boolean inserisciPagamento(Pagamento p) throws CsvValidationException, IOException, ClassNotFoundException {return true;}
    public Pagamento ultimoPagamento() throws CsvValidationException, IOException, ClassNotFoundException {return null;}
    public boolean cancellaPagamento(Pagamento p) throws IOException, CsvValidationException, ClassNotFoundException {return true;}
    public void inizializza() throws IOException, ClassNotFoundException {
        Logger.getLogger("inizializza pagamento").log(Level.INFO,"initialize payment");
    }
    public ObservableList<Pagamento> listPagamentiByUser(Pagamento p) throws CsvValidationException, IOException, ClassNotFoundException {return FXCollections.emptyObservableList();}

}
