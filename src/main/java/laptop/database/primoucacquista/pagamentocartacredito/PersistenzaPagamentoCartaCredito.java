package laptop.database.primoucacquista.pagamentocartacredito;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.pagamento.PagamentoCartaCredito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaPagamentoCartaCredito {
    public void inizializza()  {
        Logger.getLogger("inizializza pagamento cc").log(Level.INFO, "inizializza persistency cc payment");
        getException();

    }

    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p)  {

        return p.getIdPagCC()!=-1;
    }

    public boolean cancellaPagamentoCartaCredito(PagamentoCartaCredito p)  {
        return p.getIdProdotto()!=0;}

    public PagamentoCartaCredito ultimoPagamentoCartaCredito()  {
        return new PagamentoCartaCredito();}

    public ObservableList<PagamentoCartaCredito> listaPagamentiUserByCC(PagamentoCartaCredito pcc)  {
        Logger.getLogger("list pagamenti cc").log(Level.SEVERE, "list pf paymeny of : .{0}", pcc.getNomeUtente());

        return FXCollections.observableArrayList();}

    private void getException() {
        Logger.getLogger("persistenza carta credito").log(Level.INFO, "checking files...");

        try {
            if (!Files.exists(Path.of("report/reportCartaCredito.csv"))) throw new CsvValidationException("CSVException cc");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException cc");
            if (!Files.exists(Path.of("memory/serializzazionePagamentoCartaCredito.ser"))) throw new ClassNotFoundException("ClassNotFoundException cc");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file").log(Level.SEVERE, "exception csv cc :{0}", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database").log(Level.SEVERE, "exception database cc :{0}", e);

        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE, "exception memory cc :{0}", e);

        }
    }

}
