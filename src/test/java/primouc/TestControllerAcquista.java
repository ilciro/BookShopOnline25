package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerAcquista;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestControllerAcquista {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private final ControllerAcquista cA=new ControllerAcquista();
     private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetNomeCostoDispL(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
         vis.setTypeAsBook();
         vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
         assertNotEquals("",cA.getNomeCostoDisp(strings)[0]);
     }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetPrezzoL(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsBook();
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
        assertNotEquals(0,cA.getPrezzo("3",strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetNomeCostoDispG(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsDaily();
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idG")));
        assertNotEquals("",cA.getNomeCostoDisp(strings)[0]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetPrezzoG(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsDaily();
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idG")));
        assertNotEquals(0,cA.getPrezzo("3",strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetNomeCostoDispR(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsMagazine();
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idR")));
        assertNotEquals("",cA.getNomeCostoDisp(strings)[0]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetPrezzoR(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsMagazine();
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idR")));
        assertNotEquals(0,cA.getPrezzo("3",strings));
    }

}
