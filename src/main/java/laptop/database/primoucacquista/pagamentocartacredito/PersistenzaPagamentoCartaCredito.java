package laptop.database.primoucacquista.pagamentocartacredito;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.pagamento.PagamentoCartaCredito;

import java.io.IOException;
import java.sql.SQLException;

public class PersistenzaPagamentoCartaCredito {
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {}

    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p) throws CsvValidationException, IOException, ClassNotFoundException {
        return p.getIdPagCC()!=-1;
    }

    public boolean cancellaPagamentoCartaCredito(PagamentoCartaCredito p) throws IOException, CsvValidationException, ClassNotFoundException { return p.getIdProdotto()!=0;}

    public PagamentoCartaCredito ultimoPagamentoCartaCredito() throws IOException, CsvValidationException, ClassNotFoundException { return new PagamentoCartaCredito();}
}
