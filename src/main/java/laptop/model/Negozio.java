package laptop.model;


import java.io.Serial;
import java.io.Serializable;

public class Negozio implements Serializable {
	@Serial
	private static final long serialVersionUID = 2L;


	private String nome;
	private String via;
	private Boolean isValid;
	private Boolean isOpen;
	private int id;

	
	public Negozio(String nome, String via, Boolean isValid, Boolean isOpen) {

		this.nome = nome;
		this.via = via;
		this.isValid = isValid;
		this.isOpen = isOpen;
	}
	
	public Negozio() {
		this(null,null,null,null);
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {

		this.isValid = isValid;
	}
	public Boolean getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Boolean isOpen)  {

		this.isOpen = isOpen;
	}

	
	
	@Override
	public String toString() {
		return "Negozio [nome=" + nome + ", via=" + via + ", isOpen=" + isOpen + ", isValid=" + isValid+"]";
	}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
