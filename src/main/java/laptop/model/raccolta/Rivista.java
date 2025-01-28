package laptop.model.raccolta;

import java.awt.Desktop;
import java.io.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;



public class Rivista implements Raccolta, Serializable {
	@Serial
	private static final long serialVersionUID = 2L;


	private String titolo;
	private String categoria;
	private String autore;
	private String lingua;
	private String editore;
	private String descrizione;
	private LocalDate dataPubb;
	private int disp;
	private float prezzo;
	private int copieRim;
	private int id;

	private final transient ResourceBundle rbTitoli=ResourceBundle.getBundle("configurations/titles");
	private String [] infoGenerali=new String[5];
	private static final String TITOLOR="titolo15";
	private static final String DSTPATH="dstPath";




    public Rivista(String [] info,String descrizione, LocalDate dataPubb2, int disp, float prezzo, int copieRim,int id) {
		this.setInfoGenerali(info);
		this.descrizione = descrizione;
		this.dataPubb = dataPubb2;
		this.disp = disp;
		this.prezzo = prezzo;
		this.copieRim = copieRim;
		this.id = id;
		this.titolo=info[0];
		this.categoria=info[5];
		this.editore=info[2];
		this.lingua=info[4];
		this.autore=info[3];
	}

	public Rivista() {}

	public String getTitolo() {
		return this.titolo;
	}
	public String getAutore() {
		return this.autore;
	}
	public String getLingua() {
		return this.lingua;
	}
	public String getEditore() {
		return this.editore;
	}
	public String getDescrizione() {
		return this.descrizione;
	}
	public LocalDate getDataPubb() {
		return this.dataPubb;
	}
	public String getCategoria(){return this.categoria;}
	public int getDisp() {
		return this.disp;
	}
	public float getPrezzo() {
		return this.prezzo;
	}
	public int getCopieRim() {
		return this.copieRim;
	}
	public int getId() {
		return this.id;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public void setCategoria(String categoria) {
		switch (categoria){
		
		case "SETTIMANALE": 
			this.categoria = CategorieRivista.SETTIMANALE.toString();
			break;

		case "BISETTIMANALE": 
			this.categoria = CategorieRivista.BISETTIMANALE.toString();
			break;

		case "MENSILE" : 
			this.categoria = CategorieRivista.MENSILE.toString();
			break;

		case "BIMESTRALE" : 
			this.categoria = CategorieRivista.BIMESTRALE.toString();
			break;

		case "TRIMESTRALE" : 
			this.categoria = CategorieRivista.TRIMESTRALE.toString();
			break;

		case "ANNUALE" : 
			this.categoria = CategorieRivista.ANNUALE.toString();
			break;

		case "ESTIVO" : 
			this.categoria = CategorieRivista.ESTIVO.toString();
			break;

		case "INVERNALE" : 
			this.categoria = CategorieRivista.INVERNALE.toString();
			break;

		case "SPORTIVO" : 
			this.categoria = CategorieRivista.SPORTIVO.toString();
			break;

		case "CINEMATOGRAFICA" : 
			this.categoria = CategorieRivista.CINEMATOGRAFICA.toString();
			break;

		case "GOSSIP" : 
			this.categoria = CategorieRivista.GOSSIP.toString();
			break;

		case "TELEVISIVO" : 
			this.categoria = CategorieRivista.TELEVISIVO.toString();
			break;

		case "MILITARE" : 
			this.categoria = CategorieRivista.MILITARE.toString();
			break;

		case "INFORMATICA" : 
			this.categoria = CategorieRivista.INFORMATICA.toString();
			break;

		default :
			this.categoria = null;
			break;
		}
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public void setLingua(String lingua) {
		this.lingua = lingua;
	}
	public void setEditore(String editore) {
		this.editore = editore;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public void setDataPubb(LocalDate dataPubb) {
		this.dataPubb = dataPubb;
	}
	public void setDisp(int disp) {
		this.disp = disp;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public void setCopieRim(int copieRim) {
		this.copieRim = copieRim;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public void scarica(int i) throws IOException {
		Document document=new Document();
		try{
			PdfWriter writer=PdfWriter.getInstance(document,new FileOutputStream(rbTitoli.getString(DSTPATH)+ rbTitoli.getString(TITOLOR)));
			document.open();
			document.addTitle("Rivista ");
			document.add(new Paragraph("""
                    Rivista/Magazine not avalaible
                    Nam ultricies efficitur magna, sit amet luctus magna luctus volutpat
                    Pellentesque facilisis lacinia mi, nec posuere justo pharetra non:
                    Nulla vel risus sit amet risus aliquam auctor.
                    Nunc viverra felis sit amet nulla faucibus, sed euismod neque lacinia.
                    Integer pharetra sapien sed odio mattis, sed efficitur justo blandit.
                    Praesent in quam non neque hendrerit pulvinar ut quis tortor.
                    Maecenas nec convallis nunc.
                    Donec ultricies malesuada mauris ac accumsan.
                    Vestibulum auctor est ac laoreet egestas.
                    Nam malesuada in massa eu venenatis."""));

			readPdf();
			document.close();
			writer.close();




		}catch (DocumentException | IOException e)
		{
			Logger.getLogger("create pdf").log(Level.SEVERE,"pdf not created");
		}
	}
	@Override
	public void leggi(int i) throws IOException, DocumentException {
		if (Desktop.isDesktopSupported()) {
			new Thread(() -> {
				try {
					Desktop.getDesktop().open(new File(rbTitoli.getString(DSTPATH)+rbTitoli.getString(TITOLOR)));
				} catch (IOException e) {
					Logger.getLogger("open file").log(Level.SEVERE, "open error");				}
			}).start();
		}


		
	}
	public String [] getInfoGenerali() {
		return infoGenerali;
	}
	public void setInfoGenerali(String [] infoGenerali) {
		this.infoGenerali = infoGenerali;
	}

	private void readPdf() throws IOException, DocumentException {

		Document document = new Document();

		PdfReader reader = new PdfReader(rbTitoli.getString("srcPath") + rbTitoli.getString(TITOLOR));
		PdfCopy copy=new PdfCopy(document,new FileOutputStream(rbTitoli.getString(DSTPATH)+ rbTitoli.getString(TITOLOR)));
		document.open();

		int pages = reader.getNumberOfPages();
		for (int i = 1; i <= pages; i++) {
			copy.addPage(copy.getImportedPage(reader,i));

		}


		reader.close();
		document.close();

	}
	
}
