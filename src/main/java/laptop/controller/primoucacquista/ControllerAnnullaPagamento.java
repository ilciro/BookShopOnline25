package laptop.controller.primoucacquista;


import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.primoucacquista.pagamentoCartacredito.CsvPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentoCartacredito.MemoriaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentoCartacredito.PagamentoCartaCreditoDao;
import laptop.database.primoucacquista.pagamentoCartacredito.PersistenzaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentoFattura.ContrassegnoDao;
import laptop.database.primoucacquista.pagamentoFattura.CsvFattura;
import laptop.database.primoucacquista.pagamentoFattura.MemoriaFattura;
import laptop.database.primoucacquista.pagamentoFattura.PersistenzaPagamentoFattura;
import laptop.database.primoucacquista.pagamentoTotale.PagamentoTotale;
import laptop.database.primoucacquista.pagamentoTotale.PagamentoTotaleCsv;
import laptop.database.primoucacquista.pagamentoTotale.PagamentoTotaleDao;
import laptop.database.primoucacquista.pagamentoTotale.PagamentoTotaleMemoria;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAnnullaPagamento  {

    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";

    private PersistenzaPagamentoFattura pPF;
    private  PagamentoFattura pF;
    private PersistenzaPagamentoCartaCredito pPCC;
    private PagamentoCartaCredito pCC;
    private PagamentoTotale pT;





    public ObservableList<PagamentoFattura> getFattura(String persistenza) throws CsvValidationException, IOException, ClassNotFoundException {
        ObservableList<PagamentoFattura> list= FXCollections.observableArrayList();
        switch (persistenza)
        {
            case DATABASE -> pPF=new ContrassegnoDao();
            case FILE -> pPF=new CsvFattura();
            case MEMORIA -> pPF=new MemoriaFattura();
            default -> Logger.getLogger("get fattura ").log(Level.SEVERE,"error with persistency");

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
        pF=new PagamentoFattura();
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
            default -> Logger.getLogger("get carta credito ").log(Level.SEVERE,"error with persistency");

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
            default -> Logger.getLogger("cancella pagamento cc").log(Level.SEVERE, "error with persistency");
        }
        pCC=new PagamentoCartaCredito();
        pCC.setIdPagCC(idPagamentoCC);
        return pPCC.cancellaPagamentoCartaCredito(pCC) && pT.cancellaPagamentoCC(pCC);
    }





}


