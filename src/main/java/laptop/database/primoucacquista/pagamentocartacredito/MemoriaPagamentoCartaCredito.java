package laptop.database.primoucacquista.pagamentocartacredito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.pagamento.PagamentoCartaCredito;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaPagamentoCartaCredito extends PersistenzaPagamentoCartaCredito{
    private  ArrayList<PagamentoCartaCredito> list= new ArrayList<>();
    private static final String SERIALIZZAZIONE="memory/serializzazionePagamentoCartaCredito.ser";
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
                Logger.getLogger("inizializzaPagamentoCC memoria").log(Level.SEVERE,"exception {0}",e);
            }
            Logger.getLogger("inizializza memoria pagamentoCC").log(Level.INFO," file has been created");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p)  {
        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();

        }catch (IOException e){
            Logger.getLogger("inserisciPagamento").log(Level.SEVERE,"insPag io exception {0}",e);
        }catch (ClassNotFoundException e1){
            Logger.getLogger("inserisciPagaemnto csv").log(Level.SEVERE,"insPag csv exception {0}",e1);

        }
        p.setIdPagCC(list.size()+1);
        list.add(p);


        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }catch (IOException e2)
        {
            Logger.getLogger("scrittura pagamento").log(Level.SEVERE,"payment error {0}",e2);
        }



        Logger.getLogger("insert payment in memory").log(Level.INFO, "payment is wrote");

        return true;
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
            Logger.getLogger("cancella pagamento io").log(Level.SEVERE,"del payment io error {0}",e);
        }catch (ClassNotFoundException e1)
        {
            Logger.getLogger("cancella pagamento csv").log(Level.SEVERE,"del payment csv error {0}",e1);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == p.getIdPagCC()-1) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id payment {0}.",p.getIdPagCC());

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
               Logger.getLogger("create file").log(Level.SEVERE,"error with creation {0}",e3);
            }
            try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
                ObjectOutputStream oos=new ObjectOutputStream(fos)){
                oos.writeObject(list);
            }catch (IOException e4)
            {
                Logger.getLogger("scrittura su nuovo file").log(Level.SEVERE,"writing error {0}",e4);
            }
        }
        return status;
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagamentoCartaCredito ultimoPagamentoCartaCredito() {
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoCartaCredito>) ois.readObject();
        }catch (IOException e){
            Logger.getLogger("ultimoPagamentoCC").log(Level.SEVERE,"lastPaymentCC io exception {0}",e);
        }catch (ClassNotFoundException e1){
            Logger.getLogger("ultimoPagamentoCC csv").log(Level.SEVERE,"lastPaymentCC csv exception {0}",e1);

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
