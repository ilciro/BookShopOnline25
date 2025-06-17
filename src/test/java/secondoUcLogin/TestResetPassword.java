package secondoUcLogin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.terzogestioneprofilooggetto.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerAggiornaPassword;
import laptop.exception.IdException;
import laptop.model.user.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestResetPassword {

    private static final User u= User.getInstance();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private final ControllerAggiornaPassword cAP=new ControllerAggiornaPassword();
    private final ControllerAdmin cA=new ControllerAdmin();
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAggiornaPassword(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        u.setEmail(RBUTENTE.getString("email2"));
        u.setPassword(RBUTENTE.getString("pwd2"));
        cAP.aggiorna(RBUTENTE.getString("passAgg"),strings);
        assertTrue(cA.logout(strings));


    }
}
