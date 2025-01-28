package laptop.controller.primoucacquista;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.fattura.ContrassegnoDao;
import laptop.database.fattura.CsvFattura;
import laptop.database.fattura.MemoriaFattura;
import laptop.database.fattura.PersistenzaFattura;
import laptop.database.pagamento.CsvPagamento;
import laptop.database.pagamento.MemoriaPagamento;
import laptop.database.pagamento.PagamentoDao;
import laptop.database.pagamento.PersistenzaPagamento;
import laptop.model.Fattura;
import laptop.model.Pagamento;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAnnullaPagamento {

    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";
    private PersistenzaFattura pF;
    private PersistenzaPagamento pP;


    public String getFattura(String type) throws CsvValidationException, IOException, ClassNotFoundException {
        String fattura;

        switch (type)
        {
            case DATABASE -> pF=new ContrassegnoDao();
            case FILE -> pF=new CsvFattura();
            case MEMORIA -> pF=new MemoriaFattura();
            default -> Logger.getLogger("ultima fattura").log(Level.SEVERE,"error with last fattura");

        }
        fattura=pF.ultimaFattura().toString();

        return fattura;

    }
    public String getPagamento(String type) throws CsvValidationException, IOException, ClassNotFoundException {
        String pagamento;
        switch (type)
        {
            case DATABASE -> pP=new PagamentoDao();
            case FILE -> pP=new CsvPagamento();
            case MEMORIA -> pP=new MemoriaPagamento();
            default -> Logger.getLogger("ultimo pagamento").log(Level.SEVERE,"error with last payment");

        }
        pagamento=pP.ultimoPagamento().toString();
        return pagamento;

    }




    public boolean cancellaFattura(String text, String string) throws CsvValidationException, IOException, ClassNotFoundException {

        switch (string)
        {
            case DATABASE -> pF=new ContrassegnoDao();
            case FILE -> pF=new CsvFattura();
            case MEMORIA -> pF=new MemoriaFattura();
            default -> Logger.getLogger("cancella fattura").log(Level.SEVERE," fattura not deleted!!");
        }
        Fattura f=new Fattura();
        f.setIdFattura(Integer.parseInt(text));
        return pF.cancellaFattura(f);


    }
    public boolean cancellaPagamento(String text, String string) throws CsvValidationException, IOException, ClassNotFoundException {

        switch (string)
        {
            case DATABASE -> pP=new PagamentoDao();
            case FILE -> pP=new CsvPagamento();
            case MEMORIA -> pP=new MemoriaPagamento();
            default -> Logger.getLogger("cancella pagamento").log(Level.SEVERE," payment not deleted!!");
        }
        Pagamento p=new Pagamento();
        p.setIdPag(Integer.parseInt(text));
        return pP.cancellaPagamento(p);

    }
}
