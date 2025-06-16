package laptop.database.primoucacquista.cartacredito;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.database.primoucacquista.DaoInitialize;
import laptop.model.CartaDiCredito;
import laptop.utilities.ConnToDb;

import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartaCreditoDao extends PersistenzaCC{

    @Override
    public void inizializza() throws IOException, ClassNotFoundException, SQLException {
        DaoInitialize daoI=new DaoInitialize();
        daoI.inizializza("cartacredito");


    }

    private String query;
    private static final String ECCEZIONE="eccezione ottenuta :";


    @Override
    public boolean insCC(CartaDiCredito cc) {
        long row=0;
        query = "insert into cartacredito (nomeP,cognomeP,codiceCarta,scadenza,pin,ammontare)  values(?,?,?,?,?,?)";


        try (Connection conn = ConnToDb.connectionToDB();
             PreparedStatement prepQ = conn.prepareStatement(query)) {
            prepQ.setString(1, cc.getNomeUser());
            prepQ.setString(2, cc.getCognomeUser());
            prepQ.setString(3, cc.getNumeroCC());
            prepQ.setDate(4, (Date) cc.getScadenza());
            prepQ.setString(5, cc.getCiv());
            //in alternativa vis.getSpesa
            prepQ.setFloat(6, cc.getPrezzoTransazine());
            row=prepQ.executeLargeUpdate();
        } catch (SQLException e) {
            Logger.getLogger("report libro").log(Level.SEVERE, "\n eccezione ottenuta .", e);

        }
        return row==1;
    }


    @Override
    public ObservableList<CartaDiCredito> getCarteDiCredito(CartaDiCredito cc) {
        String cod;
        query="select nomeP,cognomeP,codiceCarta from cartacredito where nomeP=?";

        ObservableList<CartaDiCredito> catalogo= FXCollections.observableArrayList();

        try(Connection conn= ConnToDb.connectionToDB();
            PreparedStatement prepQ=conn.prepareStatement(query))
        {
            prepQ.setString(1, cc.getNomeUser());
            ResultSet rs=prepQ.executeQuery();
            while(rs.next())
            {
                String n=rs.getString(1);
                String cog=rs.getString(2);
                cod=rs.getString(3);


                catalogo.add(new CartaDiCredito(n,cog,cod, null, cod,0));


            }
        }catch(SQLException e)
        {
            Logger.getLogger("Test Eccezione").log(Level.INFO, ECCEZIONE, e);
        }


        return catalogo;    }
}
