package demo.terzoucgestione;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerVisualizzaOrdini;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;


class TestControllerGestioneordini {
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
    private static final ControllerLogin cL=new ControllerLogin();
    private static final ControllerVisualizzaOrdini cVO=new ControllerVisualizzaOrdini();

    @Test
    void testAnnullaOrdineCash() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        boolean status=false;
        vis.setTipologiaApplicazione("demo");
        cL.login("giuliaConforto@gmail.eu","12345678Gc",MEMORIA);
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
        cPCash.controlla(cPCash.getInfo()[0],cPCash.getInfo()[1],"via prova","",MEMORIA);
        //scarico
        cD.scarica(vis.getType(),MEMORIA);
        //prendo lista
        for(int i=0;i<cVO.getListaFattura(MEMORIA).size();i++)
        {
           status=cVO.cancellaPagamento(cVO.getListaFattura(MEMORIA).get(0).getIdFattura(),MEMORIA);
        }
        assertTrue(status);




    }
    @Test
    void testAnnullaOrdineCredito() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException { vis.setTipologiaApplicazione("demo");
        boolean status=false;
        vis.setTipologiaApplicazione("demo");
        cL.login("giuliaConforto@gmail.eu","12345678Gc",MEMORIA);
        vis.setTypeAsMagazine();
        vis.setMetodoP("cCredito");
        //inizializzo lista
        cHP.persistenza(MEMORIA);
        //scelgo id
        cCV.checkId(3,MEMORIA,vis.getType());
        //setto id
        vis.setIdRivista(3);
        //visualizza
        cV.getID();
        cV.getListRivista(MEMORIA);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",MEMORIA);
        //controllo correttezza cc
        cPCC.correttezza("1952-7488-1111-5252","2030/09/09","841");
        //effettuo pagamento
        cPCC.pagamentoCC("prova",MEMORIA,"prova");
        //scarico oggetto
        cSN.getNegozi(MEMORIA);
        //conbtrollo negozio
         cSN.isOpen(MEMORIA,2);
         cSN.isValid(MEMORIA,2);
        for(int i=0;i<cVO.getListaCC(MEMORIA).size();i++)
        {
            status=cVO.cancellaPagamento(cVO.getListaCC(MEMORIA).get(0).getIdPagCC(),MEMORIA);
        }
        assertTrue(status);

    }

}
