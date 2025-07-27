package laptop.controller;


// this is singelton battona 
// know evreything about the system

public class ControllerSystemState {

	private int idLibro;
	private int idGiornale;
	private int idRivista;
	private int idUtente;
	private String type;
	private boolean isLogged ;
	private boolean isSearch;
	private boolean isPickup;
	private float spesaT;// usato per avere importo totale
	private int quantita; //usato per avere quantita oggetto che compro
	private String metodoP;	//usato per vedere se contanti o cc
	private String tipologiaApplicazione;


	public String getTipologiaApplicazione() {
		return tipologiaApplicazione;
	}

	public void setTipologiaApplicazione(String tipologiaApplicazione) {
		this.tipologiaApplicazione = tipologiaApplicazione;
	}


	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public int getIdGiornale() {
		return idGiornale;
	}

	public void setIdGiornale(int idGiornale) {
		this.idGiornale = idGiornale;
	}

	public int getIdRivista() {
		return idRivista;
	}

	public void setIdRivista(int idRivista) {
		this.idRivista = idRivista;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}






	private String tipoModifica;
	 
	 private static ControllerSystemState instance = null;

	    private ControllerSystemState() {}

	    public static ControllerSystemState getInstance() {
	        if (instance == null) {
	          
	        	instance = new ControllerSystemState();
	                }
	       
	        return instance;
	    }
	 public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public float getSpesaT() {
		return spesaT;
	}

	public void setSpesaT(float spesaT) {
		this.spesaT = spesaT;
	}

	


	public void setTypeAsBook()
	{
		this.type = "libro";
	}
	public void setTypeAsMagazine() { this.type = "rivista";	}
	public void setTypeAsDaily()
	{
		this.type = "giornale";
	}
	public String getType()
	{
		return type;
	}

	public boolean getIsLogged() {
		return isLogged;
	}

	public void setIsLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	
	public boolean getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}

	public boolean getIsPickup() {
		return isPickup;
	}

	public void setPickup(boolean isPickup) {
		this.isPickup = isPickup;
	}

	public String getMetodoP() {
		return metodoP;
	}

	public void setMetodoP(String metodoP) {
		this.metodoP = metodoP;
	}

    public String getTipoModifica() {
        return tipoModifica;
    }

    public void setTipoModifica(String tipoModifica) {
        this.tipoModifica = tipoModifica;
    }




}
