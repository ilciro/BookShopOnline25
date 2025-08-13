package laptop.database.terzoucgestioneprofiloggetto.report;

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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportDao extends PersistenzaReport {


    private String query;

    private static final String LIBRO = "libro";
    private static final String GIORNALE = "giornale";
    private static final String RIVISTA = "rivista";

    private static final ResourceBundle RBVIEW=ResourceBundle.getBundle("sql/view");

    @Override
    public boolean insertReport(Report r)  {
        return true;
    }





    @Override
    public ObservableList<Report> report(String type) {
        ObservableList<Report> list = FXCollections.observableArrayList();
        switch (type) {
            case LIBRO ->
                    {
                        int row=-1;
                        query = RBVIEW.getString("queryL");


                        try (Connection conn = ConnToDb.connectionToDB();
                             PreparedStatement preQ = conn.prepareStatement(query)) {
                            row = preQ.executeUpdate();
                        }
                        catch (SQLException e)
                        {
                            Logger.getLogger("report libri view").log(Level.SEVERE,"error with book .{0}",e);
                        }

                        if(row==0) query="select distinct * from reportL";

                    }
            case GIORNALE ->
                    {
                        int row=-1;
                        query = RBVIEW.getString("queryG");


                        try (Connection conn = ConnToDb.connectionToDB();
                             PreparedStatement preQ = conn.prepareStatement(query)) {
                            row = preQ.executeUpdate();
                        }
                        catch (SQLException e)
                        {
                            Logger.getLogger("report giornali view").log(Level.SEVERE,"error with daily .{0}",e);
                        }

                        if(row==0) query="select distinct * from reportG";
                    }
            case RIVISTA -> {
                int row=-1;
                query = RBVIEW.getString("queryR");


                try (Connection conn = ConnToDb.connectionToDB();
                     PreparedStatement preQ = conn.prepareStatement(query)) {
                    row = preQ.executeUpdate();
                }
                catch (SQLException e)
                {
                    Logger.getLogger("report magazine view").log(Level.SEVERE,"error with magazine .{0}",e);
                }

                if(row==0) query="select distinct * from reportR";
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
    public ObservableList<TempUser> reportU()  {
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
        }catch (SQLException e)
        {
            Logger.getLogger("reportU").log(Level.SEVERE,"reportU is empty {0}",e);
        }
        return lista;

    }

    private static @NotNull TempUser getTempUser(ResultSet rs)  {
        TempUser tu=new TempUser();
        try {
            tu.setId(rs.getInt(1));
            tu.setIdRuoloT(rs.getString(2));
            tu.setNomeT(rs.getString(3));
            tu.setCognomeT(rs.getString(4));
            tu.setEmailT(rs.getString(5));
            tu.setPasswordT(rs.getString(6));
            tu.setDescrizioneT(rs.getString(7));
            tu.setDataDiNascitaT(rs.getDate(8).toLocalDate());
        } catch (SQLException e) {
           Logger.getLogger("getTempUser").log(Level.SEVERE,"tempUser is null {0}",e);
        }
        return tu;
    }




    @Override
    public void inizializza()  {
        Logger.getLogger("inizializza reportDao").log(Level.INFO,"initialize report dao");
    }
}