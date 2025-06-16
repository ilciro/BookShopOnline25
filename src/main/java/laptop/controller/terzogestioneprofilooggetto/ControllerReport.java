package laptop.controller.terzogestioneprofilooggetto;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;

import laptop.database.terzogestioneprofilooggetto.report.CsvReport;
import laptop.database.terzogestioneprofilooggetto.report.MemoriaReport;
import laptop.database.terzogestioneprofilooggetto.report.PersistenzaReport;
import laptop.database.terzogestioneprofilooggetto.report.ReportDao;
import laptop.model.Report;
import laptop.model.user.TempUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerReport {
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMEORIA="memoria";
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private PersistenzaReport pRepo;




    public ObservableList<Report> reportTotale(String persistenza) throws IOException, ClassNotFoundException, SQLException, CsvValidationException {

        ObservableList<Report> list=FXCollections.observableArrayList();

        switch (persistenza)
        {
            case DATABASE -> pRepo=new ReportDao();
            case FILE -> pRepo=new CsvReport();
            case MEMEORIA -> pRepo=new MemoriaReport();
            default -> Logger.getLogger("report totale").log(Level.SEVERE," error in persitency!!");
        }
        list.addAll(reportL(persistenza));
        list.addAll(reportG(persistenza));
        list.addAll(reportR(persistenza));
        return list;

    }




    public ObservableList<Report> reportL(String persistenza) throws IOException, ClassNotFoundException, SQLException, CsvValidationException {


        ObservableList<Report> list= FXCollections.observableArrayList();

        switch (persistenza)
       {
           case DATABASE->pRepo=new ReportDao();
           case FILE -> pRepo=new CsvReport();
           case MEMEORIA -> pRepo=new MemoriaReport();
           default -> Logger.getLogger("reportL").log(Level.SEVERE,"report book not created!!");
       }


        list.addAll(pRepo.report(vis.getType()));
        return list;


    }

    public ObservableList<Report> reportG(String persistenza) throws IOException, ClassNotFoundException, SQLException, CsvValidationException {
        ObservableList<Report> list= FXCollections.observableArrayList();

        switch (persistenza)
        {
            case DATABASE->pRepo=new ReportDao();
            case FILE -> pRepo=new CsvReport();
            case MEMEORIA -> pRepo=new MemoriaReport();
            default -> Logger.getLogger("reportG").log(Level.SEVERE,"report daily not created!!");
        }

        list.addAll(pRepo.report(vis.getType()));


        return list;


    }
    public ObservableList<Report> reportR(String persistenza) throws IOException, ClassNotFoundException, SQLException, CsvValidationException {
        ObservableList<Report> list= FXCollections.observableArrayList();

        switch (persistenza)
        {
            case DATABASE->pRepo=new ReportDao();
            case FILE -> pRepo=new CsvReport();
            case MEMEORIA -> pRepo=new MemoriaReport();
            default -> Logger.getLogger("reportR").log(Level.SEVERE,"report magazine not created!!");
        }
        list.addAll(pRepo.report(vis.getType()));
        return list;


    }
    public ObservableList<TempUser> reportUser(String persistenza) throws SQLException, CsvValidationException, IOException {
        ObservableList<TempUser> list=FXCollections.observableArrayList();
        switch (persistenza)
        {
            case DATABASE->pRepo=new ReportDao();
            case FILE -> pRepo=new CsvReport();
            case MEMEORIA -> pRepo=new MemoriaReport();
            default -> Logger.getLogger("reportR").log(Level.SEVERE,"report user not created!!");
        }
       list.addAll(pRepo.reportU());
        return list;
    }





}


