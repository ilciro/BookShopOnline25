package laptop.controller.terzoucgestioneprofilooggetto;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.giornale.CsvGiornale;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.MemoriaGiornale;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.CsvLibro;
import laptop.database.libro.LibroDao;
import laptop.database.libro.MemoriaLibro;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.rivista.CsvRivista;
import laptop.database.rivista.MemoriaRivista;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerGestione {
    private Libro l;
    private Giornale g=new Giornale();
    private Rivista r;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String LIBRO="libro";
    private static final String GIORNALE="giornale";
    private static final String RIVISTA="rivista";
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";
    private PersistenzaLibro pL;
    private PersistenzaGiornale pG;
    private PersistenzaRivista pR;


    public boolean inserisci(String[] param,String persistenza) throws CsvValidationException, IOException, SQLException, ClassNotFoundException {
        boolean status = false;
        vis.setTipoModifica("insert");

        switch (vis.getType())
        {
            case LIBRO -> {
                Libro appoggio=setLibro(param);
                switch (persistenza)
                {
                    case DATABASE -> pL=new LibroDao();
                    case FILE -> pL=new CsvLibro();
                    case MEMORIA -> pL=new MemoriaLibro();
                    default -> Logger.getLogger("inserisci libro").log(Level.SEVERE,"persistency insert book error!!");
                }
               status=pL.inserisciLibro(appoggio);
            }
            case GIORNALE -> {
               // Giornale appoggio=setGiornale(param);
                switch (persistenza){
                    case DATABASE -> pG=new GiornaleDao();
                    case FILE -> pG=new CsvGiornale();
                    case MEMORIA -> pG=new MemoriaGiornale();
                    default -> Logger.getLogger("inserisci giornale").log(Level.SEVERE,"persistency insert daily error!!");

                }
                status=pG.inserisciGiornale(setGiornale(param));

            }
            case RIVISTA -> {
                Rivista appoggio=setRivista(param);

                switch (persistenza)
                {
                    case DATABASE -> pR=new RivistaDao();
                    case FILE -> pR=new CsvRivista();
                    case MEMORIA -> pR=new MemoriaRivista();
                    default -> Logger.getLogger("inserisci rivista").log(Level.SEVERE,"persistency insert magazine error!!");

                }
                status=pR.inserisciRivista(appoggio);
            }
            default -> Logger.getLogger("inserisci").log(Level.SEVERE,"insert is wrong");


        }
        return status;
    }

    public ControllerGestione() {
        l=new Libro();
        g=new Giornale();
        r=new Rivista();

    }


    public ObservableList<Libro>libroById(String persistenza) throws CsvValidationException, IOException, IdException, ClassNotFoundException {


        switch (persistenza)
        {
            case DATABASE -> pL=new LibroDao();
            case FILE -> pL=new CsvLibro();
            case MEMORIA -> pL=new MemoriaLibro();
            default -> Logger.getLogger("libro by id").log(Level.SEVERE,"persistency of book is wrong!!");
        }
        l.setId(vis.getIdLibro());
        return pL.getLibroByIdTitoloAutoreLibro(l);
    }
    public ObservableList<Giornale> giornaleById(String persistenza) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        switch (persistenza)
        {
            case DATABASE -> pG=new GiornaleDao();
            case FILE -> pG=new CsvGiornale();
            case MEMORIA -> pG=new MemoriaGiornale();
            default -> Logger.getLogger("giornali by id").log(Level.SEVERE,"persistency of daily is wrong!!");
        }
        g.setId(vis.getIdGiornale());
        return pG.getGiornaleByIdTitoloAutoreLibro(g);

    }
    public ObservableList<Rivista> rivistaById(String persistenza) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        switch (persistenza)
        {
            case DATABASE -> pR=new RivistaDao();
            case FILE -> pR=new CsvRivista();
            case MEMORIA -> pR=new MemoriaRivista();
            default -> Logger.getLogger("rivista by id").log(Level.SEVERE,"persistency of magazine is wrong!!");
        }
        r.setId(vis.getIdRivista());
        return pR.getRivistaByIdTitoloAutoreRivista(r);

    }



    public boolean modifica(String[] dati,String persistenza) throws CsvValidationException, IOException, IdException, SQLException, ClassNotFoundException {
       boolean status=false;
       vis.setTipoModifica("im");

       switch (vis.getType())
       {
           case LIBRO ->  status= modificaLibro(dati, persistenza);

           case GIORNALE -> status= modificaGiornale(dati, persistenza);

           case RIVISTA ->  status= modificaRivista(dati,persistenza);

           default -> Logger.getLogger("modif").log(Level.SEVERE, "error in modif");
       }


        return status;
    }

    private boolean modificaLibro(String[] dati, String persistenza) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTipoModifica("im");

        //prendo giornale
        Libro appoggio=libroById(persistenza).get(0);

        System.out.println("id gionale preso :"+ appoggio.getId());

        vis.setIdLibro(appoggio.getId());

        switch (persistenza)
        {
            case DATABASE -> pL=new LibroDao();
            case FILE -> pL=new CsvLibro();
            case MEMORIA -> pL=new MemoriaLibro();
            default -> Logger.getLogger("libro by id").log(Level.SEVERE,"persistency of daily is wrong!!");
        }


        if(pL.removeLibroById(appoggio))
        {
            System.out.println("ho cancellato titolo"+appoggio.getTitolo());
            l=setLibro(dati);
            System.out.println("titolo da inserire"+g.getTitolo());

        }
        System.out.println("tupo modifica:"+ vis.getTipoModifica());
        return pL.inserisciLibro(l);


    }

    private boolean modificaGiornale(String[] dati, String persistenza) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {

        vis.setTipoModifica("im");

        //prendo giornale
        Giornale appoggio=giornaleById(persistenza).get(0);

        System.out.println("id gionale preso :"+ appoggio.getId());

        vis.setIdGiornale(appoggio.getId());

        switch (persistenza)
        {
            case DATABASE -> pG=new GiornaleDao();
            case FILE -> pG=new CsvGiornale();
            case MEMORIA -> pG=new MemoriaGiornale();
            default -> Logger.getLogger("giornale by id").log(Level.SEVERE,"persistency of daily is wrong!!");
        }


        if(pG.removeGiornaleById(appoggio))
        {
            System.out.println("ho cancellato titolo"+appoggio.getTitolo());
            g=setGiornale(dati);
            System.out.println("titolo da inserire"+g.getTitolo());

        }
        System.out.println("tupo modifica:"+ vis.getTipoModifica());
        return pG.inserisciGiornale(g);


    }


    private Libro setLibro(String []param)
    {
        l=new Libro();
        l.setTitolo(param[0]);
        l.setCodIsbn(param[1]);
        l.setEditore(param[2]);
        l.setAutore(param[3]);
        l.setLingua(param[4]);
        l.setCategoria(param[5]);
        l.setDesc(param[6]);
        l.setDataPubb(LocalDate.parse(param[7]));
        l.setRecensione(param[8]);
        l.setNrPagine(Integer.parseInt(param[9]));
        l.setNrCopie(Integer.parseInt(param[10]));
        l.setDisponibilita(Integer.parseInt(param[11]));
        l.setPrezzo(Float.parseFloat(param[12]));
        l.setId(vis.getIdLibro());
        return l;


    }
    private Giornale setGiornale(String[] param)
    {
        g.setTitolo(param[0]);
        g.setEditore(param[2]);
        g.setLingua(param[4]);
        g.setCategoria(param[5]);
        g.setDataPubb(LocalDate.parse(param[7]));
        g.setCopieRimanenti(Integer.parseInt(param[10]));
        g.setDisponibilita(Integer.parseInt(param[11]));
        g.setPrezzo(Float.parseFloat(param[12]));
        g.setId(vis.getIdGiornale());
        return g;
    }
    private Rivista setRivista(String []param)
    {
        r=new Rivista();
        r.setTitolo(param[0]);
        r.setEditore(param[2]);
        r.setAutore(param[3]);
        r.setLingua(param[4]);
        r.setCategoria(param[5]);
        r.setDescrizione(param[6]);
        r.setDataPubb(LocalDate.parse(param[7]));
        r.setCopieRim(Integer.parseInt(param[10]));
        r.setDisp(Integer.parseInt(param[11]));
        r.setPrezzo(Float.parseFloat(param[12]));
        r.setId(vis.getIdRivista());
        return r;
    }

    private boolean modificaRivista(String[]dati,String persistenza) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {

        vis.setTipoModifica("im");

        //prendo giornale
        Rivista appoggio=rivistaById(persistenza).get(0);

        System.out.println("id rivista preso :"+ appoggio.getId());

        vis.setIdRivista(appoggio.getId());

        switch (persistenza)
        {
            case DATABASE -> pR=new RivistaDao();
            case FILE -> pR=new CsvRivista();
            case MEMORIA -> pR=new MemoriaRivista();
            default -> Logger.getLogger("rivista by id").log(Level.SEVERE,"persistency of daily is wrong!!");
        }


        if(pR.removeRivistaById(appoggio))
        {
            System.out.println("ho cancellato titolo"+appoggio.getTitolo());
            r=setRivista(dati);
            System.out.println("titolo da inserire"+r.getTitolo());

        }
        System.out.println("tupo modifica:"+ vis.getTipoModifica());
        return pR.inserisciRivista(r);


    }
}
