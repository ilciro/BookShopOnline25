package terzoucgestione;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerAdmin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerReport;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestReport {

     private final ControllerLogin cL=new ControllerLogin();
     private final ControllerReport cR=new ControllerReport();
     private final ControllerAdmin cA=new ControllerAdmin();

     @ParameterizedTest
     @ValueSource(strings = {"database","file"})
     void testReportLGR(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //login
        cL.login("admin@admin.com","Admin871",strings) ;
        //report
         cR.reportTotale(strings);
         assertTrue(cA.logout(strings));

     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testReportUsers(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        //login
        cL.login("admin@admin.com","Admin871",strings) ;
        //report
        cR.reportUser(strings);
        assertTrue(cA.logout(strings));

    }
}
