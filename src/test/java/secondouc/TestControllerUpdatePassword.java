package secondouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.secondouclogin.ControllerAggiornaPassword;
import laptop.exception.IdException;
import laptop.model.user.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerUpdatePassword {
     private final ControllerAggiornaPassword cAP=new ControllerAggiornaPassword();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private static final User u= User.getInstance();
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testCambiaPass(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
         u.setEmail(RBUTENTE.getString("email2"));
         u.setPassword(RBUTENTE.getString("pwd2"));
         assertTrue(cAP.aggiorna("Tronchetto-95",strings));
     }
}
