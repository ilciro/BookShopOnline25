package laptop.database.primoucacquista.pagamentototale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.DaoInitialize;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;

import java.io.IOException;

public class PagamentoTotaleDao extends PagamentoTotale {


    @Override
    public void inizializza() {
        DaoInitialize dI=new DaoInitialize();
        dI.inizializza("pagamentoTotale");


    }

    @Override
    public boolean inserisciPagamentoFattura(PagamentoFattura p) throws CsvValidationException, IOException, ClassNotFoundException {
        return super.inserisciPagamentoFattura(p);
    }

    @Override
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito pCC) throws CsvValidationException, IOException, ClassNotFoundException {
        return super.inserisciPagamentoCartaCredito(pCC);
    }

    @Override
    public boolean cancellaFattura(PagamentoFattura p) throws IOException, ClassNotFoundException {
       return super.cancellaFattura(p);
    }

    @Override
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) throws IOException, ClassNotFoundException {
        return super.cancellaPagamentoCC(pCC);
    }
}
