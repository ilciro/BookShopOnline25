package testFinali;

import laptop.database.primoucacquista.cartacredito.PersistenzaCC;
import laptop.database.primoucacquista.fattura.PersistenzaFattura;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.PersistenzaLibro;
import laptop.database.primoucacquista.negozio.PersistenzaNegozio;
import laptop.database.primoucacquista.pagamento.PersistenzaPagamento;
import laptop.database.terzogestioneprofilooggetto.report.PersistenzaReport;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.secondouclogin.users.PersistenzaUtente;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TestFinali {

     private final PersistenzaGiornale pG=new PersistenzaGiornale();
     private final PersistenzaLibro pL=new PersistenzaLibro();
     private final PersistenzaRivista pR=new PersistenzaRivista();
     private final PersistenzaFattura pF=new PersistenzaFattura();
     private final PersistenzaPagamento pP=new PersistenzaPagamento();
     private final PersistenzaUtente pU=new PersistenzaUtente();
     private final PersistenzaReport pRepo=new PersistenzaReport();
     private final PersistenzaNegozio pN=new PersistenzaNegozio();
     private final PersistenzaCC pCC=new PersistenzaCC();

     @Test
    void testPGExceptions()
     {
         assertDoesNotThrow(pG::initializza);
     }
    @Test
    void testPLExceptions()
    {
        assertDoesNotThrow(pL::initializza);
    }
    @Test
    void testPRExceptions()
    {
        assertDoesNotThrow(pR::initializza);
    }
    @Test
    void testPFExceptions()
    {
        assertDoesNotThrow(pF::inizializza);
    }
    @Test
    void testPPExceptions()
    {
        assertDoesNotThrow(pP::inizializza);
    }
    @Test
    void testPUExceptions()
    {
        assertDoesNotThrow(pU::initializza);
    }
    @Test
    void testPRepoExceptions()
    {
        assertDoesNotThrow(pRepo::inizializza);
    }
    @Test
    void testPNExceptions()
    {
        assertDoesNotThrow(pN::initializza);
    }
    @Test
    void testPCCException ()
    {
        assertDoesNotThrow(pCC::inizializza);
    }

}
