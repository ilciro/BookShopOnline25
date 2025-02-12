package laptop.controller.secondouclogin;

import com.opencsv.exceptions.CsvValidationException;

import laptop.controller.ControllerSystemState;
import laptop.database.users.CsvUtente;
import laptop.database.users.MemoriaUtente;
import laptop.database.users.PersistenzaUtente;
import laptop.database.users.UsersDao;
import laptop.exception.IdException;
import laptop.model.user.TempUser;
import laptop.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAggiornaPassword {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    private final User u= User.getInstance();
    private PersistenzaUtente pU;
    public String getEmail() {
       return u.getEmail();
    }
    public String getPass() {
     return u.getPassword();
    }

    public boolean aggiorna( String nuovaP,String type) throws CsvValidationException, IOException, IdException, SQLException, ClassNotFoundException {


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



        return pU.inserisciUtente(tu2);
    }
}
