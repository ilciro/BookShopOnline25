package laptop.controller.terzoucgestioneprofiloggetto;

import javafx.collections.ObservableList;


import laptop.controller.ControllerSystemState;
import laptop.database.primoucacquista.pagamentocartacredito.CsvPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentocartacredito.MemoriaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentocartacredito.PagamentoCartaCreditoDao;
import laptop.database.primoucacquista.pagamentocartacredito.PersistenzaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentofattura.ContrassegnoDao;
import laptop.database.primoucacquista.pagamentofattura.CsvFattura;
import laptop.database.primoucacquista.pagamentofattura.MemoriaFattura;
import laptop.database.primoucacquista.pagamentofattura.PersistenzaPagamentoFattura;


import laptop.model.user.User;
import laptop.pagamento.PagamentoCartaCredito;
import laptop.pagamento.PagamentoFattura;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerVisualizzaOrdini {
    private PersistenzaPagamentoFattura pPF;
    private PersistenzaPagamentoCartaCredito pCC;
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();



    public String getEmail()
    {
        return User.getInstance().getEmail();
    }

    public ObservableList<PagamentoFattura> getListaFattura(String persistenza)  {

        PagamentoFattura pf=new PagamentoFattura();
        pf.setEmail(getEmail());

        switch (persistenza)
        {
            case DATABASE->pPF=new ContrassegnoDao();
            case FILE->pPF=new CsvFattura();
            case MEMORIA->pPF=new MemoriaFattura();
            default -> Logger.getLogger("getLista pagamenti").log(Level.SEVERE,"list pf payment cash is empty!!");
        }

        pPF.inizializza();


        return pPF.listPagamentiByUserF(pf);
    }

    public ObservableList<PagamentoCartaCredito> getListaCC(String persistenza)  {
        switch (persistenza)
        {
            case DATABASE->pCC=new PagamentoCartaCreditoDao();
            case FILE->pCC=new CsvPagamentoCartaCredito();
            case MEMORIA->pCC=new MemoriaPagamentoCartaCredito();

            default -> Logger.getLogger("getLista pagamenti cc").log(Level.SEVERE,"list pf payment with cc is empty!!");
        }
        PagamentoCartaCredito pCC1=new PagamentoCartaCredito();
        pCC1.setEmail(getEmail());
        return pCC.listaPagamentiUserByCC(pCC1);
    }


    public boolean cancellaPagamento(int id,String persistenza)  {

        boolean status;
        switch (persistenza)
        {
            case DATABASE->
                    {
                        pPF=new ContrassegnoDao();
                        pCC=new PagamentoCartaCreditoDao();
                    }
            case FILE->
                    {
                        pPF=new CsvFattura();
                        pCC=new CsvPagamentoCartaCredito();
                    }
            case MEMORIA->
                    {
                        pPF=new MemoriaFattura();
                        pCC=new MemoriaPagamentoCartaCredito();
                    }
            default -> Logger.getLogger("cancella pagamento").log(Level.SEVERE,"delete payment has not occurred");
        }
        pPF.inizializza();
        pCC.inizializza();

        if(vis.getMetodoP().equals("cash"))
        {
            PagamentoFattura pF=new PagamentoFattura();
            pF.setIdFattura(id);
           status=pPF.cancellaPagamentoFattura(pF);

        }
        else{
            PagamentoCartaCredito pagCC=new PagamentoCartaCredito();
            pagCC.setIdPagCC(id);

            status=pCC.cancellaPagamentoCartaCredito(pagCC);

        }
        return status;
    }




}
