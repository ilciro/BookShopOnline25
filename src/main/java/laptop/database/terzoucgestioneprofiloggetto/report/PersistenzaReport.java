package laptop.database.terzoucgestioneprofiloggetto.report;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Report;
import laptop.model.user.TempUser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaReport {

    public ObservableList<TempUser> reportU() {return FXCollections.observableArrayList(); }

    public ObservableList<Report> report(String type) {return FXCollections.observableArrayList();}

    public boolean insertReport(Report r)  { return r.getIdReport()!=0;}



    public void inizializza()  {
        Logger.getLogger("inizializza gestione report").log(Level.INFO,"inizializza");
        getException();

    }

    private void getException() {
        Logger.getLogger("persistenza report").log(Level.INFO, "checking files...");
        try {
            if (!Files.exists(Path.of("report/reportFinale.csv"))) throw new CsvValidationException("CSVException report");
            if (!Files.exists(Path.of("sql/tableCreate.sql"))) throw new SQLException("SQLException report");
            if (!Files.exists(Path.of("memory/serializzazioneReport.ser"))) throw new ClassNotFoundException("ClassNotFoundException report");
        } catch (CsvValidationException e) {
            Logger.getLogger("exception modalita file report").log(Level.SEVERE, "exception report csv :", e);
        } catch (SQLException e) {
            Logger.getLogger("exception modalita database report").log(Level.SEVERE, "exception report database :", e);
        } catch (ClassNotFoundException e) {
            Logger.getLogger("exception modalita memoria report").log(Level.SEVERE, "exception report memory :", e);
        }
    }
}