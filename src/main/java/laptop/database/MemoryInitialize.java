package laptop.database;

import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MemoryInitialize {
    //class used for reduce duplication

   private static final String SERIALIZZAZIONEGIORNALE="memory/serializzazioneGiornale.ser";
    private static final String SERIALIZZAZIONELIBRO="memory/serializzazioneLibro.ser";
    private static final String SERIALIZZAZIONERIVISTA="memory/serializzazioneRivista.ser";


    @SuppressWarnings("unchecked")
    public boolean cancellaGiornale(Giornale g) throws IOException, ClassNotFoundException {
        ArrayList<Giornale> list;

        boolean status=false;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONEGIORNALE);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Giornale>) ois.readObject();

        }


        for(int i=0;i<list.size();i++)
        {
            if(i== (g.getId()-1)) {
                status = list.remove(list.get(i));
            }
        }
        removeFile(SERIALIZZAZIONEGIORNALE,list,new ArrayList<>(),new ArrayList<>());

        return status;
    }

    @SuppressWarnings("unchecked")
    public boolean cancellaLibro(Libro l) throws IOException, ClassNotFoundException {
        ArrayList<Libro> list;

        boolean status=false;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONELIBRO);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Libro>) ois.readObject();

        }


        for(int i=0;i<list.size();i++)
        {
            if(i== (l.getId()-1)) {
                status = list.remove(list.get(i));
            }
        }
        removeFile(SERIALIZZAZIONELIBRO,new ArrayList<>(),list,new ArrayList<>());

        return status;
    }

    @SuppressWarnings("unchecked")
    public boolean cancellaRivista(Rivista r) throws IOException, ClassNotFoundException {
        ArrayList<Rivista> list;

        boolean status=false;
        try(FileInputStream fis=new FileInputStream(SERIALIZZAZIONERIVISTA);
            ObjectInputStream ois=new ObjectInputStream(fis)){
            list= (ArrayList<Rivista>) ois.readObject();

        }


        for(int i=0;i<list.size();i++)
        {
            if(i== (r.getId()-1)) {
                status = list.remove(list.get(i));
            }
        }
        removeFile(SERIALIZZAZIONERIVISTA,new ArrayList<>(),new ArrayList<>(),list);

        return status;
    }

    private void removeFile(String stringPath,ArrayList<Giornale> listG,ArrayList<Libro> listL,ArrayList<Rivista> listR) throws IOException {

        Path path = Path.of(stringPath);
        try {
            Files.delete(path);
            if(!Files.exists(path))
            {
                throw new IOException("file daily is deleted!!");
            }
        }catch (IOException e)
        {
            if(!listG.isEmpty())
            {
                Files.createFile(path);
                try(FileOutputStream fos=new FileOutputStream(stringPath);
                    ObjectOutputStream oos=new ObjectOutputStream(fos)){
                    oos.writeObject(listG);
                }
            }
            if(!listL.isEmpty())
            {
                Files.createFile(path);
                try(FileOutputStream fos=new FileOutputStream(stringPath);
                    ObjectOutputStream oos=new ObjectOutputStream(fos)){
                    oos.writeObject(listL);
                }
            }
            if(!listR.isEmpty())
            {
                Files.createFile(path);
                try(FileOutputStream fos=new FileOutputStream(stringPath);
                    ObjectOutputStream oos=new ObjectOutputStream(fos)){
                    oos.writeObject(listR);
                }
            }

        }
    }
}
