package web.bean;


public class SystemBean {

		
		 private int idB;
		 private String typeB;
		 private boolean isLoggedB ;
		 private boolean isSearchB;
		 private boolean isPickupB;
		 private float spesaTB;// usato per avere importo totale 
		 private int quantitaB; //usato per avere quantita oggetto che compro
		 private String metodoPB; //usato per vedere se contanti o cc
		 private boolean negozioSelezionatoB;// per vedere se download o negozio
		 private String titoloB;
		 private String tipoModifica;
		 
		 
		 private static SystemBean userInstance = null;

		    private SystemBean() {}

		    public static SystemBean getInstance() {
		        if (userInstance == null) {
		          
		        	userInstance = new SystemBean();
		                }
		       
		        return userInstance;
		    }
			
		 
		public int getIdB() {
			return idB;
		}

		public void setIdB(int idB) {
			this.idB = idB;
		}

		public String getTypeB() {
			return typeB;
		}

		public void setTypeB(String typeB) {
			this.typeB = typeB;
		}

		public boolean isLoggedB() {
			return isLoggedB;
		}

		public void setLoggedB(boolean isLoggedB) {
			this.isLoggedB = isLoggedB;
		}

		public boolean isSearchB() {
			return isSearchB;
		}

		public void setSearchB(boolean isSearchB) {
			this.isSearchB = isSearchB;
		}

		public boolean isPickupB() {
			return isPickupB;
		}

		public void setPickupB(boolean isPickupB) {
			this.isPickupB = isPickupB;
		}

		public float getSpesaTB() {
			return spesaTB;
		}

		public void setSpesaTB(float spesaTB) {
			this.spesaTB = spesaTB;
		}

		public int getQuantitaB() {
			return quantitaB;
		}

		public void setQuantitaB(int quantitaB) {
			this.quantitaB = quantitaB;
		}

		public String getMetodoPB() {
			return metodoPB;
		}

		public void setMetodoPB(String metodoPB) {
			this.metodoPB = metodoPB;
		}

		public boolean isNegozioSelezionatoB() {
			return negozioSelezionatoB;
		}

		public void setNegozioSelezionatoB(boolean negozioSelezionatoB) {
			this.negozioSelezionatoB = negozioSelezionatoB;
		}

		public String getTitoloB() {
			return titoloB;
		}

		public void setTitoloB(String titoloB) {
			this.titoloB = titoloB;
		}

		

		public void setTypeAsBook() {
			this.typeB="libro";
			
		}

		public void setTypeAsDaily() {
			this.typeB="giornale";
			
		}

		public void setTypeAsMagazine() {
			this.typeB="rivista";
			
		}


    public String getTipoModifica() {
        return tipoModifica;
    }

    public void setTipoModifica(String tipoModifica) {
        this.tipoModifica = tipoModifica;
    }
}
