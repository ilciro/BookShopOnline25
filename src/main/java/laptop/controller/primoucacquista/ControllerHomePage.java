package laptop.controller.primoucacquista;


import laptop.controller.ControllerSystemState;
import laptop.database.primoucacquista.giornale.CsvGiornale;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.MemoriaGiornale;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.MemoriaLibro;
import laptop.database.primoucacquista.libro.PersistenzaLibro;
import laptop.database.primoucacquista.rivista.CsvRivista;
import laptop.database.primoucacquista.rivista.MemoriaRivista;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
import laptop.exception.LogoutException;
import laptop.model.user.User;
import laptop.database.primoucacquista.libro.CsvLibro;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerHomePage {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";
    private PersistenzaLibro pL;
    private PersistenzaGiornale pG;
    private  PersistenzaRivista pR;


    private void inizializzaLibro(String type) {
        switch (type) {
            case DATABASE -> pL = new LibroDao();
            case FILE -> pL = new CsvLibro();
            case MEMORIA -> pL = new MemoriaLibro();
            default -> Logger.getLogger("inizializza libro").log(Level.SEVERE,"persistency init book is wrong!!");
        }
        Logger.getLogger("inizializzazione libro").log(Level.INFO,"type of persistency of book {0}",pL);
    }

    private void inizializzaGiornale(String type)  {
        switch (type) {
            case DATABASE -> pG = new GiornaleDao();
            case FILE -> pG = new CsvGiornale();
            case MEMORIA -> pG = new MemoriaGiornale();
            default -> Logger.getLogger("inizializza giornale").log(Level.SEVERE,"persistency init daily is wrong!!");
        }
        Logger.getLogger("inizializzazione giornale").log(Level.INFO,"type of persistency of daily {0}",pG);

    }
    private void inizializzaRivista(String type) {
        switch (type) {
            case DATABASE -> pR = new RivistaDao();
            case FILE -> pR = new CsvRivista();
            case MEMORIA -> pR = new MemoriaRivista();
            default -> Logger.getLogger("inizializza rivista").log(Level.SEVERE,"persistency magazine book is wrong!!");
        }
        Logger.getLogger("inizializzazione rivista").log(Level.INFO,"type of persistency of magazine {0}",pR);
    }

    public void persistenza(String type) {
        switch (vis.getType())
        {
            case "libro"-> inizializzaLibro(type);
            case "giornale"-> inizializzaGiornale(type);
            case "rivista"-> inizializzaRivista(type);
            default -> Logger.getLogger("persistenza").log(Level.SEVERE," type is incorrect !!");
        }
    }

    public boolean logout()
    {
        try {
            if (User.getInstance().annullaUtente()) vis.setIsLogged(false);
            if(vis.getIsLogged()) throw new LogoutException(" user logged");
        }catch (LogoutException e)
        {
            Logger.getLogger("logout").log(Level.SEVERE," user yet logged");
        }
        return !vis.getIsLogged();
    }

    public String getRuolo()
    {
        return User.getInstance().getIdRuolo();
    }
    public String getId() {
        return String.valueOf(User.getInstance().getId());
    }







}
