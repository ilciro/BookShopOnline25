package web.bean;

import java.sql.Date;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import laptop.model.raccolta.Raccolta;


public class RivistaBean {
	private int idB;
	private Exception mexB;
	private ObservableList<Raccolta> listaRivisteB;
	private String listaCategorieB;
	private String titoloB;
	private String tipologiaB;
	private String autoreB;
	private String linguaB;
	public Exception getMexB() {
		return mexB;
	}




	public void setMexB(Exception mexB) {
		this.mexB = mexB;
	}




	public ObservableList<Raccolta> getListaRivisteB() {
		return listaRivisteB;
	}




	public void setListaRivisteB(ObservableList<Raccolta> listaRivisteB) {
		this.listaRivisteB = listaRivisteB;
	}




	public String getListaCategorieB() {
		return listaCategorieB;
	}




	public void setListaCategorieB(String listaCategorieB) {
		this.listaCategorieB = listaCategorieB;
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




	public String getAutoreB() {
		return autoreB;
	}




	public void setAutoreB(String autoreB) {
		this.autoreB = autoreB;
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




	public String getDescrizioneB() {
		return descrizioneB;
	}




	public void setDescrizioneB(String descrizioneB) {
		this.descrizioneB = descrizioneB;
	}




	public LocalDate getDataPubbB() {
		return dataPubbB;
	}




	public void setDataPubbB(LocalDate dataPubbB) {
		this.dataPubbB = dataPubbB;
	}




	public int getDispB() {
		return dispB;
	}




	public void setDispB(int dispB) {
		this.dispB = dispB;
	}




	public float getPrezzoB() {
		return prezzoB;
	}




	public void setPrezzoB(float prezzoB) {
		this.prezzoB = prezzoB;
	}




	public int getCopieRimB() {
		return copieRimB;
	}




	public void setCopieRimB(int copieRimB) {
		this.copieRimB = copieRimB;
	}




	public Date getDataB() {
		return dataB;
	}




	public void setDataB(Date dataB) {
		this.dataB = dataB;
	}




	private String editoreB;
	private String descrizioneB;
	private LocalDate dataPubbB;
	private int dispB;
	private float prezzoB;
	private int copieRimB;
	private Date dataB;

	
	
	
	public String elencoCategorie()
	{
		String s= "SETTIMANALE";
		s+="\n";
		s+="BISETTIMANALE" ;
		s+="\n";
		s+= "MENSILE";
		s+="\n";
		s+= "BIMESTRALE" ;
		s+="\n";
		s+="TRIMESTRALE" ;	
		s+="\n";
		s+= "ANNUALE" ;
		s+="\n";

		s+= "ESTIVO" ;
		s+="\n";

		s+= "INVERNALE" ;
		s+="\n";

		s+= "SPORTIVO" ;
		s+="\n";

		s+= "CINEMATOGRAFICA" ; 
		s+="\n";

		s+= "GOSSIP" ;
		s+="\n";

		s+= "TELEVISIVO" ;
		s+="\n";

		s+= "MILITARE" ;
		s+="\n";

		s+= "INFORMATICA";
		s+="\n";

		return s;
	}




	public int getIdB() {
		return idB;
	}




	public void setIdB(int idB) {
		this.idB = idB;
	}
	
}
