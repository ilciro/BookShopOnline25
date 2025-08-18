package secondouclogin;


import laptop.controller.secondouclogin.ControllerAggiornaPassword;
import laptop.controller.secondouclogin.ControllerRegistraUtente;
import laptop.model.user.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)


class TestRegistraUItente {

    private static final ControllerRegistraUtente cRU=new ControllerRegistraUtente();
    private static final ControllerAggiornaPassword cAP=new ControllerAggiornaPassword();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @ParameterizedTest
    @ValueSource(strings = {"database", "file"})
    void testRegistraUtente(String strings)  {
        cRU.setType(strings);
        assertTrue(cRU.registra(RBUTENTE.getString("nome2"),RBUTENTE.getString("cognome2"),RBUTENTE.getString("email2"),RBUTENTE.getString("pwd2"),RBUTENTE.getString("desc2"), LocalDate.parse(RBUTENTE.getString("data2"))
                ,RBUTENTE.getString("ruolo2")));
    }

     @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testRegistraUtenteModifPwd(String strings)  {
         cRU.setType(strings);
         User.getInstance().setEmail(RBUTENTE.getString("email2"));
        User.getInstance().setPassword(RBUTENTE.getString("pwd2"));
         assertTrue(cAP.aggiorna(RBUTENTE.getString("passAgg"),strings));
    }
}
