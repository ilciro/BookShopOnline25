package laptop.controller.secondouclogin;


import laptop.controller.ControllerSystemState;
import laptop.database.secondouclogin.users.CsvUtente;
import laptop.database.secondouclogin.users.MemoriaUtente;
import laptop.database.secondouclogin.users.PersistenzaUtente;
import laptop.database.secondouclogin.users.UsersDao;
import laptop.model.user.TempUser;
import laptop.model.user.User;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAggiornaPassword {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    private static final User u= User.getInstance();
    private PersistenzaUtente pU;
    public String getEmail() {
       return u.getEmail();
    }
    public String getPass() {
     return u.getPassword();
    }

    public boolean aggiorna( String nuovaP,String type)  {


        vis.setTipoModifica("im");

        TempUser tu2 = new TempUser();

        switch (type)
        {
            case "database"->pU=new UsersDao();
            case "file"->pU=new CsvUtente();
            case "memoria"->pU=new MemoriaUtente();
            default -> Logger.getLogger("aggiorna").log(Level.SEVERE,"error in update persistency");
        }



        for(int i=0;i<pU.getUserData().size();i++)
        {




            if(pU.getUserData().get(i).getEmailT().equals(getEmail())
            && pU.getUserData().get(i).getPasswordT().equals(getPass()))
            {

                tu2=pU.getUserData().get(i);
                pU.removeUserByIdEmailPwd(pU.getUserData().get(i));

            }


        }

       tu2.setPasswordT(nuovaP);

        Logger.getLogger("insert modif user ").log(Level.INFO,"user inserted : {0},{1}",new Object[] {tu2.getEmailT(), tu2.getPasswordT()});


        return pU.inserisciUtente(tu2);
    }
}
