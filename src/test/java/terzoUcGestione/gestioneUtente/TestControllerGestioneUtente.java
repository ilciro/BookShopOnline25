package terzoUcGestione.gestioneUtente;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerGestioneUtente;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerUtenti;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerGestioneUtente {

     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private final ControllerLogin cL=new ControllerLogin();
     private final ControllerAdmin cA=new ControllerAdmin();
     private final ControllerUtenti cU=new ControllerUtenti();
     private final ControllerGestioneUtente cGU=new ControllerGestioneUtente();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void userInsert(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
         //login
         cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
         //prendo lista
         cU.getList(strings);
         //inserisco dati
         String[] dati=new String[7];

         dati[0]=RBUTENTE.getString("ruoloIns").substring(0,1);
         dati[1]=RBUTENTE.getString("nomeIns");
         dati[2]=RBUTENTE.getString("cognomeIns");
         dati[3]=RBUTENTE.getString("emailIns");
         dati[4]=RBUTENTE.getString("pwdIns");
         dati[5]=RBUTENTE.getString("descIns");
         dati[6]=RBUTENTE.getString("dataIns");

        assertTrue(cGU.inserisciUtente(dati,strings));
        cA.logout(strings);

     }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void userModif(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista
        cU.getList(strings);
        //inserisco dati
        String[] dati=new String[7];

        dati[0]=RBUTENTE.getString("ruoloInsM").substring(0,1);
        dati[1]=RBUTENTE.getString("nomeInsM");
        dati[2]=RBUTENTE.getString("cognomeInsM");
        dati[3]=RBUTENTE.getString("emailInsM");
        dati[4]=RBUTENTE.getString("pwdInsM");
        dati[5]=RBUTENTE.getString("descInsM");
        dati[6]=RBUTENTE.getString("dataIns");

        vis.setId(7);

        assertTrue(cGU.modifica(dati,strings,"editore185@gmail.com"));
        cA.logout(strings);

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRemove(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista
        cU.getList(strings);
        //setto id
        vis.setId(2);
        assertTrue(cU.elimina("giannni@gmail.com",strings));
        cA.logout(strings);
    }


}
