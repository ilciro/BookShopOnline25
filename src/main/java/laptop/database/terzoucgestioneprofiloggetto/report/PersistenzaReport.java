package laptop.database.terzoucgestioneprofiloggetto.report;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import laptop.model.Report;
import laptop.model.user.TempUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaReport {

    public ObservableList<TempUser> reportU() throws SQLException, IOException, CsvValidationException {
        getException();

       return FXCollections.observableArrayList();
    }

    public ObservableList<Report> report(String type) throws IOException, ClassNotFoundException {
        getException();

        return FXCollections.observableArrayList();
    }

    public boolean insertReport(Report r) throws CsvValidationException, IOException, ClassNotFoundException {
        getException();

        return r.getIdReport()!=0;
    }



    public void inizializza() throws IOException, ClassNotFoundException {
        Logger.getLogger("inizializza gestione report").log(Level.INFO,"inizializza");
        getException();

    }

    private void getException() {
        Logger.getLogger("persistenza report").log(Level.INFO, "checking files...");

        try {
            if (!Files.exists(Path.of("report/reportFinale.csv"))) throw new CsvValidationException("CSVException");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException");
            if (!Files.exists(Path.of("memory/serializzazioneReport.ser"))) throw new ClassNotFoundException("ClassNotFoundException");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file").log(Level.SEVERE, "exception csv :{0}", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database").log(Level.SEVERE, "exception database :{0}", e);

        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria").log(Level.SEVERE, "exception memory :{0}", e);

        }
    }
}