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
        Path path = Path.of(LOCATIONR);
        if(!Files.exists(path)) Files.createFile(path);
    }

    @Override
    public ObservableList<Report> report(String type) throws IOException, CsvValidationException {
        ObservableList<Report> list = FXCollections.observableArrayList();
        try (CSVReader reader = new CSVReader(new BufferedReader(new FileReader(fileReport)))) {
            String[] gVector;
            boolean found = false;

            while ((gVector = reader.readNext()) != null) {

                //vedere come prendere le categorie
                switch (type) {
                    case "giornale" -> found = gVector[GETINDEXTIPOOGG].equals("QUOTIDIANO");


                    case "libro" ->
                        found= getStrings().contains(gVector[GETINDEXTIPOOGG]);


                    case "rivista" ->

                            found = getCatR().contains(gVector[GETINDEXTIPOOGG]);


                    default -> Logger.getLogger("report csv").log(Level.SEVERE, "error in report csv");

                }
                if (found) {
                    Report r = getReport(gVector);
                    list.add(r);
                }


            }

            return list;
        }
    }



    private static @NotNull ObservableList<String> getCatR() {
        ObservableList<String> catR=FXCollections.observableArrayList();
        catR.add("SETTIMANALE");
        catR.add("BISETTIMANALE");
        catR.add("MENSILE");
        catR.add("BIMESTRALE");
        catR.add("TRIMESTRALE");
        catR.add("ANNUALE");
        catR.add("ESTIVO");
        catR.add("INVERNALE");
        catR.add("SPORTIVO");
        catR.add("CINEMATOGRAFICA");
        catR.add("GOSSIP");
        catR.add("TELEVISIVO");
        catR.add("MILITARE");
        catR.add("INFORMATICA");
        return catR;
    }

    private static @NotNull ObservableList<String> getStrings() {
        ObservableList<String> catL=FXCollections.observableArrayList();
        catL.add("ADOLESCENTI_RAGAZZI");
        catL.add("ARTE");
        catL.add("CINEMA_FOTOGRAFIA");
        catL.add("BIOGRAFIE");
        catL.add("DIARI_MEMORIE");
        catL.add("CALENDARI_AGENDE");
        catL.add("DIRITTO");
        catL.add("DIZINARI_OPERE");
        catL.add("ECONOMIA");
        catL.add("FAMIGLIA");
        catL.add("FANTASCIENZA_FANTASY");
        catL.add("FUMETTI_MANGA");
        catL.add("GIALLI_THRILLER");
        catL.add("COMPUTER_GIOCHI");
        catL.add("HUMOR");
        catL.add("INFORMATICA");
        catL.add("WEB_DIGITAL_MEDIA");
        catL.add("LETTERATURA_NARRATIVA");
        catL.add("LIBRI_BAMBINI");
        catL.add("LIBRI_SCOLASTICI");
        catL.add("LIBRI_UNIVERSITARI");
        catL.add("RICETTARI_GENERALI");
        catL.add("LINGUISTICA_SCRITTURA");
        catL.add("POLITICA");
        catL.add("RELIGIONE");
        catL.add("ROMANZI_ROSA");
        catL.add("SCIENZE");
        catL.add("TECNOLOGIA_MEDICINA");
        catL.add("ALTRO");
        return catL;
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
