package web.bean;



import javafx.collections.ObservableList;
import laptop.model.Pagamento;


public class PagamentoBean  {
	/**
	 * 
	 */
	private int idB;
	private String metodoB;
	private int esitoB; //0 ok 1 fallito
	private String nomeUtenteB;
	private float ammontareB;
	private String tipoB;
	private int idOggettoB;
	private String emailB;
	private String categoriaB;
	private ObservableList<Pagamento> listaPagamentiB;
	public int getIdB() {
		return idB;
	}
	public void setIdB(int idB) {
		this.idB = idB;
	}
	public String getMetodoB() {
		return metodoB;
	}
	public void setMetodoB(String metodoB) {
		this.metodoB = metodoB;
	}
	public int getEsitoB() {
		return esitoB;
	}
	public void setEsitoB(int esitoB) {
		this.esitoB = esitoB;
	}
	public String getNomeUtenteB() {
		return nomeUtenteB;
	}
	public void setNomeUtenteB(String nomeUtenteB) {
		this.nomeUtenteB = nomeUtenteB;
	}
	public float getAmmontareB() {
		return ammontareB;
	}
	public void setAmmontareB(float ammontareB) {
		this.ammontareB = ammontareB;
	}
	public String getTipoB() {
		return tipoB;
	}
	public void setTipoB(String tipoB) {
		this.tipoB = tipoB;
	}
	public int getIdOggettoB() {
		return idOggettoB;
	}
	public void setIdOggettoB(int idOggettoB) {
		this.idOggettoB = idOggettoB;
	}
	public ObservableList<Pagamento> getListaPagamentiB() {
		return listaPagamentiB;
	}
	public void setListaPagamentiB(ObservableList<Pagamento> listaPagamentiB) {
		this.listaPagamentiB = listaPagamentiB;
	}


    public String getEmailB() {
        return emailB;
    }

    public void setEmailB(String emailB) {
        this.emailB = emailB;
    }

    public String getCategoriaB() {
        return categoriaB;
    }

    public void setCategoriaB(String categoriaB) {
        this.categoriaB = categoriaB;
    }
	@Override
	public String toString() {
		return "Pagamento [id=" + idB + ", metodo=" + metodoB + ", nomeUtente=" + nomeUtenteB
				+ ", ammontare=" + ammontareB + ", tipo=" + tipoB + ", idOggetto="+idOggettoB+"]";
	}
}
