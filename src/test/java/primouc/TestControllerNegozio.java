package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.primoucacquista.ControllerScegliNegozio;
import laptop.exception.IdException;
import laptop.model.Negozio;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerNegozio {

     private final ControllerScegliNegozio cSN=new ControllerScegliNegozio();

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testListaNegozi(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
         assertNotNull(cSN.getNegozi(strings));
     }

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testIsOpen(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
         Negozio n=new Negozio();
         n.setNome("Negozio B");
         n.setId(2);
         assertTrue(cSN.isOpen(strings,n.getId()));
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testIsValid(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        Negozio n=new Negozio();
        n.setNome("Negozio B");
        n.setId(2);
        assertTrue(cSN.isValid(strings,n.getId()));
    }
}
