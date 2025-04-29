package terzoUcGestione.gestioneOggetto;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerGestione;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerRaccolta;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerGestioneRivista {

    private final ControllerLogin cL=new ControllerLogin();
    private final ControllerRaccolta cR=new ControllerRaccolta();
    private final ControllerGestione cG=new ControllerGestione();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerAdmin cA=new ControllerAdmin();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private static final String RIVISTA="rivista";




   @ParameterizedTest
   @ValueSource(strings = {"database","file","memoria"})
    void testInsert(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //giornale
        vis.setTypeAsMagazine();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        // prendo lista
        cR.getRaccoltaLista(RIVISTA,strings);
        //inserisco nuovo giornale
        String []param=new String[13];

        param[0]=RBOGGETTO.getString("titoloRI");
       param[2]=RBOGGETTO.getString("editoreRI");
       param[3]=RBOGGETTO.getString("autoreRI");
       param[4]=RBOGGETTO.getString("linguaRI");
       param[5]=RBOGGETTO.getString("categoriaR");
       param[6]=RBOGGETTO.getString("descrizioneR");
       param[7]=RBOGGETTO.getString("dataPubbR");
       param[10]=RBOGGETTO.getString("nrCopieR");
       param[11]=RBOGGETTO.getString("dispR");
       param[12]=RBOGGETTO.getString("prezzoRI");

        assertTrue(cG.inserisci(param,strings));
        cA.logout(strings);



    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testModif(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {


        String []param=new String[13];

        //giornale
        vis.setTypeAsMagazine();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"), RBUTENTE.getString("passA"), strings);
        // prendo lista
        cR.getRaccoltaLista(RIVISTA, strings);
        //prendo id

        vis.setIdRivista(3);

        param[0]=RBOGGETTO.getString("titoloModR");
        param[2]=RBOGGETTO.getString("editoreModR");
        param[3]=RBOGGETTO.getString("autoreModR");
        param[4]=RBOGGETTO.getString("linguaModR");
        param[5]=RBOGGETTO.getString("categoriaModR");
        param[6]=RBOGGETTO.getString("descrizioneModR");
        param[7]=RBOGGETTO.getString("dataPubbModR");
        param[10]=RBOGGETTO.getString("nrCopieModR");
        param[11]=RBOGGETTO.getString("dispModR");
        param[12]=RBOGGETTO.getString("prezzoModR");
        assertTrue(cG.modifica(param,strings));
        cA.logout(strings);




    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRemove(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //giornale
        vis.setTypeAsMagazine();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"), RBUTENTE.getString("passA"), strings);
        // prendo lista
        cR.getRaccoltaLista(RIVISTA,strings );

        //prendo id
       vis.setIdRivista(4);
        assertTrue(cR.elimina(strings));
        cA.logout(strings);
    }



}
