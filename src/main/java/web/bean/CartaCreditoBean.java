package web.bean;

import java.util.Date;


import javafx.collections.ObservableList;
import laptop.model.CartaDiCredito;

public class CartaCreditoBean {
	private String nomeB;
	private String cognomeB;
	private String numeroCCB;
	private Date dataScadB;
	private String civB;
	private ObservableList<CartaDiCredito> listaCarteCreditoB;
	private float prezzoTransazioneB;
	public String getNomeB() {
		return nomeB;
	}
	public void setNomeB(String nomeB) {
		this.nomeB = nomeB;
	}
	public String getCognomeB() {
		return cognomeB;
	}
	public void setCognomeB(String cognomeB) {
		this.cognomeB = cognomeB;
	}
	public String getNumeroCCB() {
		return numeroCCB;
	}
	public void setNumeroCCB(String numeroCCB) {
		this.numeroCCB = numeroCCB;
	}
	public Date getDataScadB() {
		return dataScadB;
	}
	public void setDataScadB(Date dataScadB) {
		this.dataScadB = dataScadB;
	}
	public String getCivB() {
		return civB;
	}
	public void setCivB(String civB) {
		this.civB = civB;
	}
	public ObservableList<CartaDiCredito> getListaCarteCreditoB() {
		return listaCarteCreditoB;
	}
	public void setListaCarteCreditoB(ObservableList<CartaDiCredito> listaCarteCreditoB) {
		this.listaCarteCreditoB = listaCarteCreditoB;
	}
	public float getPrezzoTransazioneB() {
		return prezzoTransazioneB;
	}
	public void setPrezzoTransazioneB(float prezzoTransazioneB) {
		this.prezzoTransazioneB = prezzoTransazioneB;
	}
	
	
	

}
