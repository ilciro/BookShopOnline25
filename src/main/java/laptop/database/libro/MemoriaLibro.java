package laptop.database.libro;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.MemoryInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import org.jetbrains.annotations.NotNull;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;


public class MemoriaLibro extends PersistenzaLibro{
    private static final String SERIALIZZAZIONE="memory/serializzazioneLibro.ser";
    private static final String SERIALIZZAZIONEAPPOGGIO="memory/serializzazioneLibroAppoggio.ser";

    private  transient ArrayList<Libro> list=new ArrayList<>();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    @Override
    @SuppressWarnings("unchecked")
    public void initializza() throws IOException, CsvValidationException, ClassNotFoundException {
        for(int i=1;i<=19;i++) {
            String line;


            ArrayList<String> listaR = new ArrayList<>();


            try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/libro" + i + ".txt");
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while ((line = bufferedReader.readLine()) != null) {
                    listaR.add(line);

                }
            }
            Libro l = getLibro(listaR);

            list.add(l);
        }
        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(list);
        }

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Libro>) ois.readObject();
        }
    }

    private static @NotNull Libro getLibro(ArrayList<String> listaR) {
        Libro l = new Libro();
        l.setTitolo(listaR.get(0));
        l.setNrPagine(Integer.parseInt(listaR.get(1)));
        l.setCodIsbn(listaR.get(2));
        l.setEditore(listaR.get(3));
        l.setAutore(listaR.get(4));
        l.setLingua(listaR.get(5));
        l.setCategoria(listaR.get(6));
        l.setDataPubb(LocalDate.parse(listaR.get(7)));
        l.setRecensione(listaR.get(8));
        l.setNrCopie(Integer.parseInt(listaR.get(9)));
        l.setDesc(listaR.get(10));
        l.setDisponibilita(Integer.parseInt(listaR.get(11)));
        l.setPrezzo(Float.parseFloat(listaR.get(12)));
        l.setId(Integer.parseInt(listaR.get(13)));
        return l;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciLibro(Libro l) throws CsvValidationException, IOException, ClassNotFoundException {
        Path path = Path.of(SERIALIZZAZIONEAPPOGGIO);
        if (!Files.exists(path))
        {
            Files.createFile(path);
        }


        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Libro>) ois.readObject();
        }


        if (vis.getTipoModifica().equals("im")) l.setId(vis.getIdLibro());
        else if (vis.getTipoModifica().equals("insert")) l.setId(list.size() + 1);
        list.add(l);
        //scrivo lista in appoggio
        try (FileOutputStream fos = new FileOutputStream(SERIALIZZAZIONEAPPOGGIO, true);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);

        }


        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONEAPPOGGIO);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<Libro>) ois.readObject();
        }
        System.out.println("lista after write on appoggio" + list.size());


        try (FileOutputStream fos = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(list);
        }


        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Libro>) ois.readObject();
        }
        return FXCollections.observableArrayList(list);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        ObservableList<Libro> listaRecuperata = FXCollections.observableArrayList();
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Libro>) ois.readObject();
        }

        for(int i=0;i<list.size();i++)
        {
            if(i==l.getId()-1
                    || list.get(i).getTitolo().equals(l.getTitolo())
                    || list.get(i).getEditore().equals(l.getEditore())
                    || list.get(i).getAutore().equals(l.getAutore()))
            {
                listaRecuperata=FXCollections.observableArrayList(list.get(i));
            }


        }


        return listaRecuperata;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Libro> getLibri() throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Libro>) ois.readObject();
        }
        return FXCollections.observableList(list);
    }

    @Override

    public boolean removeLibroById(Libro l) throws  IOException, ClassNotFoundException {
        MemoryInitialize mI=new MemoryInitialize();
        return mI.cancellaLibro(l);
    }


}
