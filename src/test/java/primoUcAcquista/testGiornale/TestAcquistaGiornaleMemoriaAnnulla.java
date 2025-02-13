package primoUcAcquista.testGiornale;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class TestAcquistaGiornaleMemoriaAnnulla {
    private final ControllerHomePage cHP=new ControllerHomePage();
    private final ControllerCompravendita cCopravendita=new ControllerCompravendita();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private final ControllerAnnullaPagamento cAP=new ControllerAnnullaPagamento();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String MEMORIA="memoria";
    private static final String GIORNALE="giornale";
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");
    private final ControllerPagamentoCC cPCC=new ControllerPagamentoCC();

    @Test
    void testAcquistaGiornaleCash() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        //inizializzo tabella giornale
        vis.setTypeAsDaily();
        vis.setTipoModifica("insert");
        cHP.persistenza(MEMORIA);
        //prendo lista oggetti
        cCopravendita.getLista(GIORNALE,MEMORIA);
        vis.setId(6);
        //acquisto
        cA.getPrezzo("2",MEMORIA);
        //fattura
        vis.setMetodoP("cash");
        cPCash.controlla(RBUTENTE.getString("nome"),RBUTENTE.getString("cognome"),RBUTENTE.getString("via"),RBUTENTE.getString("com"),MEMORIA);
        //annulla fattura
        int numeroFattura=0;
        String[] arr=cAP.getFattura(MEMORIA).split(", ");

        for(String s1:arr)
        {
            if(s1.contains("numero"))
            {
                String[] bb = s1.split("=");
                numeroFattura=Integer.parseInt(bb[1]);
            }
        }
        String[] arrP=cAP.getPagamento(MEMORIA).split(", ");
        int numeroPagamento=0;

        for(String s1:arrP)
        {
            if(s1.contains("[id"))
            {
                String[] bb = s1.split("=");
                numeroPagamento=Integer.parseInt(bb[1]);
            }
        }
        cAP.cancellaPagamento(String.valueOf(numeroPagamento),MEMORIA);


        assertTrue(cAP.cancellaFattura(String.valueOf(numeroFattura),MEMORIA));


    }

    @Test
    void testAcquistaGiornaleCredito() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        //inizializzo tabella giornale
        vis.setTypeAsDaily();
        cHP.persistenza(MEMORIA);
        vis.setTipoModifica("insert");
        //prendo lista oggetti
        cCopravendita.getLista(GIORNALE,MEMORIA);
        vis.setId(6);
        //acquisto
        cA.getPrezzo("2",MEMORIA);
        //pagamento cc
        vis.setMetodoP("cCredito");
        cPCC.pagamentoCC(RBUTENTE.getString("nome"),MEMORIA);
        //anulla pagamento

        int numeroPagamento=0;
        String[] arrP=cAP.getPagamento(MEMORIA).split(", ");


        for(String s1:arrP)
        {
            if(s1.contains("[id"))
            {
                String[] bb = s1.split("=");
                numeroPagamento=Integer.parseInt(bb[1]);
            }
        }
        assertTrue(cAP.cancellaPagamento(String.valueOf(numeroPagamento),MEMORIA));

    }
}
