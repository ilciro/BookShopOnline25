package laptop.controller.terzogestioneprofilooggetto;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;

import laptop.database.primoucacquista.pagamento.CsvPagamento;
import laptop.database.primoucacquista.pagamento.MemoriaPagamento;
import laptop.database.primoucacquista.pagamento.PagamentoDao;
import laptop.database.primoucacquista.pagamento.PersistenzaPagamento;
import laptop.model.Pagamento;
import laptop.model.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerVisualizzaOrdini {
    private PersistenzaPagamento pP;
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";



    public String getEmail()
    {
        return User.getInstance().getEmail();
    }

    public ObservableList<Pagamento> getLista(String persistenza) throws CsvValidationException, IOException, ClassNotFoundException, SQLException {



        switch (persistenza)
        {
            case DATABASE->pP=new PagamentoDao();
            case FILE->pP=new CsvPagamento();
            case MEMORIA->pP=new MemoriaPagamento();
            default -> Logger.getLogger("getLista pagamenti").log(Level.SEVERE,"list pf payment is empty!!");
        }

        Pagamento p=new Pagamento();
        p.setEmail(getEmail());

            pP.inizializza();

        return pP.listPagamentiByUser(p);
    }
    public boolean cancellaPagamento(int id,String persistenza) throws CsvValidationException, IOException, ClassNotFoundException {

        Pagamento p=new Pagamento();
        p.setIdPag(id);

        switch (persistenza)
        {
            case DATABASE->pP=new PagamentoDao();
            case FILE->pP=new CsvPagamento();
            case MEMORIA->pP=new MemoriaPagamento();
            default -> Logger.getLogger("cancella pagamento").log(Level.SEVERE,"delete payment has not occurred");
        }

        return pP.cancellaPagamento(p);
    }
}
