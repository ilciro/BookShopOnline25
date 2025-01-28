package secondouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.secondouclogin.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TestControllerLoginLogout {

     private final ControllerLogin cL=new ControllerLogin();
     private final ControllerAdmin cA=new ControllerAdmin();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAdAdmin(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
         assertEquals("ADMIN",cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings));
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAdAdminLogout(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);

        assertTrue(cA.logout(strings));
     }

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAdUser(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        assertEquals("UTENTE",cL.login(RBUTENTE.getString("emailU"),RBUTENTE.getString("passU"),strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAdScrittore(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        assertEquals("SCRITTORE",cL.login(RBUTENTE.getString("emailS"),RBUTENTE.getString("passS"),strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAdEditore(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        assertEquals("EDITORE",cL.login(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),strings));
    }

}
