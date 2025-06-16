package laptop.database.primoucacquista.giornale;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.primoucacquista.DaoInitialize;
import laptop.exception.IdException;
import laptop.model.raccolta.Factory;
import laptop.model.raccolta.Giornale;
import laptop.model.raccolta.Raccolta;
import laptop.utilities.ConnToDb;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GiornaleDao extends PersistenzaGiornale{
    private final Factory f;


    private String query;



    private final ControllerSystemState vis = ControllerSystemState.getInstance();
    private static final String GIORNALE = "giornale";
    private static final String ECCEZIONE = "eccezione generata:";

    public GiornaleDao(){this.f=new Factory();}
    @Override
    public boolean inserisciGiornale(Giornale g)  {
        int row = 0;



        query = "INSERT INTO `giornale`"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query)) {

            prepQ.setString(1, g.getTitolo());
            prepQ.setString(2, g.getCategoria());
            prepQ.setString(3, g.getLingua());
            prepQ.setString(4, g.getEditore());
            prepQ.setDate(5, Date.valueOf(g.getDataPubb().toString()));
            prepQ.setInt(6, g.getCopieRimanenti());
            prepQ.setInt(7, g.getDisponibilita());
            prepQ.setFloat(8, g.getPrezzo());
            //uso vis.get id perchè se no sarebbe 0-> quindi inserisci uno nuovo
            if(vis.getTipoModifica().equals("im")) prepQ.setInt(9,vis.getIdGiornale());
            else if(vis.getTipoModifica().equals("insert")) prepQ.setInt(9,0);


            row = prepQ.executeUpdate();


        } catch (SQLException e) {
            Logger.getLogger("creazione giornale").log(Level.INFO, ECCEZIONE, e);
        }


        return row==1;

    }

    @Override
    public boolean removeGiornaleById(Giornale g) {
        int row = 0;

        query="delete from giornale where idGiornale=? or idGiornale=? or titolo=?";
        try (Connection conn=ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(query)){

            prepQ.setInt(1,g.getId());
            prepQ.setInt(2,vis.getIdGiornale());
            prepQ.setString(3,g.getTitolo());

            row= prepQ.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger("elimina").log(Level.SEVERE," error in mysql delete", e);
        }

        return row==1;

    }

    @Override
    public ObservableList<Giornale> getGiornali()  {
        ObservableList<Giornale> catalogo= FXCollections.observableArrayList();
        query = "select * from giornale";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query)) {

            ResultSet rs = prepQ.executeQuery();
            while (rs.next()) {

                f.createRaccoltaFinale1(GIORNALE, rs.getString(1), null, rs.getString(4), null, rs.getString(4), rs.getString(2));


                f.createRaccoltaFinale2(GIORNALE, 0, rs.getInt(6), rs.getInt(7), rs.getFloat(8), rs.getInt(9));

                catalogo.add((Giornale)f.createRaccoltaFinaleCompleta(GIORNALE, rs.getDate(5).toLocalDate(), rs.getString(6), null));


            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger("get giornale id").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;
    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException {
        ObservableList<Raccolta> catalogo= FXCollections.observableArrayList();
        query = "select  * from giornale";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query)) {

            ResultSet rs = prepQ.executeQuery();
            while (rs.next()) {

                f.createRaccoltaFinale1(GIORNALE, rs.getString(1), null, rs.getString(4), null, rs.getString(4), rs.getString(2));


                f.createRaccoltaFinale2(GIORNALE, 0, rs.getInt(6), rs.getInt(7), rs.getFloat(8), rs.getInt(9));

                catalogo.add(f.createRaccoltaFinaleCompleta(GIORNALE, rs.getDate(5).toLocalDate(), rs.getString(6), null));


            }
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger("get giornale id").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;

    }



    @Override
    public ObservableList<Giornale> getGiornaleByIdTitoloAutoreLibro(Giornale g)  {
        ObservableList<Giornale> catalogo = FXCollections.observableArrayList();
        String[] info = new String[7];



        query = "select * from giornale where idGiornale=? or idGiornale=? or titolo=? or editore=?";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query)) {

            prepQ.setInt(1, g.getId());
            prepQ.setInt(2, vis.getIdGiornale());
            prepQ.setString(3, g.getTitolo());
            prepQ.setString(4, g.getEditore());

            ResultSet rs = prepQ.executeQuery();
            while (rs.next()) {

                String titolo=rs.getString("titolo");
                String categoria=rs.getString("categoria");
                String lingua=rs.getString("lingua");
                String editore=rs.getString("editore");
                LocalDate data= rs.getDate("dataPubblicazione").toLocalDate();
                int copie=rs.getInt("copieRimanenti");
                int disp=rs.getInt("disp");
                float prezzo=rs.getFloat("prezzo");
                info[0] = titolo;
                info[1]="";
                info[2] = editore;
                info[3]="";
                info[4] = lingua;
                info[5] = categoria;
                catalogo.add((Giornale) f.creaGiornale(info, data,copie,disp,prezzo, rs.getInt("idGiornale")));

            }

        } catch (SQLException e) {
            Logger.getLogger("get giornale titolo id").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;
    }

    @Override
    public void initializza() throws FileNotFoundException, SQLException {


        DaoInitialize daoI=new DaoInitialize();
        daoI.inizializza(GIORNALE);
    }


}
