package laptop.boundary.primoucacquista;


import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerPagamentoCC;
import laptop.exception.IdException;
import laptop.model.CartaDiCredito;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;


public class BoundaryPagamentoCC implements Initializable {
	@FXML
	private Pane pane;
	@FXML
	private Label header;
	@FXML
	private VBox vbox;
	@FXML
	private Label labelN;
	@FXML
	private Label labelC;
	@FXML
	private Label labelCodice;
	@FXML
	private Label labelD;
	@FXML
	private Label labelCiv;
	@FXML
	private VBox vbox2;
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField cognomeTF;
	@FXML
	private TextField codiceTF;
	@FXML
	private TextField scadenzaTF;
	@FXML
	private PasswordField passTF;
	@FXML
	private Button buttonReg;
	@FXML
	private TableView<CartaDiCredito> tableview;
	@FXML
	private TableColumn<String,CartaDiCredito> codiceCC;
	@FXML
	private Label labelNU;
	@FXML
	private TextField nomeInput;
	@FXML
	private RadioButton buttonPrendi;
	@FXML
	private RadioButton databaseButton;
	@FXML
	private RadioButton fileButton;
	@FXML
	private RadioButton memoriaButton;
	@FXML
	private ToggleGroup toggleGroup;
	@FXML
	private RadioButton completa;
	@FXML
	private Button buttonI;


	protected Scene scene;
	private ControllerPagamentoCC cPCC;
	private final ControllerSystemState vis=ControllerSystemState.getInstance();
	private static final String DATABASE="database";
	private static final String FILE="file";
	private static final String MEMORIA="memoria";
	protected Stage stage;

	@FXML
	private void registraCC() throws CsvValidationException, IOException, ParseException, ClassNotFoundException, SQLException {

		String persistenza="";
		if(databaseButton.isSelected()) persistenza=DATABASE;
		if(fileButton.isSelected()) persistenza=FILE;
		if(memoriaButton.isSelected()) persistenza=MEMORIA;

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
		Date date = sdf1.parse(scadenzaTF.getText());
		java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
		/*
		if(cPCC.aggiungiCartaDB(nomeTF.getText(),cognomeTF.getText(),codiceTF.getText(), sqlStartDate,passTF.getText(),vis.getSpesaT(),persistenza))
		{
			Logger.getLogger("carta di credito registrata").log(Level.INFO," cc inserted with success");
			buttonReg.setDisable(true);
		}

		 */






	}

	@FXML
	private void popolaTabella() throws IOException, ClassNotFoundException, CsvValidationException,  SQLException {
		String persistenza="";
		if(databaseButton.isSelected()) persistenza=DATABASE;
		if(fileButton.isSelected()) persistenza=FILE;
		if(memoriaButton.isSelected()) persistenza=MEMORIA;
		//tableview.setItems(cPCC.ritornaElencoCC(nomeInput.getText(),persistenza));


	}

	@FXML
	private void procediCC() throws IOException, CsvValidationException, IdException, ClassNotFoundException, SQLException {


		if(vis.getIsPickup())
		{

			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/scegliNegozio.fxml")));
			stage.setTitle("Benvenuto nella schermata per annullare fattura");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
        }
		else {


				String persistenza = "";
				if (databaseButton.isSelected()) persistenza = DATABASE;
				else if (fileButton.isSelected()) persistenza = FILE;
				else if (memoriaButton.isSelected()) persistenza = MEMORIA;
				cPCC.pagamentoCC(nomeTF.getText(), persistenza, cognomeTF.getText());
			if (cPCC.correttezza(codiceTF.getText(),scadenzaTF.getText(),passTF.getText())) {

				Parent root;
				stage = (Stage) buttonI.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/download.fxml")));
				stage.setTitle("Benvenuto nella schermata per il download");
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}

			}



    }




	@Override
	public void initialize(URL location, ResourceBundle resources) {

            cPCC=new ControllerPagamentoCC();

			if(vis.getTipologiaApplicazione().equals("demo") && !vis.getIsLogged())
			{
				databaseButton.setVisible(false);
				fileButton.setVisible(false);

				nomeTF.setText("prova");
				cognomeTF.setText("prova");
				codiceTF.setText("1111-5241-8888-9652");
				scadenzaTF.setText("2030/09/08");
				passTF.setText("951");
			}
			if(vis.getTipologiaApplicazione().equals("demo")&&vis.getIsLogged())
			{
				databaseButton.setVisible(false);
				fileButton.setVisible(false);
				nomeTF.setText(cPCC.getInfo()[0]);
				cognomeTF.setText(cPCC.getInfo()[1]);
				nomeTF.setEditable(false);
				cognomeTF.setEditable(false);
			}


		else {
			buttonReg.setDisable(true);
		}
		if (vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);
		codiceCC.setCellValueFactory(new PropertyValueFactory<>("numeroCC"));

	}

	@FXML
	private void completa() throws IOException, ClassNotFoundException, CsvValidationException, SQLException {
		String persistenza="";
		if(databaseButton.isSelected()) persistenza=DATABASE;
		if(fileButton.isSelected()) persistenza=FILE;
		if(memoriaButton.isSelected()) persistenza=MEMORIA;

		//provo a prendere i dati dal db
		if(tableview.getSelectionModel().getSelectedItem().getNumeroCC()!=null )
		{
			codiceTF.setText(tableview.getSelectionModel().getSelectedItem().getNumeroCC());
		//	scadenzaTF.setText(String.valueOf(cPCC.ritornaElencoCByNumero(codiceTF.getText(),persistenza).get(0).getScadenza()));
		//	passTF.setText(cPCC.ritornaElencoCByNumero(codiceTF.getText(),persistenza).get(0).getCiv());

		}
		String scadenza1=scadenzaTF.getText().replace("-","/");
		scadenzaTF.setText(scadenza1);

	}
}
