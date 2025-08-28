package laptop.boundary.primoucacquista;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import laptop.exception.PersistenzaException;

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

	private String persistenza() {
		String type = "";
		try {
			if (databaseButton.isSelected()) type = "database";
			else if (fileButton.isSelected()) type = "file";
			else if (memoriaButton.isSelected()) type = "memoria";
			else throw new PersistenzaException("persistency exception!!");
		}catch (PersistenzaException e)
		{
			Logger.getLogger("persistenza ").log(Level.SEVERE,"persistency is empty !! {0}",e);
        }
		return type;
	}

	@FXML
	private void mostra(){

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
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cA=new ControllerAcquista();
		if(vis.getTipologiaApplicazione().equals("demo")&&!vis.getIsLogged())
		{
			databaseButton.setVisible(false);
			fileButton.setVisible(false);
			switch (vis.getType()) {
				case "libro" -> quantita.setText("3");
				case "giornale" -> quantita.setText("5");
				case "rivista" -> quantita.setText("2");
				default -> Logger.getLogger("initialize").log(Level.SEVERE," type of object is null");
			}
		}
		if(vis.getTipologiaApplicazione().equals("demo"))
		{
			databaseButton.setVisible(false);
			fileButton.setVisible(false);
		}
		else if (vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);



    }
	@FXML
	private void indietro() {
		try {
			Stage stage;
			Parent root;
			stage = (Stage) link.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/compravendita.fxml")));
			stage.setTitle("Benvenuto nella schermata della compravendita");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch (IOException e)
		{
			Logger.getLogger("indietro").log(Level.SEVERE,"exception {0}",e);
        }
	}

	@FXML
	private void pagaCC()  {
		try {
			vis.setMetodoP("cCredito");
			vis.setQuantita(Integer.parseInt(quantita.getText()));
			if (ritiroN.isSelected())
				vis.setPickup(true);
			Stage stage;
			Parent root;
			stage = (Stage) buttonCC.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/pagamentoCCFinale.fxml")));
			stage.setTitle("Benvenuto nella schermata del riepilogo cartaCredito");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch (IOException e)
		{
			Logger.getLogger("pagaCC").log(Level.SEVERE,"exception pagaCC {0}",e);
        }

	}
	@FXML
	private void pagaCash()  {
		try {
			vis.setMetodoP("cash");
			vis.setQuantita(Integer.parseInt(quantita.getText()));
			if (ritiroN.isSelected())
				vis.setPickup(true);
			Stage stage;
			Parent root;
			stage = (Stage) buttonCash.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/pagamentoContrassegno.fxml")));
			stage.setTitle("Benvenuto nella schermata del riepilogo fattura");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch (IOException e){
			Logger.getLogger("paaaga cash").log(Level.SEVERE,"exception paga cash {0}",e);

		}
	}
	@FXML
	private void importo()  {
		//invalido l'importo.
		try{
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
	}catch (AcquistaException e3){
		Logger.getLogger("importo acquista").log(Level.SEVERE,"importo acquista exception {0}",e3);
	}
	}

}
