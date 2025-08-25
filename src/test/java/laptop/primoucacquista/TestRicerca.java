package laptop.primoucacquista;

import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.controller.secondouclogin.ControllerLogin;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRicerca {

    private static final ControllerRicerca cR=new ControllerRicerca();
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final String DATABASE="database";
    private static final String FILE="file";

    @ParameterizedTest
     @ValueSource(strings = {"Il libro dello splendore","Laterza","Rizzoli"})
    void testRicercaLibroDatabase(String strings)  {
        cL.login(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),DATABASE);
        cR.listaLibri(strings,DATABASE);
        assertTrue(cR.logout(DATABASE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Scheletri","Zerocalcare","Bao publishing"})
    void testRicercaLibroFile(String strings) {

        cL.login(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),FILE);
        cR.listaLibri(strings,FILE);
        assertTrue(cR.logout(FILE));

    }

    @ParameterizedTest
    @ValueSource(strings = {"Il Fatto Quotidiano1","Hoepli"})
    void testRicercaGiornaleDatabase(String strings)  {

        cL.login(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),DATABASE);
        cR.listaGiornali(strings,DATABASE);
        assertTrue(cR.logout(DATABASE));

    }

    @ParameterizedTest
    @ValueSource(strings = {"La gazzetta del profeta","Il Fatto Quotidiano"})
    void testRicercaGiornaleFile(String strings)  {

        cL.login(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),FILE);
        cR.listaGiornali(strings,FILE);
        assertTrue(cR.logout(FILE));


    }

    @ParameterizedTest
    @ValueSource(strings = {"Focus","Panorama","Bao Publishing"})
    void testRicercaRivistaDatabase(String strings)  {

        cL.login(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),DATABASE);
        cR.listaRiviste(strings,DATABASE);
        assertTrue(cR.logout(DATABASE));


    }

    @ParameterizedTest
    @ValueSource(strings = {"rivista A","Bao Publishing","Bao publishing"})
    void testRicercaRivistaFile(String strings)  {

        cL.login(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),FILE);
        cR.listaRiviste(strings,FILE);
        assertTrue(cR.logout(FILE));


    }

}
