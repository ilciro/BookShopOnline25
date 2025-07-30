package secondouclogin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerAggiornaPassword;
import laptop.controller.secondouclogin.ControllerRegistraUtente;
import laptop.exception.IdException;
import laptop.model.user.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestRegistraUItente {

    private static final ControllerRegistraUtente cRU=new ControllerRegistraUtente();
    private static final ControllerAggiornaPassword cAP=new ControllerAggiornaPassword();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configTest/users");

    @ParameterizedTest
    @ValueSource(strings = {"database", "file"})
    void testRegistraUtente1(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        cRU.setType(strings);
        LocalDate date=LocalDate.of(1995,10,31);
        assertTrue(cRU.registra(RBUTENTE.getString("regNome"),RBUTENTE.getString("regCognome"),RBUTENTE.getString("regEmail"),RBUTENTE.getString("regPwd"),RBUTENTE.getString("regDesc"),date,RBUTENTE.getString("regRuolo")));
    }

     @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testRegistraUtente2(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
         cRU.setType(strings);
         User.getInstance().setEmail(RBUTENTE.getString("regEmail"));
         User.getInstance().setPassword(RBUTENTE.getString("regPwd"));
         assertTrue(cAP.aggiorna(RBUTENTE.getString("modifPwd"),strings));
    }
}
