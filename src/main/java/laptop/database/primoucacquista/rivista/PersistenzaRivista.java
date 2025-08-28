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
        Logger.getLogger("inserisci persistenza rivista").log(Level.INFO,"persistenza rivista insert");
        return r.getId()!=0;
    }
    public  boolean removeRivistaById(Rivista r)  {
        Logger.getLogger("remove persistenza rivista").log(Level.INFO,"persistenza rivista remove");
        return r.getId()!=-1;}

    public ObservableList<Raccolta> retrieveRaccoltaData()  {
        Logger.getLogger("raccolta rivista persistenza").log(Level.INFO,"persistenza rivista raccolta");
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
    public ObservableList<Rivista> getRiviste() {
        Logger.getLogger("inserisci persistenza getRiviste").log(Level.INFO,"persistenza rivista getMagazines");
        return FXCollections.observableArrayList();
    }


}
