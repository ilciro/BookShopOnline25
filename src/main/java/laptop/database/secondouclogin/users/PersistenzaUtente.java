package laptop.database.secondouclogin.users;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PersistenzaGenerale;
import laptop.model.user.TempUser;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistenzaUtente {

    public boolean inserisciUtente(TempUser tu)  {
        Logger.getLogger("inserisci persistenza utente").log(Level.INFO,"peristenza utente insert");
        return tu.getId()!=-1;}
    public ObservableList<TempUser> getUserData() {
        Logger.getLogger("user data persistenza utente").log(Level.INFO,"peristenza utente getUSerData");
        return FXCollections.observableArrayList();}
    public boolean removeUserByIdEmailPwd(TempUser tu)  {
        Logger.getLogger("remove persistenza utente").log(Level.INFO,"peristenza utente remove");
        return tu.getId()!=-0;
    }
    public void initializza()  {
        Logger.getLogger("inizializza persistenza utente").log(Level.INFO,"peristenza utente initialize");
        PersistenzaGenerale pG=new PersistenzaGenerale();
        pG.getExcepptionInit("utente");


    }

}
