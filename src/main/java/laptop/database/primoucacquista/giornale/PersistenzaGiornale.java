package laptop.database.primoucacquista.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.DaoInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PersistenzaGiornale {



    public  boolean inserisciGiornale(Giornale g) throws CsvValidationException, IOException,  ClassNotFoundException {

        getException();
        return g.getId()!=0;}
    public  boolean removeGiornaleById(Giornale g) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        getException();

        return g.getId()!=-1;}
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        getException();

        return FXCollections.observableArrayList();
    }
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        getException();

        Logger.getLogger("giornale by id").log(Level.INFO,"id giornale .{0}",g.getId());

        return FXCollections.observableArrayList();}
    public void initializza() throws IOException, SQLException, CsvValidationException, IdException, ClassNotFoundException{

        Logger.getLogger("inizializza persistenza giornale").log(Level.INFO," persistenza giornale inizializza");



    }
    public ObservableList<Giornale> getGiornali() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        getException();

        return FXCollections.observableArrayList();
    }

    private void getException()  {
        Logger.getLogger("persistenza giornale").log(Level.INFO,"checking files...");

        try {
            if (!Files.exists(Path.of("report/reportGiornale.csv")))throw  new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw  new SQLException("SQLException");
            if(!Files.exists(Path.of("memory/serializzazioneGiornale.ser"))) throw new ClassNotFoundException("ClassNotFoundException");
        }catch (CsvValidationException e)
        {
            Logger.getLogger("exception modalita file").log(Level.SEVERE,"exception csv :{0}",e);
        }
        catch (SQLException e)
        {
            Logger.getLogger("exception modalita database").log(Level.SEVERE,"exception database :{0}",e);

        }
        catch (ClassNotFoundException e)
        {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE,"exception memory :{0}",e);

        }

    }

}
