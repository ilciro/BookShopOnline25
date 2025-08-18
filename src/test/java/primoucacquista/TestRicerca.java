package primoucacquista;

import laptop.controller.primoucacquista.ControllerRicerca;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestRicerca {

    private static final ControllerRicerca cR=new ControllerRicerca();

    @ParameterizedTest
     @ValueSource(strings = {"Il libro dello splendore","Laterza","Rizzoli"})
    void testRicercaLibroDatabase(String strings)  {
        assertNotNull(cR.listaLibri(strings,"database"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Scheletri","Zerocalcare","Bao publishing"})
    void testRicercaLibroFile(String strings) {
        assertNotNull(cR.listaLibri(strings,"file"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Il Fatto Quotidiano1","Hoepli"})
    void testRicercaGiornaleDatabase(String strings)  {
        assertNotNull(cR.listaGiornali(strings,"database"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"La gazzetta del profeta","Il Fatto Quotidiano"})
    void testRicercaGiornaleFile(String strings)  {
        assertNotNull(cR.listaGiornali(strings,"file"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Focus","Panorama","Bao Publishing"})
    void testRicercaRivistaDatabase(String strings)  {
        assertNotNull(cR.listaRiviste(strings,"database"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"rivista A","Bao Publishing","Bao publishing"})
    void testRicercaRivistaFile(String strings)  {
        assertNotNull(cR.listaLibri(strings,"file"));
    }

}
