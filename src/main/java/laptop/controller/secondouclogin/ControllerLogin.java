package laptop.controller.secondouclogin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.users.MemoriaUtente;
import laptop.database.users.PersistenzaUtente;
import laptop.database.users.UsersDao;
import laptop.database.users.CsvUtente;
import laptop.exception.IdException;
import laptop.model.user.User;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerLogin {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final User u= User.getInstance();
    private PersistenzaUtente pU;
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";


    public String login(String email,String pwd,String type) throws CsvValidationException, IOException, IdException, SQLException, ClassNotFoundException {


        u.setEmail(email);
        u.setPassword(pwd);


        switch (type) {
            case DATABASE->pU=new UsersDao();
            case FILE-> pU = new CsvUtente();
            case MEMORIA -> pU = new MemoriaUtente();
            default -> Logger.getLogger("login").log(Level.SEVERE,"persistency error!!");
        }
        if(!Files.exists(Path.of("memory/serializzazioneUtente.ser")))
            pU.initializza();


        String ruolo="";
        String nome = "";
        String cognome="";
        int id=0;


        switch (type) {
            case DATABASE->pU=new UsersDao();
            case FILE -> pU = new CsvUtente();
            case MEMORIA -> pU = new MemoriaUtente();
            default -> Logger.getLogger("login").log(Level.SEVERE,"persistency error!!");
        }


        //uso tempUSer


        for (int i=0;i<pU.getUserData().size();i++)
        {
            if(pU.getUserData().get(i).getEmailT().equals(email)&& pU.getUserData().get(i).getPasswordT().equals(pwd))
            {
                nome=pU.getUserData().get(i).getNomeT();
                cognome=pU.getUserData().get(i).getCognomeT();
                id=pU.getUserData().get(i).getId();
                ruolo=pU.getUserData().get(i).getIdRuoloT();
            }

        }

        u.setNome(nome);
        u.setCognome(cognome);


        switch (ruolo)
        {
            case "U","u","utente","UTENTE"->
            {
                ruolo="UTENTE";
                vis.setIsLogged(true);
                u.setIdRuolo(ruolo);
                u.setId(id);
            }
            case "A","a","admin","ADMIN"-> {
                ruolo = "ADMIN";
                vis.setIsLogged(true);
                u.setIdRuolo(ruolo);
                u.setId(id);
            }
            case "S","s","W","w","SCRITTORE"-> {
                ruolo = "SCRITTORE";
                vis.setIsLogged(true);
                u.setIdRuolo(ruolo);
                u.setId(id);
            }
            case "E","e","EDITORE"-> {
                ruolo = "EDITORE";
                vis.setIsLogged(true);
                u.setIdRuolo(ruolo);
                u.setId(id);
            }
            default ->
            {
                ruolo="NONVALIDO";
                Logger.getLogger(" login").log(Level.SEVERE, " user not found!!");
                vis.setIsLogged(false);
            }
        }



        return ruolo;

    }



    //used for change pass
    //passo a user e poi cancello
    public boolean userPresente(String email,String pwd,String type) throws CsvValidationException, IOException, SQLException {

        boolean status=false;

        switch (type)
        {
            case DATABASE -> pU=new UsersDao();
            case FILE->pU=new CsvUtente();
            case MEMORIA->pU=new MemoriaUtente();
            default -> Logger.getLogger("user presente").log(Level.SEVERE,"error in persistency");
        }

        for (int i=0;i<pU.getUserData().size();i++)
        {
            if(pU.getUserData().get(i).getEmailT().equals(email)&&
            pU.getUserData().get(i).getPasswordT().equals(pwd))
            {
                u.setEmail(email);
                u.setPassword(pwd);
                status=true;
            }
        }



        return status;
    }



}
