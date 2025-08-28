package laptop.boundary.primoucacquista;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.primoucacquista.ControllerCompravendita;
import laptop.controller.ControllerSystemState;
import laptop.model.raccolta.Raccolta;


public class BoundaryCompravendita implements Initializable {
	@FXML
	private Pane panel;
	@FXML
	private Label header;
	@FXML
	private TableView<Raccolta> table;
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> titolo = new TableColumn<>("Titolo");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> editore = new TableColumn<>("Editore");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> autore = new TableColumn<>("Autore");
	@FXML
	private TableColumn<Raccolta, SimpleStringProperty> categoria = new TableColumn<>("Categoria");
	@FXML
	private TableColumn<Raccolta, SimpleFloatProperty> prezzo = new TableColumn<>("Prezzo");
	@FXML
	private TableColumn<Raccolta, SimpleIntegerProperty> idLibro = new TableColumn<>("Id Libro");
	@FXML
	private TableColumn<Raccolta, SimpleIntegerProperty> copieRimaste = new TableColumn<>("Copie rimaste");
	@FXML
	private VBox vbox;
	@FXML
	private Button buttonPrendi;
	@FXML
	private Button buttonMostra;
	@FXML
	private TextField idOggetto;
	@FXML
	private RadioButton buttonDatabase;
	@FXML
	private RadioButton buttonFile;
	@FXML
	private RadioButton buttonMemoria;
	@FXML
	private ToggleGroup toggleGroup;
	@FXML
	private Button buttonHomePage;
	@FXML
	private Button buttonAcquista;

	private  ControllerCompravendita cCV;
	private static final ControllerSystemState vis=ControllerSystemState.getInstance();
	private static final String TITOLOS="titolo";
	private static final String EDITORES="editore";
	private static final String PREZZOS="prezzo";
	protected Scene scene;
	private  String type="";


	@FXML
	private void prendiLista()  {


            type=controllaPersistenza();
            table.setItems(cCV.getLista(vis.getType(),type));


    }
	@FXML
	private void mostra()  {
		int id=Integer.parseInt(idOggetto.getText());
        try {
            type=controllaPersistenza();
            if(cCV.checkId(id,type,vis.getType())) {

                checkTipologiaCompravendita(id);


                Stage stage;
                Parent root;
                stage = (Stage) buttonMostra.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/visualizzaPage.fxml")));
                stage.setTitle("Benvenuto nella schermata del riepilogo");

                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } catch (IOException e1) {
			Logger.getLogger("mostra io").log(Level.SEVERE,"mostsra io exception {0}",e1);
		}

    }
	@FXML
	private void acquista() {
		int id=Integer.parseInt(idOggetto.getText());
        try {
            type=controllaPersistenza();
            if(cCV.checkId(id,type,vis.getType()))
            {
                checkTipologiaCompravendita(id);


                Stage stage;
                Parent root;
                stage = (Stage) buttonAcquista.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/acquista.fxml")));
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e1) {
			Logger.getLogger("acquista io").log(Level.SEVERE,"acquista io exception {0}",e1);
		}

    }
	@FXML
	private void homePage()  {
		try {
			if (controllaPersistenza() != null) {

				Stage stage;
				Parent root;
				stage = (Stage) buttonHomePage.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		}catch (IOException e)
		{
			Logger.getLogger("home page").log(Level.SEVERE,"home page ioexception {0}",e);

		}



	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {

        cCV = new ControllerCompravendita();
		if(vis.getTipologiaApplicazione().equals("demo") && !vis.getIsLogged())
		{
			buttonDatabase.setVisible(false);
			buttonFile.setVisible(false);
            switch (vis.getType()) {
                case "libro" -> idOggetto.setText("6");
                case "giornale" -> idOggetto.setText("1");
                case "rivista" -> idOggetto.setText("3");
				default -> Logger.getLogger("initialize").log(Level.SEVERE," type of object is null");
            }
		}else if(vis.getTipologiaApplicazione().equals("demo") && vis.getIsLogged())
		{
			buttonDatabase.setVisible(false);
			buttonFile.setVisible(false);
		}
		if(vis.getTipologiaApplicazione().equals("full")) buttonMemoria.setVisible(false);


        if(vis.getType().equals("libro") || vis.getType().equals("rivista"))
		{
			header.setText("elenco oggetto :\t"+vis.getType()+"\tpresenti nel db");
			titolo.setCellValueFactory(new PropertyValueFactory<>(TITOLOS));
			editore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
			autore.setCellValueFactory(new PropertyValueFactory<>("autore"));
			categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
			prezzo.setCellValueFactory(new PropertyValueFactory<>(PREZZOS));
			idLibro.setCellValueFactory(new PropertyValueFactory<>("id"));
			copieRimaste.setCellValueFactory(new PropertyValueFactory<>("nrCopie"));

		}
		else if(vis.getType().equals("giornale"))
		{
			header.setText("elenco giornali presenti nel db");
			titolo.setCellValueFactory(new PropertyValueFactory<>(TITOLOS));
			//same on value editore
			// giornale not have autore attr
			autore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
			editore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
			categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
			prezzo.setCellValueFactory(new PropertyValueFactory<>(PREZZOS));
			idLibro.setCellValueFactory(new PropertyValueFactory<>("id"));
			copieRimaste.setCellValueFactory(new PropertyValueFactory<>("copieRimanenti"));

		}

		


	}

	private String controllaPersistenza()  {

		if(buttonDatabase.isSelected()) type="database";
		else if(buttonFile.isSelected())type="file";
		else if(buttonMemoria.isSelected()) type="memoria";

		return type;
	}

	private void checkTipologiaCompravendita(int id)
	{
		switch (vis.getType())
		{
			case "libro"->vis.setIdLibro(id);
			case "giornale"->vis.setIdGiornale(id);
			case "rivista"->vis.setIdRivista(id);
			default -> Logger.getLogger("check tipologia").log(Level.SEVERE,"id not correct assigned!!");

		}
	}





}

