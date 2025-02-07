package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerRicerca {
    private final ControllerRicerca cR=new ControllerRicerca();
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaLibroT(String strings) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {
        assertNotNull(cR.listaLibri(RBOGGETTO.getString("titoloL"),strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaLibroE(String strings) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {
        assertNotNull(cR.listaLibri(RBOGGETTO.getString("editoreL"),strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaLibroA(String strings) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {
        assertNotNull(cR.listaLibri(RBOGGETTO.getString("autoreL"),strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaGiornaleT(String strings) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {
        assertNotNull(cR.listaGiornali(RBOGGETTO.getString("titoloG"),strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaGiornaleE(String strings) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {
        assertNotNull(cR.listaGiornali(RBOGGETTO.getString("editoreG"),strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaRivistaT(String strings) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {
        assertNotNull(cR.listaRiviste(RBOGGETTO.getString("titoloR"),strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaRivistaE(String strings) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {
        assertNotNull(cR.listaRiviste(RBOGGETTO.getString("editoreR"),strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaRivistaA(String strings) throws CsvValidationException, IOException, ClassNotFoundException, IdException, SQLException {
        assertNotNull(cR.listaRiviste(RBOGGETTO.getString("autoreR"),strings));
    }
}
