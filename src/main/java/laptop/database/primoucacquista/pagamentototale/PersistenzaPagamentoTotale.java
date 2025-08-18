package laptop.database.primoucacquista.pagamentototale;

import laptop.database.PersistenzaGenerale;
import laptop.pagamento.PagamentoCartaCredito;
import laptop.pagamento.PagamentoFattura;



public class PersistenzaPagamentoTotale {
    public void inizializza()  {
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("pagamentoTotale"); }
    public boolean inserisciPagamentoFattura(PagamentoFattura p)  {return p.getIdFattura()!=0;}
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito pCC){ return pCC.getIdPagCC()!=0;}
    public boolean cancellaFattura(PagamentoFattura p) { return p.getIdFattura()!=0;}
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) { return pCC.getIdPagCC() != 0;}


}
