package laptop.database.primoucacquista.pagamentototale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaPagamentoTotale {
    public void inizializza()  { getException(); }
    public boolean inserisciPagamentoFattura(PagamentoFattura p)  {return p.getIdFattura()!=0;}
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito pCC){ return pCC.getIdPagCC()!=0;}
    public boolean cancellaFattura(PagamentoFattura p) { return p.getIdFattura()!=0;}
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) { return pCC.getIdPagCC() != 0;}

    private void getException() {
        Logger.getLogger("persistenza pagamento totale").log(Level.INFO, "checking files payment total...");
        try {
            if (!Files.exists(Path.of("report/reportPagamentoTotale.csv"))) throw new CsvValidationException("CSVException pagamentoTotale");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException pagamentoTotale");
            if (!Files.exists(Path.of("memory/serializzazionePagamentoTotale.ser"))) throw new ClassNotFoundException("ClassNotFoundException pagamentoTotale");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file pagTotale").log(Level.SEVERE, "exception csv payTot :{0}", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database pagTotale").log(Level.SEVERE, "exception database payTot :{0}", e);

        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria pagTotale").log(Level.SEVERE, "exception memory payTot :{0}", e);

        }
    }
}
