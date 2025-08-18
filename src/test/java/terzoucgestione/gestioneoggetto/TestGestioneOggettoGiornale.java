package terzoucgestione.gestioneoggetto;

import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerAdmin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerGestione;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerRaccolta;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestMethodOrder(MethodOrderer.MethodName.class)



class TestGestioneOggettoGiornale {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ControllerAdmin cA=new ControllerAdmin();
    private static final ControllerRaccolta cR=new ControllerRaccolta();
    private static final ControllerGestione cG=new ControllerGestione();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");



    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testInsertGiornale(String strings)  {

        vis.setTipologiaApplicazione("full");
        vis.setTypeAsDaily();
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista libri
        cR.getRaccoltaLista(vis.getType(),strings);
        //aggiungo libro
        String[] param =new String[14];
        param[0]=RBOGGETTO.getString("titoloGI");
        param[2]= RBOGGETTO.getString("editoreGI");
        param[4]=RBOGGETTO.getString("linguaGI");
        param[5]=RBOGGETTO.getString("categoriaG");
        param[7]= RBOGGETTO.getString("dataPubbG");
        param[10]=RBOGGETTO.getString("nrCopieG");
        param[11]=RBOGGETTO.getString("dispG");
        param[12]=RBOGGETTO.getString("prezzoGI");
        //inserisco libro
        cG.inserisci(param,strings);
        //logout
        assertTrue(cA.logout(strings));

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testModifGiornale(String strings) {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsDaily();
        vis.setIdGiornale(Integer.parseInt(RBOGGETTO.getString("idG")));
        vis.setTipoModifica("im");
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista libri
        cR.getRaccoltaLista(vis.getType(),strings);
        //aggiungo libro
        String[] param =new String[14];
        param[0]=RBOGGETTO.getString("titoloModG");
        param[2]= RBOGGETTO.getString("editoreModG");
        param[4]=RBOGGETTO.getString("linguaModG");
        param[5]=RBOGGETTO.getString("categoriaModG");
        param[7]= RBOGGETTO.getString("dataPubbModG");
        param[10]=RBOGGETTO.getString("nrCopieModG");
        param[11]=RBOGGETTO.getString("dispModG");
        param[12]=RBOGGETTO.getString("prezzoModG");
        //inserisco libro
        cG.modifica(param,strings);
        //logout
        assertTrue(cA.logout(strings));

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testRemoveGiornale(String strings)  {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsDaily();
        vis.setIdLibro(Integer.parseInt(RBOGGETTO.getString("idG")));
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista libri
        cR.getRaccoltaLista(vis.getType(),strings);
        //remove libro
        cR.elimina(strings);
        //logout
        assertTrue(cA.logout(strings));

    }


}
