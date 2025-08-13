package laptop.controller.secondouclogin;

import laptop.controller.ControllerSystemState;
import laptop.database.secondouclogin.users.MemoriaUtente;
import laptop.database.secondouclogin.users.PersistenzaUtente;
import laptop.database.secondouclogin.users.UsersDao;
import laptop.database.secondouclogin.users.CsvUtente;
import laptop.model.user.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerLogin {
    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final User u= User.getInstance();
    private PersistenzaUtente pU;
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";


    public String login(String email,String pwd,String type)  {


        u.setEmail(email);
        u.setPassword(pwd);


        switch (type) {
            case DATABASE->pU=new UsersDao();
            case FILE-> pU = new CsvUtente();
            case MEMORIA -> pU = new MemoriaUtente();
            default -> Logger.getLogger("login").log(Level.SEVERE,"persistency error!!");
        }
            pU.initializza();


        String ruolo="";
        String nome = "";
        String cognome="";
        int id=0;


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
    public boolean userPresente(String email,String pwd,String type)  {

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
