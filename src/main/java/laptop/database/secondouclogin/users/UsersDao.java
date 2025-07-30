package laptop.database.secondouclogin.users;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

import java.util.logging.Level;
import java.util.logging.Logger;


import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.controller.ControllerSystemState;
import laptop.database.DaoInitialize;
import laptop.database.secondouclogin.users.PersistenzaUtente;
import laptop.exception.IdException;
import laptop.utilities.ConnToDb;
import laptop.model.user.TempUser;
import org.jetbrains.annotations.NotNull;


public class UsersDao extends PersistenzaUtente {


	private  String query="";

	private  static final String ECCEZIONE = "errore in mysql :";
	private  int row = 0;
	private static final ControllerSystemState vis=ControllerSystemState.getInstance();



	// use this function on controller after you had check the email
	// add an user on db after registration
	// prende i dati dall'oggetto che gli abbiamo passato
	@Override
	public  boolean inserisciUtente(TempUser tu)  {



		LocalDate d = tu.getDataDiNascitaT();


		query = "INSERT INTO `utenti` VALUES (?,?,?,?,?,?,?,?)";

		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			if(vis.getTipoModifica().equals("im")) prepQ.setInt(1,tu.getId());
			else if (vis.getTipoModifica().equals("insert"))prepQ.setInt(1,0);


			prepQ.setString(2,tu.getIdRuoloT().substring(0,1));
			prepQ.setString(3, tu.getNomeT());
			prepQ.setString(4, tu.getCognomeT());
			prepQ.setString(5, tu.getEmailT());
			prepQ.setString(6, tu.getPasswordT());
			prepQ.setString(7, tu.getDescrizioneT());
			prepQ.setDate(8,Date.valueOf(d));
			row=prepQ.executeUpdate();


		} catch (SQLException e) {
			Logger.getLogger("createUser").log(Level.INFO, ECCEZIONE, e);

		}


		return row==1;

	}

	@Override
	public ObservableList<TempUser> getUserData() throws SQLException {
		ObservableList<TempUser> lista=FXCollections.observableArrayList();
		query="select * from utenti";
		try(Connection conn=ConnToDb.connectionToDB();
		PreparedStatement preQ=conn.prepareStatement(query)){

			ResultSet rs= preQ.executeQuery();
			while (rs.next())
			{
				TempUser tu = new TempUser();
				tu.setId(rs.getInt(1));
				tu.setIdRuoloT(rs.getString(2));
				tu.setNomeT(rs.getString(3));
				tu.setCognomeT(rs.getString(4));
				tu.setEmailT(rs.getString(5));
				tu.setPasswordT(rs.getString(6));
				tu.setDescrizioneT(rs.getString(7));
				tu.setDataDiNascitaT(rs.getDate(8).toLocalDate());
				lista.add(tu);
			}

		}
		return lista;
	}



	@Override
	public boolean removeUserByIdEmailPwd(TempUser u) throws SQLException {
		query="delete from utenti where idUser=? or email=? or pwd=?";

		try(Connection conn=ConnToDb.connectionToDB();
		PreparedStatement prepQ=conn.prepareStatement(query))
		{
			prepQ.setInt(1,u.getId());
			prepQ.setString(2,u.getEmailT());
			prepQ.setString(3,u.getPasswordT());
			row=prepQ.executeUpdate();
		}
		return row==1;
	}



	@Override
	public void initializza() throws CsvValidationException, IOException, IdException, SQLException {

		DaoInitialize daoI=new DaoInitialize();
		daoI.inizializza("utenti");
    }







}

