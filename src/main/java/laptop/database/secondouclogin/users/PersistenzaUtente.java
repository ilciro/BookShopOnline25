package laptop.database.secondouclogin.users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.model.user.TempUser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaUtente {

    public boolean inserisciUtente(TempUser tu)  { return tu.getId()!=-1;}
    public ObservableList<TempUser> getUserData() { return FXCollections.observableArrayList();}
    public boolean removeUserByIdEmailPwd(TempUser tu)  { return tu.getId()!=-0;
    }



    public void initializza()  {

        Logger.getLogger("inizializza persistenza utente").log(Level.INFO,"peristenza utente initialize");
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("utente");


    }

}
