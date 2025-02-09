package laptop.database.report;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.users.CsvUtente;
import laptop.model.Report;
import laptop.model.user.TempUser;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CsvReport extends PersistenzaReport{
    private static final String LOCATIONR = "report/reportFinale.csv";
    private static final int GETINDEXIDR = 0;
    private static final int GETINDEXTIPOOGG = 1;
    private static final int GETINDEXTITOLOOGG = 2;
    private static final int GETINDEXNRPEZZI = 3;
    private static final int GETINDEXPREZZO = 4;
    private static final int GETINDEXTOTALE = 5;



    private static final File fileReport=new File(LOCATIONR);

    @Override
    public boolean insertReport(Report r) throws CsvValidationException, IOException {
        boolean status=false;
        try (CSVWriter writer = new CSVWriter(new BufferedWriter(new FileWriter(fileReport, true)))) {

            String[] gVectore = new String[6];

            gVectore[GETINDEXIDR] = String.valueOf(getIdMax() + 1);
            gVectore[GETINDEXTIPOOGG] = r.getTipologiaOggetto();
            gVectore[GETINDEXTITOLOOGG] = r.getTitoloOggetto();
            gVectore[GETINDEXNRPEZZI] = String.valueOf(r.getNrPezzi());
            gVectore[GETINDEXPREZZO] = String.valueOf(r.getPrezzo());
            gVectore[GETINDEXTOTALE] = String.valueOf(r.getTotale());
            writer.writeNext(gVectore);

            writer.flush();

        }
        if (r.getIdReport()!=0) status=true;
        return status;
    }
    private static  int getIdMax() throws IOException, CsvValidationException {
        //used for insert correct idOgg
        String[] gVector;
        int id = 0;
        try (CSVReader reader = new CSVReader(new FileReader(LOCATIONR))) {
            while ((gVector = reader.readNext()) != null) {
                id = Integer.parseInt(gVector[GETINDEXIDR]);
            }
        }
        return id;

    }

    @Override
    public void inizializza() throws IOException, ClassNotFoundException {
        if(!Files.exists(Path.of(LOCATIONR))) Files.createFile(Path.of(LOCATIONR));
    }

    @Override
    public ObservableList<Report> report(String type) {
        Logger.getLogger("getReport").log(Level.INFO,"yet used by reportIdTitolo");
        return FXCollections.emptyObservableList();
    }

    @Override
    public ObservableList<Report> returnReportIDTipoTitolo(int id, String tipo, String titolo) throws IOException, CsvValidationException {
        ObservableList<Report> list= FXCollections.observableArrayList();
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fileReport)))) {
            String[] gVector;
            boolean recordFound;
            while ((gVector = reader.readNext()) != null) {
                if (tipo == null) {
                    Report report = getReport(gVector);
                    list.add(report);
                } else {
                    recordFound = gVector[GETINDEXIDR].equals(String.valueOf(id)) || gVector[GETINDEXTIPOOGG].equals(tipo)
                            || gVector[GETINDEXTITOLOOGG].equals(titolo);
                    if (recordFound) {
                        Report report = getReport(gVector);
                        list.add(report);
                    }
                }
            }
        }
        return list;
    }

    private static @NotNull Report getReport(String[] gVector) {
        Report report=new Report();
        report.setIdReport(Integer.parseInt(gVector[GETINDEXIDR]));
        report.setTipologiaOggetto(gVector[GETINDEXTIPOOGG]);
        report.setTitoloOggetto(gVector[GETINDEXTITOLOOGG]);
        report.setNrPezzi(Integer.parseInt(gVector[GETINDEXNRPEZZI]));
        report.setPrezzo(Float.parseFloat(gVector[GETINDEXPREZZO]));
        report.setTotale(Float.parseFloat(gVector[GETINDEXNRPEZZI])*Float.parseFloat(gVector[GETINDEXPREZZO]));
        return report;
    }

    @Override
    public ObservableList<TempUser> reportU() throws  IOException, CsvValidationException {

        CsvUtente csv=new CsvUtente();
        return FXCollections.observableArrayList(csv.getUserData());
    }



}
