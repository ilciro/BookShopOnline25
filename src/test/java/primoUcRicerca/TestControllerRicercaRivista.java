package primoUcRicerca;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.exception.IdException;
import laptop.model.user.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestControllerRicercaRivista {
    private final ControllerRicerca cR=new ControllerRicerca();
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaRivistaTitolo(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotEquals(0,cR.listaRiviste(RBOGGETTO.getString("titoloR"),strings).size());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaRivistaEditore(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotEquals(0,cR.listaRiviste(RBOGGETTO.getString("editoreR"),strings).size());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaRivistaAutore(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotEquals(0,cR.listaRiviste(RBOGGETTO.getString("autoreR"),strings).size());
    }



}
