package laptop.database.terzoucgestioneprofiloggetto.report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.model.Report;
import laptop.model.user.TempUser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaReport {

    public ObservableList<TempUser> reportU() {return FXCollections.observableArrayList(); }

    public ObservableList<Report> report(String type) {
        Logger.getLogger("report").log(Level.INFO,"report of :{0}",type);
        return FXCollections.observableArrayList();}

    public boolean insertReport(Report r)  { return r.getIdReport()!=0;}



    public void inizializza()  {
        Logger.getLogger("inizializza gestione report").log(Level.INFO,"inizializza");
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("reportS");


    }


}