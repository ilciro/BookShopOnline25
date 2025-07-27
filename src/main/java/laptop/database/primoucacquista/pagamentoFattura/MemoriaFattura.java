package laptop.database.primoucacquista.pagamentoFattura;

import com.opencsv.exceptions.CsvValidationException;
import laptop.model.pagamento.PagamentoFattura;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaFattura extends PersistenzaPagamentoFattura{

    private static final String SERIALIZZAZIONE="memory/serializzazionePagamentoFattura.ser";
    private ArrayList<PagamentoFattura> list=new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamentoFattura(PagamentoFattura f) throws IOException, ClassNotFoundException {



        try (FileInputStream fin = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fin)) {

            list = (ArrayList<PagamentoFattura>) ois.readObject();

        }catch (EOFException e)
        {
            System.out.println("eccezione :"+e.getMessage());
        }
        f.setIdFattura(list.size()+1);
        list.add(f);


        try (FileOutputStream fout = new FileOutputStream(SERIALIZZAZIONE);
             ObjectOutputStream oos = new ObjectOutputStream(fout)) {
            oos.writeObject(list);
        }



        Logger.getLogger("insert fattura in memory").log(Level.INFO, "fattura is wrote");

        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaPagamentoFattura(PagamentoFattura f) throws  IOException, ClassNotFoundException {
        boolean status = false;
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoFattura>) ois.readObject();
        }
        for (int i = 1; i <= list.size(); i++) {
            if (i == f.getIdFattura()) {
                Logger.getLogger("cancella fattura").log(Level.INFO,"id fattura {0}.",f.getIdFattura());

                status = list.remove(list.get(i - 1));
                break;
            }
        }
        return status;
    }

    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        Path path = Path.of(SERIALIZZAZIONE);
        try
       {
           if(!Files.exists(path)) throw new IOException(" file not exists");
       }catch (IOException e)
       {
           Files.createFile(path);
           Logger.getLogger("inizializza memoria fattura").log(Level.INFO," file has been created");
       }
    }

    @Override
    @SuppressWarnings("unchecked")
    public PagamentoFattura ultimaFattura() throws IOException,  ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(SERIALIZZAZIONE);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            list = (ArrayList<PagamentoFattura>) ois.readObject();


        }
        return list.get(list.size() - 1);
    }
}
