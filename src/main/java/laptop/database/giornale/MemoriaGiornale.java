package laptop.database.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.MemoryInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;


public class  MemoriaGiornale extends PersistenzaGiornale{

    private static final String SERIALIZZAZIONE="memory/serializzazioneGiornale.ser";
    private static final String SERIALIZZAZIONEAPPOGGIO="memory/serializzazioneGiornaleAppoggio.ser";



    private static final MemoryInitialize mI=new MemoryInitialize() ;

    @Override
    public void initializza() throws IOException, CsvValidationException, SQLException, ClassNotFoundException {

        mI.inizializza(SERIALIZZAZIONE);



    }




    @Override

    public boolean inserisciGiornale(Giornale g) throws IOException, ClassNotFoundException {

        Path path2 = Path.of(SERIALIZZAZIONEAPPOGGIO);
        if (!Files.exists(path2))
        {
            Files.createFile(path2);
        }
        return mI.inserisci(null,g,null,SERIALIZZAZIONE,SERIALIZZAZIONEAPPOGGIO);

    }



    @Override

    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException, ClassNotFoundException {

        return FXCollections.observableArrayList(mI.listaGiornali(SERIALIZZAZIONE));
    }

    @Override

    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        ObservableList<Giornale> listaRecuperata = FXCollections.observableArrayList();

        ArrayList<Giornale> list=mI.listaGiornali(SERIALIZZAZIONE);

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
    public ObservableList<Giornale> getGiornali() throws CsvValidationException, IOException, IdException, ClassNotFoundException {

        ArrayList<Giornale> list=mI.listaGiornali(SERIALIZZAZIONE);
        return FXCollections.observableList(list);
    }

    @Override
    public boolean removeGiornaleById(Giornale g) throws  IOException,  ClassNotFoundException {
        return mI.cancellaGiornale(g);
    }









}
