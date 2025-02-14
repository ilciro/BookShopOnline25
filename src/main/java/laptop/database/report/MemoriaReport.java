package laptop.database.report;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.users.MemoriaUtente;
import laptop.model.Report;
import laptop.model.user.TempUser;

import java.io.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaReport extends PersistenzaReport{


    private static final String SERIALIZZAZIONE="memory/serializzazioneReport.ser";
    private ArrayList<Report> list=new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public boolean insertReport(Report r) throws  IOException, ClassNotFoundException {


        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)) {
            list = (ArrayList<Report>) ois.readObject();

        }
            r.setIdReport(list.size()+1);
            list.add(r);


            try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
                ObjectOutputStream oos=new ObjectOutputStream(fos))
            {
                oos.writeObject(list);
            }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Report> report(String type) throws IOException, ClassNotFoundException {

        ArrayList<Report> repoLibri=new ArrayList<>();
        ArrayList<Report> repoGiornali=new ArrayList<>();
        ArrayList<Report> repoRiviste=new ArrayList<>();
        ObservableList<Report> reportFinale = null;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Report>) ois.readObject();
        }
        for (Report report : list) {
            switch (type) {
                case "libro" -> {
                    repoLibri.add(report);
                    reportFinale = FXCollections.observableArrayList(repoLibri);
                }
                case "giornale" -> {
                    repoGiornali.add(report);
                    reportFinale = FXCollections.observableArrayList(repoGiornali);
                }
                case "rivista" -> {
                    repoRiviste.add(report);
                    reportFinale = FXCollections.observableArrayList(repoRiviste);
                }
                default -> Logger.getLogger("report").log(Level.SEVERE, " report persistency is wrong!!");
            }
        }

       return reportFinale;

    }

    @Override
    public ObservableList<TempUser> reportU() throws SQLException, IOException, CsvValidationException {
        MemoriaUtente mU=new MemoriaUtente();
        return mU.getUserData();
    }



    @Override
    @SuppressWarnings("unchecked")
    public void inizializza() throws IOException, ClassNotFoundException {
        for(int i=1;i<=3;i++)
        {
            String line;
            ArrayList<String> listaR = new ArrayList<>();

            try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/report" + i + ".txt");
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while ((line = bufferedReader.readLine()) != null) {
                    listaR.add(line);

                }
            }
            Report r=new Report();
            r.setIdReport(Integer.parseInt(listaR.get(0)));
            r.setTipologiaOggetto(listaR.get(1));
            r.setTitoloOggetto(listaR.get(2));
            r.setNrPezzi(Integer.parseInt(listaR.get(3)));
            r.setPrezzo(Float.parseFloat(listaR.get(4)));
            r.setTotale(Float.parseFloat(listaR.get(5)));

            list.add(r);
        }
        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(list);
        }

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Report>) ois.readObject();
        }

    }


}
