package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerPagamentoCash;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestControllerPagamentoCash {

     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testControllaL(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
         vis.setMetodoP("cash");
         vis.setId(1);
         vis.setTypeAsBook();
         cPCash.controlla("francesca","violi","via gerbere 8","",strings);
        assertEquals("cash",vis.getMetodoP());
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testControllaR(String strings) throws CsvValidationException,  IOException, IdException, ClassNotFoundException {
        vis.setMetodoP("cash");
        vis.setId(1);
        vis.setTypeAsMagazine();
        cPCash.controlla("francesca","violi","via gerbere 8","",strings);
        assertEquals("cash",vis.getMetodoP());
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testControllaG(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setMetodoP("cash");
        vis.setId(2);
        vis.setTypeAsDaily();
        cPCash.controlla("francesca","violi","via gerbere 8","",strings);
        assertEquals("cash",vis.getMetodoP());
    }
}
