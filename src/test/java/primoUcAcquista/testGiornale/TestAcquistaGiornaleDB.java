package primoUcAcquista.testGiornale;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestAcquistaGiornaleDB {

    private final ControllerHomePage cHP=new ControllerHomePage();
    private final ControllerCompravendita cCopravendita=new ControllerCompravendita();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private final ControllerDownload cD=new ControllerDownload();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String DATABASE="database";
    private static final String GIORNALE="giornale";
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private final ControllerScegliNegozio cSN=new ControllerScegliNegozio();


     @Test
    void testAcquistaGiornaleCash() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        //inizializzo tabella giornale
        vis.setTypeAsDaily();
        cHP.persistenza(DATABASE);
        //prendo lista oggetti
        cCopravendita.getLista(GIORNALE,DATABASE);
        vis.setId(6);
        //acquisto
        cA.getPrezzo("2",DATABASE);
        //fattura
        vis.setMetodoP("cash");
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"),DATABASE);
        //download
        cD.scarica(GIORNALE,DATABASE);
        assertNotEquals(0,vis.getId());
    }
     @Test
     void testAcquistaGiornaleCredito() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
         //inizializzo tabella giornale
         vis.setTypeAsDaily();
         cHP.persistenza(DATABASE);
         //prendo lista oggetti
         cCopravendita.getLista(GIORNALE,DATABASE);
         vis.setId(6);
         //acquisto
         cA.getPrezzo("2",DATABASE);
         //pagamento cc
         vis.setMetodoP("cCredito");
         cPCC.pagamentoCC(RBUTENTE.getString("nome"),DATABASE);
         //negozio
         cSN.getNegozi(DATABASE);
         boolean status=cSN.isOpen(DATABASE,2)&&cSN.isValid(DATABASE,2);
         assertTrue(status);
     }


}
