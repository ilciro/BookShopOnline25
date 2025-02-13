package laptop.database.pagamento;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.model.Pagamento;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaPagamento extends PersistenzaPagamento{
    private  ArrayList<Pagamento> lista= new ArrayList<>();
    private static final String SERIALIZZAZIONE="memory/serializzazionePagamento.ser";
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();


    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciPagamento(Pagamento p) throws  IOException, ClassNotFoundException {


        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            lista = (ArrayList<Pagamento>) ois.readObject();

        }
        if(vis.getTipoModifica().equals("im")) p.setIdPag(p.getIdPag());
        else if(vis.getTipoModifica().equals("insert")) p.setIdPag(lista.size()+1);

        lista.add(p);

        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos))
        {
            oos.writeObject(lista);
        }
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Pagamento ultimoPagamento() throws IOException, ClassNotFoundException {


        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            lista= (ArrayList<Pagamento>) ois.readObject();

        }

        return lista.get(lista.size()-1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean cancellaPagamento(Pagamento p) throws IOException, ClassNotFoundException {
       boolean status=false;


        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            lista= (ArrayList<Pagamento>) ois.readObject();

        }
        for(int i=0;i<lista.size();i++)
        {


            if(i-1==p.getIdPag()) {

                Logger.getLogger("cancella pagamento").log(Level.INFO,"id fattura {0}.",p.getIdPag());

                status = lista.remove(lista.get(i-1));
                break;
            }


        }
        Path path = Path.of(SERIALIZZAZIONE);
        try {
            Files.delete(path);
            if(!Files.exists(path))
            {
                throw new IOException("file is deleted!!");

            }

        }catch (IOException e)
        {
            Files.createFile(path);
            try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
                ObjectOutputStream oos=new ObjectOutputStream(fos)){
                oos.writeObject(lista);
            }
        }

        return status;

    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Pagamento> listPagamentiByUser(Pagamento p) throws  IOException, ClassNotFoundException {
        ObservableList<Pagamento> listaRecuperata = FXCollections.observableArrayList();
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            lista= (ArrayList<Pagamento>) ois.readObject();
        }


        for (Pagamento pagamento : lista) {


            if (pagamento.getEmail().equals(p.getEmail()))
                listaRecuperata.add(pagamento);


        }


            return listaRecuperata;

    }

    @Override
    @SuppressWarnings("unchecked")
    public void inizializza() throws IOException, ClassNotFoundException {
        String line;


        ArrayList<String> listaR = new ArrayList<>();


        try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/pagamento1.txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                listaR.add(line);

            }
        }
        Pagamento p=new Pagamento();
        p.setIdPag(Integer.parseInt(listaR.get(0)));
        p.setMetodo(listaR.get(1));
        p.setNomeUtente(listaR.get(2));
        p.setAmmontare(Float.parseFloat(listaR.get(3)));
        p.setEmail(listaR.get(4));
        p.setTipo(listaR.get(5));
        p.setIdOggetto(Integer.parseInt(listaR.get(6)));

        lista.add(p);

        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(lista);
    }

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
        ObjectInputStream ois=new ObjectInputStream(fis)) {
            lista = (ArrayList<Pagamento>) ois.readObject();
        }
    }
}
