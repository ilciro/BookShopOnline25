package laptop.database.terzoucgestioneprofiloggetto.report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import laptop.database.secondouclogin.users.MemoriaUtente;
import laptop.model.Report;
//import laptop.model.user.TempUser;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
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

        }catch (EOFException e)
        {
            Logger.getLogger("insert report").log(Level.SEVERE," exception {0}",e);
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
    /*

    @Override
    public ObservableList<TempUser> reportU() throws SQLException, IOException, CsvValidationException {
        MemoriaUtente mU=new MemoriaUtente();
        return mU.getUserData();
    }

     */



    @Override

    public void inizializza() throws IOException, ClassNotFoundException {

        Path path = Path.of(SERIALIZZAZIONE);
        try
        {
           if(!Files.exists(path)) throw new IOException("report memoria non esiste!!");
        }catch (IOException e)
        {
            Logger.getLogger("inizializza memoria report").log(Level.SEVERE,"file not exists .",SERIALIZZAZIONE);
            Files.createFile(path);
            Logger.getLogger("inizializza memoria report").log(Level.INFO,"file created");


        }

    }


}
