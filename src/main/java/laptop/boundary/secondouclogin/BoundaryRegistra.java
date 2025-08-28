package laptop.boundary.secondouclogin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import laptop.controller.secondouclogin.ControllerRegistraUtente;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryRegistra implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox vbox;
    @FXML
    private Label nomeL;
    @FXML
    private Label cognomeL;
    @FXML
    private Label emailL;
    @FXML
    private Label passL;
    @FXML
    private Label descL;
    @FXML
    private Label dataL;
    @FXML
    private Label ruoloL;
    @FXML
    private VBox vbox2;
    @FXML
    private TextField nomeTF;
    @FXML
    private TextField cognomeTF;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passTF;
    @FXML
    private TextArea descTF;
    @FXML
    private DatePicker calendarL;
    @FXML
    private ListView<String> listTF;
    @FXML
    private Button buttonReg;
    @FXML
    private Button buttonI;
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
    private ControllerRegistraUtente cRU;
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    @FXML
    private boolean registra()  {


        boolean state = false;
        try{
        if (checkPersistenza()) {

            String type = "";
            if (databaseButton.isSelected()) type = "database";
            if (fileButton.isSelected()) type = "file";
            if (memoriaButton.isSelected()) type = "memoria";

            cRU.setType(type);

            LocalDate data = calendarL.getValue();

            state = cRU.registra(nomeTF.getText(), cognomeTF.getText(), emailTF.getText(), passTF.getText(), descTF.getText(), data, listTF.getSelectionModel().getSelectedItem().substring(0, 1));

            if (state) {
                Stage stage;
                Parent root;
                stage = (Stage) buttonReg.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/login.fxml")));
                stage.setTitle("Benvenuto nella schermata del login");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Stage stage;
                Parent root;
                stage = (Stage) buttonReg.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/secondouclogin/registraUtente.fxml")));
                stage.setTitle("Benvenuto nella schermata della registrazione");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        }catch (IOException e)
        {
            Logger.getLogger("regista").log(Level.SEVERE,"register error {0}",e);
        }


        return state;
    }
    @FXML
    private void indietro() {
        if(checkPersistenza())
            login();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cRU=new ControllerRegistraUtente();
        ObservableList<String> list= FXCollections.observableArrayList();
        list.add("UTENTE");
        list.add("ADMIN");
        list.add("SCRITTORE");
        list.add("EDITORE");

        listTF.setItems(list);

        if(vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);

    }

    private boolean checkPersistenza()
    {
        return databaseButton.isSelected() || fileButton.isSelected() || memoriaButton.isSelected();
    }

    private void login() {
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
            Logger.getLogger("login").log(Level.SEVERE,"login error {0}",e);
        }
    }
}
