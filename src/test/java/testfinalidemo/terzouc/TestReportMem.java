package testfinalidemo.terzouc;

import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerAdmin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerReport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestReportMem {
    private final ControllerLogin cL=new ControllerLogin();
    private final ControllerReport cR=new ControllerReport();
    private final ControllerAdmin cA=new ControllerAdmin();

    @ParameterizedTest
    @ValueSource(strings = {"memoria"})
    void testReportLGR(String strings)  {
        //login
        cL.login("admin@admin.com","Admin871",strings) ;
        //report
        cR.reportTotale(strings);
        assertTrue(cA.logout(strings));

    }
    @ParameterizedTest
    @ValueSource(strings = {"memoria"})
    void testReportUsers(String strings)  {
        //login
        cL.login("admin@admin.com","Admin871",strings) ;
        //report
        cR.reportUser(strings);
        assertTrue(cA.logout(strings));

    }
}
