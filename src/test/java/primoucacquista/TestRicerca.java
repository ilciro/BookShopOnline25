package primoucacquista;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestRicerca {

    private static final ControllerRicerca cR=new ControllerRicerca();

    @ParameterizedTest
     @ValueSource(strings = {"Il libro dello splendore","Laterza","Rizzoli"})
    void testRicercaLibroDatabase(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotNull(cR.listaLibri(strings,"database"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Scheletri","Zerocalcare","Bao publishing"})
    void testRicercaLibroFile(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotNull(cR.listaLibri(strings,"file"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Il Fatto Quotidiano1","Hoepli"})
    void testRicercaGiornaleDatabase(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotNull(cR.listaGiornali(strings,"database"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"La gazzetta del profeta","Il Fatto Quotidiano"})
    void testRicercaGiornaleFile(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotNull(cR.listaGiornali(strings,"file"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Focus","Panorama","Bao Publishing"})
    void testRicercaRivistaDatabase(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotNull(cR.listaRiviste(strings,"database"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"rivista A","Bao Publishing","Bao publishing"})
    void testRicercaRivistaFile(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotNull(cR.listaLibri(strings,"file"));
    }

}
