package laptop.database.primoucacquista.negozio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.model.Negozio;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaNegozio {



     public ObservableList<Negozio> getNegozi() {
         Logger.getLogger("get negozi").log(Level.INFO,"persitenza negozio getNegozi");

         return FXCollections.observableArrayList();
     }
     public boolean checkOpen(Negozio  shop)  {
         Logger.getLogger("check open").log(Level.INFO,"persitenza negozio checkOpen");

         return shop.getIsOpen();
     }
     public boolean checkRitiro(Negozio shop)  {
         Logger.getLogger("check ritiro").log(Level.INFO,"persitenza negozio checkRitiro");
         return shop.getIsValid();
     }
     public void initializza() {
         Logger.getLogger("inizializza persistenza negozio").log(Level.INFO," persistenza negozio inizializza");
         PersistenzaGenerale pG=new PersistenzaGenerale();
         pG.getExcepptionInit("negozio");

     }

}
