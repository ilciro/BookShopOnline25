package primouc;

import com.opencsv.exceptions.CsvValidationException;
import laptop.controller.primoucacquista.ControllerAnnullaPagamento;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

 class TestControllerScaricaAnnulla {

    private static final ControllerAnnullaPagamento cAP=new ControllerAnnullaPagamento();
    private int numeroFattura=0;
    private int numeroPagamento=0;

    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testEliminaUltimaFattura(String strings) throws  CsvValidationException, IOException, ClassNotFoundException {
        String[] arr=cAP.getFattura(strings).split(", ");

        for(String s1:arr)
        {
            if(s1.contains("numero"))
            {
                String[] bb = s1.split("=");
                numeroFattura=Integer.parseInt(bb[1]);
            }
        }



        assertTrue(cAP.cancellaFattura(String.valueOf(numeroFattura),strings));

    }


    @ParameterizedTest
    @ValueSource(strings = {"database","file","memoria"})
    void testEliminaUltimoPagamento(String strings) throws  CsvValidationException, IOException, ClassNotFoundException {

        String[] arr=cAP.getPagamento(strings).split(", ");

        for(String s1:arr)
        {
            if(s1.contains("[id"))
            {
                String[] bb = s1.split("=");
                numeroPagamento=Integer.parseInt(bb[1]);
            }
        }

        assertTrue(cAP.cancellaPagamento(String.valueOf(numeroPagamento),strings));

    }




}
