package demo.secondouclogin;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Platform;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;
import laptop.model.user.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDemoLogin {
    private static final ControllerSystemState vis = ControllerSystemState.getInstance();
    private static final String MEMORIA = "memoria";
    private static final ControllerHomePage cHP = new ControllerHomePage();
    private static final ControllerCompravendita cCV = new ControllerCompravendita();
    private static final ControllerAcquista cA = new ControllerAcquista();
    private static final ControllerPagamentoCash cPCash = new ControllerPagamentoCash();
    private static final ControllerDownload cD = new ControllerDownload();
    private static final ControllerPagamentoCC cPCC = new ControllerPagamentoCC();
    private static final ControllerVisualizza cV = new ControllerVisualizza();
    private static final ControllerScegliNegozio cSN = new ControllerScegliNegozio();
    private static final ControllerLogin cL=new ControllerLogin();

    @Test
    void testLoginAsUser() throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException, DocumentException, URISyntaxException {
        vis.setTipologiaApplicazione("demo");
        //loggo come user
        cL.login("giuliaConforto@gmail.eu","12345678Gc",MEMORIA);
        //acquisto normale
        vis.setTypeAsBook();
        //inizializzo lista
        cHP.persistenza(MEMORIA);
        //scelgo id
        cCV.checkId(8,MEMORIA,vis.getType());
        //setto id
        vis.setIdLibro(8);
        //scelgo quantita e prezzo
        cA.getPrezzo("4",MEMORIA);
        //prendo oggetto e crea fattura
        cPCash.controlla(User.getInstance().getNome(),User.getInstance().getCognome(), "via papaveri 8","dopo le 18",MEMORIA);
        //scarico oggetto
        cD.scarica(vis.getType(),MEMORIA);
        //logout
        assertTrue(cHP.logout());

    }
    @Test
    void testLoginAsScrittore() throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException, DocumentException, URISyntaxException {
        vis.setTipologiaApplicazione("demo");
        //loggo come user
        cL.login("zerocalcare@gmail.com","Zerocalcare21",MEMORIA);
        //acquisto normale
        vis.setTypeAsMagazine();
        //inizializzo lista
        cHP.persistenza(MEMORIA);
        //scelgo id
        cCV.checkId(3,MEMORIA,vis.getType());
        //setto id
        vis.setIdRivista(3);
        //scelgo quantita e prezzo
        cA.getPrezzo("4",MEMORIA);
        //prendo oggetto e crea fattura
        cPCash.controlla(User.getInstance().getNome(),User.getInstance().getCognome(), "piazza sempio snc","dopo le 9",MEMORIA);
        //scarico oggetto
        cD.scarica(vis.getType(),MEMORIA);
        //logout
        assertTrue(cHP.logout());

    }

    @Test
    void testLoginAsEditore() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException{
        vis.setTipologiaApplicazione("demo");
        cL.login("editore185@gmail.com","EdiP415",MEMORIA);
        vis.setTypeAsDaily();
        //inizializzo lista
        cHP.persistenza(MEMORIA);
        //scelgo id
        cCV.checkId(3,MEMORIA,vis.getType());
        //setto id
        vis.setIdGiornale(3);
        //visualizza
        cV.getID();
        cV.getListGiornale(MEMORIA);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",MEMORIA);
        //controllo correttezza cc
        cPCC.correttezza("1952-7488-1111-5252","2030/09/09","841");
        //effettuo pagamento
        cPCC.pagamentoCC(User.getInstance().getNome(),MEMORIA,User.getInstance().getCognome());
        //scarico oggetto
        cSN.getNegozi(MEMORIA);
        cSN.isOpen(MEMORIA,2);
        cSN.isValid(MEMORIA,2);
        //logout
        assertTrue(cHP.logout());


    }

    @AfterAll
    public static void teardown()
    {
        boolean status=false;
        Platform.exit();
        File path=new File("memory");
        File[] files = path.listFiles();
        for(int i = 0; i< Objects.requireNonNull(files).length; i++) {

            status=files[i].delete();
        }
        assertTrue(status);
    }
}
