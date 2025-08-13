package laptop.database.primoucacquista.libro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.MemoryInitialize;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MemoriaLibro extends PersistenzaLibro{
    private static final String SERIALIZZAZIONE="memory/serializzazioneLibro.ser";
    private static final String SERIALIZZAZIONEAPPOGGIO="memory/serializzazioneLibroAppoggio.ser";

    private static final MemoryInitialize mI = new MemoryInitialize();

    @Override
    public void initializza(){
     mI.inizializza(SERIALIZZAZIONE);
    }


    @Override
    public boolean inserisciLibro(Libro l)  {
        Path path2 = Path.of(SERIALIZZAZIONEAPPOGGIO);
        if (!Files.exists(path2))
        {
            try {
                Files.createFile(path2);
            } catch (IOException e) {
                Logger.getLogger("inserisci libro").log(Level.SEVERE,"inseirsci libro exception {0}",e);
            }
        }


        return mI.inserisci(l,null,null,SERIALIZZAZIONE,SERIALIZZAZIONEAPPOGGIO);

    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData()  {


        return FXCollections.observableArrayList(mI.listaLibri(SERIALIZZAZIONE));
    }

    @Override
    public ObservableList<Libro> getLibroByIdTitoloAutoreLibro(Libro l) {
        ObservableList<Libro> listaRecuperata = FXCollections.observableArrayList();

        List<Libro> list=mI.listaLibri(SERIALIZZAZIONE);


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
    public ObservableList<Libro> getLibri() {
        List<Libro> list=mI.listaLibri(SERIALIZZAZIONE);
        return FXCollections.observableList(list);
    }

    @Override

    public boolean removeLibroById(Libro l)  {
        return mI.cancellaLibro(l);
    }






}
