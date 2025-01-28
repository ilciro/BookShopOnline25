package laptop.database.libro;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
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
    private  ArrayList<Libro> list=new ArrayList<>();

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
        //leggo
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Libro>) ois.readObject();
        }
        l.setId(list.size()+1);
        list.add(l);

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
            if(list.get(i).getId()==l.getId()
                    || list.get(i).getTitolo().equals(l.getTitolo())
                    || list.get(i).getAutore().equals(l.getAutore())
                    || list.get(i).getEditore().equals(l.getEditore()))
            {

                listaRecuperata=FXCollections.observableArrayList(list);
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
    @SuppressWarnings("unchecked")
    public boolean removeLibroById(Libro l) throws  IOException, ClassNotFoundException {
        boolean status=false;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Libro>) ois.readObject();

        }


        status = isStatus(l, status);

        return status;
    }

    private boolean isStatus(Libro l, boolean status) throws IOException {
        for(int i=1;i<=list.size();i++)
        {
            if(i== l.getId()) {
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
}
