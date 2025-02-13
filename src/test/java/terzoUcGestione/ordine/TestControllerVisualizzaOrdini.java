package terzoUcGestione.ordine;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import laptop.controller.terzoucgestioneprofilooggetto.ControllerVisualizzaOrdini;
import laptop.model.Pagamento;
import laptop.model.user.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestControllerVisualizzaOrdini {

     private final ControllerVisualizzaOrdini cVO=new ControllerVisualizzaOrdini();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private static final User u= User.getInstance();
     @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testRemoveOrderDBF(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException {
        u.setEmail(RBUTENTE.getString("email2"));
        //prendo lista
        ObservableList<Pagamento> lista=cVO.getLista(strings);
        //cancello pagamento
       assertTrue(cVO.cancellaPagamento(lista.get(0).getIdPag(),strings));
     }
    @ParameterizedTest
    @ValueSource(strings = {"file"})
    void testRemoveOrderM(String strings) throws CsvValidationException,  IOException, ClassNotFoundException {
        u.setEmail("prova@prova.com");
        //cancello pagamento
          assertTrue(cVO.cancellaPagamento(1,strings));
    }

}
