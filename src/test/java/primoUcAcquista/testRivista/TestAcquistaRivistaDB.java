package primoUcAcquista.testRivista;

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

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAcquistaRivistaDB {
    private final ControllerHomePage cHP=new ControllerHomePage();
    private final ControllerCompravendita cCopravendita=new ControllerCompravendita();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private final ControllerDownload cD=new ControllerDownload();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String DATABASE="database";
    private static final String RIVISTA="rivista";
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @Test
    void testAcquistaRivista() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        //inizializzo tabella rivista
        vis.setTypeAsMagazine();
        cHP.persistenza(DATABASE);
        //prendo lista oggetti
        cCopravendita.getLista(RIVISTA,DATABASE);
        vis.setId(5);
        //acquisto
        cA.getPrezzo("7",DATABASE);
        //fattura
        vis.setMetodoP("cash");
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"),DATABASE);
        //download
        cD.scarica(RIVISTA,DATABASE);
        assertEquals(5,vis.getId());
    }
}
