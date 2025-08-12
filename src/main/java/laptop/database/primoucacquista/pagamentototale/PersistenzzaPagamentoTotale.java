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

public class PersistenzzaPagamentoTotale {
    public void inizializza() throws IOException {}
    public boolean inserisciPagamentoFattura(PagamentoFattura p) throws CsvValidationException, IOException, ClassNotFoundException {
        getException();
        return p.getIdFattura()!=0;}
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito pCC) throws IOException, CsvValidationException, ClassNotFoundException {
        getException();
        return pCC.getIdPagCC()!=0;}
    public boolean cancellaFattura(PagamentoFattura p) throws IOException, ClassNotFoundException, CsvValidationException {
        getException();
        return p.getIdFattura()!=0;}
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) throws IOException, ClassNotFoundException, CsvValidationException {
        getException();
        return pCC.getIdPagCC() != 0;}

    private void getException() {
        Logger.getLogger("persistenza pagamento totale").log(Level.INFO, "checking files...");

        try {
            if (!Files.exists(Path.of("report/reportPagamentoTotale.csv"))) throw new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException");
            if (!Files.exists(Path.of("memory/serializzazionePagamentoTotale.ser"))) throw new ClassNotFoundException("ClassNotFoundException");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file").log(Level.SEVERE, "exception csv :{0}", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database").log(Level.SEVERE, "exception database :{0}", e);

        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE, "exception memory :{0}", e);

        }
    }
}
