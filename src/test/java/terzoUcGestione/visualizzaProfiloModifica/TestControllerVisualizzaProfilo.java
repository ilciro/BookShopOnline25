package terzoUcGestione.visualizzaProfiloModifica;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.terzogestioneprofilooggetto.ControllerVisualizzaProfilo;
import laptop.exception.IdException;
import laptop.model.user.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerVisualizzaProfilo {

    private final ControllerVisualizzaProfilo cVP=new ControllerVisualizzaProfilo();
    private static final User u= User.getInstance();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testModificaCredenziali(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {

        u.setEmail(RBUTENTE.getString("emailU"));
        u.setPassword(RBUTENTE.getString("passU"));
        //prendo credenziali
        cVP.infoUtente(strings);
        //cambio dati
        String[] dati=new String[6];
        dati[0]=RBUTENTE.getString("ruoloHM");
        dati[1]=RBUTENTE.getString("nomeHM");
        dati[2]=RBUTENTE.getString("cognomeHM");
        dati[3]=RBUTENTE.getString("emailHM");
        dati[4]=RBUTENTE.getString("pwdHM");
        dati[5]=RBUTENTE.getString("descHM");
        assertTrue(cVP.modifica(dati,strings));
    }
}
