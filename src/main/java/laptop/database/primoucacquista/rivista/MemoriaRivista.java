package laptop.database.primoucacquista.rivista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.MemoryInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MemoriaRivista extends PersistenzaRivista{
    private static final String SERIALIZZAZIONE="memory/serializzazioneRivista.out";
    private static final String SERIALIZZAZIONEAPPOGGIO="memory/serializzazioneRivistaAppoggio.out";

   private static final MemoryInitialize mI=new MemoryInitialize();
    @Override
    public boolean inserisciRivista(Rivista r) throws IOException, ClassNotFoundException {

        Path path2 = Path.of(SERIALIZZAZIONEAPPOGGIO);
        if (!Files.exists(path2))
        {
            Files.createFile(path2);
        }

        return mI.inserisci(null,null,r,SERIALIZZAZIONE,SERIALIZZAZIONEAPPOGGIO);


    }

    @Override
    public boolean removeRivistaById(Rivista r) throws IOException, ClassNotFoundException {

        return mI.cancellaRivista(r);
    }



    @Override
    public ObservableList<Rivista> getRiviste() throws IOException, ClassNotFoundException {

        return FXCollections.observableList(mI.listaRiviste(SERIALIZZAZIONE));
    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {

        return FXCollections.observableArrayList(mI.listaRiviste(SERIALIZZAZIONE));


    }



    @Override

    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) throws IOException, ClassNotFoundException {


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
    public void initializza() throws CsvValidationException, SQLException, ClassNotFoundException, IOException {



        mI.inizializza(SERIALIZZAZIONE);



    }







}
