package laptop.database.primoucacquista.pagamentofattura;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaPagamentoFattura {

    public boolean inserisciPagamentoFattura(PagamentoFattura f) throws IOException, ClassNotFoundException {

        return f.getIdFattura()!=-1;
    }
    public boolean cancellaPagamentoFattura(PagamentoFattura f) throws CsvValidationException, IOException, ClassNotFoundException {
        return f.getIdFattura()!=-1;
    }
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        Logger.getLogger("persistenza fattura inizializza").log(Level.INFO,"persistenza pagamento fattura inizializza");
          }

    public PagamentoFattura ultimaFattura() throws IOException, CsvValidationException, ClassNotFoundException {
        Logger.getLogger("Persistenza pagamento fattura").log(Level.INFO,"ultima fattur");
        return null;
    }
    public ObservableList<PagamentoFattura> listPagamentiByUserF(PagamentoFattura pF) throws IOException, ClassNotFoundException, CsvValidationException, IdException { return FXCollections.observableArrayList();}


}
