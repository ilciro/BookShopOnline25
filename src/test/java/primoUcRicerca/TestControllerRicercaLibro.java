package primoUcRicerca;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestControllerRicercaLibro {
    private final ControllerRicerca cR=new ControllerRicerca();
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaLibroTitolo(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotEquals(0,cR.listaLibri(RBOGGETTO.getString("titoloL"),strings).size());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaLibroEditore(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotEquals(0,cR.listaLibri(RBOGGETTO.getString("editoreL"),strings).size());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaLibroAutore(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotEquals(0,cR.listaLibri(RBOGGETTO.getString("autoreL"),strings).size());
    }
}
