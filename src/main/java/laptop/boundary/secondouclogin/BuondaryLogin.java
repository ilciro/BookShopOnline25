package laptop.boundary.secondouclogin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.secondouclogin.ControllerLogin;
import laptop.exception.IdException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuondaryLogin implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox vbox1;
    @FXML
    private Label emailL;
    @FXML
    private Label passL;
    @FXML
    private VBox vbox2;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passTF;
    @FXML
    private VBox vbox3;
    @FXML
    private Button buttonL;
    @FXML
    private Button buttonReg;
    @FXML
    private Button buttonCambio;
    @FXML
    private Button buttonI;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private VBox vbox4;

    private ControllerLogin cL;
    private Scene scene;

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    @FXML
    private void login() {

        try {

            String type = "";
            if (databaseButton.isSelected()) type = "database";
            if (fileButton.isSelected()) type = "file";
            if (memoriaButton.isSelected()) type = "memoria";


            if (cL.login(emailTF.getText(), passTF.getText(), type).equals("NONVALIDO")) {

                vis.setIsLogged(false);
                throw new IdException(" user role not valid!!");
            }
            if (cL.login(emailTF.getText(), passTF.getText(), type).equals("ADMIN")) {

                vis.setIsLogged(true);
                //caricare scehrmata admin

                Stage stage;
                Parent root;
                stage = (Stage) buttonI.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/admin.fxml")));
                stage.setTitle("Benvenuto nella schermata di admin");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();


            } else {
                vis.setIsLogged(true);


                Stage stage;
                Parent root;
                stage = (Stage) buttonL.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
                stage.setTitle("Benvenuto nella home page dopo il login scrittore/utente/editore");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (IOException e)
        {
            Logger.getLogger("login").log(Level.SEVERE,"login error {0}",e);
        }catch (IdException e1)
        {
            Logger.getLogger("user error").log(Level.SEVERE,"user already present {0}",e1);
        }


    }

    @FXML
    private void registra() {
        try {
            if (checkPersistenza()) {
                Stage stage;
                Parent root;
                stage = (Stage) buttonReg.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/registraUtente.fxml")));
                stage.setTitle("Benvenuto nella home page dopo il login");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (IOException e)
        {
            Logger.getLogger("registra").log(Level.SEVERE,"register not avalaible {0}",e);
        }

    }

    @FXML
    private void cambia()  {
        try {

            String type = "";
            if (databaseButton.isSelected()) type = "database";
            if (fileButton.isSelected()) type = "file";
            if (memoriaButton.isSelected()) type = "memoria";

            if (cL.userPresente(emailTF.getText(), passTF.getText(), type)) {
                Stage stage;
                Parent root;
                stage = (Stage) buttonCambio.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/aggiornaPassword.fxml")));
                stage.setTitle("Benvenuto nella home page per cambiare credenziali");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else {
                throw new IdException("change user not found!!");
            }
        }catch (IOException e)
        {
            Logger.getLogger("aggiorna pwd").log(Level.SEVERE,"update pwd error {0}",e);
        }catch (IdException e1)
        {
            Logger.getLogger("aggourna pwd id").log(Level.SEVERE,"user npt found {0}",e1);
        }




    }


    @FXML
    private void indietro()  {
        if(checkPersistenza())
            homePage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cL=new ControllerLogin();
        if(vis.getTipologiaApplicazione().equals("demo"))
        {
            databaseButton.setVisible(false);
            fileButton.setVisible(false);
            buttonReg.setVisible(false);
            buttonCambio.setVisible(false);
        }
        else memoriaButton.setVisible(false);

    }

    private boolean checkPersistenza()
    {
        return databaseButton.isSelected() || fileButton.isSelected() || memoriaButton.isSelected();
    }

    private void homePage()  {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella home page dopo il login");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e)
        {
            Logger.getLogger("home page").log(Level.SEVERE,"home page npt found {0}",e);
        }
    }
}
