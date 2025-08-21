package testfinalidemo.secondouc;

import laptop.controller.secondouclogin.ControllerAggiornaPassword;
import laptop.controller.secondouclogin.ControllerRegistraUtente;
import laptop.model.user.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.MethodName.class)


public class TestRegModUser {
    private static final ResourceBundle RBUTENE =ResourceBundle.getBundle("configurations/users");
    private static final ControllerRegistraUtente cRU=new ControllerRegistraUtente();
    private static final ControllerAggiornaPassword cAP=new ControllerAggiornaPassword();

    @Test
    void registerUserMem()
    {
        cRU.setType("memoria");
        assertTrue(cRU.registra(RBUTENE.getString("nome"),RBUTENE.getString("cognome"),RBUTENE.getString("email"),RBUTENE.getString("pwd"),
                RBUTENE.getString("desc"), LocalDate.parse(RBUTENE.getString("data")),RBUTENE.getString("ruolo")));
    }

    @Test
    void registerUserMemPwd()
    {
        //cambio password
        User.getInstance().setEmail(RBUTENE.getString("email"));
        User.getInstance().setPassword(RBUTENE.getString("pwd"));

        assertTrue(cAP.aggiorna(RBUTENE.getString("pwdM"),"memoria" ));
    }
}

