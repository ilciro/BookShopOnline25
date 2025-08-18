package laptop.database.primoucacquista.pagamentototale;

import laptop.database.primoucacquista.pagamentocartacredito.MemoriaPagamentoCartaCredito;
import laptop.database.primoucacquista.pagamentofattura.MemoriaFattura;
import laptop.pagamento.Pagamento;
import laptop.pagamento.PagamentoCartaCredito;
import laptop.pagamento.PagamentoFattura;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagamentoTotaleMemoria extends PersistenzaPagamentoTotale {
    private static final String SERIALIZZAZIONE="memory/serializzazionePagamentoTotale.ser";
    private ArrayList<Pagamento> list=new ArrayList<>();
    @Override
    public void inizializza() {
        Path path = Path.of(SERIALIZZAZIONE);
        try
        {
            if(!Files.exists(path)) throw new IOException(" file not exists");
        }catch (IOException e)
        {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                Logger.getLogger("inizializza pagTotaleMem").log(Level.SEVERE,"error with file total payment :",e);
            }
            Logger.getLogger("inizializza memoria fattura").log(Level.INFO," file has been created");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoFattura(PagamentoFattura p)  {
        MemoriaFattura mF = new MemoriaFattura();


        PagamentoFattura p1=mF.ultimaFattura();
        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<Pagamento>) ois.readObject();

        }catch (IOException |ClassNotFoundException e)
        {
           Logger.getLogger("lista è vuota").log(Level.SEVERE," list is empty :{0}",list.size());
        }


        list.add(p1);
        Logger.getLogger("lista dopo insert").log(Level.INFO," list  after insert : {0} :",list);



        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }catch (IOException e)
        {
            Logger.getLogger("inserisciPagamentoFattura totale csv").log(Level.SEVERE,"error with list :",e);
        }

        return true;

    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoCartaCredito(PagamentoCartaCredito p )  {
        MemoriaPagamentoCartaCredito pCC=new MemoriaPagamentoCartaCredito();

        PagamentoCartaCredito p1=pCC.ultimoPagamentoCartaCredito();
        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<Pagamento>) ois.readObject();

        }catch (IOException |ClassNotFoundException e)
        {
            Logger.getLogger("inserisci pagamento cc").log(Level.SEVERE," excepion :.",e);
        }

        list.add(p1);


        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }catch (IOException e)
        {
            Logger.getLogger("inserisciPagCC").log(Level.SEVERE,"error witd cc list :",e);
        }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaFattura(PagamentoFattura p)  {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<Pagamento>) ois.readObject();
        }catch (IOException |ClassNotFoundException e)
        {
            Logger.getLogger("cancella fattura csv da totale").log(Level.SEVERE,"delete f from total :",e);
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == p.getIdFattura()-1) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id payment :.",p.getIdFattura());

                status = list.remove(list.get(i));
            }
        }
        cancellaCreaFile();

        return status;
    }

    private void cancellaCreaFile() {
        Path path=Path.of(SERIALIZZAZIONE);
        try{
            Files.delete(path);
            if(!Files.exists(path)) throw new IOException("file "+SERIALIZZAZIONE+" cancellato");
        }catch (IOException e)
        {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                Logger.getLogger("cancelloCreo file pagamentoTotale").log(Level.SEVERE,"error with creation pagamentoTotale :",ex);
            }
            try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
                ObjectOutputStream oos=new ObjectOutputStream(fos)){
                oos.writeObject(list);
            }catch (IOException e2)
            {
             Logger.getLogger("scrittura doppo canc pagamentoTotale").log(Level.SEVERE,"error with list after del pagamentoTotale :",e2);
            }
        }
    }
    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaPagamentoCC(PagamentoCartaCredito pCC) {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<Pagamento>) ois.readObject();
        }catch (IOException e){
            Logger.getLogger("cancellaPagamentoCC").log(Level.SEVERE,"cancPagCC from total io exception :",e);
        }catch (ClassNotFoundException e1){
            Logger.getLogger("cancellaPAgamentoCC csv").log(Level.SEVERE,"cancPagCC from total csv exception :",e1);

        }
        for (int i = 1; i <= list.size(); i++) {
            if (i == pCC.getIdPagCC()) {
                Logger.getLogger("cancella pagaemnto cc").log(Level.INFO,"id payment :.",pCC.getIdPagCC());

                status = list.remove(list.get(i - 1));
                break;
            }
        }
        return status;
    }


}
