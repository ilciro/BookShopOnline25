package laptop.database.rivista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Factory;
import laptop.model.raccolta.Raccolta;
import laptop.model.raccolta.Rivista;
import laptop.utilities.ConnToDb;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RivistaDao extends PersistenzaRivista{
    private final Factory f;

    private  String query ;


    private boolean state=false;

    private final ControllerSystemState vis=ControllerSystemState.getInstance();
    private  static final String RIVISTA="rivista";

    private static final String ECCEZIONE="eccezione generata:";





    public RivistaDao() {
        f = new Factory();


    }

    @Override
    public boolean inserisciRivista(Rivista r) throws CsvValidationException, IOException, SQLException {
        int row;


        query= "INSERT INTO `RIVISTA`"
                + "(`titolo`,"
                + "`categoria`,"
                + "`autore`,"
                + "`lingua`,"
                + "`editore`,"
                + "`descrizione`,"
                + "`dataPubblicazione`,"
                + "`copieRimanenti`,"
                + "`disp`,"
                + "`prezzo`)"
                + "VALUES"
                + "(?,?,?,?,?,?,?,?,?,?)";
        try(Connection conn= ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query))
        {

            prepQ.setString(1,r.getTitolo());
            prepQ.setString(2,r.getCategoria());
            prepQ.setString(3,r.getAutore());
            prepQ.setString(4,r.getLingua());
            prepQ.setString(5,r.getEditore());
            prepQ.setString(6,r.getDescrizione());
            prepQ.setDate(7, Date.valueOf(r.getDataPubb().toString()));
            prepQ.setInt(8,r.getCopieRim());
            prepQ.setInt(9, r.getDisp());
            prepQ.setFloat(10, r.getPrezzo());


            row=prepQ.executeUpdate();
            state= row == 1; // true

        }catch(SQLException e)
        {
            java.util.logging.Logger.getLogger("creazione Rivista").log(Level.INFO, ECCEZIONE, e);
        }


        return state;

    }

    @Override
    public boolean removeRivistaById(Rivista r) throws CsvValidationException, IOException, SQLException {
        int row = 0;

        query="delete from RIVISTA where idRivista=? or idRivista=?";
        try (Connection conn=ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(query)){

            prepQ.setInt(1,r.getId());
            prepQ.setInt(2,vis.getId());

            row= prepQ.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger("elimina").log(Level.SEVERE," error in mysql delete", e);
        }
        return  row==1;

    }

    @Override
    public ObservableList<Raccolta> retrieveRaccoltaData() throws CsvValidationException, IOException, IdException {
        ObservableList<Raccolta> catalogo = FXCollections.observableArrayList();

        query = "select * from RIVISTA";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query);
             ResultSet rs = prepQ.executeQuery()) {
            while (rs.next()) {
                f.createRaccoltaFinale1(RIVISTA,rs.getString(1),null,rs.getString(5),rs.getString(3),rs.getString(4),rs.getString(2));



                f.createRaccoltaFinale2(RIVISTA, 0, rs.getInt(10), rs.getInt(8),rs.getFloat(9),rs.getInt(11));

                catalogo.add(f.createRaccoltaFinaleCompleta(RIVISTA, rs.getDate(7).toLocalDate(),null, null));




            }
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger("get libri").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;
    }

    @Override
    public ObservableList<Rivista> getRiviste() throws CsvValidationException, IOException, IdException {
        ObservableList<Rivista> catalogo = FXCollections.observableArrayList();
        String[] info=new String[7];

        query = "select * from RIVISTA ";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(query))  {


            ResultSet rs=prepQ.executeQuery();
            while (rs.next())
            {
                info[0]=rs.getString("titolo");
                info[2]=rs.getString("editore");
                info[3]=rs.getString("autore");
                info[4]=rs.getString("lingua");
                info[5]=rs.getString("categoria");
                catalogo.add((Rivista)f.creaRivista(info,rs.getString("descrizione"),rs.getDate("dataPubblicazione").toLocalDate(),rs.getInt("disp"),rs.getFloat("prezzo"),rs.getInt("copieRimanenti"),rs.getInt("idRivista")));

            }
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger("get data rivista obs").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;
    }

    @Override
    public ObservableList<Rivista> getRivistaByIdTitoloAutoreRivista(Rivista r) throws CsvValidationException, IOException, IdException {
        ObservableList<Rivista> catalogo = FXCollections.observableArrayList();
        String[] info=new String[7];

        query = "select * from RIVISTA where idRivista=? or idRivista=? or titolo=? or autore=?";
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ= conn.prepareStatement(query))  {

            prepQ.setInt(1,r.getId());
            prepQ.setInt(2,vis.getId());
            prepQ.setString(3,r.getTitolo());
            prepQ.setString(4,r.getEditore());

            ResultSet rs=prepQ.executeQuery();
            while (rs.next())
            {
                info[0]=rs.getString("titolo");
                info[2]=rs.getString("editore");
                info[3]=rs.getString("autore");
                info[4]=rs.getString("lingua");
                info[5]=rs.getString("categoria");
                catalogo.add((Rivista)f.creaRivista(info,rs.getString("descrizione"),rs.getDate("dataPubblicazione").toLocalDate(),rs.getInt("disp"),rs.getFloat("prezzo"),rs.getInt("copieRimanenti"),rs.getInt("idRivista")));

            }
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger("get data rivista obs").log(Level.INFO, ECCEZIONE, e);
        }
        return catalogo;
    }

    @Override
    public void initializza() throws IOException, CsvValidationException {
        Logger.getLogger("crea db sql per le riviste").log(Level.INFO, "\n creating tables ro magazine ..");
        try{
            if(vis.isPopulated())
            {
                Logger.getLogger(" crea tabella riviste if").log(Level.INFO, " table magazines already populated");
            }
            else {
                ConnToDb.creaPopolaDb();
                vis.setPopulated(true);
            }
        }catch (FileNotFoundException e)
        {
            Logger.getLogger("crea tabella riviste ").log(Level.SEVERE, "\n eccezione ottenuta nella rivista.", e);

        }
    }

}
