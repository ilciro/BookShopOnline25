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

class TestControllerRicercaGiornale {
    private final ControllerRicerca cR=new ControllerRicerca();
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaGiornaleTitolo(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotEquals(0,cR.listaGiornali(RBOGGETTO.getString("titoloG"),strings).size());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRicercaGiornaleEditore(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        assertNotEquals(0,cR.listaGiornali(RBOGGETTO.getString("editoreG"),strings).size());
    }

}
