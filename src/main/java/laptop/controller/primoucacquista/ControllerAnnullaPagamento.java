package laptop.controller.primoucacquista;


import com.opencsv.exceptions.CsvValidationException;
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
import laptop.database.primoucacquista.pagamentototale.PagamentoTotale;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleCsv;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleDao;
import laptop.database.primoucacquista.pagamentototale.PagamentoTotaleMemoria;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAnnullaPagamento  {

    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";
    private static final String ERROR="error with persistency";

    private PersistenzaPagamentoFattura pPF;
    private PersistenzaPagamentoCartaCredito pPCC;
    private PagamentoTotale pT;





    public ObservableList<PagamentoFattura> getFattura(String persistenza) throws CsvValidationException, IOException, ClassNotFoundException {
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

    public boolean cancellaFattura(int idFattura,String persistency) throws IOException, CsvValidationException, ClassNotFoundException {
        switch (persistency) {
            case DATABASE ->
                    {
                        pPF=new ContrassegnoDao();
                        pT=new PagamentoTotaleDao();
                    }
            case FILE ->
                    {
                        pPF=new CsvFattura();
                        pT=new PagamentoTotaleCsv();
                    }
            case MEMORIA ->
                    {
                        pPF=new MemoriaFattura();
                        pT=new PagamentoTotaleMemoria();
                    }
            default -> Logger.getLogger("persistenza errata").log(Level.SEVERE, " persistency is wrong or null!!");
        }
        PagamentoFattura pF = new PagamentoFattura();
        pF.setIdFattura(idFattura);
        return pPF.cancellaPagamentoFattura(pF) && pT.cancellaFattura(pF);
    }

    public ObservableList<PagamentoCartaCredito> getPagamentoCartaCredito(String persistenza) throws IOException, CsvValidationException, ClassNotFoundException {
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

    public boolean cancellaPagamentoCC(int idPagamentoCC,String persistency) throws IOException, CsvValidationException, ClassNotFoundException {
        switch (persistency) {
            case DATABASE ->
            {
                pPCC=new PagamentoCartaCreditoDao();
                pT=new PagamentoTotaleDao();
            }
            case FILE ->
            {
                pPCC=new CsvPagamentoCartaCredito();
                pT=new PagamentoTotaleCsv();
            }
            case MEMORIA ->
            {
                pPCC=new MemoriaPagamentoCartaCredito();
                pT=new PagamentoTotaleMemoria();
            }
            default -> Logger.getLogger("cancella pagamento cc").log(Level.SEVERE, ERROR);
        }
        PagamentoCartaCredito pCC = new PagamentoCartaCredito();
        pCC.setIdPagCC(idPagamentoCC);
        return pPCC.cancellaPagamentoCartaCredito(pCC) && pT.cancellaPagamentoCC(pCC);
    }





}


