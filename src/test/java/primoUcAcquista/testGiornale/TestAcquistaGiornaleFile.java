package primoUcAcquista.testGiornale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestAcquistaGiornaleFile {

    private final ControllerHomePage cHP=new ControllerHomePage();
    private final ControllerCompravendita cCopravendita=new ControllerCompravendita();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private final ControllerScegliNegozio cSN=new ControllerScegliNegozio();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String FILE="file";
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @Test
    void testAcquistaGiornale() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        //inizializzo tabella giornale
        vis.setTypeAsDaily();
        cHP.persistenza(FILE);
        //prendo lista oggetti
        cCopravendita.getLista("giornale",FILE);
        vis.setId(6);
        //acquisto
        cA.getPrezzo("2",FILE);
        //pagamento cc
        vis.setMetodoP("cCredito");
        cPCC.pagamentoCC(RBUTENTE.getString("nome"),FILE);
        //negozio
        cSN.getNegozi(FILE);
        assertNotEquals(0,vis.getId());
    }

}
