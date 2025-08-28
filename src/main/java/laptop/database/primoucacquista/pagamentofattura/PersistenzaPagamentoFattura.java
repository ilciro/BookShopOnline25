package laptop.database.primoucacquista.pagamentofattura;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.pagamento.PagamentoFattura;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaPagamentoFattura {

    public boolean inserisciPagamentoFattura(PagamentoFattura f) {
        Logger.getLogger("persistenza fattura insert").log(Level.INFO,"persistenza pagamento insert fattura");
        return f.getIdFattura()!=-1;
    }
    public boolean cancellaPagamentoFattura(PagamentoFattura f) {
        Logger.getLogger("persistenza fattura cancella").log(Level.INFO,"persistenza pagamento fattura delete");
        return f.getIdFattura()!=-1;
    }
    public void inizializza()   {
        Logger.getLogger("persistenza fattura inizializza").log(Level.INFO,"persistenza pagamento fattura inizializza");
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("pagamentoFattura");
          }

    public PagamentoFattura ultimaFattura()  {
        Logger.getLogger("Persistenza pagamento fattura").log(Level.INFO,"ultima fattura");
        return null;
    }
    public ObservableList<PagamentoFattura> listPagamentiByUserF(PagamentoFattura pF)  {
        Logger.getLogger("Persistenza pagamento fattura by user").log(Level.INFO,"lisa payment by user:.",pF.getNome());
        return FXCollections.observableArrayList();}


}
