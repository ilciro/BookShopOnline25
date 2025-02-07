package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerVisualizza;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerVisualizza {

     private  final ControllerVisualizza cV=new ControllerVisualizza();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");


    @ParameterizedTest
     @ValueSource(strings = {"database","file","memoria"})
     void testLibroById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
         vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
         assertNotNull(cV.getListLibro(strings));
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGiornaleById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idG")));
        assertNotNull(cV.getListGiornale(strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRivistaById(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idR")));
        assertNotNull(cV.getListRivista(strings));
    }
}
