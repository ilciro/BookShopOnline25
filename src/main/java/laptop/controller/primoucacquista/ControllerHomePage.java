package laptop.controller.primoucacquista;


import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.giornale.MemoriaGiornale;
import laptop.database.libro.MemoriaLibro;
import laptop.database.rivista.MemoriaRivista;
import laptop.exception.LogoutException;
import laptop.model.user.User;
import laptop.database.giornale.CsvGiornale;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.CsvLibro;
import laptop.database.libro.LibroDao;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.rivista.CsvRivista;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;
import java.io.IOException;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerHomePage {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String DATABASE="database";
    private static final String FILE="file";


    private void inizializzaLibro(String type) throws CsvValidationException, IOException, ClassNotFoundException, SQLException {
        PersistenzaLibro pL;
        if(type.equals(DATABASE)) pL =new LibroDao();
        else if(type.equals(FILE)) pL =new CsvLibro();
        else pL=new MemoriaLibro();

            pL.initializza();
    }
    private void inizializzaGiornale(String type) throws CsvValidationException, SQLException, IOException, ClassNotFoundException {
        PersistenzaGiornale pG;
        if(type.equals(DATABASE)) pG =new GiornaleDao();
        else if(type.equals(FILE))pG =new CsvGiornale();
        else pG=new MemoriaGiornale();
            pG.initializza();
    }
    private void inizializzaRivista(String type) throws CsvValidationException, SQLException, IOException, ClassNotFoundException {
        PersistenzaRivista pR;
        if(type.equals(DATABASE)) pR =new RivistaDao();
        else if(type.equals(FILE)) pR =new CsvRivista();
        else pR=new MemoriaRivista();
            pR.initializza();
    }


    public void persistenza(String type) throws IOException, CsvValidationException, SQLException, ClassNotFoundException {

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
