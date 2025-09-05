package laptop.boundary.terzoucgestioneprofilooggetto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import laptop.controller.terzoucgestioneprofiloggetto.ControllerGestioneUtente;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryGestioneUtente implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private VBox vbox;
    @FXML
    private TextField ruoloTF;
    @FXML
    private TextField nomeTF;
    @FXML
    private TextField cognomeTF;
    @FXML
    private TextField emailTF;
    @FXML
    private PasswordField passTF;
    @FXML
    private TextArea descTA;
    @FXML
    private TextField dataTF;
    @FXML
    private VBox vbox1;
    @FXML
    private ListView<String> listView;
    @FXML
    private TextField nomeTF1;
    @FXML
    private TextField cognomeTF1;
    @FXML
    private TextField emailTF1;
    @FXML
    private PasswordField passTF1;
    @FXML
    private TextArea descTA1;
    @FXML
    private DatePicker dataN;
    @FXML
    private VBox vbox2;
    @FXML
    private Button inserisciB;
    @FXML
    private Button modificaB;
    @FXML
    private Button indietroB;
    @FXML
    private VBox vbox3;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private Button prendiButton;

    private ControllerGestioneUtente cGU;
    private Scene scene;
    private static final String UTENTE="Benvenuto nella schermata di utente";
    private static final String UTENTI= "view/terzoucgestioneprofilooggetto/utenti.fxml";
    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    @FXML
    private void inserisci()  {

            vbox.setVisible(false);
            modificaB.setVisible(false);
            try {

                String[] data = new String[7];
                data[0] = listView.getSelectionModel().getSelectedItem();
                data[1] = nomeTF1.getText();
                data[2] = cognomeTF1.getText();
                data[3] = emailTF1.getText();
                data[4] = passTF1.getText();
                data[5] = descTA1.getText();
                data[6] = String.valueOf(dataN.getValue());


                String type = "";
                if (databaseButton.isSelected()) type = DATABASE;
                if (fileButton.isSelected()) type = FILE;
                if (memoriaButton.isSelected()) type = MEMORIA;

                if (cGU.inserisciUtente(data, type) && checkPersistenza()) {
                    Stage stage;
                    Parent root;
                    stage = (Stage) inserisciB.getScene().getWindow();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(UTENTI)));
                    stage.setTitle(UTENTE);
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    Stage stage;
                    Parent root;
                    stage = (Stage) inserisciB.getScene().getWindow();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/gestioneUtente.fxml")));
                    stage.setTitle("Benvenuto nella schermata della gestione utente");
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }catch (IOException e)
            {
                Logger.getLogger("inserisci").log(Level.SEVERE,"insert error {0}",e);
            }

    }
    @FXML
    private void modifica() {
        inserisciB.setVisible(false);
        vbox1.setDisable(true);

        try {
            String[] data = new String[7];
            data[0] = ruoloTF.getText();
            data[1] = nomeTF.getText();
            data[2] = cognomeTF.getText();
            data[3] = emailTF.getText();
            data[4] = passTF.getText();
            data[5] = descTA.getText();
            data[6] = dataTF.getText();

            String type = "";
            if (databaseButton.isSelected()) type = DATABASE;
            if (fileButton.isSelected()) type = FILE;
            if (memoriaButton.isSelected()) type = MEMORIA;

            if (cGU.modifica(data, type, emailTF.getText())) {
                Stage stage;
                Parent root;
                stage = (Stage) modificaB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(UTENTI)));
                stage.setTitle(UTENTE);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                Logger.getLogger("modif").log(Level.INFO, "user modified successfully!!");
            } else {
                Logger.getLogger("modif").log(Level.SEVERE, "user modified unsuccessfully!!");


                Stage stage;
                Parent root;
                stage = (Stage) modificaB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/gestioneUtente.fxml")));
                stage.setTitle(UTENTE);
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (IOException e) {
            Logger.getLogger("modif").log(Level.SEVERE, "modif error {0}", e);
        }
    }
    @FXML
    private void indietro()  {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) indietroB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(UTENTI)));
            stage.setTitle(UTENTE);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e)
        {
            Logger.getLogger("indeitro").log(Level.SEVERE,"back not avalaible {0}",e);
        }
    }

    @FXML
    private void prendi()  {
        String type="";
        if(databaseButton.isSelected()) type=DATABASE;
        if(fileButton.isSelected()) type=FILE;
        if(memoriaButton.isSelected()) type=MEMORIA;

        ruoloTF.setText(cGU.getDataUser(type).getIdRuoloT());
        nomeTF.setText(cGU.getDataUser(type).getNomeT());
        cognomeTF.setText(cGU.getDataUser(type).getCognomeT());
        emailTF.setText(cGU.getDataUser(type).getEmailT());
        passTF.setText(cGU.getDataUser(type).getPasswordT());
        descTA.setText(cGU.getDataUser(type).getDescrizioneT());
        dataTF.setText(String.valueOf(cGU.getDataUser(type).getDataDiNascitaT()));


    }












    @Override
    public void initialize(URL location, ResourceBundle resources) {

        header.setText("per aggiungere colonna di dx ; modifica è quella sx");


        cGU=new ControllerGestioneUtente();
        ObservableList<String> ruoli= FXCollections.observableArrayList();
        ruoli.add("SCRITTORE");
        ruoli.add("EDITORE");
        ruoli.add("UTENTE");
        ruoli.add("ADMIN");
        listView.setItems(ruoli);

        if(vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);


        }

        private boolean checkPersistenza()
        {
            return databaseButton.isSelected()||fileButton.isSelected()||memoriaButton.isSelected();
        }
        




}


