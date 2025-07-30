package laptop.boundary.primoucacquista;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerHomePage;
import laptop.exception.IdException;
import laptop.exception.PersistenzaException;

public class BoundaryHomePage implements Initializable {
	@FXML
	private Pane pane;
	@FXML
	private Label header;
	@FXML
	private Label labelScelta;
	@FXML
	private Button buttonL;
	@FXML
	private Button buttonG;
	@FXML
	private Button buttonR;
	@FXML
	private ImageView imageL;
	@FXML
	private ImageView imageG;
	@FXML
	private ImageView imageR;
	@FXML
	private ImageView imageLogin;
	@FXML
	private ImageView imageRicerca;
	@FXML
	private ImageView imageVisualizza;
	@FXML
	private ImageView imageGestione;
	@FXML
	private Button buttonRicerca;
	@FXML
	private Button buttonLogin;
	@FXML
	private Button buttonVisualizza;
	@FXML
	private Button buttonGestione;
	@FXML
	private HBox hbox1;
	@FXML
	private HBox hbox2;
	@FXML
	private RadioButton checkDataBase;
	@FXML
	private RadioButton checkFile;
	@FXML
	private RadioButton checkMemoria;
	@FXML
	private TextArea tArea;
	@FXML
	private Button buttonI;
	@FXML
	private ToggleGroup toggleGroup;

	private Stage stage;


	protected Scene scene;
	private static final String COMPRAVENDITA = "view/primoucacquista/compravendita.fxml";
	private static final String PERSISTENZANULLA = "persistenza nulla";

	private final ControllerSystemState vis = ControllerSystemState.getInstance();
	private ControllerHomePage cHP;


	private boolean checkPersitenza() {
		return checkDataBase.isSelected() || checkFile.isSelected() || checkMemoria.isSelected();
	}


	@FXML
	private void getListaGiornali() throws IOException, CsvValidationException, SQLException, ClassNotFoundException, IdException, PersistenzaException {
		vis.setTypeAsDaily();
		getLista(vis.getType());
	}

	@FXML
	private void getListaRiviste() throws IOException, CsvValidationException, SQLException, ClassNotFoundException, IdException, PersistenzaException {
		vis.setTypeAsMagazine();
		getLista(vis.getType());
	}

	@FXML
	private void getListaLibri() throws IOException, CsvValidationException, SQLException, ClassNotFoundException, IdException, PersistenzaException {

		vis.setTypeAsBook();
		getLista(vis.getType());

	}

	@FXML
	private void login() throws IOException {
		if (checkPersitenza()) {

			Stage stage;
			Parent root;
			stage = (Stage) buttonLogin.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/login.fxml")));
			stage.setTitle("Benvenuto nella schermata del login");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else throw new IOException("login " + PERSISTENZANULLA);


	}

	@FXML
	private void cerca() throws IOException {
		if (checkPersitenza()) {

			vis.setIsSearch(true);
			Stage stage;
			Parent root;
			stage = (Stage) buttonRicerca.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/ricerca.fxml")));
			stage.setTitle("Benvenuto nella schermata della ricerca ");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else throw new IOException("cerca " + PERSISTENZANULLA);
	}






	private void getPersistency() throws CsvValidationException, IOException, SQLException, ClassNotFoundException, IdException {
		tArea.clear();

		String type = "";

		if(checkDataBase.isSelected())type="database";
		if(checkFile.isSelected())type="file";
		if(checkMemoria.isSelected()) type="memoria";

		cHP.persistenza(type);


	}
	@FXML
	private void ordini() throws IOException {
		if (checkPersitenza()) {
			Stage stage;
			Parent root;
			stage = (Stage) buttonVisualizza.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/visualizzaOrdini.fxml")));
			stage.setTitle("Benvenuto nella schermata degli ordini ");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}else throw new IOException("ordini "+PERSISTENZANULLA);



	}
	@FXML
	private void gestione() throws IOException {
		if(checkPersitenza()) {
			Stage stage;
			Parent root;
			stage = (Stage) buttonGestione.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/visualizzaProfilo.fxml")));
			stage.setTitle("Benvenuto nella schermata di visualizzazione/gestione");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}else throw new IOException("gestione "+PERSISTENZANULLA);



	}
	@FXML
	private void indietro() throws IOException {
		Stage stage;
		Parent root;

		if(checkPersitenza() && cHP.logout()) {

				stage = (Stage) buttonI.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
				stage.setTitle("Benvenuto nella schermata di home page");
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		else throw new IOException("gestione "+PERSISTENZANULLA);



	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cHP = new ControllerHomePage();



		if(vis.getTipologiaApplicazione().equals("demo"))
		{
			imageRicerca.setVisible(false);
			imageGestione.setVisible(false);
			imageVisualizza.setVisible(false);
			checkDataBase.setVisible(false);
			checkFile.setVisible(false);
			buttonRicerca.setVisible(false);
			buttonGestione.setVisible(false);
			buttonVisualizza.setVisible(false);
			buttonI.setVisible(false);
			tArea.setVisible(false);
			header.setText(header.getText()+" versione demo con persistenza volatile");

			if(vis.getIsLogged())
			{
				buttonLogin.setVisible(false);
				imageLogin.setVisible(false);
				buttonI.setVisible(true);

			}



		}else {


			tArea.setEditable(false);

			tArea.setText(" logged \t " + vis.getIsLogged() + " \t ruolo \t :" + cHP.getRuolo() + " \t id \t" + cHP.getId());

			checkMemoria.setVisible(false);


			if (vis.getIsLogged()) {

				switch (cHP.getRuolo()) {

					case "SCRITTORE", "EDITORE", "E", "W", "S" -> {
						imageLogin.setVisible(false);
						labelScelta.setVisible(false);
						buttonLogin.setVisible(false);
						imageVisualizza.setVisible(true);
						buttonVisualizza.setVisible(true);
						//per image view funziona
						imageVisualizza.setTranslateX(-120);
						buttonVisualizza.setTranslateX(-120);

						imageGestione.setVisible(true);
						buttonGestione.setVisible(true);

						imageGestione.setTranslateX(-120);
						imageGestione.setTranslateX(-120);

						buttonGestione.setTranslateX(-120);

						buttonI.relocate(750, 220);


					}
					case "UTENTE", "U" -> {
						imageVisualizza.setVisible(true);
						buttonVisualizza.setVisible(true);
						imageLogin.setVisible(false);
						buttonLogin.setVisible(false);
						imageGestione.setVisible(true);
						buttonGestione.setVisible(true);
						imageVisualizza.setTranslateX(-120);
						buttonVisualizza.setTranslateX(-120);
						buttonI.relocate(630, 220);
					}
					case "N" -> {
						imageVisualizza.setVisible(false);
						buttonVisualizza.setVisible(false);
						imageGestione.setVisible(false);
						buttonGestione.setVisible(false);
						buttonI.setVisible(false);
					}
					default -> Logger.getLogger("initialize").log(Level.SEVERE, " type of user not correct");
				}
			} else {
				imageVisualizza.setVisible(false);
				buttonVisualizza.setVisible(false);
				imageGestione.setVisible(false);
				buttonGestione.setVisible(false);
				buttonI.setVisible(false);
			}


		}





    }

	private void getLista(String type) throws CsvValidationException, SQLException, IOException, ClassNotFoundException, IdException, PersistenzaException {
		if (checkPersitenza()) {
			vis.setIsSearch(false);
			getPersistency();
			Stage stage;
			Parent root;
			stage = (Stage) buttonL.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(COMPRAVENDITA)));
            switch (type) {
                case "libro" -> stage.setTitle("Benvenuto nella schermata del riepilogo dei libri");
                case "giornale" -> stage.setTitle("Benvenuto nella schermata del riepilogo dei giornali");
                case "rivista" -> stage.setTitle("Benvenuto nella schermata del riepilogo delle riviste");
				default -> Logger.getLogger("get lista").log(Level.SEVERE," error with type");
            }


			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} else throw new PersistenzaException("lista giornali " + PERSISTENZANULLA);
	}

}
