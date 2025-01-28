package terzouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerReport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestControllerReport {
     private final ControllerReport cR=new ControllerReport();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testReportL(String strings) throws IOException, ClassNotFoundException {
         vis.setTypeAsBook();
         assertNotNull(cR.reportL(strings));

     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testReportG(String strings) throws IOException, ClassNotFoundException {
        vis.setTypeAsDaily();
        assertNotNull(cR.reportG(strings));

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testReportR(String strings) throws IOException, ClassNotFoundException {
        vis.setTypeAsMagazine();
        assertNotNull(cR.reportR(strings));

    }


    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testReportUser(String strings) throws IOException, CsvValidationException, SQLException {
        assertNotNull(cR.reportUser(strings));

    }

}
