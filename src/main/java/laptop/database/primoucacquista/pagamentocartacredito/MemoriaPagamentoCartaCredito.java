package laptop.database.primoucacquista.pagamentocartacredito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PagamentoTotalePersistenza;
import laptop.pagamento.PagamentoCartaCredito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaPagamentoCartaCredito extends PersistenzaPagamentoCartaCredito{
    private  ArrayList<PagamentoCartaCredito> list= new ArrayList<>();
    private static final String SERIALIZZAZIONE="memory/serializzazionePagamentoCartaCredito.ser";
    private  PagamentoTotalePersistenza pT;
    @Override
    public void inizializza() {
        Path path = Path.of(SERIALIZZAZIONE);
        try
        {
            if(!Files.exists(path)) throw new IOException(" file "+ SERIALIZZAZIONE +" not exists");
        }catch (IOException e)
        {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                Logger.getLogger("inizializzaPagamentoCC memoria").log(Level.SEVERE,"exception :",e);
            }
            Logger.getLogger("inizializza memoria pagamentoCC").log(Level.INFO," file has been created");
        }
        pT=new PagamentoTotalePersistenza("memoria");
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p)  {
        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();

        }catch (IOException |ClassNotFoundException e){
            Logger.getLogger("inserisciPagamento").log(Level.SEVERE,"insPag io exception :",e);
        }
        p.setIdPagCC(list.size()+1);
        list.add(p);


        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }catch (IOException e2)
        {
            Logger.getLogger("scrittura pagamento").log(Level.SEVERE,"payment error :",e2);
        }



        Logger.getLogger("insert payment in memory").log(Level.INFO, "payment is wrote {0}",list);



        return pT.inserisciPagamentoTotaleCC(p,"memoria");
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaPagamentoCartaCredito(PagamentoCartaCredito p)  {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();
        }catch (IOException e)
        {
            Logger.getLogger("cancella pagamento io").log(Level.SEVERE,"del payment io error :",e);
        }catch (ClassNotFoundException e1)
        {
            Logger.getLogger("cancella pagamento csv").log(Level.SEVERE,"del payment csv error :",e1);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == p.getIdPagCC()-1) {
                Logger.getLogger("cancella pagamento").log(Level.INFO,"id payment : {0}",p.getIdPagCC());

                status = list.remove(list.get(i));

            }
        }
        Path path=Path.of(SERIALIZZAZIONE);
        try{
            Files.delete(path);
            if(!Files.exists(path)) throw new IOException("file "+SERIALIZZAZIONE+" cancellato");
        }catch (IOException e2)
        {
            try {
                Files.createFile(path);
            } catch (IOException e3) {
               Logger.getLogger("create file").log(Level.SEVERE,"error with creation :",e3);
            }
            try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
                ObjectOutputStream oos=new ObjectOutputStream(fos)){
                oos.writeObject(list);
            }catch (IOException e4)
            {
                Logger.getLogger("scrittura su nuovo file").log(Level.SEVERE,"writing error :",e4);
            }
        }
        pT=new PagamentoTotalePersistenza("memoria");
        return status && pT.cancellaPagCCMem(p);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagamentoCartaCredito ultimoPagamentoCartaCredito() {
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();
        }catch (IOException e){
            Logger.getLogger("ultimoPagamentoCC").log(Level.SEVERE,"lastPaymentCC io exception :",e);
        }catch (ClassNotFoundException e1){
            Logger.getLogger("ultimoPagamentoCC csv").log(Level.SEVERE,"lastPaymentCC csv exception :",e1);

        }
        return list.get(list.size() - 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<PagamentoCartaCredito> listaPagamentiUserByCC(PagamentoCartaCredito pcc)  {
        ObservableList<PagamentoCartaCredito> listCC= FXCollections.observableArrayList();
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();
        }catch (IOException | ClassNotFoundException e)
        {
            Logger.getLogger("listPagamentoCCUser").log(Level.SEVERE,"file is empty");
        }
        for (int i = 1; i <= list.size(); i++) {
            if (list.get(i-1).getEmail().equals(pcc.getEmail())) {
                PagamentoCartaCredito pCC=list.get(i-1);
                listCC.add(pCC);

            }
        }
        return listCC;

    }
}
