package laptop.boundary.primoucacquista;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	private void scarica()  {

		try {
			a = new Alert(Alert.AlertType.CONFIRMATION);
			a.setTitle("Download effettuato");
			a.setHeaderText("Premere ok per scaricarlo e leggerlo\n");
			Optional<ButtonType> result = a.showAndWait();
			if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
				Logger.getLogger("scarica").log(Level.INFO, " all ok...");
				//passo 0 per evitare il NullPointer
				cD.scarica(vis.getType(), returnPersistenza());
			}
			Stage stage;
			Parent root;
			stage = (Stage) buttonA.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			Logger.getLogger("scarica io ").log(Level.SEVERE,"scarica io exception {0}",e);
		}
}

	@FXML
	private void pulisci(){
		try {
			if (!Objects.equals(returnPersistenza(), "")) {
				Stage stage;
				Parent root;
				stage = (Stage) buttonA.getScene().getWindow();
				root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/annullaPagamento.fxml")));
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		}catch (IOException e)
		{
			Logger.getLogger("pulisci").log(Level.SEVERE,"pulisci ioexception {0}",e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

        cD = new ControllerDownload();
     	if(vis.getTipologiaApplicazione().equals("demo")) {
			databaseButton.setVisible(false);
			fileButton.setVisible(false);
		}
		if (vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);
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
