package laptop.database.primoucacquista.cartacredito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.model.CartaDiCredito;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaCC {

    public boolean insCC(CartaDiCredito cc) {
        Logger.getLogger("ins CC").log(Level.INFO,"persitenza cc insCC");
        return cc.getNumeroCC()!=null;

    }
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc)  {

        Logger.getLogger("get cartecredito").log(Level.INFO,"lista cc con nome: ",cc.getNomeUser());
        return FXCollections.observableArrayList();



         }
    public void inizializza()  {
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("cartacredito");
        Logger.getLogger("inizializza persistenza cc").log(Level.INFO,"persistenza cc inizializza");

    }



}
