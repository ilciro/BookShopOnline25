package laptop.database.primoucacquista.rivista;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.MemoryInitialize;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MemoriaRivista extends PersistenzaRivista{
    private static final String SERIALIZZAZIONE="memory/serializzazioneRivista.ser";
    private static final String SERIALIZZAZIONEAPPOGGIO="memory/serializzazioneRivistaAppoggio.ser";

   private static final MemoryInitialize mI=new MemoryInitialize();
    @Override
    public boolean inserisciRivista(Rivista r)  {

        Path path2 = Path.of(SERIALIZZAZIONEAPPOGGIO);
        if (!Files.exists(path2))
        {
            try {
                Files.createFile(path2);
            } catch (IOException e) {
                Logger.getLogger("insiert rivista mem").log(Level.SEVERE,"error with crate file mem :",e);
            }
        }

        return mI.inserisci(null,null,r,SERIALIZZAZIONE,SERIALIZZAZIONEAPPOGGIO);


    }

    @Override
    public boolean removeRivistaById(Rivista r)  {

        return mI.cancellaRivista(r);
    }



    @Override
    public ObservableList<Rivista> getRiviste()  {

        return FXCollections.observableList(mI.listaRiviste(SERIALIZZAZIONE));
    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData() {

        return FXCollections.observableArrayList(mI.listaRiviste(SERIALIZZAZIONE));


    }



    @Override

    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r)  {


        ObservableList<Rivista> listaRecuperata = FXCollections.observableArrayList();
        List<Rivista> list=mI.listaRiviste(SERIALIZZAZIONE);

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
    public void initializza()  {



        mI.inizializza(SERIALIZZAZIONE);



    }







}
