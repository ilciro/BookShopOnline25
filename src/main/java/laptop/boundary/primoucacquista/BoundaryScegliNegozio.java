package laptop.boundary.primoucacquista;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.primoucacquista.ControllerScegliNegozio;
import laptop.exception.IdException;
import laptop.model.Negozio;


public class BoundaryScegliNegozio {

	@FXML
	private Label labelL;
	@FXML
	private Pane pane;
	@FXML
	private RadioButton radio1;
	@FXML
	private RadioButton radio2;
	@FXML
	private RadioButton radio3;
	@FXML
	private RadioButton radio4;
	@FXML
	private Button buttonV;
	@FXML
	private VBox vbox;
	@FXML
	private RadioButton databaseButton;
	@FXML
	private RadioButton fileButton;
	@FXML
	private RadioButton memoriaButton;
	@FXML
	private ToggleGroup toggleGroupP;
	@FXML
	private ToggleGroup toggleGroupB;

	private final ControllerScegliNegozio cSN;
	private ObservableList<Negozio> listOfNegozi;
	private static final String ALERTITLE = "Ordine ricevuto!";
	private static final String ALERTHEADERTEXT = "Il negozio che hai selezionato ha ricevuto il tuo ordine. \n Presentati dopo 3 giorni lavorativi per ritirare il tuo acquisto";
	private static final String ALERTCONTENTEXT = "Ricordati di presentarti con le credenziali con le quali accedi ed avrai consegnato il tuo ordine";
	private static final String WARNINGTITLE = "Negozio chiuso o non disponibile per il ritiro";
	private static final String WARNINGHEADERTEXT = "Il negozio seleziopnato non e al momento pronto per questo tipo di operazioni";
	private static final String WARNINGCONTENTTEXT = "Torna indietro e seleziona un'altro negozio fra quelli che ti vengono mostrati!";
	private static final String HOMEPAGE = "view/primoucacquista/homePageFinale.fxml";
	private String type = "";


	protected Scene scene;
	protected Alert alert;
	protected Alert alertE;
	protected FXMLLoader loader;


	public BoundaryScegliNegozio() {
		cSN = new ControllerScegliNegozio();
	}

	@FXML
	private void database() throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
		listOfNegozi = cSN.getNegozi("database");
		radio1.setText(listOfNegozi.get(0).getNome());
		radio2.setText(listOfNegozi.get(1).getNome());
		radio3.setText(listOfNegozi.get(2).getNome());
		radio4.setText(listOfNegozi.get(3).getNome());
	}

	@FXML
	private void file() throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
		listOfNegozi = cSN.getNegozi("file");
		radio1.setText(listOfNegozi.get(0).getNome());
		radio2.setText(listOfNegozi.get(1).getNome());
		radio3.setText(listOfNegozi.get(2).getNome());
		radio4.setText(listOfNegozi.get(3).getNome());
	}

	@FXML
	private void memoria() throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {
		listOfNegozi = cSN.getNegozi("memoria");
		radio1.setText(listOfNegozi.get(0).getNome());
		radio2.setText(listOfNegozi.get(1).getNome());
		radio3.setText(listOfNegozi.get(2).getNome());
		radio4.setText(listOfNegozi.get(3).getNome());
	}

	@FXML
	private void verifica() throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {

		if (databaseButton.isSelected()) type = "database";
		if (fileButton.isSelected()) type = "file";
		if (memoriaButton.isSelected()) type = "memoria";


		if (radio1.isSelected()) checkNegozio1(type);
		if (radio2.isSelected()) checkNegozio2(type);
		if (radio3.isSelected()) checkNegozio3(type);
		if (radio4.isSelected()) checkNegozio4(type);


	}


	private  void checkNegozio1(String type) throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {

		verificaDispApertura(type,1);



	}

	private void checkNegozio2(String type) throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {
		verificaDispApertura(type,2);



	}

	private void checkNegozio3(String type) throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {
		verificaDispApertura(type,3);



	}

	private void checkNegozio4(String type) throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {

		verificaDispApertura(type,4);
	}




private void negozioGiusto() throws IOException {
	alert = new Alert(AlertType.CONFIRMATION);
	alert.setTitle(ALERTITLE);
	alert.setHeaderText(ALERTHEADERTEXT);
	alert.setContentText(ALERTCONTENTEXT);
	Optional<ButtonType> result = alert.showAndWait();

	if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

			Stage stage;
			Parent root;
			stage = (Stage) buttonV.getScene().getWindow();
			loader = new FXMLLoader(getClass().getClassLoader().getResource(HOMEPAGE));
			root = loader.load();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}

	private void verificaDispApertura(String type, int idNegozio) throws CsvValidationException, SQLException, IOException, IdException, ClassNotFoundException {

		listOfNegozi = cSN.getNegozi(type);
		boolean statusA = false;
		boolean statusB = false;

		switch (idNegozio)
		{
			case 1->{
				statusA = cSN.isOpen(type,1);
				statusB =cSN.isValid(type,1);
			}
			case 2->{
				statusA = cSN.isOpen(type,2);
				statusB =cSN.isValid(type,2);
			}
			case 3->{
				statusA = cSN.isOpen(type,3);
				statusB =cSN.isValid(type,3);
			}
			case 4->{
				statusA = cSN.isOpen(type,4);
				statusB =cSN.isValid(type,4);
			}
			default -> Logger.getLogger("check negozio").log(Level.SEVERE," error with shop choice");

		}





		if (statusA && statusB) { negozioGiusto();

		} else {

			alertE = new Alert(AlertType.WARNING);
			alertE.setTitle(WARNINGTITLE);
			alertE.setHeaderText(WARNINGHEADERTEXT);
			alertE.setContentText(WARNINGCONTENTTEXT);


	}

	}
}









