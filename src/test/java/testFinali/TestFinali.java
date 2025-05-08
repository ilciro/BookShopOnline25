package testFinali;

import laptop.database.fattura.PersistenzaFattura;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.negozio.PersistenzaNegozio;
import laptop.database.pagamento.PagamentoDao;
import laptop.database.pagamento.PersistenzaPagamento;
import laptop.database.report.PersistenzaReport;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.users.PersistenzaUtente;
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

}
