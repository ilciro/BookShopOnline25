package laptop.database.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MemoriaGiornale extends PersistenzaGiornale{

    private static final String SERIALIZZAZIONE="memory/serializzazioneGiornale.ser";
    private  ArrayList<Giornale> list=new ArrayList<>();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();


    @Override
    @SuppressWarnings("unchecked")
    public void initializza() throws IOException, CsvValidationException, SQLException, ClassNotFoundException {


       for(int i=1;i<13;i++) {
           String line;


           ArrayList<String> listaG = new ArrayList<>();


           try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/giornale" + i + ".txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {
               while ((line = bufferedReader.readLine()) != null) {
                   listaG.add(line);

               }
           }
           Giornale g = new Giornale();
           g.setTitolo(listaG.get(0));
           g.setEditore(listaG.get(3));
           g.setLingua(listaG.get(2));
           g.setCategoria(listaG.get(1));
           g.setDataPubb(LocalDate.parse(listaG.get(4)));
           g.setCopieRimanenti(Integer.parseInt(listaG.get(5)));
           g.setDisponibilita(Integer.parseInt(listaG.get(6)));
           g.setPrezzo(Float.parseFloat(listaG.get(7)));
           g.setId(Integer.parseInt(listaG.get(8)));

           list.add(g);


       }
        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(list);
        }

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Giornale>) ois.readObject();
        }

        Logger.getLogger("inizializzazione memoria giornale").log(Level.INFO,"records inserted :{0}",list.size());



    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciGiornale(Giornale g) throws IOException, ClassNotFoundException {

        //leggo
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Giornale>) ois.readObject();
        }
        if(vis.getTipoModifica().equals("im")) g.setId(vis.getId());
        else if(vis.getTipoModifica().equals("insert")) g.setId(list.size()+1);

        list.add(g);

        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(list);
        }

        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Giornale>) ois.readObject();
        }
        return FXCollections.observableArrayList(list);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        ObservableList<Giornale> listaRecuperata = FXCollections.observableArrayList();
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Giornale>) ois.readObject();
        }

        for(int i=0;i<list.size();i++)
        {
            if(i==g.getId()-1
            || list.get(i).getTitolo().equals(g.getTitolo())
            || list.get(i).getEditore().equals(g.getEditore()))
            {
                listaRecuperata=FXCollections.observableArrayList(list.get(i));
            }

        }


        return listaRecuperata;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Giornale> getGiornali() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Giornale>) ois.readObject();
        }
        return FXCollections.observableList(list);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeGiornaleById(Giornale g) throws  IOException,  ClassNotFoundException {
        boolean status=false;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Giornale>) ois.readObject();

        }

        status = isStatus(g, status);

        return status;
    }

    private boolean isStatus(Giornale g, boolean status) throws IOException {



        for(int i=0;i<list.size();i++)
        {
            if(i== (g.getId()-1)) {
                status = list.remove(list.get(i));
            }
        }

        Path path = Path.of(SERIALIZZAZIONE);
        try {
            Files.delete(path);
            if(!Files.exists(path))
            {
                throw new IOException("file is deleted!!");

            }

        }catch (IOException e)
        {
            Files.createFile(path);
            try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
                ObjectOutputStream oos=new ObjectOutputStream(fos)){
                oos.writeObject(list);
            }
        }


        return status;
    }
}
