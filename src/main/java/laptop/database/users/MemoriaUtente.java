package laptop.database.users;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;

import laptop.model.user.TempUser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MemoriaUtente extends PersistenzaUtente{
    private  ArrayList<TempUser> listaTU= new ArrayList<>();

    private static final String SERIALIZZAZIONE="memory/serializzazioneUtente.ser";
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    @Override
    @SuppressWarnings("unchecked")
    public boolean inserisciUtente(TempUser u) throws IOException, ClassNotFoundException {


        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            listaTU= (ArrayList<TempUser>) ois.readObject();
        }
        if(vis.getTipoModifica().equals("im")) u.setId(u.getId());
        else if(vis.getTipoModifica().equals("insert")) u.setId(listaTU.size()+1);
        listaTU.add(u);

        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos))
        {
            oos.writeObject(new ArrayList<>(listaTU));
        }

        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObservableList<TempUser> getUserData() throws IOException {
        List<TempUser> listaRecuperata = new ArrayList<>();
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            listaRecuperata= (List<TempUser>) ois.readObject();
        }  catch (ClassNotFoundException e) {
            Logger.getLogger("lista utenti").log(Level.SEVERE,"exception in list users",e);
        }
        return FXCollections.observableArrayList(listaRecuperata);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean removeUserByIdEmailPwd(TempUser u) throws  IOException {
        boolean status=false;

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            listaTU=(ArrayList<TempUser>) ois.readObject();

        }  catch (ClassNotFoundException e) {
            Logger.getLogger("lista libri").log(Level.SEVERE,"exception .",e);
        }

        for(int i=0;i<listaTU.size();i++)
        {
            if (i==(u.getId()-1) || listaTU.get(i).getEmailT().equals(u.getEmailT())) {
                status = listaTU.remove(listaTU.get(i));
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
                oos.writeObject(listaTU);
            }
        }


        return status;

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TempUser> userList(TempUser u) throws CsvValidationException, IOException {
        List<TempUser> listaRec=FXCollections.emptyObservableList();
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            listaRec=(List<TempUser>) ois.readObject();

        }  catch (ClassNotFoundException e) {
            Logger.getLogger("lista libri").log(Level.SEVERE,"exception .",e);
        }

        return listaRec;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initializza() throws IOException, CsvValidationException, IdException, ClassNotFoundException {

        for(int i=1;i<=7;i++)
        {
            String line;


            ArrayList<String> listaR = new ArrayList<>();


            try (FileReader fileReader = new FileReader("src/main/resources/tmpFiles/utente" + i + ".txt");
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while ((line = bufferedReader.readLine()) != null) {
                    listaR.add(line);

                }
            }
            TempUser tu=new TempUser();
            tu.setId(Integer.parseInt(listaR.get(0)));
            tu.setIdRuoloT(listaR.get(1));
            tu.setNomeT(listaR.get(2));
            tu.setCognomeT(listaR.get(3));
            tu.setEmailT(listaR.get(4));
            tu.setPasswordT(listaR.get(5));
            tu.setDescrizioneT(listaR.get(6));
            tu.setDataDiNascitaT(LocalDate.parse(listaR.get(7)));
            listaTU.add(tu);

        }
        try(FileOutputStream fos=new FileOutputStream(SERIALIZZAZIONE);
            ObjectOutputStream oos=new ObjectOutputStream(fos)){
            oos.writeObject(listaTU);
        }

        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            listaTU= (ArrayList<TempUser>) ois.readObject();
        }


    }

}
