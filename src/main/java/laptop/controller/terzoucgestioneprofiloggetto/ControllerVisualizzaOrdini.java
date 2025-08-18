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
import laptop.database.primoucacquista.pagamentototale.PersistenzaPagamentoTotale;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleCsv;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleDao;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleMemoria;

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
    private PersistenzaPagamentoTotale pT;



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
                        pT=new PagamentoTotaleDao();
                        pPF=new ContrassegnoDao();
                        pCC=new PagamentoCartaCreditoDao();
                    }
            case FILE->
                    {
                        pT=new PagamentoTotaleCsv();
                        pPF=new CsvFattura();
                        pCC=new CsvPagamentoCartaCredito();
                    }
            case MEMORIA->
                    {
                        pT=new PagamentoTotaleMemoria();
                        pPF=new MemoriaFattura();
                        pCC=new MemoriaPagamentoCartaCredito();
                    }
            default -> Logger.getLogger("cancella pagamento").log(Level.SEVERE,"delete payment has not occurred");
        }
        pT.inizializza();
        pPF.inizializza();
        pCC.inizializza();

        if(vis.getMetodoP().equals("cash"))
        {
            PagamentoFattura pF=new PagamentoFattura();
            pF.setIdFattura(id);
            if(persistenza.equals(MEMORIA) || persistenza.equals(FILE))
                status=pT.cancellaFattura(pF)&& pPF.cancellaPagamentoFattura(pF);
            else status=pT.cancellaFattura(pF);
        }
        else{
            PagamentoCartaCredito pagCC=new PagamentoCartaCredito();
            pagCC.setIdPagCC(id);
            if(persistenza.equals(MEMORIA)|| persistenza.equals(FILE))
                status=pT.cancellaPagamentoCC(pagCC)&&pCC.cancellaPagamentoCartaCredito(pagCC);
            else status=pT.cancellaPagamentoCC(pagCC);
        }
        return status;
    }


}
