package laptop.database.primoucacquista.pagamentototale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;

import java.io.IOException;

public class PagamentoTotale {
    public void inizializza() throws IOException {}
    public boolean inserisciPagamentoFattura(PagamentoFattura p) throws CsvValidationException, IOException, ClassNotFoundException { return p.getIdFattura()!=0;}
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito pCC) throws IOException, CsvValidationException, ClassNotFoundException {return pCC.getIdPagCC()!=0;}
    public boolean cancellaFattura(PagamentoFattura p) throws IOException, ClassNotFoundException { return p.getIdFattura()!=0;}
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) throws IOException, ClassNotFoundException { return pCC.getIdPagCC() != 0;}


}
