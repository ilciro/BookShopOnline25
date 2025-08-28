package testfinalidemo.testricerca;

import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.controller.secondouclogin.ControllerLogin;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ResourceBundle;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRicercaMem {
    private static final ControllerRicerca cR=new ControllerRicerca();
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @ParameterizedTest
    @ValueSource(strings = {"Il libro dello splendore","Laterza","Rizzoli"})
    void testRicercaLibroMemoria(String strings)  {
        cL.login(RBUTENTE.getString("emailU"),RBUTENTE.getString("passU"),"memoria");
        cR.listaLibri(strings,"memoria");
        assertTrue(cR.logout("memoria"));
    }


    @ParameterizedTest
    @ValueSource(strings = {"Il Fatto Quotidiano1","Hoepli"})
    void testRicercaGiornaleMemoria(String strings){
        cL.login(RBUTENTE.getString("emailU"),RBUTENTE.getString("passU"),"memoria");
        cR.listaGiornali(strings,"memoria");
        assertTrue(cR.logout("memoria"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Focus","Panorama","Bao Publishing"})
    void testRicercaRivistaDatabase(String strings)  {
        cL.login(RBUTENTE.getString("emailU"),RBUTENTE.getString("passU"),"memoria");
        cR.listaRiviste(strings,"memoria");
        assertTrue(cR.logout("memoria"));
    }



}
