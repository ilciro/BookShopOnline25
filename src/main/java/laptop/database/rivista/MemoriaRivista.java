package laptop.database.rivista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;


public class MemoriaRivista extends PersistenzaRivista{
    private static final String SERIALIZZAZIONE="memory/serializzazioneRivista.ser";
    private   ArrayList<Rivista> list=new ArrayList<>();
    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciRivista(Rivista r) throws IOException, ClassNotFoundException {


        //leggo
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
                list= (ArrayList<Rivista>) ois.readObject();
        }
        r.setId(list.size()+1);
        list.add(r);

        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(list);
        }


       return true;

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeRivistaById(Rivista r) throws IOException, ClassNotFoundException {

        boolean status=false;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Rivista>) ois.readObject();

        }

        for(int i=1;i<=list.size();i++)
        {
            if(i==r.getId()) {
                status = list.remove(list.get(i-1));
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
        for (Rivista rivista : list) {
            if (rivista.getId() == r.getId()
                    || rivista.getTitolo().equals(r.getTitolo())
                    || rivista.getAutore().equals(r.getAutore())) {
                listaRecuperata = FXCollections.observableArrayList(rivista);
                break;
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
            list.add(r);
        }
        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(list);
        }




    }




}
