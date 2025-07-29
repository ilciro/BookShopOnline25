package laptop.controller.primoucacquista;


import com.opencsv.exceptions.CsvValidationException;
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
import laptop.exception.IdException;
import laptop.exception.LogoutException;
import laptop.model.user.User;
import laptop.database.primoucacquista.libro.CsvLibro;



import java.io.IOException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerHomePage {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";

    private static PersistenzaLibro pL;
    private static PersistenzaGiornale pG;
    private static PersistenzaRivista pR;


    private void inizializzaLibro(String type) throws CsvValidationException, IOException, ClassNotFoundException, SQLException, IdException {
        switch (type) {
            case DATABASE -> pL = new LibroDao();
            case FILE -> pL = new CsvLibro();
            case MEMORIA -> pL = new MemoriaLibro();
            default -> Logger.getLogger("inizializza libro").log(Level.SEVERE,"persistency init book is wrong!!");

        }
            pL.initializza();
    }


    private void inizializzaGiornale(String type) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        switch (type) {
            case DATABASE -> pG = new GiornaleDao();
            case FILE -> pG = new CsvGiornale();
            case MEMORIA -> pG = new MemoriaGiornale();
            default -> Logger.getLogger("inizializza giornale").log(Level.SEVERE,"persistency init daily is wrong!!");

        }
        pG.initializza();
    }
    private void inizializzaRivista(String type) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        switch (type) {
            case DATABASE -> pR = new RivistaDao();
            case FILE -> pR = new CsvRivista();
            case MEMORIA -> pR = new MemoriaRivista();
            default -> Logger.getLogger("inizializza rivista").log(Level.SEVERE,"persistency magazine book is wrong!!");

        }
            pR.initializza();
    }






    public void persistenza(String type) throws IOException, CsvValidationException, SQLException, ClassNotFoundException, IdException {

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
