package laptop.database.primoucacquista.giornale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.MemoryInitialize;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class  MemoriaGiornale extends PersistenzaGiornale{

    private static final String SERIALIZZAZIONE="memory/serializzazioneGiornale.ser";
    private static final String SERIALIZZAZIONEAPPOGGIO="memory/serializzazioneGiornaleAppoggio.ser";



    private static final MemoryInitialize mI=new MemoryInitialize() ;

    @Override
    public void initializza()  {
        mI.inizializza(SERIALIZZAZIONE);
        super.initializza();
    }

    @Override

    public boolean inserisciGiornale(Giornale g)  {
        super.inserisciGiornale(g);
        Path path2 = Path.of(SERIALIZZAZIONEAPPOGGIO);
        if (!Files.exists(path2))
        {
            try {
                Files.createFile(path2);
            } catch (IOException e) {
                Logger.getLogger("inserisci giornale").log(Level.SEVERE,"insert giornale exception ",e);
            }
        }
        return mI.inserisci(null,g,null,SERIALIZZAZIONE,SERIALIZZAZIONEAPPOGGIO);

    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData() {
        super.retrieveRaccoltaData();
        return FXCollections.observableArrayList(mI.listaGiornali(SERIALIZZAZIONE));
    }

    @Override
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g)  {
        ObservableList<Giornale> listaRecuperata = FXCollections.observableArrayList();
        List<Giornale> list=mI.listaGiornali(SERIALIZZAZIONE);
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
    public ObservableList<Giornale> getGiornali()  {
        super.getGiornali();

        List<Giornale> list=mI.listaGiornali(SERIALIZZAZIONE);
        return FXCollections.observableList(list);
    }

    @Override
    public boolean removeGiornaleById(Giornale g)  {
        super.removeGiornaleById(g);
        return mI.cancellaGiornale(g);
    }









}
