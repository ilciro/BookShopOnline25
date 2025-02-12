package secondoUcLogin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.secondouclogin.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerRegistraUtente;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRegistraUtente {
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private final ControllerRegistraUtente cRU=new ControllerRegistraUtente();
     private final ControllerAdmin cA=new ControllerAdmin();

     @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testRegistraUtente(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        cRU.setType(strings);
         cRU.registra(RBUTENTE.getString("nome2"),RBUTENTE.getString("cognome2"),RBUTENTE.getString("email2"),
                RBUTENTE.getString("pwd2"),RBUTENTE.getString("desc2"), LocalDate.parse(RBUTENTE.getString("data2")),RBUTENTE.getString("ruolo2"));
        assertTrue(cA.logout(strings));
     }
}
