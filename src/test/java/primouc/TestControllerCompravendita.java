package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerCompravendita;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerCompravendita {

     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private final ControllerCompravendita cc=new ControllerCompravendita();

     @ParameterizedTest
    @ValueSource(strings = {"database","memoria","file"})
    void testGetListaLibro(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
         vis.setTypeAsBook();
         assertNotNull(cc.getLista(vis.getType(),strings));
     }

    @ParameterizedTest
    @ValueSource(strings = {"database","memoria","file"})
    void testGetListaGiornale(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
        vis.setTypeAsDaily();
        assertNotNull(cc.getLista(vis.getType(),strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","memoria","file"})
    void testGetListaRivista(String strings) throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
        vis.setTypeAsMagazine();
        assertNotNull(cc.getLista(vis.getType(),strings));
    }


}
