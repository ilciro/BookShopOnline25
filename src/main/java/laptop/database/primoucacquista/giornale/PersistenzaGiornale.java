package laptop.database.primoucacquista.giornale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import laptop.database.PersistenzaGenerale;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaGiornale {



    public  boolean inserisciGiornale(Giornale g) {

        return g.getId()!=0;}
    public  boolean removeGiornaleById(Giornale g) {
        return g.getId()!=-1;}
    public ObservableList<Raccolta> retrieveRaccoltaData()  {

        return FXCollections.observableArrayList();
    }
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) {

        Logger.getLogger("giornale by id").log(Level.INFO,"id giornale .",g.getId());

        return FXCollections.observableArrayList();}
    public void initializza(){

        Logger.getLogger("inizializza persistenza giornale").log(Level.INFO," persistenza giornale inizializza");
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("giornale");



    }
    public ObservableList<Giornale> getGiornali()  {

        return FXCollections.observableArrayList();
    }



}
