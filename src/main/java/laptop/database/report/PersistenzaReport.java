package laptop.database.report;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Report;
import laptop.model.user.TempUser;

import java.io.IOException;
import java.sql.SQLException;

public class PersistenzaReport {

    public ObservableList<TempUser> reportU() throws SQLException, IOException, CsvValidationException {
        return FXCollections.emptyObservableList();
    }

    public ObservableList<Report> report(String type) throws IOException, ClassNotFoundException {
        return FXCollections.emptyObservableList();
    }

    public boolean insertReport(Report r) throws CsvValidationException, IOException, ClassNotFoundException {
        return false;
    }

    public ObservableList<Report> returnReportIDTipoTitolo(int id, String tipo, String titolo) throws IOException, CsvValidationException {
        return FXCollections.emptyObservableList();
    }

    public void inizializza() throws IOException, ClassNotFoundException {
    }
}