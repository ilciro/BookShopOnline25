package laptop.terzoucgestione.gestioneutente;

import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerHomePage;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerVisualizzaProfilo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGestioneUtenteByUtente {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ControllerVisualizzaProfilo cVP=new ControllerVisualizzaProfilo();
    private static final ControllerHomePage cHP=new ControllerHomePage();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testModifProfiloUtente(String strings) {
        vis.setTipologiaApplicazione("full");
        //login
        cL.login(RBUTENTE.getString("emailU"),RBUTENTE.getString("passU"),strings);
        //prendo credenziali
        cVP.infoUtente(strings);
        String []data=new String[6];
        data[0]=RBUTENTE.getString("ruoloHM");
        data[1]=RBUTENTE.getString("nomeHM");
        data[2]=RBUTENTE.getString("cognomeHM");
        data[3]=RBUTENTE.getString("emailHM");
        data[4]=RBUTENTE.getString("pwdHM");
        data[5]=RBUTENTE.getString("descHM");
        //modifico
        cVP.modifica(data,strings);
        //logout
        assertTrue(cHP.logout());

    }
}
