package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerPagamentoCC;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestControllerPagamentoCCredito {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();

     @ParameterizedTest
     @ValueSource(strings = {"database","file","memoria"})
    void testPagamentoCCL(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
         vis.setMetodoP("cCredito");
         vis.setIsLogged(false);
         vis.setTypeAsBook();
         vis.setSpesaT(12f);
         vis.setId(6);
         cPCC.pagamentoCC("francesca",strings);
         assertEquals(6,vis.getId());

     }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testPagamentoCCG(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setIsLogged(false);
        vis.setMetodoP("cCredito");
        vis.setTypeAsDaily();
        vis.setSpesaT(5f);
        vis.setId(2);
        cPCC.pagamentoCC("francesca",strings);
        assertEquals(2,vis.getId());

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testPagamentoCCR(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException {
        vis.setIsLogged(false);
        vis.setMetodoP("cCredito");
        vis.setTypeAsMagazine();
        vis.setSpesaT(12f);
        vis.setId(5);
        cPCC.pagamentoCC("francesca",strings);
        assertEquals(5,vis.getId());

    }


}
