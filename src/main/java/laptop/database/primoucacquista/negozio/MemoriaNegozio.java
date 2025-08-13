package laptop.database.primoucacquista.negozio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Negozio;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MemoriaNegozio extends PersistenzaNegozio{
    private static final String SERIALIZZAZIONE="memory/serializzazioneNegozio.ser";
    private ArrayList<Negozio> lista=new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<Negozio> getNegozi()  {

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            lista= (ArrayList<Negozio>) ois.readObject();
           }catch (IOException e){
            Logger.getLogger("getNegozi").log(Level.SEVERE,"getNegozi io exception {0}",e);
        }catch (ClassNotFoundException e1){
            Logger.getLogger("getNegozi csv").log(Level.SEVERE,"getNegozi csv exception {0}",e1);

        }
        return FXCollections.observableArrayList(lista);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkOpen(Negozio shop){

        boolean status=false;

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            lista = (ArrayList<Negozio>) ois.readObject();
        }catch (IOException e){
            Logger.getLogger("checkOpen").log(Level.SEVERE,"checkOpen io exception {0}",e);
        }catch (ClassNotFoundException e1){
            Logger.getLogger("checkOpen csv").log(Level.SEVERE,"checkOpen csv exception {0}",e1);

        }

        for (Negozio negozio : lista) {
            if (negozio.getNome().equals(shop.getNome()))
                status = negozio.getIsOpen();
        }


        return status;



    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean checkRitiro(Negozio shop)  {
        boolean status=false;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            lista=(ArrayList<Negozio>) ois.readObject();

        }catch (IOException e){
            Logger.getLogger("checkRitiro").log(Level.SEVERE,"checkRitiro io exception {0}",e);
        }catch (ClassNotFoundException e1){
            Logger.getLogger("checkRitiro csv").log(Level.SEVERE,"checkRitiro csv exception {0}",e1);

        }
        for (Negozio negozio : lista) {
            if (negozio.getNome().equals(shop.getNome()))
                status = negozio.getIsValid();
        }
        return status;
    }

    @Override
    public void initializza() {
        for(int i=1;i<=4;i++) {
            lista.add(leggiNegozio(i));

        }
        inserisciNegozio();
    }

    private void inserisciNegozio() {
        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos))
        {
            oos.writeObject(new ArrayList<>(lista));
        }catch (IOException e)
        {
            Logger.getLogger("inserisciNegozio").log(Level.SEVERE,"insert negozio exception {0}",e);
        }

    }

    private Negozio leggiNegozio(int i) {
        String line;
        ArrayList<String> listaR = new ArrayList<>();
        try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/negozio"+ i +".txt");
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            while ((line = bufferedReader.readLine()) != null) {
                listaR.add(line);

            }
        }catch (IOException e)
        {
            Logger.getLogger("leggiNegozio").log(Level.SEVERE,"read shop exception {0}",e);
        }
            Negozio n=new Negozio();
            n.setId(Integer.parseInt(listaR.get(0)));
            n.setNome(listaR.get(1));
            n.setVia(listaR.get(2));
            n.setIsValid(listaR.get(3).equals(String.valueOf(1)));

             n.setIsOpen(listaR.get(4).equals(String.valueOf(1)));


            return n;
        }




}
