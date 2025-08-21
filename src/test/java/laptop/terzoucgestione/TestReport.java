package laptop.terzoucgestione;

import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerAdmin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerReport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestReport {

     private final ControllerLogin cL=new ControllerLogin();
     private final ControllerReport cR=new ControllerReport();
     private final ControllerAdmin cA=new ControllerAdmin();

     @ParameterizedTest
     @ValueSource(strings = {"database","file"})
     void testReportLGR(String strings)  {
        //login
        cL.login("admin@admin.com","Admin871",strings) ;
        //report
         cR.reportTotale(strings);
         assertTrue(cA.logout(strings));

     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testReportUsers(String strings)  {
        //login
        cL.login("admin@admin.com","Admin871",strings) ;
        //report
        cR.reportUser(strings);
        assertTrue(cA.logout(strings));

    }
}
