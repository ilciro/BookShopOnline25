package primoucacquista;

import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;

import laptop.pagamento.PagamentoCartaCredito;
import laptop.pagamento.PagamentoFattura;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestAcquistoLibro {

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
    private static final ResourceBundle RBUTENE =ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBCC =ResourceBundle.getBundle("configurations/cartaCredito");

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAcquistaLibroCashDownload(String strings)  {
        vis.setTipologiaApplicazione("full");
        vis.setMetodoP("cash");
        vis.setTypeAsBook();
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(10,strings,vis.getType());
        //setto id
        vis.setIdLibro(10);
        //scelgo quantita e prezzo
        cA.getPrezzo("4",strings);
        //prendo oggetto e crea fattura
        cPCash.controlla(RBUTENE.getString("prova"),RBUTENE.getString("prova"),"via "+RBUTENE.getString("prova"),"",strings);
        //scarico oggetto
        cD.scarica(vis.getType(),strings);

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAcquistaLibroCCNegozio(String strings) throws  IdException {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsBook();
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(4,strings,vis.getType());
        //setto id
        vis.setIdLibro(4);
        //visualizza
        cV.getID();
        cV.getListLibro(strings);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //controllo correttezza cc
        cPCC.correttezza(RBCC.getString("codice1"),RBCC.getString("data1").replace("-","/"),RBCC.getString("civ1"));
        //effettuo pagamento
        cPCC.pagamentoCC(RBUTENE.getString("prova"),strings,RBUTENE.getString("prova"));
        //scarico oggetto
        cSN.getNegozi(strings);
        boolean status= cSN.isOpen(strings,2)&&cSN.isValid(strings,2);
        assertTrue(status);

    }

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAnnullaLibroCashDownload(String strings)  {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsBook();
        vis.setMetodoP("cash");
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(3,strings,vis.getType());
        //setto id
        vis.setIdLibro(1);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //prendo oggetto e crea fattura
        cPCash.controlla(RBUTENE.getString("prova"),RBUTENE.getString("prova"),"via "+RBUTENE.getString("prova"),"",strings);
        //annullo pagamento -> prendo lista
        PagamentoFattura pf=cAP.getFattura(strings).get(0);
        //cancello fattura
        assertTrue(cAP.cancellaFattura(pf.getIdFattura(), strings));

    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAnnullaLibroCCredito(String strings) throws  IdException {
        vis.setTipologiaApplicazione("full");
        vis.setTypeAsBook();
        vis.setMetodoP("cCredito");
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(1,strings,vis.getType());
        //setto id
        vis.setIdLibro(1);
        //visualizza
        cV.getID();
        cV.getListLibro(strings);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //controllo correttezza cc
        cPCC.correttezza(RBCC.getString("codice1"),RBCC.getString("data1").replace("-","/"),RBCC.getString("civ1"));
        //effettuo pagamento
        cPCC.pagamentoCC(RBUTENE.getString("prova"),strings,RBUTENE.getString("prova"));
        //annullo pagamento
        PagamentoCartaCredito pCC=cAP.getPagamentoCartaCredito(strings).get(0);
        assertTrue(cAP.cancellaPagamentoCC(pCC.getIdPagCC(),strings));
    }



}
