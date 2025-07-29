package demo;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.application.Platform;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDemo {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String MEMORIA="memoria";
    private static final ControllerHomePage cHP=new ControllerHomePage();
    private static final ControllerCompravendita cCV=new ControllerCompravendita();
    private static final ControllerAcquista cA=new ControllerAcquista();
    private static final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private static final ControllerDownload cD=new ControllerDownload();
    private static final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private static final ControllerVisualizza cV=new ControllerVisualizza();
    private static final ControllerScegliNegozio cSN=new ControllerScegliNegozio();
    private static final ControllerAnnullaPagamento cAP=new ControllerAnnullaPagamento();
     @Test
    void testAcquistaLibroCashDownload() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
         vis.setTipologiaApplicazione("demo");
         vis.setTypeAsBook();
         //inizializzo lista
         cHP.persistenza(MEMORIA);
         //scelgo id
         cCV.checkId(6,MEMORIA,vis.getType());
         //setto id
         vis.setIdLibro(6);
         //scelgo quantita e prezzo
         cA.getPrezzo("3",MEMORIA);
         //prendo oggetto e crea fattura
         cPCash.controlla("prova","prova","via prova","",MEMORIA);
         //scarico oggetto
         cD.scarica(vis.getType(),MEMORIA);

     }
    @Test
    void testAcquistaGiornaleCCNegozio() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException{
        vis.setTipologiaApplicazione("demo");
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
        cPCC.pagamentoCC("prova",MEMORIA,"prova");
        //scarico oggetto
        cSN.getNegozi(MEMORIA);
        boolean status= cSN.isOpen(MEMORIA,2)&&cSN.isValid(MEMORIA,2);
        assertTrue(status);

    }
    @Test
    void testAnnullaRivistaCashDownload() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        vis.setTipologiaApplicazione("demo");
        vis.setTypeAsMagazine();
        vis.setMetodoP("cash");
        //inizializzo lista
        cHP.persistenza(MEMORIA);
        //scelgo id
        cCV.checkId(3,MEMORIA,vis.getType());
        //setto id
        vis.setIdRivista(1);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",MEMORIA);
        //prendo oggetto e crea fattura
        cPCash.controlla("prova","prova","via prova","",MEMORIA);
        //annullo pagamento -> prendo lista
        PagamentoFattura pf=cAP.getFattura(MEMORIA).get(0);
        //cancello fattura
       assertTrue(cAP.cancellaFattura(pf.getIdFattura(), MEMORIA));

    }

    @Test
    void testAnnullaGiornaleCCredito() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        vis.setTipologiaApplicazione("demo");
        vis.setTypeAsDaily();
        vis.setMetodoP("cCredito");
        //inizializzo lista
        cHP.persistenza(MEMORIA);
        //scelgo id
        cCV.checkId(1,MEMORIA,vis.getType());
        //setto id
        vis.setIdGiornale(1);
        //visualizza
        cV.getID();
        cV.getListGiornale(MEMORIA);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",MEMORIA);
        //controllo correttezza cc
        cPCC.correttezza("1952-7488-1111-5252","2030/09/09","841");
        //effettuo pagamento
        cPCC.pagamentoCC("prova",MEMORIA,"prova");
        //annullo pagamento
        PagamentoCartaCredito pCC=cAP.getPagamentoCartaCredito(MEMORIA).get(0);
        assertTrue(cAP.cancellaPagamentoCC(pCC.getIdPagCC(),MEMORIA));
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
