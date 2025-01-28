package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerVisualizza;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerVisualizza {

     private  final ControllerVisualizza cV=new ControllerVisualizza();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();

     @ParameterizedTest
     @ValueSource(strings = {"database","file","memoria"})
     void testLibroById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
         vis.setId(7);
         assertNotNull(cV.getListLibro(strings));
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGiornaleById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setId(2);
        assertNotNull(cV.getListGiornale(strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRivistaById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setId(5);
        assertNotNull(cV.getListRivista(strings));
    }
}
