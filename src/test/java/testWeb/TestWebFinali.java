package testWeb;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.giornale.GiornaleDao;
import laptop.database.giornale.PersistenzaGiornale;
import laptop.database.libro.LibroDao;
import laptop.database.libro.PersistenzaLibro;
import laptop.database.negozio.NegozioDao;
import laptop.database.negozio.PersistenzaNegozio;
import laptop.database.rivista.PersistenzaRivista;
import laptop.database.rivista.RivistaDao;
import laptop.exception.IdException;
import laptop.model.Negozio;
import laptop.model.raccolta.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import web.bean.GiornaleBean;
import web.bean.LibroBean;
import web.bean.NegozioBean;
import web.bean.RivistaBean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class TestWebFinali {

     private static final PersistenzaLibro pL=new LibroDao();
     private static final LibroBean lB=new LibroBean();
     private static final PersistenzaRivista pR=new RivistaDao();
     private static final RivistaBean rB=new RivistaBean();
     private static final NegozioBean nB=new NegozioBean();
     private static final PersistenzaNegozio pN=new NegozioDao();
     private static final PersistenzaGiornale pG=new GiornaleDao();
     private static final GiornaleBean gB=new GiornaleBean();

     @Test
     void testLibro() throws CsvValidationException, IOException, IdException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
          Libro l=pL.getLibri().get(5);

         PropertyUtils.setProperty(lB,"titoloB",l.getTitolo());
          PropertyUtils.setProperty(lB,"codIsbnB",l.getCodIsbn());
          PropertyUtils.setProperty(lB,"autoreB",l.getAutore());
          PropertyUtils.setProperty(lB,"editoreB",l.getEditore());
          PropertyUtils.setProperty(lB,"linguaB",l.getLingua());
          PropertyUtils.setProperty(lB,"listaLibriB",pL.retrieveRaccoltaData());
          lB.setCategorie();

          for(CategorieLibro cat: CategorieLibro.values())
               PropertyUtils.setProperty(lB,"categoriaB",cat.toString());
          assertEquals(PropertyUtils.getProperty(lB,"categoriaB"),lB.getcategoriaB());
     }

     @Test
     void testRivista() throws CsvValidationException, IOException, IdException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
          Rivista r=pR.getRiviste().get(2);
          PropertyUtils.setProperty(rB,"titoloB",r.getTitolo());
          PropertyUtils.setProperty(rB,"autoreB",r.getAutore());
          PropertyUtils.setProperty(rB,"linguaB",r.getLingua());
          PropertyUtils.setProperty(rB,"dataPubbB",r.getDataPubb());
          PropertyUtils.setProperty(rB,"prezzoB",3.54f);



          PropertyUtils.setProperty(rB,"listaRivisteB",pR.getRiviste());
          rB.elencoCategorie();

          for(CategorieRivista cat:CategorieRivista.values())
               PropertyUtils.setProperty(rB,"tipologiaB",cat.toString());
          assertNotEquals("",PropertyUtils.getProperty(rB,"tipologiaB"));
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

     @Test
     void testGiornale() throws CsvValidationException, IOException, IdException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
          Giornale g=pG.getGiornali().get(2);
          PropertyUtils.setProperty(gB,"idB",g.getId());
          PropertyUtils.setProperty(gB,"titoloB",g.getTitolo());
          PropertyUtils.setProperty(gB,"linguaB",g.getLingua());
          PropertyUtils.setProperty(gB,"editoreB",g.getEditore());
          PropertyUtils.setProperty(gB,"tipologiaB","QUOTIDIANO");
          PropertyUtils.setProperty(gB,"disponibilitaB",1);
          PropertyUtils.setProperty(gB,"prezzoB",1.25f);
          assertNotEquals(0,PropertyUtils.getProperty(gB,"idB"));

     }
}
