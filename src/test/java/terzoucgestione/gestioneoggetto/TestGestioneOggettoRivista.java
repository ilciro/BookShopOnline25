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


 class TestGestioneOggettoRivista {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ControllerAdmin cA=new ControllerAdmin();
    private static final ControllerRaccolta cR=new ControllerRaccolta();
    private static final ControllerGestione cG=new ControllerGestione();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");



    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testInsertRivista(String strings) {

        vis.setTipologiaApplicazione("full");
        vis.setTypeAsMagazine();
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista libri
        cR.getRaccoltaLista(vis.getType(),strings);
        //aggiungo libro
        String[] param =new String[14];
        param[0]=RBOGGETTO.getString("titoloRI");
        param[2]= RBOGGETTO.getString("autoreRI");
        param[3]=RBOGGETTO.getString("editoreRI");
        param[4]=RBOGGETTO.getString("linguaRI");
        param[5]=RBOGGETTO.getString("categoriaR");
        param[6]=RBOGGETTO.getString("descrizioneR");
        param[7]= RBOGGETTO.getString("dataPubbR");
        param[10]=RBOGGETTO.getString("nrCopieR");
        param[11]=RBOGGETTO.getString("dispR");
        param[12]=RBOGGETTO.getString("prezzoRI");
        //inserisco libro
        cG.inserisci(param,strings);
        //logout
        assertTrue(cA.logout(strings));

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testModifRivista(String strings)  {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsMagazine();
        vis.setIdLibro(Integer.parseInt(RBOGGETTO.getString("idR")));
        vis.setTipoModifica("im");
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista libri
        cR.getRaccoltaLista(vis.getType(),strings);
        //aggiungo libro
        String[] param =new String[14];
        param[0]=RBOGGETTO.getString("titoloModR");
        param[2]= RBOGGETTO.getString("autoreModR");
        param[3]=RBOGGETTO.getString("editoreModR");
        param[4]=RBOGGETTO.getString("linguaModR");
        param[5]=RBOGGETTO.getString("categoriaModR");
        param[6]=RBOGGETTO.getString("descrizioneModR");
        param[7]= RBOGGETTO.getString("dataPubbModR");
        param[10]=RBOGGETTO.getString("nrCopieModR");
        param[11]=RBOGGETTO.getString("dispModR");
        param[12]=RBOGGETTO.getString("prezzoModR");
        //inserisco libro
        cG.modifica(param,strings);
        //logout
        assertTrue(cA.logout(strings));

    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testRemoveRivista(String strings)  {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsMagazine();
        vis.setIdLibro(Integer.parseInt(RBOGGETTO.getString("idR")));
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
