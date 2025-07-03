package laptop.database.primoucacquista.negozio;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;

import laptop.database.DaoInitialize;
import laptop.model.Negozio;
import laptop.utilities.ConnToDb;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NegozioDao extends PersistenzaNegozio{
	private String query;
	private static final String ECCEZIONE="eccezione ottenuta:";

@Override
	public ObservableList<Negozio> getNegozi() {
		Negozio shop;
		 ObservableList<Negozio> listOfNegozi;
		listOfNegozi=FXCollections.observableArrayList();

		query="SELECT  nome,via,isValid,isOpen from negozio";
				

			 try(Connection conn= ConnToDb.connectionToDB();
			 PreparedStatement prepQ=conn.prepareStatement(query))
			 {
	 			ResultSet rs=prepQ.executeQuery();
			
				while (rs.next())
				{
					shop = new Negozio(rs.getString(1),rs.getString(2),rs.getBoolean(3),rs.getBoolean(4));
					listOfNegozi.add(shop);
				}
			 }catch(SQLException e)
			 {
				java.util.logging.Logger.getLogger("get negozi").log(Level.INFO, ECCEZIONE, e);
			 }
		
		return listOfNegozi;
	}
	


	
	@Override
	// controllo che il negozio sia aperto
	public boolean checkOpen(Negozio  shop) {
		int aperto;
		boolean state=false;
		query="select isOpen from negozio where nome=?";
		try(Connection conn=ConnToDb.connectionToDB();
				PreparedStatement prepQ=conn.prepareCall(query))
		{
			prepQ.setString(1, shop.getNome());
			ResultSet rs=prepQ.executeQuery();
			while(rs.next()){
				aperto=rs.getInt(1);
				if(aperto==1)
					state=true;
				
			}
		}catch(SQLException e)
		{
			java.util.logging.Logger.getLogger("Test Eccezione").log(Level.INFO, ECCEZIONE, e);
		}
			 
			
		
		return state;
	}

	
	//controllo se il negozio fa PickUP
	@Override
	public boolean checkRitiro(Negozio shop)
	{
		query="select isValid from negozio where nome=?";
		boolean state=false;
		int disp;
		
		try(Connection conn=ConnToDb.connectionToDB();
				PreparedStatement prepQ=conn.prepareStatement(query))
		{
			prepQ.setString(1, shop.getNome());
			ResultSet rs=prepQ.executeQuery();
			while ( rs.next() ) {

					disp=rs.getInt(1);
					if (disp==1)
						state=true;
				
						
			}
			
		}catch(SQLException e)
		{
			java.util.logging.Logger.getLogger("Test Eccezione").log(Level.INFO, ECCEZIONE, e);
		}
			
		return state;
	}

	@Override
	public void initializza() throws IOException, SQLException {
		DaoInitialize daoI=new DaoInitialize();
		daoI.inizializza("negozio");
	}
}
