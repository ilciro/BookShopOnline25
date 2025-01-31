package laptop.view.primoucacquista;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.itextpdf.text.DocumentException;


import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerDownload;
import laptop.exception.IdException;

public class BoundaryDownload implements Initializable {
	@FXML
	private SplitPane split;
	@FXML
	private AnchorPane ap1;
	@FXML
	private Label header;
	@FXML
	private AnchorPane ap2;
	@FXML
	private ImageView image;
	@FXML
	private Button buttonI;
	@FXML
	private Button buttonA;
	@FXML
	private RadioButton databaseButton;
	@FXML
	private RadioButton fileButton;
	@FXML
	private RadioButton memoriaButton;
	@FXML
	private ToggleGroup toggleGroup;

	private ControllerDownload cD;
	private final ControllerSystemState vis = ControllerSystemState.getInstance() ;
	protected Alert a;
	protected Scene scene;

	@FXML
	private void scarica() throws IOException, DocumentException, URISyntaxException, SQLException, CsvValidationException, IdException, ClassNotFoundException {
		
		a = new Alert(Alert.AlertType.CONFIRMATION);
		a.setTitle("Download effettuato");
		a.setHeaderText("Premere ok per scaricarlo e leggerlo\n");
		Optional<ButtonType> result = a.showAndWait();
		
		
		 if ((result.isPresent()) && (result.get() == ButtonType.OK))
	        	
	        {
				Logger.getLogger("scarica").log(Level.INFO," all ok...");

	            //passo 0 per evitare il NullPointer


				cD.scarica(vis.getType(),returnPersistenza());


	        }
		Stage stage;
		Parent root;
		stage = (Stage) buttonA.getScene().getWindow();
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void pulisci() throws IOException {


		if(!Objects.equals(returnPersistenza(), "")) {

			Stage stage;
			Parent root;
			stage = (Stage) buttonA.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("annullaPagamento.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}


	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

        try {
            cD = new ControllerDownload();
        } catch (IOException e) {
			Logger.getLogger("Test initialize").log(Level.SEVERE, "eccezione ottenuta",e);

		}

    }

	private String returnPersistenza()
	{
		String type = "";
		if(databaseButton.isSelected()) type="database";
		if(fileButton.isSelected())type="file";
		if(memoriaButton.isSelected())type="memoria";
		return type;
	}

}
