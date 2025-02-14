package laptop.database.report;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Report;
import laptop.model.user.TempUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class PersistenzaReport {

    public ObservableList<TempUser> reportU() throws SQLException, IOException, CsvValidationException {
       if(!Files.exists(Path.of("FileSql/users.sql"))) throw new SQLException("file not found");
       if(!Files.exists(Path.of("report/reportUtenti.csv"))) throw new CsvValidationException(" file csv not created");
       if(!Files.exists(Path.of("memory/serializzazioneUsers.ser"))) throw new IOException(" file inmemory not exists");
       return FXCollections.observableArrayList();
    }

    public ObservableList<Report> report(String type) throws IOException, ClassNotFoundException {
        if(type.isEmpty()) throw new IOException(" type is wrong!!");
        else throw new ClassNotFoundException("report class not found!");
    }

    public boolean insertReport(Report r) throws CsvValidationException, IOException, ClassNotFoundException {
        if(r.getIdReport()==0) throw new IOException(" id report is null");
        if(r.getPrezzo()==0) throw new CsvValidationException(" report by file is null");
        else throw new ClassNotFoundException(" report class not created");
    }



    public void inizializza() throws IOException, ClassNotFoundException {
        if(!Files.exists(Path.of("FileSql/report.sql"))|| !Files.exists(Path.of("report/report.csv"))) throw new IOException(" file not exists");
        if(!Files.exists(Path.of("memory/serializzazioneReport.ser"))) throw new ClassNotFoundException(" class in memory not found");
    }
}