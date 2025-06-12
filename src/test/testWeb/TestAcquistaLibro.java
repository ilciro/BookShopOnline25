package testWeb;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.libro.LibroDao;
import laptop.database.libro.PersistenzaLibro;
import laptop.exception.IdException;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestAcquistaLibro {
    private static final LibroBean lB=new LibroBean();
    private static final PersistenzaLibro pL =new LibroDao();
    private static final SystemBean sB=SystemBean.getInstance();
    private static final FatturaBean fB=new FatturaBean();
    private static final PagamentoBean pB=new PagamentoBean();
    private static final DownloadBean dB=new DownloadBean();
     @Test
     void testIndexBook() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, CsvValidationException, IOException, IdException, ClassNotFoundException {
         System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver/chromedriver");
         //schermata index
         WebDriver driver = new ChromeDriver();
         driver.get("http://localhost:8080/original-BookShopOnline25/index.jsp");
         driver.findElement(By.id("libri")).click();
         PropertyUtils.setProperty(sB,"typeB","libro");
         //libri.jsp
         PropertyUtils.setProperty(lB,"listaLibriB",pL.retrieveRaccoltaData());
         driver.findElement(By.id("genera lista")).click();
         driver.findElement(By.id("idOgg")).clear();
         driver.findElement(By.id("idOgg")).sendKeys("6");
         PropertyUtils.setProperty(sB,"idB",Integer.parseInt(Objects.requireNonNull(driver.findElement(By.id("idOgg")).getDomProperty("value"))));
         driver.findElement(By.id("procedi")).click();
         //acquista
         driver.findElement(By.id("quantita")).clear();
         driver.findElement(By.id("quantita")).sendKeys("2");
         PropertyUtils.setProperty(sB,"quantitaB",Integer.parseInt(Objects.requireNonNull(driver.findElement(By.id("quantita")).getDomProperty("value"))));
         driver.findElement(By.id("totaleB")).click();
         //totale
         PropertyUtils.setProperty(sB,"spesaTB",Float.parseFloat(Objects.requireNonNull(driver.findElement(By.id("totale")).getDomProperty("value"))));
        //download
         WebElement input =driver.findElement(By.xpath("//input[@list='metodi']"));
         WebElement option =driver.findElement(By.xpath("//*[@id='metodi']/option[1]"));
         String value = option.getDomProperty("value");
         input.sendKeys(Objects.requireNonNull(value));
         PropertyUtils.setProperty(SystemBean.getInstance(),"metodoPB",value);
         driver.findElement(By.xpath("//input[@id='pdfB']")).click();
         //fattura
         driver.findElement(By.id("nomeL")).sendKeys("francoB");
         driver.findElement(By.id("cognomeL")).sendKeys("rossiB");
         driver.findElement(By.id("indirizzoL")).sendKeys("via papaveri 12");
         driver.findElement(By.id("com")).sendKeys("il cap è 00005 . Chiamare prima al numero");
         String nome=driver.findElement(By.id("nomeL")).getDomProperty("value");
         String cognome=driver.findElement(By.id("cognomeL")).getDomProperty("value");
         String indirizzo=driver.findElement(By.id("indirizzoL")).getDomProperty("value");
         String com=driver.findElement(By.id("com")).getDomProperty("value");
         //setto fattura
         PropertyUtils.setProperty(fB,"nomeB",nome);
         PropertyUtils.setProperty(fB,"cognomeB",cognome);
         PropertyUtils.setProperty(fB,"indirizzoB",indirizzo);
         PropertyUtils.setProperty(fB,"comunicazioniB",com);
         //setto pagamento
         PropertyUtils.setProperty(sB,"tipoModifica","insert");
         PropertyUtils.setProperty(pB,"idB",0);
         PropertyUtils.setProperty(pB,"metodoB", PropertyUtils.getProperty(SystemBean.getInstance(),"metodoPB"));
         PropertyUtils.setProperty(pB,"ammontareB",PropertyUtils.getProperty(SystemBean.getInstance(),"spesaTB"));
         PropertyUtils.setProperty(pB,"esitoB",0);
         PropertyUtils.setProperty(pB,"nomeUtenteB","");
         driver.findElement(By.id("buttonC")).click();
         //schermata download
         String titoloL=driver.findElement(By.id("titoloL")).getDomProperty("value");
         PropertyUtils.setProperty(dB,"idB",sB.getIdB());
         PropertyUtils.setProperty(dB,"titoloB",titoloL);
         driver.findElement(By.id("downloadB")).click();
         assertNotEquals(0,Integer.parseInt(PropertyUtils.getProperty(sB,"idB").toString()));
         driver.quit();


     }
}
