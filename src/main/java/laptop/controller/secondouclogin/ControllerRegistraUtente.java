package laptop.controller.secondouclogin;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import laptop.controller.ControllerSystemState;
import laptop.database.secondouclogin.users.CsvUtente;
import laptop.database.secondouclogin.users.MemoriaUtente;
import laptop.database.secondouclogin.users.PersistenzaUtente;
import laptop.database.secondouclogin.users.UsersDao;
import laptop.exception.IdException;
import laptop.model.user.TempUser;


public class ControllerRegistraUtente {
	private Boolean state=false;
	private final TempUser tu;
	private PersistenzaUtente pU;

	private static final ControllerSystemState vis=ControllerSystemState.getInstance();


	//usato per persistenza
	private String type="";



	private String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean registra(String n, String c, String email, String pwd,String desc, LocalDate localDate,String ruolo) {

		vis.setTipoModifica("insert");

		switch (getType())
		{
			case "database"->pU=new UsersDao();
			case "file"->pU=new CsvUtente();
			case "memoria"->pU=new MemoriaUtente();
			default -> Logger.getLogger("registra").log(Level.SEVERE,"error in persistency");
		}
			pU.initializza();

		//controllo se utente presente()

		for (int i=0;i<pU.getUserData().size();i++)
		{
			if(nomePresente(pU.getUserData().get(i).getNomeT(),n)&&
			cognomePresente(pU.getUserData().get(i).getCognomeT(),c)&&
			emailPresente(pU.getUserData().get(i).getEmailT(),email)) try {
                throw new IdException(" user already inserted!!");
            } catch (IdException e) {
               Logger.getLogger("registraUtente").log(Level.SEVERE,"duplicated user {0}",e);
            }


        }
		if(checkData(n,c,email,pwd))
		{
			tu.setNomeT(n);
			tu.setCognomeT(c);
			tu.setEmailT(email);
			tu.setPasswordT(pwd);
			tu.setDescrizioneT(desc);
			tu.setDataDiNascitaT(localDate);
			tu.setIdRuoloT(ruolo.substring(0,1));
			state=pU.inserisciUtente(tu);


		}
		Logger.getLogger("registra").log(Level.INFO,"user registered with email {0}",tu.getEmailT());




		


		return state;
	}

	private boolean nomePresente(String nomeL,String nome){return nomeL.equals(nome);}
	private boolean cognomePresente(String cognomeL,String cognome){return cognomeL.equals(cognome);}
	private boolean emailPresente(String emailL,String email){return emailL.equals(email);}
	
	//le chiamo protected perchele uso nel controller stesso e basta 
	private boolean checkData (String n, String c, String email, String pwd)
	// controll  all data
	{
		if(checkEmail(email) && checkPassword(pwd) && checkNomeCognome(n,c))
		{
			state=true;
		}
		return state;
	}

	private boolean checkEmail(String email)
	{
		 Pattern pattern;

		String emailRegex;
		emailRegex= "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
                  
		pattern = Pattern.compile(emailRegex); 
		if (email == null) 
			return false; 
		return pattern.matcher(email).matches();
	}

	private boolean checkPassword(String pwd )
	{
		if(pwd.length()>=8 ) {
			state= true;
		}
		return state;
	}

	private boolean checkNomeCognome(String n, String c)
	{
		if (n != null && c != null)
		{
			state= true;
		}
		return state;
	}
	
	public ControllerRegistraUtente()
	{
		Logger.getLogger("Test Costruttore").log(Level.INFO, "Costruttore ControllerRegistaUtente");
		tu=new TempUser();
	}


	// TO DO: checkData o lo facciamo diretti in mysql
}
