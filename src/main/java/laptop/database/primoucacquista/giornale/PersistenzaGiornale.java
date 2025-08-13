package laptop.database.primoucacquista.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;


import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
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

        Logger.getLogger("giornale by id").log(Level.INFO,"id giornale .{0}",g.getId());

        return FXCollections.observableArrayList();}
    public void initializza(){
        getException();

        Logger.getLogger("inizializza persistenza giornale").log(Level.INFO," persistenza giornale inizializza");



    }
    public ObservableList<Giornale> getGiornali()  {

        return FXCollections.observableArrayList();
    }

    private void getException()  {
        Logger.getLogger("persistenza giornale").log(Level.INFO,"checking files...");

        try {
            if (!Files.exists(Path.of("report/reportGiornale.csv")))throw  new CsvValidationException("persistenza giornale CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw  new SQLException("persistenza giornale SQLException");
            if(!Files.exists(Path.of("memory/serializzazioneGiornale.ser"))) throw new ClassNotFoundException("persistenza giornale ClassNotFoundException");
        }catch (CsvValidationException e)
        {
            Logger.getLogger("exception modalita file").log(Level.SEVERE," persistenza giornale exception csv :{0}",e);
        }
        catch (SQLException e1)
        {
            Logger.getLogger("exception modalita database").log(Level.SEVERE,"persistenza giornale exception database :{0}",e1);

        }
        catch (ClassNotFoundException e2)
        {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE," persistenza giornale exception memory :{0}",e2);

        }

    }

}
