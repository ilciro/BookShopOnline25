package secondoUcLogin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.secondouclogin.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestLogin {
     private final ControllerLogin cL=new ControllerLogin();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private final ControllerAdmin cA=new ControllerAdmin();

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAsAdmin(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
         cL.login(RBUTENTE.getString("emailA"),RBUTENTE.getString("passA"),strings);
         assertTrue(cA.logout(strings));
     }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAsEditore(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        if(cL.userPresente(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),strings))
            cL.login(RBUTENTE.getString("emailE"),RBUTENTE.getString("passE"),strings);
        assertTrue(cA.logout(strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAsScrittore(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        cL.login(RBUTENTE.getString("emailS"),RBUTENTE.getString("passS"),strings);
        assertTrue(cA.logout(strings));
    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAsUtente(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        cL.login(RBUTENTE.getString("emailU"),RBUTENTE.getString("passU"),strings);
        assertTrue(cA.logout(strings));
    }

}
