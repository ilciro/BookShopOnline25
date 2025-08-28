package vari;

import laptop.model.raccolta.Libro;
import laptop.model.raccolta.Rivista;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestVari {

    @Test
    void testCategorieLibro() {
        Libro l=new Libro();
        String value = "";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("configurations/bookCategories.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find " + "configurations/bookCategories");
                return;
            }

            prop.load(input);


            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                 value = prop.getProperty(key);
                 l.setCategoria(value);

            }

        } catch (IOException ex) {
            Logger.getLogger("categorie libro").log(Level.SEVERE," error with book categories");
        }
        assertEquals(l.getCategoria(),value);

    }
    @Test
    void testCategorieRivista() {
        Rivista r=new Rivista();
        String value = "";
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("configurations/magazineCategories.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find " + "configurations/magazineCategories");
                return;
            }

            prop.load(input);


            Enumeration<?> e = prop.propertyNames();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                value = prop.getProperty(key);
                r.setCategoria(value);

            }

        } catch (IOException ex) {
            Logger.getLogger("categorie rivista").log(Level.SEVERE," error with magazine categories");
        }
        assertEquals(r.getCategoria(),value);

    }

}
