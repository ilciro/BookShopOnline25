package laptop.controller.terzoucgestioneprofiloggetto;

import laptop.controller.ControllerSystemState;
import laptop.database.secondouclogin.users.CsvUtente;
import laptop.database.secondouclogin.users.MemoriaUtente;
import laptop.database.secondouclogin.users.PersistenzaUtente;
import laptop.database.secondouclogin.users.UsersDao;
import laptop.model.user.TempUser;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerGestioneUtente {

    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private PersistenzaUtente pU;

    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";


    public TempUser getDataUser(String type)  {

        TempUser tu = null;
        switch (type)
        {
            case DATABASE -> pU=new UsersDao();
            case FILE -> pU=new CsvUtente();
            case MEMORIA -> pU=new MemoriaUtente();
            default -> Logger.getLogger("inserisci utente").log(Level.SEVERE,"error in persistency data user");
        }
        for(int i=0;i<pU.getUserData().size();i++) {
            if(pU.getUserData().get(i).getId()==vis.getIdUtente()-1)
                tu=pU.getUserData().get(i);

        }
        return tu;

    }



    public boolean inserisciUtente(String []data ,String type)  {


        vis.setTipoModifica("insert");

        TempUser tu=new TempUser();


        tu.setIdRuoloT(data[0].substring(0,1));
        tu.setNomeT(data[1]);
        tu.setCognomeT(data[2]);
        tu.setEmailT(data[3]);
        tu.setPasswordT(data[4]);
        tu.setDescrizioneT(data[5]);
        tu.setDataDiNascitaT(LocalDate.parse(data[6]));

        switch (type)
        {
            case DATABASE -> pU=new UsersDao();
            case FILE -> pU=new CsvUtente();
            case MEMORIA -> pU=new MemoriaUtente();
            default -> Logger.getLogger("inserisci utente").log(Level.SEVERE,"error in persistency");
        }

        return pU.inserisciUtente(tu);
    }


    private TempUser dati(String [] data)
    {
        TempUser tu=new TempUser();
        tu.setIdRuoloT(data[0].substring(0,1));
        tu.setNomeT(data[1]);
        tu.setCognomeT(data[2]);
        tu.setEmailT(data[3]);
        tu.setPasswordT(data[4]);
        tu.setDescrizioneT(data[5]);
        tu.setDataDiNascitaT(LocalDate.parse(data[6]));
        tu.setId(vis.getIdUtente());
        return tu;
    }



    public boolean modifica(String[] data,String persistenza,String vecchiaMail)  {
        TempUser tu=new TempUser();
        vis.setTipoModifica("im");

        tu.setEmailT(vecchiaMail);



        switch (persistenza)
        {
            case DATABASE -> pU=new UsersDao();
            case FILE -> pU=new CsvUtente();
            case MEMORIA -> pU=new MemoriaUtente();
            default -> Logger.getLogger("modifica utente").log(Level.SEVERE,"error in persistency");
        }

        for (int i=0;i<pU.getUserData().size();i++)
        {
            if(pU.getUserData().get(i).getId()==vis.getIdUtente()-1
            || pU.getUserData().get(i).getEmailT().equals(vecchiaMail))
            {

                vis.setIdUtente(pU.getUserData().get(i).getId()+1);
                pU.removeUserByIdEmailPwd(tu);

            }
        }
       tu=dati(data);
        return pU.inserisciUtente(tu);
    }
}
