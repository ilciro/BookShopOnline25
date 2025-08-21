package laptop.database.primoucacquista.pagamentocartacredito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.pagamento.PagamentoCartaCredito;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaPagamentoCartaCredito {
    public void inizializza()  {
        Logger.getLogger("inizializza pagamento cc").log(Level.INFO, "inizializza persistency cc payment");
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("pagamentoCC");


    }

    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p)  {

        return p.getIdPagCC()!=-1;
    }

    public boolean cancellaPagamentoCartaCredito(PagamentoCartaCredito p)  {
        return p.getIdProdottoCC()!=0;}

    public PagamentoCartaCredito ultimoPagamentoCartaCredito()  {
        return new PagamentoCartaCredito();}

    public ObservableList<PagamentoCartaCredito> listaPagamentiUserByCC(PagamentoCartaCredito pcc)  {
        Logger.getLogger("list pagamenti cc").log(Level.SEVERE, "list pf paymeny of : {0}", pcc.getNomeUtenteCC());

        return FXCollections.observableArrayList();}



}
