package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerAcquista;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestControllerAcquista {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private final ControllerAcquista cA=new ControllerAcquista();

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetNomeCostoDispL(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
         vis.setTypeAsBook();
         vis.setId(1);
         assertNotEquals("",cA.getNomeCostoDisp(strings)[0]);
     }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetPrezzoL(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsBook();
        vis.setId(1);
        assertNotEquals(0,cA.getPrezzo("3",strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetNomeCostoDispG(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsDaily();
        vis.setId(1);
        assertNotEquals("",cA.getNomeCostoDisp(strings)[0]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetPrezzoG(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsDaily();
        vis.setId(1);
        assertNotEquals(0,cA.getPrezzo("3",strings));
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetNomeCostoDispR(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsMagazine();
        vis.setId(5);
        assertNotEquals("",cA.getNomeCostoDisp(strings)[0]);
    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testGetPrezzoR(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsMagazine();
        vis.setId(5);
        assertNotEquals(0,cA.getPrezzo("3",strings));
    }

}
