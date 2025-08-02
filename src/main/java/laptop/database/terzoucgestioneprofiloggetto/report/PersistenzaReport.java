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

       return FXCollections.observableArrayList();
    }

    public ObservableList<Report> report(String type) throws IOException, ClassNotFoundException {
        return FXCollections.observableArrayList();
    }

    public boolean insertReport(Report r) throws CsvValidationException, IOException, ClassNotFoundException {
       return r.getIdReport()!=0;
    }



    public void inizializza() throws IOException, ClassNotFoundException {
        Logger.getLogger("inizializza gestione report").log(Level.INFO,"inizializza");
    }
}