package primoUcVisualizza;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerVisualizza;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestControllerVisualizza {
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
     private final ControllerVisualizza cV=new ControllerVisualizza();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testVisualizzaGiornale(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsDaily();
         vis.setIdGiornale(Integer.parseInt(RBOGGETTO.getString("idG")));
         assertEquals(1,cV.getListGiornale(strings).size());
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testVisualizzaLibro(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsBook();
         vis.setIdLibro(Integer.parseInt(RBOGGETTO.getString("idL")));
        assertEquals(1,cV.getListLibro(strings).size());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testVisualizzaRivista(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsMagazine();
         vis.setIdRivista(Integer.parseInt(RBOGGETTO.getString("idR")));
        assertEquals(1,cV.getListRivista(strings).size());
    }
}
