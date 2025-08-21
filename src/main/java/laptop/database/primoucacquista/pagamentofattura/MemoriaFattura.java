package laptop.database.primoucacquista.pagamentofattura;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.PagamentoTotalePersistenza;
import laptop.pagamento.PagamentoFattura;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaFattura extends PersistenzaPagamentoFattura{

    private static final String SERIALIZZAZIONE="memory/serializzazionePagamentoFattura.ser";
    private ArrayList<PagamentoFattura> list=new ArrayList<>();
    private  PagamentoTotalePersistenza pT;
    private static final String MEMORIA="memoria";

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoFattura(PagamentoFattura f)  {




        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<PagamentoFattura>) ois.readObject();

        }catch (IOException |ClassNotFoundException e)
        {
           Logger.getLogger("pagaemnto fattura").log(Level.SEVERE," error with file payment .",e);
        }
        f.setIdFattura(list.size()+1);
        list.add(f);


        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }catch (IOException e1){
            Logger.getLogger("inserisciPagamentofattura").log(Level.SEVERE,"error with write exception :",e1);
        }



        Logger.getLogger("insert fattura in memory").log(Level.INFO, "fattura is wrote {0}",list);


        return pT.inserisciPagamentoTotaleFattura(f,MEMORIA);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaPagamentoFattura(PagamentoFattura f) {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoFattura>) ois.readObject();
        }catch (IOException e){
            Logger.getLogger("cancellaPagamentoFattura").log(Level.SEVERE,"delPaymentF io exception :",e);
        }catch (ClassNotFoundException e1){
            Logger.getLogger("cancellaPAgamentoFattura csv").log(Level.SEVERE,"delPaymentF csv exception :",e1);

        }

        for (int i = 0; i <list.size(); i++) {
            if (i == f.getIdFattura()-1) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id fattura :.",f.getIdFattura());

                status = list.remove(list.get(i));

            }
        }
        //cancello file e lo ricreo
        Path path=Path.of(SERIALIZZAZIONE);
        try{
            Files.delete(path);
            if(!Files.exists(path)) throw new IOException("file "+SERIALIZZAZIONE+" cancellato");
        }catch (IOException e)
        {
            try {
                Files.createFile(path);
            } catch (IOException ex) {
                Logger.getLogger("creazione Fattura file").log(Level.SEVERE,"file not created :",ex);
            }
            try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
                ObjectOutputStream oos=new ObjectOutputStream(fos)){
                oos.writeObject(list);
            }catch (IOException e1)
            {
             Logger.getLogger("scrittura").log(Level.SEVERE,"error with write :",e1);
            }
        }
        return status&&pT.cancellaFatturaMem(f);
    }

    @Override
    public void inizializza()  {
        Path path = Path.of(SERIALIZZAZIONE);
        try
       {
           if(!Files.exists(path)) throw new IOException(" file serializzazionePagamentoFattura.ser not exists");

           Files.createFile(path);

           Logger.getLogger("inizializza memoria fattura").log(Level.INFO," file has been created {0}",SERIALIZZAZIONE);
       }catch (IOException ex) {
            Logger.getLogger("inizializza").log(Level.SEVERE,"error with file :",ex);
        }
        pT=new PagamentoTotalePersistenza(MEMORIA);
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagamentoFattura ultimaFattura()  {
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoFattura>) ois.readObject();
        }catch (IOException |ClassNotFoundException e)
        {
            Logger.getLogger("ultimaFattura").log(Level.SEVERE,"error withh last fattura :",e);
        }
        return list.get(list.size() - 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<PagamentoFattura> listPagamentiByUserF(PagamentoFattura pF)  {


        ObservableList<PagamentoFattura> listFatture= FXCollections.observableArrayList();

        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoFattura>) ois.readObject();
        }catch (IOException |ClassNotFoundException  e)
        {
            Logger.getLogger("listPagemntoFatturaUser").log(Level.SEVERE,"file is empty");
        }

       Logger.getLogger("list pagamenti by user ").log(Level.INFO,"list payment by user : {0}",list);


        for (int i = 1; i <= list.size(); i++) {
            if (list.get(i-1).getEmail().equals(pF.getEmail())) {
                PagamentoFattura pf=list.get(i-1);
                listFatture.add(pf);

            }

        }



        return listFatture;
    }
}
