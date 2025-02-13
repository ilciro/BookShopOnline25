package laptop.controller.terzoucgestioneprofilooggetto;

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

public class ControllerVisualizzaProfilo {

    private static final String DATABASE = "database";
    private static final String FILE = "file";
    private static final String MEMORIA = "memoria";
    private PersistenzaUtente pU;
    private static final User u = User.getInstance();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    public String infoUtente(String persistenza) throws CsvValidationException, SQLException, IOException {
        String utente = "";
        switch (persistenza) {
            case DATABASE -> pU = new UsersDao();
            case FILE -> pU = new CsvUtente();
            case MEMORIA -> pU = new MemoriaUtente();
            default -> Logger.getLogger("info utente").log(Level.SEVERE, "persistency is wrong");
        }
        for (int i = 0; i < pU.getUserData().size(); i++) {
            if (pU.getUserData().get(i).getEmailT().equals(u.getEmail())
                    && pU.getUserData().get(i).getPasswordT().equals(u.getPassword()))
                utente = pU.getUserData().get(i).toString();
        }
        return utente;
    }


    public boolean modifica(String[] dataU, String persistenza) throws CsvValidationException, IOException, IdException, SQLException, ClassNotFoundException {
        boolean status = false;
        String vecchiaMail = u.getEmail();

        vis.setTipoModifica("im");

        switch (persistenza) {
            case DATABASE -> pU = new UsersDao();
            case FILE -> pU = new CsvUtente();
            case MEMORIA -> pU = new MemoriaUtente();
            default -> Logger.getLogger("modifica utente").log(Level.SEVERE, "persistency is wrong");
        }
        TempUser tu = new TempUser();
        tu.setEmailT(vecchiaMail);
        for (int i = 0; i < pU.getUserData().size(); i++) {
            if (pU.getUserData().get(i).getEmailT().equals(tu.getEmailT())) {
                tu = pU.getUserData().get(i);
                setTemptoUser(tu);
                if (pU.removeUserByIdEmailPwd(tu)) {

                    Logger.getLogger("email utente rimosso").log(Level.INFO, "email temp user deleted :{0}", tu.getEmailT());
                    //setto user


                }
                modifUser(dataU);
                status=pU.inserisciUtente(inserisciTempUser());
            }


        }
        return status;
    }

    private void setTemptoUser(TempUser tu) {
        u.setIdRuolo(tu.getIdRuoloT());
        u.setId(tu.getId());
        u.setNome(tu.getNomeT());
        u.setCognome(tu.getCognomeT());
        u.setEmail(tu.getEmailT());
        u.setPassword(tu.getPasswordT());
        u.setDescrizione(tu.getDescrizioneT());
        u.setDataDiNascita(tu.getDataDiNascitaT());

    }
    private void modifUser(String[] dataU)
    {
        if (!dataU[0].isEmpty())
            u.setIdRuolo(dataU[0].substring(0, 1));
        if (!dataU[1].isEmpty())
            u.setNome(dataU[1]);
        if (!dataU[2].isEmpty())
            u.setCognome(dataU[2]);
        if (!dataU[3].isEmpty())
            u.setEmail(dataU[3]);
        if (!dataU[4].isEmpty())
            u.setPassword(dataU[4]);
        if (!dataU[5].isEmpty())
            u.setDescrizione(dataU[5]);


    }
    private TempUser inserisciTempUser()
    {
        TempUser tu=new TempUser();
        tu.setIdRuoloT(u.getIdRuolo());
        tu.setId(u.getId());
        tu.setNomeT(u.getNome());
        tu.setCognomeT(u.getCognome());
        tu.setEmailT(u.getEmail());
        tu.setPasswordT(u.getPassword());
        tu.setDescrizioneT(u.getDescrizione());
        tu.setDataDiNascitaT(u.getDataDiNascita());
        return tu;
    }
}
