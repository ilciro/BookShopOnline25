package laptop.database.primoucacquista.rivista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaRivista {


    public  boolean inserisciRivista(Rivista r){
        return r.getId()!=0;
    }
    public  boolean removeRivistaById(Rivista r)  {
        return r.getId()!=-1;}

    public ObservableList<Raccolta> retrieveRaccoltaData()  {

        return FXCollections.observableArrayList();

    }
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r)  {
        Logger.getLogger("list rivista").log(Level.INFO,"id rivista : {0}",r.getId());

        return FXCollections.observableArrayList();
    }
    public void initializza() {

        Logger.getLogger("inizializza persistenza rivista").log(Level.INFO,"persistenza rivista inizializza");

        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("rivista");



    }
    public ObservableList<Rivista> getRiviste()  {
     return FXCollections.observableArrayList();
    }


}
