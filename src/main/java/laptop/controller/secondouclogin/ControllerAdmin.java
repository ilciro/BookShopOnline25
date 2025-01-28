package laptop.controller.secondouclogin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.database.users.CsvUtente;
import laptop.database.users.MemoriaUtente;
import laptop.database.users.PersistenzaUtente;
import laptop.database.users.UsersDao;
import laptop.model.user.TempUser;
import laptop.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAdmin {

    private PersistenzaUtente pU;
    private static final User u=User.getInstance();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    public boolean logout(String type) throws SQLException, CsvValidationException, IOException {
        boolean stastus = false;
        TempUser tu=new TempUser();

        switch (type)
        {
            case "database"->pU=new UsersDao();
            case "file"->pU=new CsvUtente();
            case "memoria"->pU=new MemoriaUtente();
            default -> Logger.getLogger("logout").log(Level.SEVERE,"error in logout persistency");
        }

        for (int i=0;i<pU.getUserData().size();i++)
        {
            if(pU.getUserData().get(i).getEmailT().equals(u.getEmail())&&
            pU.getUserData().get(i).getPasswordT().equals(u.getPassword()))
            {
                tu=pU.getUserData().get(i);
            }
        }
        //annullo tutto
        u.setId(0);
        tu.setId(u.getId());
        tu.setNomeT("");
        tu.setCognomeT("");
        u.setEmail("");
        tu.setEmailT(u.getEmail());
        u.setPassword("");
        tu.setPasswordT(u.getPassword());
        tu.setDescrizioneT("");
        tu.setDataDiNascitaT(LocalDate.of(1900,1,1));
        u.setIdRuolo("x");
        tu.setIdRuoloT(u.getIdRuolo());

        if(tu.getEmailT().isEmpty())
        {
            vis.setIsLogged(false);
            stastus=true;
        }


        return stastus;
    }


}
