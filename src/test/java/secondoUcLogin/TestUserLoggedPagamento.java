package secondoUcLogin;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerAcquista;
import laptop.controller.primoucacquista.ControllerHomePage;
import laptop.controller.primoucacquista.ControllerPagamentoCC;
import laptop.controller.terzogestioneprofilooggetto.ControllerAdmin;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestUserLoggedPagamento {

    private final ControllerLogin cL=new ControllerLogin();
    private final ControllerAdmin cA=new ControllerAdmin();
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBCC=ResourceBundle.getBundle("configurations/cartaCredito");
    private final ControllerHomePage cHP=new ControllerHomePage();
    private final ControllerAcquista cAcquista=new ControllerAcquista();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testPagamentoCCutenteRegistrato(String strings) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {
        vis.setTypeAsBook();
        //login
        cL.login(RBUTENTE.getString("email2"),RBUTENTE.getString("pwd2"),strings);
        //home page after login -> compro un libro
        cHP.persistenza("libro");
        //scelgo di acquistare libro 1
        vis.setIdUtente(1);
        cAcquista.getPrezzo("10",strings);
        vis.setMetodoP("cCredito");
        //pagamento cc , aggiugendo carta
        cPCC.aggiungiCartaDB(RBUTENTE.getString("nome2"),RBUTENTE.getString("cognome2"),RBCC.getString("codice"), Date.valueOf(RBCC.getString("data")),
                RBCC.getString("civ"), Float.parseFloat(RBCC.getString("prezzo1")),strings);
        //prendo i dati completi
        cPCC.ritornaElencoCByNumero( cPCC.ritornaElencoCC(RBUTENTE.getString("nome2"),"database").get(0).getNumeroCC(),strings);
        //effettuo pagamento
        cPCC.pagamentoCC(RBUTENTE.getString("nome2"),strings);
        assertTrue(cA.logout(strings));
    }


}
