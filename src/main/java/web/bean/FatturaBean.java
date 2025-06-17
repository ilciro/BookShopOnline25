package web.bean;



public class FatturaBean  {
	/**
	 * 
	 */
	private String nomeB;
	private String cognomeB;
	private String indirizzoB;
	private String comunicazioniB;
	private Exception mexB;
	private float ammontare;
	private int idFattura;
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
	public String getIndirizzoB() {
		return indirizzoB;
	}
	public void setIndirizzoB(String indirizzoB) {
		this.indirizzoB = indirizzoB;
	}
	public String getComunicazioniB() {
		return comunicazioniB;
	}
	public void setComunicazioniB(String comunicazioniB) {
		this.comunicazioniB = comunicazioniB;
	}
	public Exception getMexB() {
		return mexB;
	}
	public void setMexB(Exception mexB) {
		this.mexB = mexB;
	}


    public float getAmmontare() {
        return ammontare;
    }

    public void setAmmontare(float ammontare) {
        this.ammontare = ammontare;
    }

    public int getIdFattura() {
        return idFattura;
    }

    public void setIdFattura(int idFattura) {
        this.idFattura = idFattura;
    }

	@Override
	public String toString() {
		return "Fattura [nome=" + nomeB + ", cognome=" + cognomeB + ", via=" + indirizzoB + ", com=" + comunicazioniB + ", numero=" + idFattura
				+ ", ammontare=" + ammontare + "]";
	}
}
