package laptop.boundary.terzoucgestioneprofilooggetto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerAdmin;
import laptop.exception.IdException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BoundaryAdmin implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private VBox vbox;
    @FXML
    private Button reportB;
    @FXML
    private Button raccoltaB;
    @FXML
    private Button utentiB;
    @FXML
    private Button logoutB;
    @FXML
    private VBox vbox1;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private ToggleGroup toggleGroup;
    protected Scene scene;
    private ControllerAdmin cA;

    private static final String PERSISTENZAERRORE="persistenza non selezionata";
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    //caricare le altre schermate
    @FXML
    private void report()  {
        try {
            if (checkPersistenza()) {
                Stage stage;
                Parent root;
                stage = (Stage) reportB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/report.fxml")));
                stage.setTitle("Benvenuto nella schermata del login");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else throw new IdException("report " + PERSISTENZAERRORE);
        }catch (IOException e)
        {
            Logger.getLogger("report").log(Level.SEVERE,"report not avalaible {0}",e);
        }catch (IdException e1)
        {
            Logger.getLogger("report id").log(Level.SEVERE,"id is null {0}",e1);
        }
    }
    @FXML
    private void raccolta()  {
        try {
            if (checkPersistenza()) {
                Stage stage;
                Parent root;
                stage = (Stage) raccoltaB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/raccolta.fxml")));
                stage.setTitle("Benvenuto nella schermata della raccolta");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else throw new IdException("raccolta " + PERSISTENZAERRORE);
        }catch (IOException e)
        {
            Logger.getLogger("raccolta").log(Level.SEVERE,"raccolta not avalaible {0}",e);
        }catch (IdException e1)
        {
            Logger.getLogger("raccolta id").log(Level.SEVERE,"id raccolta is null {0}",e1);
        }
    }
    @FXML
    private void utenti()  {
        try {
            if (checkPersistenza()) {
                Stage stage;
                Parent root;
                stage = (Stage) utentiB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/utenti.fxml")));
                stage.setTitle("Benvenuto nella schermata di utente");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else throw new IdException("utenti " + PERSISTENZAERRORE);
        }catch (IOException e)
        {
            Logger.getLogger("utenti").log(Level.SEVERE,"report utenti not avalaible {0}",e);
        }catch (IdException e1)
        {
            Logger.getLogger("utenti id").log(Level.SEVERE,"id users is null {0}",e1);
        }
    }
    @FXML
    private void logout()  {
        try {

            String type = "";
            if (databaseButton.isSelected()) type = "database";
            if (fileButton.isSelected()) type = "file";
            if (memoriaButton.isSelected()) type = "memoria";
            if (cA.logout(type)) {
                Stage stage;
                Parent root;
                stage = (Stage) logoutB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
                stage.setTitle("Benvenuto nella schermata iniziale");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Logger.getLogger("logout").log(Level.INFO, " logout success");

            } else {
                Stage stage;
                Parent root;
                stage = (Stage) utentiB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/admin.fxml")));
                stage.setTitle("Benvenuto nella schermata di admin");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Logger.getLogger("logout").log(Level.SEVERE, " error with logoutt!!");
            }
        }catch (IOException e)
        {
            Logger.getLogger("logout").log(Level.SEVERE,"logout exception {0}",e);
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cA=new ControllerAdmin();
        if(vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);

    }

    private boolean checkPersistenza()
    {
        return databaseButton.isSelected() || fileButton.isSelected() || memoriaButton.isSelected();
    }

}
