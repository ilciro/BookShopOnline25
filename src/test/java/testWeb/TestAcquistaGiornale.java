package testWeb;

import com.opencsv.exceptions.CsvValidationException;
import laptop.database.primoucacquista.giornale.GiornaleDao;
import laptop.database.primoucacquista.giornale.PersistenzaGiornale;
import laptop.exception.IdException;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import web.bean.AcquistaBean;
import web.bean.CartaCreditoBean;
import web.bean.GiornaleBean;
import web.bean.SystemBean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestAcquistaGiornale {
    private static final GiornaleBean gB = new GiornaleBean();
    private static final PersistenzaGiornale pG = new GiornaleDao();
    private static final CartaCreditoBean ccB = new CartaCreditoBean();
    private static final SystemBean sB = SystemBean.getInstance();
    private static final AcquistaBean aB=new AcquistaBean();
    private static final String IDOGG="idOgg";
    private static final String VALUE="value";
    private static final String QUANTITA="quantita";
    private static final String SPESATB="spesaTB";

    @Test
    void testPagamentoCashEDownloadGiornale() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, CsvValidationException, IOException, IdException, ClassNotFoundException, ParseException {
        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver/chromedriver");
        //schermata index
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/original-BookShopOnline25/index.jsp");
        driver.findElement(By.id("giornali")).click();
        PropertyUtils.setProperty(sB, "typeB", "giornale");
        //schermata giornali
        PropertyUtils.setProperty(gB, "listaGiornaliB", pG.retrieveRaccoltaData());
        driver.findElement(By.id("genera lista")).click();
        driver.findElement(By.id(IDOGG)).clear();
        driver.findElement(By.id(IDOGG)).sendKeys("10");
        PropertyUtils.setProperty(sB,"idB",Integer.parseInt(Objects.requireNonNull(driver.findElement(By.id(IDOGG)).getDomProperty(VALUE))));
        driver.findElement(By.id("procedi")).click();
        //acquista
        PropertyUtils.setProperty(aB,"titoloB",driver.findElement(By.id("nome")).getDomProperty(VALUE));
        driver.findElement(By.id(QUANTITA)).clear();
        driver.findElement(By.id(QUANTITA)).sendKeys("2");
        PropertyUtils.setProperty(sB,"quantitaB",Integer.parseInt(Objects.requireNonNull(driver.findElement(By.id(QUANTITA)).getDomProperty(VALUE))));
        driver.findElement(By.id("totaleB")).click();
        //totale
        PropertyUtils.setProperty(sB,SPESATB,Float.parseFloat(Objects.requireNonNull(driver.findElement(By.id("totale")).getDomProperty(VALUE))));
        PropertyUtils.setProperty(aB,"prezzoB",PropertyUtils.getProperty(sB,SPESATB));
        //pagamento cc
        WebElement input =driver.findElement(By.xpath("//input[@list='metodi']"));
        WebElement option =driver.findElement(By.xpath("//*[@id='metodi']/option[2]"));
        String value = option.getDomProperty(VALUE);
        input.sendKeys(Objects.requireNonNull(value));
        //negozio
        driver.findElement(By.id("negozioB")).click();
        //pagaento cc
        driver.findElement(By.id("nomeL")).sendKeys("luigiB");
        driver.findElement(By.id("cognomeL")).sendKeys("neriB");
        driver.findElement(By.id("cartaL")).sendKeys("1526-8549-9662-1100");
        driver.findElement(By.id("scadL")).sendKeys("2028/08/11");
        driver.findElement(By.id("passL")).sendKeys("145");

        //converto data
        java.util.Date utilDate;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        utilDate = format.parse(driver.findElement(By.id("scadL")).getDomProperty(VALUE));


        PropertyUtils.setProperty(ccB, "nomeB",  driver.findElement(By.id("nomeL")).getDomProperty(VALUE));
        PropertyUtils.setProperty(ccB, "cognomeB",  driver.findElement(By.id("cognomeL")).getDomProperty(VALUE));
        PropertyUtils.setProperty(ccB, "numeroCCB",  driver.findElement(By.id("cartaL")).getDomProperty(VALUE));
        PropertyUtils.setProperty(ccB, "dataScadB", utilDate);
        PropertyUtils.setProperty(ccB, "civB",  driver.findElement(By.id("passL")).getDomProperty(VALUE));
        PropertyUtils.setProperty(ccB,"prezzoTransazioneB",PropertyUtils.getProperty(sB,SPESATB));
        driver.findElement(By.id("buttonI")).click();
        //negozio
        driver.findElement(By.id("buttonNeg2")).click();

        assertNotEquals(0, Integer.parseInt(PropertyUtils.getProperty(sB,"idB").toString()));
        driver.quit();
    }
}
