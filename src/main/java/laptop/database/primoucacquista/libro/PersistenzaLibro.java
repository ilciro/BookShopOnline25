package laptop.database.primoucacquista.libro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import java.util.logging.Level;
import java.util.logging.Logger;


public  class PersistenzaLibro {

     public  boolean inserisciLibro(Libro l){
         return l.getId()!=-1 ;}

     public  boolean removeLibroById(Libro l)  {

         return l.getId()!=-0; }

    public  ObservableList<Raccolta> retrieveRaccoltaData()  {
        return FXCollections.observableArrayList();}

    public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) {

        Logger.getLogger("lista  persistenza libro").log(Level.INFO,"lista libro by id .",l.getId());

        return FXCollections.observableArrayList();}

    public void initializza()  {

        Logger.getLogger("inizializza persistenza libro").log(Level.INFO," persistenza libro inizializza");
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("libro");



    }
    public ObservableList<Libro> getLibri()  { return FXCollections.observableArrayList();}




}
