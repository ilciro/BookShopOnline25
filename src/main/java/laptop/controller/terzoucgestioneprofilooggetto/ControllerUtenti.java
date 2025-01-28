package laptop.controller.terzoucgestioneprofilooggetto;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.users.CsvUtente;
import laptop.database.users.MemoriaUtente;
import laptop.database.users.PersistenzaUtente;
import laptop.database.users.UsersDao;
import laptop.model.user.TempUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerUtenti {


    private PersistenzaUtente pU;
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();



    public ObservableList<TempUser> getList(String persistenza) throws SQLException, CsvValidationException, IOException {
        ObservableList<TempUser> list;

        switch (persistenza){
            case DATABASE -> pU=new UsersDao();
            case FILE -> pU=new CsvUtente();
            case MEMORIA -> pU=new MemoriaUtente();
            default -> Logger.getLogger("get list").log(Level.SEVERE,"persistency not correct");
        }
        list=pU.getUserData();
        return list;
    }



    public boolean elimina(String emailT,String persistenza) throws SQLException, CsvValidationException, IOException {
        boolean status ;


        switch (persistenza){
            case DATABASE -> pU=new UsersDao();
            case FILE -> pU=new CsvUtente();
            case MEMORIA -> pU=new MemoriaUtente();
            default -> Logger.getLogger("elimina").log(Level.SEVERE,"persistency not correct");
        }
        TempUser tu=new TempUser();
        tu.setId(vis.getId());
        tu.setEmailT(emailT);
        status=pU.removeUserByIdEmailPwd(tu);

        return status;
    }


}
