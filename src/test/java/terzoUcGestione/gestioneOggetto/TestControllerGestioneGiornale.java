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

class TestControllerGestioneGiornale {

    private final ControllerLogin cL=new ControllerLogin();
    private final ControllerRaccolta cR=new ControllerRaccolta();
    private final ControllerGestione cG=new ControllerGestione();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerAdmin cA=new ControllerAdmin();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private static final String GIORNALE="giornale";




   @ParameterizedTest
   @ValueSource(strings = {"database","file","memoria"})
    void testInsert(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //giornale
        vis.setTypeAsDaily();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        // prendo lista
        cR.getRaccoltaLista(GIORNALE,strings);
        //inserisco nuovo giornale
        String []param=new String[13];

         param[0]=RBOGGETTO.getString("titoloGI");
         param[2]=RBOGGETTO.getString("editoreGI");
         param[4]=RBOGGETTO.getString("linguaGI");
         param[5]=RBOGGETTO.getString("categoriaG");
         param[7]= RBOGGETTO.getString("dataPubbG");
         param[10]=RBOGGETTO.getString("nrCopieG");
         param[11]=RBOGGETTO.getString("dispG");
         param[12]=RBOGGETTO.getString("prezzoGI");
        assertTrue(cG.inserisci(param,strings));
        cA.logout(strings);



    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testModif(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {


        String []param=new String[13];

        //giornale
        vis.setTypeAsDaily();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"), RBUTENTE.getString("passA"), strings);
        // prendo lista
        cR.getRaccoltaLista(GIORNALE, strings);
        //prendo id

        vis.setId(2);

        param[0]=RBOGGETTO.getString("titoloModG");
        param[2]=RBOGGETTO.getString("editoreModG");
        param[4]=RBOGGETTO.getString("linguaModG");
        param[5]=RBOGGETTO.getString("categoriaModG");
        param[7]= RBOGGETTO.getString("dataPubbModG");
        param[10]=RBOGGETTO.getString("nrCopieModG");
        param[11]=RBOGGETTO.getString("dispModG");
        param[12]=RBOGGETTO.getString("prezzoModG");
        assertTrue(cG.modifica(param,strings));
        cA.logout(strings);




    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRemove(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //giornale
        vis.setTypeAsDaily();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"), RBUTENTE.getString("passA"), strings);
        // prendo lista
        cR.getRaccoltaLista(GIORNALE,strings );

        //prendo id
       vis.setId(4);
        assertTrue(cR.elimina(GIORNALE));
        cA.logout(strings);
    }



}
