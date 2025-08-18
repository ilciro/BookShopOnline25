package demo.primoucacquista;

import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;

import laptop.pagamento.PagamentoCartaCredito;
import laptop.pagamento.PagamentoFattura;
import org.junit.jupiter.api.Test;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestDemoAcquista {

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
    private static final ResourceBundle RBUTENE =ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBCC =ResourceBundle.getBundle("configurations/cartaCredito");

    @Test
    void testAcquistaLibroCashDownload()  {
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
         cPCash.controlla(RBUTENE.getString("prova"),RBUTENE.getString("prova"),"via"+RBUTENE.getString("prova"),"",MEMORIA);
         //scarico oggetto
         cD.scarica(vis.getType(),MEMORIA);

     }
    @Test
    void testAcquistaGiornaleCCNegozio() throws IdException {
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
        cPCC.correttezza(RBCC.getString("codice"),RBCC.getString("data").replace("-","/"),RBCC.getString("civ"));
        //effettuo pagamento
        cPCC.pagamentoCC(RBUTENE.getString("prova"),MEMORIA,RBUTENE.getString("prova"));
        //scarico oggetto
        cSN.getNegozi(MEMORIA);
        boolean status= cSN.isOpen(MEMORIA,2)&&cSN.isValid(MEMORIA,2);
        assertTrue(status);

    }

    @Test
    void testAnnullaRivistaCashDownload()  {
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
        cPCash.controlla(RBUTENE.getString("prova"),RBUTENE.getString("prova"),"via"+RBUTENE.getString("prova"),"",MEMORIA);
        //annullo pagamento -> prendo lista
        PagamentoFattura pf=cAP.getFattura(MEMORIA).get(0);
        //cancello fattura
       assertTrue(cAP.cancellaFattura(pf.getIdFattura(), MEMORIA));

    }

    @Test
    void testAnnullaGiornaleCCredito() throws IdException {
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
        cPCC.correttezza(RBCC.getString("codice1"),RBCC.getString("data1").replace("-","/"),RBCC.getString("civ1"));
        //effettuo pagamento
        cPCC.pagamentoCC("prova",MEMORIA,"prova");
        //annullo pagamento
        PagamentoCartaCredito pCC=cAP.getPagamentoCartaCredito(MEMORIA).get(0);
        System.out.println("dio :"+pCC + pCC.getIdPagCC());
       assertTrue(cAP.cancellaPagamentoCC(pCC.getIdPagCC(),MEMORIA));
    }








}
