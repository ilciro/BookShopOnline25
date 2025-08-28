package demo.secondouclogin;


import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;


import java.util.ResourceBundle;

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
    private static final ResourceBundle RBUTENE =ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBCC =ResourceBundle.getBundle("configurations/cartaCredito");


    @Test
    void testLoginAsUser()  {
        vis.setTipologiaApplicazione("demo");
        //loggo come user
        cL.login(RBUTENE.getString("emailU"),RBUTENE.getString("passU"),MEMORIA);
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
        cPCash.controlla(RBUTENE.getString("nomeU"),RBUTENE.getString("cognomeU"), "via papaveri 8","dopo le 18",MEMORIA);
        //scarico oggetto
        cD.scarica(vis.getType(),MEMORIA);
        //logout
        assertTrue(cHP.logout());

    }
    @Test
    void testLoginAsScrittore()  {
        vis.setTipologiaApplicazione("demo");
        //loggo come user
        cL.login(RBUTENE.getString("emailS"),RBUTENE.getString("passS"),MEMORIA);
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
        cPCash.controlla(RBUTENE.getString("nomeU"),RBUTENE.getString("cognomeU"), "piazza sempio snc","dopo le 9",MEMORIA);
        //scarico oggetto
        cD.scarica(vis.getType(),MEMORIA);
        //logout
        assertTrue(cHP.logout());

    }

    @Test
    void testLoginAsEditore() throws  IdException{
        vis.setTipologiaApplicazione("demo");
        cL.login(RBUTENE.getString("emailE"),RBUTENE.getString("passE"),MEMORIA);
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
        cPCC.correttezza(RBCC.getString("codice1"),RBCC.getString("data1").replace("-","/"),RBCC.getString("civ1"));
        //effettuo pagamento
        cPCC.pagamentoCC(RBUTENE.getString("nomeE"),MEMORIA,RBUTENE.getString("cognomeE"));
        //scarico oggetto
        cSN.getNegozi(MEMORIA);
        cSN.isOpen(MEMORIA,2);
        cSN.isValid(MEMORIA,2);
        //logout
        assertTrue(cHP.logout());


    }


}
