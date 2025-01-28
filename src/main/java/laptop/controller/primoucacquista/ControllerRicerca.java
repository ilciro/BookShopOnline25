package laptop.controller.primoucacquista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
import laptop.database.users.CsvUtente;
import laptop.database.users.MemoriaUtente;
import laptop.database.users.PersistenzaUtente;
import laptop.database.users.UsersDao;
import laptop.exception.IdException;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;
import laptop.model.user.TempUser;
import laptop.model.user.User;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerRicerca {


    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";


    private PersistenzaLibro pL;
    private PersistenzaGiornale pG;
    private PersistenzaRivista pR;
    private PersistenzaUtente pU;

    private static final User u= User.getInstance();


    public ObservableList<Raccolta> listaLibri(String nome, String persistenza) throws CsvValidationException, IOException, ClassNotFoundException, IdException {

        ObservableList<Raccolta> listaR=FXCollections.observableArrayList();
        switch (persistenza)
        {
            case DATABASE -> pL=new LibroDao();
            case FILE -> pL=new CsvLibro();
            case MEMORIA -> pL=new MemoriaLibro();
            default -> Logger.getLogger("id oggetto libro").log(Level.SEVERE," id book in null or zero");
        }
            pL.initializza();
        Libro l=new Libro();
        l.setTitolo(nome);
        l.setEditore(nome);
        l.setAutore(nome);
        for(int i=0;i<pL.getLibri().size();i++)
        {
            if(pL.getLibri().get(i).getId()==l.getId()
                    || pL.getLibri().get(i).getTitolo().equals(l.getTitolo())
                    || pL.getLibri().get(i).getAutore().equals(l.getAutore())
                    || pL.getLibri().get(i).getEditore().equals(l.getEditore()))
            {

                listaR.add(pL.getLibri().get(i));
            }
        }
        return FXCollections.observableArrayList(listaR);

    }

    public ObservableList<Raccolta> listaGiornali(String nome, String persistenza) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {

        ObservableList<Raccolta> listaR=FXCollections.observableArrayList();
        switch (persistenza)
        {
            case DATABASE -> pG=new GiornaleDao();
            case FILE -> pG=new CsvGiornale();
            case MEMORIA -> pG=new MemoriaGiornale();
            default -> Logger.getLogger("id oggetto giornale").log(Level.SEVERE," id daily in null or zero");
        }
            pG.initializza();
        Giornale g=new Giornale();
        g.setTitolo(nome);
        g.setEditore(nome);

        for(int i=0;i<pG.getGiornali().size();i++)
        {
            if(pG.getGiornali().get(i).getId()==g.getId()
                    || pG.getGiornali().get(i).getTitolo().equals(g.getTitolo())
                    || pG.getGiornali().get(i).getEditore().equals(g.getEditore()))
            {

                listaR.add(pG.getGiornali().get(i));
            }
        }
        return FXCollections.observableArrayList(listaR);

    }
    public ObservableList<Raccolta> listaRiviste(String nome, String persistenza) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {

        ObservableList<Raccolta> listaR=FXCollections.observableArrayList();
        switch (persistenza)
        {
            case DATABASE -> pR=new RivistaDao();
            case FILE -> pR=new CsvRivista();
            case MEMORIA -> pR=new MemoriaRivista();
            default -> Logger.getLogger("id oggetto rivista").log(Level.SEVERE," id magazine in null or zero");
        }
            pR.initializza();
        Rivista r=new Rivista();
        r.setTitolo(nome);
        r.setEditore(nome);
        r.setAutore(nome);
        for(int i=0;i<pR.getRiviste().size();i++)
        {
            if(pR.getRiviste().get(i).getId()==r.getId()
                    || pR.getRiviste().get(i).getTitolo().equals(r.getTitolo())
                    || pR.getRiviste().get(i).getAutore().equals(r.getAutore())
                    || pR.getRiviste().get(i).getEditore().equals(r.getEditore()))
            {

                listaR.add(pR.getRiviste().get(i));
            }
        }
        return FXCollections.observableArrayList(listaR);

    }

    public boolean logout(String persistenza) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
        boolean status=false;
        switch (persistenza)
        {
            case DATABASE -> pU=new UsersDao();
            case FILE -> pU=new CsvUtente();
            case MEMORIA -> pU=new MemoriaUtente();
            default -> Logger.getLogger("logout").log(Level.SEVERE," persistency logout is wrong!!");
        }
        pU.initializza();
        if(u.getEmail()!=null && u.getPassword()!=null) status=true;
        else {
            TempUser tu = getTempUser();
            if(tu.getEmailT()==null) status=true;

        }
        return status;

    }

    private @NotNull TempUser getTempUser() throws IOException, CsvValidationException, SQLException {
        TempUser tu=new TempUser();
        tu.setEmailT(u.getEmail());
        tu.setPasswordT(u.getPassword());
        for(int i=0;i<pU.getUserData().size();i++)
        {
            if(pU.getUserData().get(i).getEmailT().equals(u.getEmail())
            && pU.getUserData().get(i).getPasswordT().equals(u.getEmail()))
            {
                tu.setId(0);
                tu.setIdRuoloT("x");
                tu.setNomeT("");
                tu.setCognomeT("");
                tu.setPasswordT("");
                tu.setEmailT("");
                tu.setDescrizioneT("");
                tu.setDataDiNascitaT(LocalDate.of(1900,1,1));
                u.setEmail(tu.getEmailT());
                u.setPassword(tu.getPasswordT());
            }
        }
        return tu;
    }


}
