package terzoucgestione;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerVisualizzaOrdini;
import laptop.exception.IdException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGestioneOrdini {
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

    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAnnullaOrdineCash(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        vis.setTipologiaApplicazione("full");
        cL.login("giuliaConforto@gmail.eu","12345678Gc",strings);
        vis.setTypeAsMagazine();
        vis.setMetodoP("cash");
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(3,strings,vis.getType());
        //setto id
        vis.setIdRivista(2);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //prendo oggetto e crea fattura
        cPCash.controlla(cPCash.getInfo()[0],cPCash.getInfo()[1],"via prova","",strings);
        //scarico
        cD.scarica(vis.getType(),strings);
        //prendo lista
        for(int i=0;i<cVO.getListaFattura(strings).size();i++)
        {
            cVO.cancellaPagamento(cVO.getListaFattura(strings).get(0).getIdFattura(), strings);
        }
        //logout
        assertTrue(cHP.logout());




    }
    @ParameterizedTest
    @ValueSource(strings = {"database","file"})
    void testAnnullaOrdineCredito(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException { vis.setTipologiaApplicazione("demo");
        vis.setTipologiaApplicazione("full");
        cL.login("giuliaConforto@gmail.eu","12345678Gc",strings);
        vis.setTypeAsMagazine();
        vis.setMetodoP("cCredito");
        //inizializzo lista
        cHP.persistenza(strings);
        //scelgo id
        cCV.checkId(3,strings,vis.getType());
        //setto id
        vis.setIdRivista(3);
        //visualizza
        cV.getID();
        cV.getListRivista(strings);
        //scelgo quantita e prezzo
        cA.getPrezzo("3",strings);
        //controllo correttezza cc
        cPCC.correttezza("1952-7488-1111-5252","2030/09/09","841");
        //effettuo pagamento
        cPCC.pagamentoCC("prova",strings,"prova");
        //scarico oggetto
        cSN.getNegozi(strings);
        //conbtrollo negozio
        cSN.isOpen(strings,2);
        cSN.isValid(strings,2);
        for(int i=0;i<cVO.getListaCC(strings).size();i++)
        {
            cVO.cancellaPagamento(cVO.getListaCC(strings).get(0).getIdPagCC(), strings);
        }
        //logout
        assertTrue(cHP.logout());

    }
}
