package web.bean;

import java.sql.Date;
import java.time.LocalDate;

import javafx.collections.ObservableList;
import laptop.model.raccolta.Raccolta;

public class GiornaleBean {
	private Exception mexB;
	private ObservableList<Raccolta> listaGiornaliB;
	private String giornaleB="giornale";
	private String  titoloB;
	private String tipologiaB;
	private String linguaB;
	private String editoreB;
	private LocalDate dataPubbB;
	private int copieRimanentiB;
	private int disponibilitaB;
	private int idB;
	private Date dataB;
	private float prezzoB;
	public Exception getMexB() {
		return mexB;
	}
	public void setMexB(Exception mexB) {
		this.mexB = mexB;
	}
	public ObservableList<Raccolta> getListaGiornaliB() {
		return listaGiornaliB;
	}
	public void setListaGiornaliB(ObservableList<Raccolta> listaGiornaliB) {
		this.listaGiornaliB = listaGiornaliB;
	}
	public String getGiornaleB() {
		return giornaleB;
	}
	public void setGiornaleB(String giornaleB) {
		this.giornaleB = giornaleB;
	}
	public String getTitoloB() {
		return titoloB;
	}
	public void setTitoloB(String titoloB) {
		this.titoloB = titoloB;
	}
	public String getTipologiaB() {
		return tipologiaB;
	}
	public void setTipologiaB(String tipologiaB) {
		this.tipologiaB = tipologiaB;
	}
	public String getLinguaB() {
		return linguaB;
	}
	public void setLinguaB(String linguaB) {
		this.linguaB = linguaB;
	}
	public String getEditoreB() {
		return editoreB;
	}
	public void setEditoreB(String editoreB) {
		this.editoreB = editoreB;
	}
	public LocalDate getDataPubbB() {
		return dataPubbB;
	}
	public void setDataPubbB(LocalDate dataPubbB) {
		this.dataPubbB = dataPubbB;
	}
	public int getCopieRimanentiB() {
		return copieRimanentiB;
	}
	public void setCopieRimanentiB(int copieRimanentiB) {
		this.copieRimanentiB = copieRimanentiB;
	}
	public int getDisponibilitaB() {
		return disponibilitaB;
	}
	public void setDisponibilitaB(int disponibilitaB) {
		this.disponibilitaB = disponibilitaB;
	}
	public int getIdB() {
		return idB;
	}
	public void setIdB(int idB) {
		this.idB = idB;
	}
	public Date getDataB() {
		return dataB;
	}
	public void setDataB(Date dataB) {
		this.dataB = dataB;
	}
	public float getPrezzoB() {
		return prezzoB;
	}
	public void setPrezzoB(float prezzoB) {
		this.prezzoB = prezzoB;
	}
	


}
