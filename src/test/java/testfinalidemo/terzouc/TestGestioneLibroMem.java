package testfinalidemo.terzouc;

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


class TestGestioneLibroMem {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ControllerAdmin cA=new ControllerAdmin();
    private static final ControllerRaccolta cR=new ControllerRaccolta();
    private static final ControllerGestione cG=new ControllerGestione();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");



    @ParameterizedTest
    @ValueSource(strings = {"memoria"})
    void testInsertLibro(String strings)  {

        vis.setTipologiaApplicazione("demo");
        vis.setTypeAsBook();
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista libri
        cR.getRaccoltaLista(vis.getType(),strings);
        //aggiungo libro
        String[] param =new String[14];
        param[0]=RBOGGETTO.getString("titoloLI");
        param[1]=RBOGGETTO.getString("isbnL");
        param[2]= RBOGGETTO.getString("editoreLI");
        param[3]=RBOGGETTO.getString("autoreLI");
        param[4]=RBOGGETTO.getString("linguaLI");
        param[5]=RBOGGETTO.getString("categoriaL");
        param[6]=RBOGGETTO.getString("descrizioneL");
        param[7]= RBOGGETTO.getString("dataPubbL");
        param[8]=RBOGGETTO.getString("recensioneL");
        param[9]=RBOGGETTO.getString("numPagL");
        param[10]=RBOGGETTO.getString("nrCopieL");
        param[11]=RBOGGETTO.getString("dispL");
        param[12]=RBOGGETTO.getString("prezzoLI");
        //inserisco libro
        cG.inserisci(param,strings);
        //logout
        assertTrue(cA.logout(strings));

    }

    @ParameterizedTest
    @ValueSource(strings = {"memoria"})
    void testModifLibro(String strings) {
        vis.setTipologiaApplicazione("demo");
        vis.setTypeAsBook();
        vis.setIdLibro(Integer.parseInt(RBOGGETTO.getString("idL")));
        vis.setTipoModifica("im");
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
        //prendo lista libri
        cR.getRaccoltaLista(vis.getType(),strings);
        //aggiungo libro
        String[] param =new String[14];
        param[0]=RBOGGETTO.getString("titoloModL");
        param[1]=RBOGGETTO.getString("isbnModL");
        param[2]= RBOGGETTO.getString("editoreModL");
        param[3]=RBOGGETTO.getString("autoreModL");
        param[4]=RBOGGETTO.getString("linguaModL");
        param[5]=RBOGGETTO.getString("categoriaModL");
        param[6]=RBOGGETTO.getString("descrizioneModL");
        param[7]= RBOGGETTO.getString("dataPubbModL");
        param[8]=RBOGGETTO.getString("recensioneModL");
        param[9]=RBOGGETTO.getString("numPagModL");
        param[10]=RBOGGETTO.getString("nrCopieModL");
        param[11]=RBOGGETTO.getString("dispModL");
        param[12]=RBOGGETTO.getString("prezzoModL");
        //inserisco libro
        cG.modifica(param,strings);
        //logout
        assertTrue(cA.logout(strings));

    }
    @ParameterizedTest
    @ValueSource(strings = {"memoria"})
    void testRemoveLibro(String strings)  {
        vis.setTipologiaApplicazione("demo");
        vis.setTypeAsBook();
        vis.setIdLibro(Integer.parseInt(RBOGGETTO.getString("idL")));
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
