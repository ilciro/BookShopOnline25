package laptop.database.primoucacquista.negozio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.model.Negozio;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaNegozio {



     public ObservableList<Negozio> getNegozi() {
         return FXCollections.observableArrayList();
     }
     public boolean checkOpen(Negozio  shop)  {

         return shop.getIsOpen();
     }
     public boolean checkRitiro(Negozio shop)  {

         return shop.getIsValid();
     }
     public void initializza() {
         Logger.getLogger("inizializza persistenza negozio").log(Level.INFO," persistenza negozio inizializza");
         PersistenzaGenerale pG=new PersistenzaGenerale();
         pG.getExcepptionInit("negozio");

     }

}
