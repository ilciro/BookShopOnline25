package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerPagamentoCC;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestControllerPagamentoCCredito {
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");


    @ParameterizedTest
     @ValueSource(strings = {"database","file","memoria"})
    void testPagamentoCCL(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
         vis.setMetodoP("cCredito");
         vis.setIsLogged(false);
         vis.setTypeAsBook();
         vis.setSpesaT(Float.parseFloat(RBOGGETTO.getString("prezzoL")));
         vis.setId(Integer.parseInt(RBOGGETTO.getString("idL")));
         cPCC.pagamentoCC("francesca",strings);
         assertEquals(6,vis.getId());

     }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testPagamentoCCG(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
        vis.setIsLogged(false);
        vis.setMetodoP("cCredito");
        vis.setTypeAsDaily();
        vis.setSpesaT(Float.parseFloat(RBOGGETTO.getString("prezzoG")));
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idG")));
        cPCC.pagamentoCC("francesca",strings);
        assertEquals(1,vis.getId());

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testPagamentoCCR(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
        vis.setIsLogged(false);
        vis.setMetodoP("cCredito");
        vis.setTypeAsMagazine();
        vis.setSpesaT(Float.parseFloat(RBOGGETTO.getString("prezzoG")));
        vis.setId(Integer.parseInt(RBOGGETTO.getString("idG")));
        cPCC.pagamentoCC("francesca",strings);
        assertEquals(1,vis.getId());

    }


}
