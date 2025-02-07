package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerHomePage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InitTest {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerHomePage cHP=new ControllerHomePage();
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testPopolaDatiLibro(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException {
        vis.setTypeAsBook();
        cHP.persistenza(strings);
        assertEquals("libro",vis.getType());

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testPopolaDatiGiornale(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException {
        vis.setTypeAsDaily();
        cHP.persistenza(strings);
        assertEquals("giornale",vis.getType());

    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testPopolaDatiRivista(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException {
        vis.setTypeAsMagazine();
        cHP.persistenza(strings);
        assertEquals("rivista",vis.getType());

    }

}
