package vari;

import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestVari2 {

     private static final ControllerHomePage cHP=new ControllerHomePage();
     private static final ControllerCompravendita cCV=new ControllerCompravendita();
     private static final ControllerAcquista cA=new ControllerAcquista();
     private static final ControllerLogin cL=new ControllerLogin();
     private static final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
     private static final ControllerScegliNegozio cSN=new ControllerScegliNegozio();
     private static final ControllerSystemState vis=ControllerSystemState.getInstance();
     private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
     private static final ResourceBundle RBCC=ResourceBundle.getBundle("configurations/cartaCredito");


     @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAggiungiCartaPerRivista(String strings) throws ParseException, IdException {
         //login
         cL.login(RBUTENTE.getString("email2"),RBUTENTE.getString("passAgg"),strings);
         vis.setMetodoP("cCredito");
         //setto rivista
         vis.setTypeAsMagazine();
         //setto id rivista
         vis.setIdRivista(5);
         cCV.checkId(vis.getIdRivista(),strings,vis.getType());
         //setto modalita
         cHP.persistenza(strings);
         //prendo lista
         cCV.getLista(vis.getType(),strings);
         //prendo dati
         cA.getNomeCostoDisp(strings);
         cA.getPrezzo("4",strings);
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         java.sql.Date sqlDate = new java.sql.Date(df.parse(RBCC.getString("dataR")).getTime());
         //registro carta
        cPCC.aggiungiCartaDB(RBUTENTE.getString("nome2"),RBUTENTE.getString("cognome2"),RBCC.getString("codiceR"),
                 sqlDate,RBCC.getString("civR"),Float.parseFloat(RBCC.getString("prezzoR")),strings);
         //prendo lista
        CartaDiCredito cc=cPCC.ritornaElencoCC(RBUTENTE.getString("nome2"),strings,RBCC.getString("codiceR")).get(0);
         String data=RBCC.getString("dataR").replace("-","/");
        //verifico correttezza
          cPCC.correttezza(cc.getNumeroCC(), data,cc.getCiv());
          //effettuo pagamento
          cPCC.pagamentoCC(RBUTENTE.getString("nome2"),strings,RBUTENTE.getString("cognome2"));
          //scarico oggetto
          cSN.getNegozi(strings);
          boolean status= cSN.isOpen(strings,2)&&cSN.isValid(strings,2);
          assertTrue(status);







     }
}
