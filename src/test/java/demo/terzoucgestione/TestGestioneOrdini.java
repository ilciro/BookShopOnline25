package demo.terzoucgestione;


import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerVisualizzaOrdini;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGestioneOrdini {
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final ControllerHomePage cHP=new ControllerHomePage();
    private static final ControllerCompravendita cCV=new ControllerCompravendita();
    private static final ControllerAcquista cA=new ControllerAcquista();
    private static final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private static final ControllerDownload cD=new ControllerDownload();
    private static final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ControllerVisualizzaOrdini cVO=new ControllerVisualizzaOrdini();
    private static final ResourceBundle RBUTENTE =ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBCC =ResourceBundle.getBundle("configurations/cartaCredito");
    private static final String MEMORIA="memoria";


    @Test
    void testAnnullaOrdineCashByUserLogged() {
        vis.setTipologiaApplicazione("demo");
        //homepage con login
        cL.login(RBUTENTE.getString("emailU"),RBUTENTE.getString("passU"),MEMORIA);
        // login fatto-> home page
        //scelgo libro
        vis.setTypeAsBook();
        cCV.getLista(vis.getType(),MEMORIA);
        //scelgo libro 10
        vis.setIdLibro(10);
        //acquista
        cA.getNomeCostoDisp(MEMORIA);
        cA.getPrezzo("5",MEMORIA);
        //cash
        vis.setMetodoP("cash");
        //pagamento fattura
        cPCash.controlla(RBUTENTE.getString("nomeU"),RBUTENTE.getString("cognomeU"),"via papaveri 152","dopo le 13",MEMORIA);
        //download
        cD.scarica(vis.getType(),MEMORIA);
        //torno ad home page da loggato
        //prendo fattura
        int id=cVO.getListaFattura(MEMORIA).get(cVO.getListaFattura(MEMORIA).size()-1).getIdFattura();
        //cancello ordine
         cVO.cancellaPagamento(id,MEMORIA);
        assertTrue(cHP.logout());
    }

   @Test
    void testAnnullaOrdineCCByUSerLogged() throws IdException {
       vis.setTipologiaApplicazione("demo");
       //homepage con login
       cL.login(RBUTENTE.getString("emailU"),RBUTENTE.getString("passU"),MEMORIA);
       // login fatto-> home page
       //scelgo libro
       vis.setTypeAsMagazine();
       cCV.getLista(vis.getType(),MEMORIA);
       //scelgo rivista 2
       vis.setIdRivista(2);
       //acquista
       cA.getNomeCostoDisp(MEMORIA);
       cA.getPrezzo("5",MEMORIA);
       //cash
       vis.setMetodoP("cCredito");
       //pagamento cc
       cPCC.correttezza(RBCC.getString("codice1"),RBCC.getString("data1").replace("-","/"),RBCC.getString("civ1"));
       //effettuo pagamento
       cPCC.pagamentoCC("prova",MEMORIA,"prova");
       //download
       cD.scarica(vis.getType(),MEMORIA);
       //torno ad home page da loggato
       //prendo fattura
       int id=cVO.getListaFattura(MEMORIA).get(cVO.getListaFattura(MEMORIA).size()-1).getIdFattura();
       //cancello ordine
       cVO.cancellaPagamento(id,MEMORIA);
       assertTrue(cHP.logout());
   }



}