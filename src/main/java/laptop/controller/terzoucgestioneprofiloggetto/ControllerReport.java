package laptop.controller.terzoucgestioneprofiloggetto;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;

import laptop.database.terzoucgestioneprofiloggetto.report.CsvReport;
import laptop.database.terzoucgestioneprofiloggetto.report.MemoriaReport;
import laptop.database.terzoucgestioneprofiloggetto.report.PersistenzaReport;
import laptop.database.terzoucgestioneprofiloggetto.report.ReportDao;
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
        pRepo.inizializza();
        list.addAll(pRepo.report("libro"));
        list.addAll(pRepo.report("giornale"));
        list.addAll(pRepo.report("rivista"));

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
       pRepo.inizializza();


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
        pRepo.inizializza();

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
        pRepo.inizializza();

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


