package testfinalidemo.testreport;

import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerAdmin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerReport;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestReportGen {
    private final ControllerLogin cL=new ControllerLogin();
    private final ControllerReport cR=new ControllerReport();
    private final ControllerAdmin cA=new ControllerAdmin();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testReportL(String strings)  {
        vis.setTypeAsBook();
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings) ;
        //report
        cR.reportL(strings);
        assertTrue(cA.logout(strings));

    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testReportG(String strings)  {
        vis.setTypeAsDaily();
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings) ;
        //report
        cR.reportL(strings);
        assertTrue(cA.logout(strings));

    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testReportR(String strings)  {
        vis.setTypeAsMagazine();
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings) ;
        //report
        cR.reportL(strings);
        assertTrue(cA.logout(strings));

    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testReportUsers(String strings)  {
        //login
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings) ;
        //report
        cR.reportUser(strings);
        assertTrue(cA.logout(strings));

    }
}
