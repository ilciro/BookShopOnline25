package laptop.boundary.secondouclogin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerAggiornaPassword;
import laptop.exception.IdException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryAggiornaPassword implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TextField vecchiaMTF;
    @FXML
    private PasswordField vecchiaPTF;
    @FXML
    private PasswordField nuovaPTF;
    @FXML
    private Button buttonA;
    @FXML
    private Button buttonI;
    @FXML
    private VBox vbox;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private ToggleGroup toggleGroup;

    private ControllerAggiornaPassword cAP;
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    private Scene scene;

    @FXML
    private void aggiorna() {
        try {
            String type = "";
            if (databaseButton.isSelected()) type = "database";
            if (fileButton.isSelected()) type = "file";
            if (memoriaButton.isSelected()) type = "memoria";
            if (cAP.aggiorna(nuovaPTF.getText(), type)) {
                Stage stage;
                Parent root;
                stage = (Stage) buttonI.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/login.fxml")));
                stage.setTitle("Benvenuto nella schermata del login");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                throw new IdException("not updated");
            }
        }catch (IOException e)
        {
            Logger.getLogger("aggiorna").log(Level.SEVERE,"update error {0}",e);
        }catch (IdException e1)
        {
            Logger.getLogger("id error").log(Level.SEVERE,"id is null {0}",e1);
        }
    }

    @FXML
    private void indietro()  {
        if(databaseButton.isSelected()||fileButton.isSelected()||memoriaButton.isSelected())
            ritorna();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cAP=new ControllerAggiornaPassword();


            vecchiaMTF.setText(cAP.getEmail());
            vecchiaPTF.setText(cAP.getPass());

            if(vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);


    }

    private void ritorna() {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/login.fxml")));
            stage.setTitle("Benvenuto nella schermata del login");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e)
        {
            Logger.getLogger("ritorna").log(Level.SEVERE,"return to login not avalaible {0}",e);
        }
    }
}
