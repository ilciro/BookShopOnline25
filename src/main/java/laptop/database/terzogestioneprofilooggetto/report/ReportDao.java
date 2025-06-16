package laptop.database.terzogestioneprofilooggetto.report;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.model.Report;
import laptop.model.user.TempUser;
import laptop.utilities.ConnToDb;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportDao extends PersistenzaReport {


    private String query;

    private static final String LIBRO = "libro";
    private static final String GIORNALE = "giornale";
    private static final String RIVISTA = "rivista";

    @Override
    public boolean insertReport(Report r) throws CsvValidationException, IOException {
        return true;
    }
    private boolean reportL()  {
        int row=-1;
        query = "create or replace view REPORTL (idProdotto,titolo,categoria,spesaTotale) as select l.idLibro,l.titolo,l.categoria,sum(p.spesaTotale) from libro l join pagamento  p on l.idLibro=p.idProdotto group by l.idLibro;";


        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement preQ = conn.prepareStatement(query)) {
            row = preQ.executeUpdate();
        }
        catch (SQLException e)
        {
            Logger.getLogger("report libri view").log(Level.SEVERE," error");
        }

        return row==0 ;
    }
    private boolean reportG() {
        int row = -1;
        query = "create or replace view REPORTG (idProdotto,titolo,categoria,spesaTotale) as select g.idGiornale,g.titolo,g.categoria,sum(g.prezzo) from giornale g join pagamento  p on g.idGiornale=p.idProdotto group by g.idGiornale;";


        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement preQ = conn.prepareStatement(query)) {
            row = preQ.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger("crete view giornale ").log(Level.SEVERE, " could not create daily view !!");
        }
        return row == 0;
    }
    private boolean reportR() {
        int row = -1;
        query = "create or replace view REPORTR (idProdotto,titolo,categoria,spesaTotale) as select r.idRivista,r.titolo,r.categoria,sum(p.spesaTotale) from rivista r join pagamento  p on r.idRivista=p.idProdotto group by r.idRivista;";


        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement preQ = conn.prepareStatement(query)) {
            row = preQ.executeUpdate();        } catch (SQLException e) {
            Logger.getLogger("crete rivista view ").log(Level.SEVERE, " could not create magazine view !!");
        }
        return row == 0;
    }



    @Override
    public ObservableList<Report> report(String type) {
        ObservableList<Report> list = FXCollections.observableArrayList();
        switch (type) {
            case LIBRO ->
            {
                if(reportL())  query = "select * from REPORTL";
            }
            case GIORNALE ->
            {
                if(reportG()) query = "select * from REPORTG";
            }
            case RIVISTA -> {
                if (reportR()) query = "select * from REPORTR";
            }
            default -> Logger.getLogger("report").log(Level.SEVERE, " type in cot correct !!");
        }
        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prep = conn.prepareStatement(query)) {

            ResultSet rs = prep.executeQuery(query);
            while (rs.next()) {

                Report report = new Report();

                report.setIdReport(rs.getInt(1));
                report.setTitoloOggetto(rs.getString(2));
                report.setTipologiaOggetto(rs.getString(3));
                report.setTotale(rs.getFloat(4));

                list.add(report);

            }

        } catch (SQLException e) {
            Logger.getLogger(" report ").log(Level.SEVERE, " REPORTL is empty {0}",e.getMessage());
        }
        return list;
    }

    @Override
    public ObservableList<TempUser> reportU() throws SQLException {
        ObservableList<TempUser> lista=FXCollections.observableArrayList();
        query="select * from utenti";
        try(Connection conn=ConnToDb.connectionToDB();
        PreparedStatement prepQ= conn.prepareStatement(query))
        {
         ResultSet rs= prepQ.executeQuery();
             while (rs.next())
             {
                 TempUser tu = getTempUser(rs);
                 lista.add(tu);
             }
        }
        return lista;

    }

    private static @NotNull TempUser getTempUser(ResultSet rs) throws SQLException {
        TempUser tu=new TempUser();
        tu.setId(rs.getInt(1));
        tu.setIdRuoloT(rs.getString(2));
        tu.setNomeT(rs.getString(3));
        tu.setCognomeT(rs.getString(4));
        tu.setEmailT(rs.getString(5));
        tu.setPasswordT(rs.getString(6));
        tu.setDescrizioneT(rs.getString(7));
        tu.setDataDiNascitaT(rs.getDate(8).toLocalDate());
        return tu;
    }




    @Override
    public void inizializza() throws IOException, ClassNotFoundException {
        Logger.getLogger("inizializza reportDao").log(Level.INFO,"initialize report dao");
    }
}