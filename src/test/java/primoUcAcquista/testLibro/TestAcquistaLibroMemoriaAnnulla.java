package primoUcAcquista.testLibro;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.*;
import laptop.exception.IdException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestAcquistaLibroMemoriaAnnulla {
    private final ControllerHomePage cHP=new ControllerHomePage();
    private final ControllerCompravendita cCopravendita=new ControllerCompravendita();
    private final ControllerAcquista cA=new ControllerAcquista();
    private final ControllerPagamentoCash cPCash=new ControllerPagamentoCash();
    private final ControllerAnnullaPagamento cAP=new ControllerAnnullaPagamento();
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private static final String MEMORIA="memoria";
    private static final ResourceBundle RBUTENTE=ResourceBundle.getBundle("configurations/users");

    @Test
    void testAcquistaLibro() throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException {
        //inizializzo tabella giornale
        vis.setTypeAsBook();
        cHP.persistenza(MEMORIA);
        //prendo lista oggetti
        cCopravendita.getLista("libro",MEMORIA);
        vis.setId(2);
        //acquisto
        cA.getPrezzo("3",MEMORIA);
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
}
