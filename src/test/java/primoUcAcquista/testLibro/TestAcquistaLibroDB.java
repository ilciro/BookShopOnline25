package primoUcAcquista.testLibro;

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

class TestAcquistaLibroDB {

    private final ControllerHomePage cHP=new ControllerHomePage();
    private final ControllerCompravendita cCopravendita=new ControllerCompravendita();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private final ControllerDownload cD=new ControllerDownload();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String DATABASE="database";
    private static final String LIBRO="libro";
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @Test
    void testAcquistaLibro() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        //inizializzo tabella giornale
        vis.setTypeAsBook();
        cHP.persistenza(DATABASE);
        //prendo lista oggetti
        cCopravendita.getLista(LIBRO,DATABASE);
        vis.setId(2);
        //acquisto
        cA.getPrezzo("3",DATABASE);
        //fattura
        vis.setMetodoP("cash");
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"),DATABASE);
        //download
        cD.scarica(LIBRO,DATABASE);
        assertEquals(2,vis.getId());
    }
}
