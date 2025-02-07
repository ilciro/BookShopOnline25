package laptop.database.fattura;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.Fattura;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaFattura {

    public boolean inserisciFattura(Fattura f) throws IOException, ClassNotFoundException {return true;}
    public boolean cancellaFattura(Fattura f) throws CsvValidationException, IOException, ClassNotFoundException {return false;}
    public Fattura ultimaFattura() throws CsvValidationException, IOException, ClassNotFoundException {return null;}
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        Logger.getLogger("inizializza fattura").log(Level.INFO,"inserting fattura");}
}
