package primoUcAcquista.testGiornale;

import com.itextpdf.text.DocumentException;
import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

 class TestAcquistaGiornale {
    private final ControllerHomePage cHP=new ControllerHomePage();
    private final ControllerCompravendita cCopravendita=new ControllerCompravendita();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private final ControllerDownload cD=new ControllerDownload();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String GIORNALE="giornale";
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private static final ResourceBundle RBOGGETTO=ResourceBundle.getBundle("configurations/objects");
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();
    private final ControllerScegliNegozio cSN=new ControllerScegliNegozio();
    private final ControllerAnnullaPagamento cAP=new ControllerAnnullaPagamento();

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testAcquistaGiornaleCredito(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        //inizializzo tabella giornale
        vis.setTypeAsDaily();
        cHP.persistenza(strings);
        //prendo lista oggetti
        cCopravendita.getLista(GIORNALE,strings);
        vis.setIdGiornale(Integer.parseInt(RBOGGETTO.getString("idG")));
        //acquisto
        cA.getPrezzo("2",strings);
        //pagamento cc
        vis.setMetodoP("cCredito");
        cPCC.pagamentoCC(RBUTENTE.getString("nome"),strings);
        //negozio
        cSN.getNegozi(strings);
        boolean status=cSN.isOpen(strings,1)&&cSN.isValid(strings,1);
       assertFalse(status);
    }


    @ParameterizedTest
    @ValueSource(strings = {"database","memoria","file"})
    void testAcquistaGiornaleCash(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, DocumentException, URISyntaxException {
        //inizializzo tabella giornale
        vis.setTypeAsDaily();
        cHP.persistenza(strings);
        //prendo lista oggetti
        cCopravendita.getLista(GIORNALE,strings);
        vis.setIdGiornale(Integer.parseInt(RBOGGETTO.getString("idG")));
        //acquisto
        cA.getPrezzo("2",strings);
        //fattura
        vis.setMetodoP("cash");
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"),strings);
        //download
        cD.scarica(GIORNALE,strings);
        assertEquals(Integer.parseInt(RBOGGETTO.getString("idG")),vis.getIdGiornale());
    }

     @ParameterizedTest
     @ValueSource(strings = {"database","file","memoria"})
     void testAcquistaGiornaleCashRemove(String strings) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
         //inizializzo tabella giornale
         vis.setTypeAsDaily();
         cHP.persistenza(strings);
         //prendo lista oggetti
         cCopravendita.getLista(GIORNALE,strings);
         vis.setIdGiornale(Integer.parseInt(RBOGGETTO.getString("idG")));
         //acquisto
         cA.getPrezzo("2",strings);
         //fattura
         vis.setMetodoP("cash");
         cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"),strings);
         //annulla fattura
         int numeroFattura=0;
         String[] arr=cAP.getFattura(strings).split(", ");

         for(String s1:arr)
         {
             if(s1.contains("numero"))
             {
                 String[] bb = s1.split("=");
                 numeroFattura=Integer.parseInt(bb[1]);
             }
         }
         String[] arrP=cAP.getPagamento(strings).split(", ");
         int numeroPagamento=0;

         for(String s1:arrP)
         {
             if(s1.contains("[id"))
             {
                 String[] bb = s1.split("=");
                 numeroPagamento=Integer.parseInt(bb[1]);
             }
         }
         cAP.cancellaPagamento(String.valueOf(numeroPagamento),strings);


         assertTrue(cAP.cancellaFattura(String.valueOf(numeroFattura),strings));


     }


}
