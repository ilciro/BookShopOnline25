package laptop.model;


import java.io.Serial;
import java.io.Serializable;

public class Pagamento implements Serializable {
	@Serial
	private static final long serialVersionUID = 2L;

	public Pagamento(int i, String metodoP, String nome, float spesaT, String email, String categoria, int id) {
	this.idPag=i;
	this.metodo=metodoP;
	this.nomeUtente=nome;
	this.setAmmontare(spesaT);
	this.email=email;
	this.idOggetto=id;
	this.tipo=categoria;
	}
	public Pagamento(){}


	public int getIdPag() {
		return idPag;
	}

	public void setIdPag(int idPag) {
		this.idPag = idPag;
	}

	private int idPag;
	private String metodo;
	private String nomeUtente;
	private float ammontare;
	private String email;
	private String tipo;
	private int idOggetto;

	public float getAmmontare() {
		return ammontare;
	}

	public void setAmmontare(float ammontare) {
		this.ammontare = ammontare;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdOggetto() {
		return idOggetto;
	}

	public void setIdOggetto(int idOggetto) {
		this.idOggetto = idOggetto;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getNomeUtente() {
		return nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Pagamento [id=" + idPag + ", metodo=" + metodo + ", nomeUtente=" + nomeUtente
				+ ", ammontare=" + ammontare + ", tipo=" + tipo + ", idOggetto="+idOggetto+"]";
	}









	
	
	
}
