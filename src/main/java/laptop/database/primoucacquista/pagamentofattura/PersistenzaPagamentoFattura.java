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
        getException();

        return f.getIdFattura()!=-1;
    }
    public boolean cancellaPagamentoFattura(PagamentoFattura f) throws CsvValidationException, IOException, ClassNotFoundException {
        getException();

        return f.getIdFattura()!=-1;
    }
    public void inizializza() throws IOException, ClassNotFoundException, SQLException  {
        getException();

        Logger.getLogger("persistenza fattura inizializza").log(Level.INFO,"persistenza pagamento fattura inizializza");
          }

    public PagamentoFattura ultimaFattura() throws IOException, CsvValidationException, ClassNotFoundException {
        getException();

        Logger.getLogger("Persistenza pagamento fattura").log(Level.INFO,"ultima fattura");
        return null;
    }
    public ObservableList<PagamentoFattura> listPagamentiByUserF(PagamentoFattura pF) throws IOException, ClassNotFoundException, CsvValidationException, IdException {
        Logger.getLogger("Persistenza pagamento fattura by user").log(Level.INFO,"lisa payment by user:.{0}",pF.getNome());
        getException();


        return FXCollections.observableArrayList();}

    private void getException() {
        Logger.getLogger("persistenza pagamento fattura").log(Level.INFO, "checking files...");

        try {
            if (!Files.exists(Path.of("report/reportPagamentoFattura.csv"))) throw new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException");
            if (!Files.exists(Path.of("memory/serializzazionePagamentoFattura.ser"))) throw new ClassNotFoundException("ClassNotFoundException");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file").log(Level.SEVERE, "exception csv :{0}", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database").log(Level.SEVERE, "exception database :{0}", e);

        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE, "exception memory :{0}", e);

        }
    }

}
