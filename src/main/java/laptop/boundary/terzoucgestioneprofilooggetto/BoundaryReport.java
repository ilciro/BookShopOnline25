package laptop.boundary.terzoucgestioneprofilooggetto;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.terzoucgestioneprofiloggetto.ControllerReport;
import laptop.model.Report;
import laptop.model.user.TempUser;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryReport implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private HBox hbox;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton totaleRadio;
    @FXML
    private RadioButton libriRadio;
    @FXML
    private RadioButton giornaliRadio;
    @FXML
    private RadioButton rivisteRadio;
    @FXML
    private RadioButton utentiRadio;
    @FXML
    private Button generaButton;
    @FXML
    private TableView<Report> tableViewReport;
    @FXML
    private TableColumn<Report,String> idReport;
    @FXML
    private TableColumn<Report,String> titoloOggetto;
    @FXML
    private TableColumn<Report,String> tipologiaOggetto;
    @FXML
    private TableColumn<Report,Float> totale;
    @FXML
    private TableView<TempUser> tableViewUtenti;
    @FXML
    private TableColumn<Integer,TempUser> idUser;
    @FXML
    private TableColumn<String,TempUser> email;
    @FXML
    private TableColumn<LocalDate, TempUser> dataN;
    @FXML
    private VBox vbox;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private ToggleGroup toggleGroup1;


    @FXML
    private Button buttonI;
    protected Scene scene;

    private ControllerReport cR;
    private final ControllerSystemState vis=ControllerSystemState.getInstance();

    @FXML
    private void genera()  {

        String type="";
        if(databaseButton.isSelected()) type="database";
        if(fileButton.isSelected()) type="file";
        if(memoriaButton.isSelected()) type="memoria";


        if(totaleRadio.isSelected()) {
            tableViewReport.setItems(cR.reportTotale(type));
            tableViewUtenti.setItems(cR.reportUser(type));

        }


        if(libriRadio.isSelected())
        {
            tableViewReport.getItems().clear();
            vis.setTypeAsBook();
            tableViewUtenti.setEditable(false);
            tableViewReport.setItems(cR.reportL(type));
        }
        if(giornaliRadio.isSelected())
        {
            tableViewReport.getItems().clear();
            vis.setTypeAsDaily();
            tableViewUtenti.setEditable(false);
            tableViewReport.setItems(cR.reportG(type));
        }
        if(rivisteRadio.isSelected())
        {
            tableViewReport.getItems().clear();
            vis.setTypeAsMagazine();
            tableViewUtenti.setEditable(false);
            tableViewReport.setItems(cR.reportR(type));
        }
        if(utentiRadio.isSelected())
        {
            tableViewReport.setEditable(false);
            tableViewUtenti.setItems(cR.reportUser(type));

        }


    }



    @FXML
    private void indietro()  {
       if(databaseButton.isSelected()||fileButton.isSelected())
           login();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cR=new ControllerReport();
        idReport.setCellValueFactory(new PropertyValueFactory<>("idReport"));
        tipologiaOggetto.setCellValueFactory(new PropertyValueFactory<>("tipologiaOggetto"));
        titoloOggetto.setCellValueFactory(new PropertyValueFactory<>("titoloOggetto"));

        totale.setCellValueFactory(new PropertyValueFactory<>("totale"));

        idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailT"));
        dataN.setCellValueFactory(new PropertyValueFactory<>("dataDiNascitaT"));

        if(vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);


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
            Logger.getLogger("login").log(Level.SEVERE,"login not avalaible {0}",e);
        }
    }
}




