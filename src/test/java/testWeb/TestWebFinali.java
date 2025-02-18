package testWeb;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.libro.LibroDao;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.negozio.NegozioDao;
import laptop.database.negozio.PersistenzaNegozio;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.Negozio;
import laptop.model.raccolta.CategorieLibro;
import laptop.model.raccolta.CategorieRivista;
import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import web.bean.LibroBean;
import web.bean.NegozioBean;
import web.bean.RivistaBean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestWebFinali {

     private static final PersistenzaLibro pL=new LibroDao();
     private static final LibroBean lB=new LibroBean();
     private static final PersistenzaRivista pR=new RivistaDao();
     private static final RivistaBean rB=new RivistaBean();
     private static final NegozioBean nB=new NegozioBean();
     private static final PersistenzaNegozio pN=new NegozioDao();


     @Test
     void testLibro() throws CsvValidationException, IOException, IdException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
          Libro l=pL.getLibri().get(5);

         PropertyUtils.setProperty(lB,"titoloB",l.getTitolo());
          PropertyUtils.setProperty(lB,"codIsbnB",l.getCodIsbn());
          PropertyUtils.setProperty(lB,"autoreB",l.getAutore());
          PropertyUtils.setProperty(lB,"editoreB",l.getEditore());
          PropertyUtils.setProperty(lB,"linguaB",l.getLingua());
          PropertyUtils.setProperty(lB,"listaLibriB",pL.retrieveRaccoltaData());

          for(CategorieLibro cat: CategorieLibro.values())
               PropertyUtils.setProperty(lB,"categoriaB",cat.toString());
          assertNotNull(l);
     }

     @Test
     void testRivista() throws CsvValidationException, IOException, IdException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
          Rivista r=pR.getRiviste().get(2);
          PropertyUtils.setProperty(rB,"titoloB",r.getTitolo());
          PropertyUtils.setProperty(rB,"autoreB",r.getAutore());
          PropertyUtils.setProperty(rB,"linguaB",r.getLingua());
          PropertyUtils.setProperty(rB,"dataPubbB",r.getDataPubb());



          PropertyUtils.setProperty(rB,"listaRivisteB",pR.getRiviste());

          for(CategorieRivista cat:CategorieRivista.values())
               PropertyUtils.setProperty(rB,"tipologiaB",cat.toString());
          assertNotNull(r);
     }
     @Test
     void testNegozio() throws CsvValidationException, IOException, IdException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
          Negozio n=pN.getNegozi().get(1);
          PropertyUtils.setProperty(nB,"nomeB",n.getNome());
          PropertyUtils.setProperty(nB,"indirizzoB",n.getVia());
          PropertyUtils.setProperty(nB,"aperturaB",n.getIsOpen());
          PropertyUtils.setProperty(nB,"disponibileB",n.getIsValid());
          boolean status=(Boolean)PropertyUtils.getProperty(nB,"aperturaB")&&(Boolean)PropertyUtils.getProperty(nB,"disponibileB");
          assertTrue(status);

     }
}
