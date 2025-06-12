package laptop.view.primoucacquista;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerAcquista;
import laptop.exception.AcquistaException;
import laptop.exception.IdException;

public class BoundaryAcquista implements Initializable {
	@FXML
	private SplitPane split;
	@FXML
	private AnchorPane ap1;
	@FXML
	private AnchorPane ap2;
	@FXML
	private Label header;
	@FXML
	private Label labelN;
	@FXML
	private Label labelCosto;
	@FXML
	private Label labelQ;
	@FXML
	private Label labelT;
	@FXML
	private Label  nome;
	@FXML
	private Label  copieLabel;
	@FXML
	private Label costo;
	@FXML
	private TextField quantita;
	@FXML
	private CheckBox ritiroN;

	@FXML
	private Label totale;
	@FXML
	private Label labelPag;
	@FXML
	private RadioButton buttonCC;
	@FXML
	private RadioButton buttonCash;
	@FXML
	private Button calcola;
	@FXML
	private Button link;
	@FXML
	private ToggleGroup toggleGroup;
	@FXML
	private ToggleGroup toggleGroup1;
	@FXML
	private VBox vbox;
	@FXML
	private RadioButton databaseButton;
	@FXML
	private RadioButton fileButton;
	@FXML
	private RadioButton memoriaButton;
	@FXML
	private Button mostraButton;

	protected Scene scene;
	private ControllerAcquista cA;
	private final ControllerSystemState vis = ControllerSystemState.getInstance() ;

	private String persistenza() throws IOException {
		String type;
		if(databaseButton.isSelected()) type="database";
		else if (fileButton.isSelected()) type="file";
		else if(memoriaButton.isSelected()) type="memoria";
		else throw new IOException("persistency exception!!");
		return type;
	}

	@FXML
	private void mostra(){

		try {
			nome.setText(cA.getNomeCostoDisp(persistenza())[0]);
			costo.setText(cA.getNomeCostoDisp(persistenza())[1]);
			copieLabel.setText(cA.getNomeCostoDisp(persistenza())[2]);
			if(Integer.parseInt(copieLabel.getText())<=0)
			{
				buttonCC.setDisable(true);
				buttonCash.setDisable(true);
				ritiroN.setDisable(true);
				calcola.setDisable(true);
			}

		} catch (CsvValidationException | IOException | IdException | ClassNotFoundException e) {
			java.util.logging.Logger.getLogger("initialize").log(Level.SEVERE," data is wrong!!");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cA=new ControllerAcquista();



    }
	@FXML
	private void indietro() throws IOException {
		Stage stage;
		Parent root;
		stage = (Stage) link.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/compravendita.fxml")));
		stage.setTitle("Benvenuto nella schermata della compravendita");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void pagaCC() throws IOException {
		vis.setMetodoP("cCredito");
		vis.setQuantita(Integer.parseInt(quantita.getText()));
		if(ritiroN.isSelected())
			vis.setPickup(true);
		Stage stage;
		Parent root;
		stage = (Stage) buttonCC.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/pagamentoCCFinale.fxml")));
		stage.setTitle("Benvenuto nella schermata del riepilogo cartaCredito");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}
	@FXML
	private void pagaCash() throws IOException {
		vis.setMetodoP("cash");
		vis.setQuantita(Integer.parseInt(quantita.getText()));

		if(ritiroN.isSelected())
			vis.setPickup(true);
		Stage stage;
		Parent root;
		stage = (Stage) buttonCash.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/pagamentoContrassegno.fxml")));
		stage.setTitle("Benvenuto nella schermata del riepilogo fattura");
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	private void importo() throws CsvValidationException, IOException, IdException, AcquistaException, ClassNotFoundException {
		//invalido l'importo.
		if(Integer.parseInt(quantita.getText())>Integer.parseInt(cA.getNomeCostoDisp(persistenza())[2]))
		{
			buttonCC.setDisable(true);
			buttonCash.setDisable(true);
			ritiroN.setDisable(true);
			throw new AcquistaException(" not enough capacity");
		}
		else{
			buttonCC.setDisable(false);
			buttonCash.setDisable(false);
			ritiroN.setDisable(false);
		}
		totale.setText(String.valueOf(cA.getPrezzo(quantita.getText(),persistenza())));
	}
}
