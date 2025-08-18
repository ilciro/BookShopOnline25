package laptop.controller.primoucacquista;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.primoucacquista.pagamentocartacredito.CsvPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentocartacredito.MemoriaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentocartacredito.PagamentoCartaCreditoDao;
import laptop.database.primoucacquista.pagamentocartacredito.PersistenzaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentofattura.ContrassegnoDao;
import laptop.database.primoucacquista.pagamentofattura.CsvFattura;
import laptop.database.primoucacquista.pagamentofattura.MemoriaFattura;
import laptop.database.primoucacquista.pagamentofattura.PersistenzaPagamentoFattura;

import laptop.pagamento.PagamentoCartaCredito;
import laptop.pagamento.PagamentoFattura;


import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAnnullaPagamento  {

    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";
    private static final String ERROR="error with persistency";

    private PersistenzaPagamentoFattura pPF;
    private PersistenzaPagamentoCartaCredito pPCC;





    public ObservableList<PagamentoFattura> getFattura(String persistenza) {
        ObservableList<PagamentoFattura> list= FXCollections.observableArrayList();
        switch (persistenza)
        {
            case DATABASE -> pPF=new ContrassegnoDao();
            case FILE -> pPF=new CsvFattura();
            case MEMORIA -> pPF=new MemoriaFattura();
            default -> Logger.getLogger("get fattura ").log(Level.SEVERE,ERROR);

        }
        list.add(pPF.ultimaFattura());
        return list;

    }


    public boolean cancellaFattura(int idFattura,String persistency)  {
        boolean status;
        switch (persistency) {
            case DATABASE ->

                        pPF=new ContrassegnoDao();

            case FILE ->

                        pPF=new CsvFattura();

            case MEMORIA ->

                        pPF=new MemoriaFattura();

            default -> Logger.getLogger("persistenza errata").log(Level.SEVERE, " persistency is wrong or null!!");
        }
        PagamentoFattura pF = new PagamentoFattura();
        pF.setIdFattura(idFattura);


        status=pPF.cancellaPagamentoFattura(pF) ;
        return status;
    }

    public ObservableList<PagamentoCartaCredito> getPagamentoCartaCredito(String persistenza)  {
        ObservableList<PagamentoCartaCredito> list= FXCollections.observableArrayList();
        switch (persistenza)
        {
            case DATABASE -> pPCC=new PagamentoCartaCreditoDao();
            case FILE -> pPCC=new CsvPagamentoCartaCredito();
            case MEMORIA -> pPCC=new MemoriaPagamentoCartaCredito();
            default -> Logger.getLogger("get carta credito ").log(Level.SEVERE,ERROR);

        }
        list.add(pPCC.ultimoPagamentoCartaCredito());
        return list;
    }

    public boolean cancellaPagamentoCC(int idPagamentoCC,String persistency)  {
        boolean status;
        switch (persistency) {
            case DATABASE ->  pPCC=new PagamentoCartaCreditoDao();

            case FILE -> pPCC=new CsvPagamentoCartaCredito();

            case MEMORIA -> pPCC=new MemoriaPagamentoCartaCredito();

            default -> Logger.getLogger("cancella pagamento cc").log(Level.SEVERE, ERROR);
        }
        PagamentoCartaCredito pCC = new PagamentoCartaCredito();
        pCC.setIdPagCC(idPagamentoCC);

            status=pPCC.cancellaPagamentoCartaCredito(pCC);
        return status;
    }







}


