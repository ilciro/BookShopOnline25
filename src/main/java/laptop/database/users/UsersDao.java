package laptop.database.users;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import laptop.exception.IdException;
import laptop.utilities.ConnToDb;
import laptop.model.user.TempUser;
import laptop.model.user.User;


public class UsersDao extends PersistenzaUtente {


	private  String query="";

	private  boolean state = false;
	private  static final String ECCEZIONE = "errore in mysql :";
	private  int row = 0;



	// use this function on controller after you had check the email
	// add an user on db after registration
	// prende i dati dall'oggetto che gli abbiamo passato
	@Override
	public  boolean inserisciUtente(TempUser tu)  {



		LocalDate d = tu.getDataDiNascitaT();


		query = "INSERT INTO `USERS`"
				+ "(`idRuolo`,"
				+ "`Nome`,"
				+ "`Cognome`,"
				+ "`Email`,"
				+ "`pwd`,"
				+ " `descrizione`,"
				+ "`DataNascita`)"
				+ "VALUES"
				+ "(?,?,?,?,?,?,?)";

		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {


			prepQ.setString(1,tu.getIdRuoloT().substring(0,1));
			prepQ.setString(2, tu.getNomeT());
			prepQ.setString(3, tu.getCognomeT());
			prepQ.setString(4, tu.getEmailT());
			prepQ.setString(5, tu.getPasswordT());
			prepQ.setString(6, tu.getDescrizioneT());
			prepQ.setDate(7,Date.valueOf(d));
			row=prepQ.executeUpdate();


		} catch (SQLException e) {
			Logger.getLogger("createUser").log(Level.INFO, ECCEZIONE, e);

		}


		return row==1;

	}

	@Override
	public ObservableList<TempUser> getUserData() throws SQLException {
		ObservableList<TempUser> lista=FXCollections.emptyObservableList();
		query="select * from USERS";
		try(Connection conn=ConnToDb.connectionToDB();
		PreparedStatement preQ=conn.prepareStatement(query)){

			ResultSet rs= preQ.executeQuery();
			while (rs.next())
			{
				TempUser tu=new TempUser();
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
		query="delete from USERS where idUser=? or email=? or pwd=?";

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
	public List<TempUser> userList(TempUser u) throws CsvValidationException, IOException, SQLException {
		List<TempUser> lista=FXCollections.emptyObservableList();
		query="select * from USERS";
		try(Connection conn=ConnToDb.connectionToDB();
			PreparedStatement preQ=conn.prepareStatement(query)){

			ResultSet rs= preQ.executeQuery();
			while (rs.next())
			{
				TempUser tu=new TempUser();
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
	public void initializza() throws CsvValidationException, IOException, IdException {
		Logger.getLogger("inizializza user dao").log(Level.INFO,"initialize user dao");
	}




	public  String getRuolo(User u) throws SQLException {

		String r = "";
		query = "SELECT idRuolo FROM USERS where Email = ? and pwd=?";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {
			prepQ.setString(1, u.getEmail());
			prepQ.setString(2,u.getPassword());

			ResultSet rs = prepQ.executeQuery();
			while (rs.next()) {
				r = rs.getString("idRuolo");

			}

		} catch (SQLException e) {
			Logger.getLogger("get ruolo user").log(Level.INFO, ECCEZIONE, e);

		}
		u.setIdRuolo(r);


		return r;

	}

	// this function check if you have changed password successfully 


	// delete a user from db  terzo caso d'uso

	public  boolean deleteUser(User user) throws SQLException {


		query = "DELETE FROM USERS WHERE Email = ? or idUser=? ";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {


			prepQ.setString(1, user.getEmail());
			prepQ.setInt(2, user.getId());
			row = prepQ.executeUpdate();




		} catch (SQLException e) {
			Logger.getLogger("delete user").log(Level.INFO, ECCEZIONE, e);

		}


		Logger.getLogger("delete user ruolo").log(Level.INFO, "cancello user id{0}", user.getId());


		if (row == 1)
			state = true;
		return state;

	}

	public  boolean deleteTempUser(TempUser uT) throws SQLException {

		query = "DELETE FROM USERS WHERE Email = ? or idUser=?";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			prepQ.setString(1, uT.getEmailT());
			prepQ.setInt(2,uT.getId());
			row = prepQ.executeUpdate();
			if (row == 1)
				state = true;

		} catch (SQLException e) {
			Logger.getLogger("delete user").log(Level.INFO, ECCEZIONE, e);

		}
		Logger.getLogger("delete user okr").log(Level.INFO, "user deleted ");

		return state;
	}


	public  TempUser getTempUserSingolo(TempUser uT)  {


		query = "SELECT * FROM USERS where idUser = ? or email=?";
		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {

			prepQ.setInt(1, uT.getId());
			prepQ.setString(2,uT.getEmailT());

			ResultSet rs = prepQ.executeQuery();
			while (rs.next()) {
				uT.setId(rs.getInt(1));
				uT.setIdRuoloT(rs.getString(2));
				uT.setNomeT(rs.getString(3));
				uT.setCognomeT(rs.getString(4));
				uT.setEmailT(rs.getString(5));
				uT.setPasswordT(rs.getString(6));
				uT.setDescrizioneT(rs.getString(7));
				uT.setDataDiNascitaT(rs.getDate(8).toLocalDate());


			}
		} catch (SQLException e) {
			Logger.getLogger("get single temp user").log(Level.INFO, ECCEZIONE, e);

		}

		return uT;
	}

	public  User aggiornaUtente(User u,String vecchiaEmail)  {


		query = "UPDATE USERS set idRuolo=? , Nome=? , Cognome=? , Email=? , pwd=? , descrizione=? , dataNascita=? where idUser=? or Email=?";


		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {


			// setto i vari dati


			prepQ.setString(1, u.getIdRuolo().substring(0, 1));
			prepQ.setString(2, u.getNome());
			prepQ.setString(3, u.getCognome());
			prepQ.setString(4, u.getEmail());
			prepQ.setString(5, u.getPassword());
			prepQ.setString(6, u.getDescrizione());
			prepQ.setString(7, u.getDataDiNascita().toString());
			prepQ.setInt(8, u.getId());
			prepQ.setString(9,vecchiaEmail);


			prepQ.executeUpdate();

		} catch (SQLException e) {
			Logger.getLogger("aggiorna utente").log(Level.INFO, ECCEZIONE, e);

		}

		return u;
	}



	public  ObservableList<TempUser> getUserList() throws SQLException {


		query = "select  * from USERS";
		ObservableList<TempUser> list= FXCollections.observableArrayList();

		try (Connection conn = ConnToDb.connectionToDB();
			 PreparedStatement prepQ = conn.prepareStatement(query)) {
			ResultSet rs = prepQ.executeQuery();
			while (rs.next()) {
				TempUser tu=new TempUser();

				tu.setId(rs.getInt(1));
				tu.setIdRuoloT(rs.getString(2));
				tu.setNomeT(rs.getString(3));
				tu.setCognomeT(rs.getString(4));
				tu.setEmailT(rs.getString(5));
				tu.setPasswordT(rs.getString(6));
				tu.setDescrizioneT(rs.getString(7));
				tu.setDataDiNascitaT((rs.getDate(8).toLocalDate()));




				list.add(tu);



			}
		} catch (SQLException e) {
			Logger.getLogger("user list").log(Level.INFO, "user list {0}.", e.toString());

		}
		return list;
	}




}

