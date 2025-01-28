package laptop.database.negozio;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.Negozio;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaNegozio {
     public ObservableList<Negozio> getNegozi() throws CsvValidationException, IOException, IdException, ClassNotFoundException {return FXCollections.emptyObservableList();}
     public boolean checkOpen(Negozio  shop) throws CsvValidationException, IOException, ClassNotFoundException {return false;}
     public boolean checkRitiro(Negozio shop) throws IOException, CsvValidationException, ClassNotFoundException {return false;}
     public void initializza() throws IOException { Logger.getLogger("initializza").log(Level.INFO,"initialize");
    }
}
