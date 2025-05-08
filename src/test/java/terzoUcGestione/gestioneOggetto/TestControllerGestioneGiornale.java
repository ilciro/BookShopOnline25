package terzoUcGestione.gestioneOggetto;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerGestione;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerRaccolta;
import laptop.exception.IdException;

import laptop.model.raccolta.Giornale;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
    void test1(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
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
    void test2(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {


        String []param=new String[13];

        //giornale
        vis.setTypeAsDaily();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"), RBUTENTE.getString("passA"), strings);
        // prendo lista


        ArrayList<Giornale> list=new ArrayList<>();

        for (int i=0;i<cR.getRaccoltaLista(GIORNALE,strings).size();i++)
        {


            list.add((Giornale) cR.getRaccoltaLista(GIORNALE,strings).get(i));
        }


        for (Giornale giornale : list) {
            if (giornale.getTitolo().equals(RBOGGETTO.getString("titoloGI")))
                vis.setIdGiornale(giornale.getId());
        }






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
    void test3(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {


       //giornale
        vis.setTypeAsDaily();
        //login as admin
        cL.login(RBUTENTE.getString("emailA"), RBUTENTE.getString("passA"), strings);
        // prendo lista
        ArrayList<Giornale> list=new ArrayList<>();

        for (int i=0;i<cR.getRaccoltaLista(GIORNALE,strings).size();i++)
        {
            list.add((Giornale) cR.getRaccoltaLista(GIORNALE,strings).get(i));
        }
        //prendo id
        int id=0;
        for (Giornale giornale : list) {
            if (giornale.getTitolo().equals(RBOGGETTO.getString("titoloModG"))) {
                id = giornale.getId();
            }
        }
        vis.setIdGiornale(id);


        assertTrue(cR.elimina(strings));
        cA.logout(strings);
    }





}


