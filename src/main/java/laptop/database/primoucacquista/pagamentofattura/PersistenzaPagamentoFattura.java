package laptop.database.primoucacquista.pagamentofattura;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.pagamento.PagamentoFattura;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaPagamentoFattura {

    public boolean inserisciPagamentoFattura(PagamentoFattura f) {
        return f.getIdFattura()!=-1;
    }
    public boolean cancellaPagamentoFattura(PagamentoFattura f) {

        return f.getIdFattura()!=-1;
    }
    public void inizializza()   {

        Logger.getLogger("persistenza fattura inizializza").log(Level.INFO,"persistenza pagamento fattura inizializza");
        getException();
          }

    public PagamentoFattura ultimaFattura()  {

        Logger.getLogger("Persistenza pagamento fattura").log(Level.INFO,"ultima fattura");
        return null;
    }
    public ObservableList<PagamentoFattura> listPagamentiByUserF(PagamentoFattura pF)  {
        Logger.getLogger("Persistenza pagamento fattura by user").log(Level.INFO,"lisa payment by user:.",pF.getNome());


        return FXCollections.observableArrayList();}

    private void getException() {
        Logger.getLogger("persistenza pagamento fattura").log(Level.INFO, "checking files...");

        try {
            if (!Files.exists(Path.of("report/reportPagamentoFattura.csv"))) throw new CsvValidationException("CSVException fattura");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException fattura");
            if (!Files.exists(Path.of("memory/serializzazionePagamentoFattura.ser"))) throw new ClassNotFoundException("ClassNotFoundException fattura");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file fattura").log(Level.SEVERE, "exception csv fattura :", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database fattura").log(Level.SEVERE, "exception database fattura:", e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria fattura").log(Level.SEVERE, "exception memory fattura :", e);

        }
    }

}
