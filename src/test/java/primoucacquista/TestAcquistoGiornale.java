package primoucacquista;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestAcquistoGiornale {

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerHomePage cHP=new ControllerHomePage();
    private static final ControllerCompravendita cCV=new ControllerCompravendita();
    private static final ControllerAcquista cA=new ControllerAcquista();
    private static final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private static final ControllerDownload cD=new ControllerDownload();
    private static final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private static final ControllerVisualizza cV=new ControllerVisualizza();
    private static final ControllerScegliNegozio cSN=new ControllerScegliNegozio();
    private static final ControllerAnnullaPagamento cAP=new ControllerAnnullaPagamento();

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAcquistaLibroCashDownload(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        vis.setTipologiaApplicazione("full");
        vis.setMetodoP("cash");
        vis.setTypeAsDaily();
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(2,strings,vis.getType());
        //setto id
        vis.setIdGiornale(2);
        //scelgo quantita e prezzo
        cA.getPrezzo("4",strings);
        //prendo oggetto e crea fattura
        cPCash.controlla("prova","prova","via prova","",strings);
        //scarico oggetto
        cD.scarica(vis.getType(),strings);

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAcquistaGiornaleCCNegozio(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsDaily();
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(2,strings,vis.getType());
        //setto id
        vis.setIdGiornale(2);
        //visualizza
        cV.getID();
        cV.getListGiornale(strings);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //controllo correttezza cc
        cPCC.correttezza("1952-7488-1111-5252","2030/09/09","841");
        //effettuo pagamento
        cPCC.pagamentoCC("prova",strings,"prova");
        //scarico oggetto
        cSN.getNegozi(strings);
        boolean status= cSN.isOpen(strings,2)&&cSN.isValid(strings,2);
        assertTrue(status);

    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAnnullaLibroCashDownload(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsDaily();
        vis.setMetodoP("cash");
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(3,strings,vis.getType());
        //setto id
        vis.setIdGiornale(1);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //prendo oggetto e crea fattura
        cPCash.controlla("prova","prova","via prova","",strings);
        //annullo pagamento -> prendo lista
        PagamentoFattura pf=cAP.getFattura(strings).get(0);
        //cancello fattura
        assertTrue(cAP.cancellaFattura(pf.getIdFattura(), strings));

    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAnnullaGiornaleCCredito(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsDaily();
        vis.setMetodoP("cCredito");
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(1,strings,vis.getType());
        //setto id
        vis.setIdGiornale(1);
        //visualizza
        cV.getID();
        cV.getListGiornale(strings);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //controllo correttezza cc
        cPCC.correttezza("1952-7488-1111-5252","2030/09/09","841");
        //effettuo pagamento
        cPCC.pagamentoCC("prova",strings,"prova");
        //annullo pagamento
        PagamentoCartaCredito pCC=cAP.getPagamentoCartaCredito(strings).get(0);
        assertTrue(cAP.cancellaPagamentoCC(pCC.getIdPagCC(),strings));
    }

}
