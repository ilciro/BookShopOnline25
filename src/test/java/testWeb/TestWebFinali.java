package testWeb;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.database.primoucacquista.libro.LibroDao;
import laptop.database.primoucacquista.libro.PersistenzaLibro;
import laptop.database.primoucacquista.negozio.NegozioDao;
import laptop.database.primoucacquista.negozio.PersistenzaNegozio;
import laptop.database.primoucacquista.rivista.PersistenzaRivista;
import laptop.database.primoucacquista.rivista.RivistaDao;
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
     private static final String TITOLOB="titoloB";
     private static final String LINGUAB ="linguaB";
     private static final String TIPOLOGIAB="tipologiaB";

     @Test
     void testLibro() throws CsvValidationException, IOException, IdException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
          Libro l=pL.getLibri().get(5);

         PropertyUtils.setProperty(lB,TITOLOB,l.getTitolo());
          PropertyUtils.setProperty(lB,"codIsbnB",l.getCodIsbn());
          PropertyUtils.setProperty(lB,"autoreB",l.getAutore());
          PropertyUtils.setProperty(lB,"editoreB",l.getEditore());
          PropertyUtils.setProperty(lB,LINGUAB,l.getLingua());
          PropertyUtils.setProperty(lB,"listaLibriB",pL.retrieveRaccoltaData());
          lB.setCategorie();

          for(CategorieLibro cat: CategorieLibro.values())
               PropertyUtils.setProperty(lB,"categoriaB",cat.toString());
          assertEquals(PropertyUtils.getProperty(lB,"categoriaB"),lB.getcategoriaB());
     }

     @Test
     void testRivista() throws CsvValidationException, IOException, IdException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
          Rivista r=pR.getRiviste().get(2);
          PropertyUtils.setProperty(rB,TITOLOB,r.getTitolo());
          PropertyUtils.setProperty(rB,"autoreB",r.getAutore());
          PropertyUtils.setProperty(rB,LINGUAB,r.getLingua());
          PropertyUtils.setProperty(rB,"dataPubbB",r.getDataPubb());
          PropertyUtils.setProperty(rB,"prezzoB",3.54f);



          PropertyUtils.setProperty(rB,"listaRivisteB",pR.getRiviste());
          rB.elencoCategorie();

          for(CategorieRivista cat:CategorieRivista.values())
               PropertyUtils.setProperty(rB,TIPOLOGIAB,cat.toString());
          assertNotEquals("",PropertyUtils.getProperty(rB,TIPOLOGIAB));
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
          PropertyUtils.setProperty(gB,TITOLOB,g.getTitolo());
          PropertyUtils.setProperty(gB,LINGUAB,g.getLingua());
          PropertyUtils.setProperty(gB,"editoreB",g.getEditore());
          PropertyUtils.setProperty(gB,TIPOLOGIAB,"QUOTIDIANO");
          PropertyUtils.setProperty(gB,"disponibilitaB",1);
          PropertyUtils.setProperty(gB,"prezzoB",1.25f);
          assertNotEquals(0,PropertyUtils.getProperty(gB,"idB"));

     }
}
