package laptop.database.rivista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.MemoryInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class MemoriaRivista extends PersistenzaRivista{
    private static final String SERIALIZZAZIONE="memory/serializzazioneRivista.ser";
    private   ArrayList<Rivista> list=new ArrayList<>();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciRivista(Rivista r) throws IOException, ClassNotFoundException {


        //leggo
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
                list= (ArrayList<Rivista>) ois.readObject();
        }
        if(vis.getTipoModifica().equals("im")) r.setId(vis.getIdRivista());
        else if(vis.getTipoModifica().equals("insert")) r.setId(list.size()+1);
        list.add(r);

        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(list);
        }


       return true;

    }

    @Override
    public boolean removeRivistaById(Rivista r) throws IOException, ClassNotFoundException {

        MemoryInitialize mI=new MemoryInitialize();
        return mI.cancellaRivista(r);
    }



    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Rivista> getRiviste() throws IOException, ClassNotFoundException {
       

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Rivista>) ois.readObject();
        }
        return FXCollections.observableList(list);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
       
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){

           list= (ArrayList<Rivista>) ois.readObject();
        }
        return FXCollections.observableArrayList(list);

    }



    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) throws IOException, ClassNotFoundException {


        ObservableList<Rivista> listaRecuperata = FXCollections.observableArrayList();
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Rivista>) ois.readObject();
        }

        for(int i=0;i<list.size();i++)
        {
            if(i==r.getId()-1
                    || list.get(i).getTitolo().equals(r.getTitolo())
                    || list.get(i).getAutore().equals(r.getAutore()))
            {

                listaRecuperata=FXCollections.observableArrayList(list.get(i));
            }

        }
        return listaRecuperata;
    }

    @Override
    public void initializza() throws IOException, CsvValidationException, SQLException, ClassNotFoundException {

        for(int i=1;i<=5;i++)
        {
            String line;


            ArrayList<String> listaR = new ArrayList<>();


            try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/rivista" + i + ".txt");
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while ((line = bufferedReader.readLine()) != null) {
                    listaR.add(line);

                }
            }
            Rivista r = getRivista(listaR);
            list.add(r);
        }
        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(list);
        }




    }

    private static @NotNull Rivista getRivista(ArrayList<String> listaR) {
        Rivista r=new Rivista();
        r.setTitolo(listaR.get(0));
        r.setCategoria(listaR.get(1));
        r.setAutore(listaR.get(2));
        r.setLingua(listaR.get(3));
        r.setEditore(listaR.get(4));
        r.setDescrizione(listaR.get(5));
        r.setDataPubb(LocalDate.parse(listaR.get(6)));
        r.setDisp(Integer.parseInt(listaR.get(7)));
        r.setPrezzo(Float.parseFloat(listaR.get(8)));
        r.setCopieRim(Integer.parseInt(listaR.get(9)));
        r.setId(Integer.parseInt(listaR.get(10)));
        return r;
    }


}
