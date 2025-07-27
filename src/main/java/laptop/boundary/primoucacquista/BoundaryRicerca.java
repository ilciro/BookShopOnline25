package laptop.boundary.primoucacquista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.primoucacquista.ControllerRicerca;
import laptop.controller.ControllerSystemState;
import laptop.exception.IdException;
import laptop.model.raccolta.Raccolta;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BoundaryRicerca implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private RadioButton ricercaL;
    @FXML
    private RadioButton ricercaG;
    @FXML
    private RadioButton ricercaR;
    @FXML
    private Label labelRicerca;
    @FXML
    private TextField ricercaTA;
    @FXML
    private TextField idTF;
    @FXML
    private VBox vbox;
    @FXML
    private Button cercaB;
    @FXML
    private Button mostraB;
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
    private ToggleGroup toggleGroup1;
   @FXML
   private ToggleGroup toggleGroup;
   @FXML
   private TableView<Raccolta> tableview;
   @FXML
   private TableColumn<Raccolta, SimpleIntegerProperty> id=new TableColumn<>("ID");
    @FXML
    private TableColumn<Raccolta, SimpleStringProperty> titolo=new TableColumn<>("TITOLO");
    @FXML
    private TableColumn<Raccolta, SimpleStringProperty> editore=new TableColumn<>("EDITORE");
    @FXML
    private TableColumn<Raccolta, SimpleStringProperty> autore=new TableColumn<>("AUTORE");

   protected Scene scene;
   private final ControllerSystemState vis=ControllerSystemState.getInstance();
   private static final String DATABASE="database";
   private static final String FILE="file";
   private static final String MEMORIA="memoria";
   private ControllerRicerca cR;
   private static final String TITOLOS="titolo";
   private static final String EDITORES="editore";


    @FXML
    private void cerca() throws CsvValidationException, IOException, IdException, ClassNotFoundException, SQLException {

        String type="";
        if(databaseButton.isSelected()) type=DATABASE;
        if (fileButton.isSelected()) type=FILE;
        if (memoriaButton.isSelected()) type=MEMORIA;
        if (checkPersistenza())
        {
            if(ricercaL.isSelected())
            {
                vis.setTypeAsBook();
                id.setCellValueFactory(new PropertyValueFactory<>("id"));
                titolo.setCellValueFactory(new PropertyValueFactory<>(TITOLOS));
                editore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
                autore.setCellValueFactory(new PropertyValueFactory<>("autore"));

                tableview.setItems(cR.listaLibri(ricercaTA.getText(),type));
            }
            if(ricercaG.isSelected())
            {
                vis.setTypeAsDaily();
                id.setCellValueFactory(new PropertyValueFactory<>("id"));
                titolo.setCellValueFactory(new PropertyValueFactory<>(TITOLOS));
                editore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
                autore.setCellValueFactory(new PropertyValueFactory<>("NULL"));
                tableview.setItems(cR.listaGiornali(ricercaTA.getText(),type));
            }
            if(ricercaR.isSelected())
            {
                vis.setTypeAsMagazine();
                id.setCellValueFactory(new PropertyValueFactory<>("id"));
                titolo.setCellValueFactory(new PropertyValueFactory<>(TITOLOS));
                editore.setCellValueFactory(new PropertyValueFactory<>(EDITORES));
                autore.setCellValueFactory(new PropertyValueFactory<>("autore"));

                tableview.setItems(cR.listaRiviste(ricercaTA.getText(),type));
            }


        }

    }
    @FXML
    private void mostra() throws IOException {
        if(checkPersistenza()) {





                switch (vis.getType()) {
                    case "libro" -> vis.setIdLibro(Integer.parseInt(idTF.getText()));
                    case "giornale" -> vis.setIdGiornale(Integer.parseInt(idTF.getText()));
                    case "rivista" -> vis.setIdRivista(Integer.parseInt(idTF.getText()));
                    default -> Logger.getLogger("check tipologia riverca").log(Level.SEVERE, "id ricerca not correct assigned!!");

                }

            Stage stage;
            Parent root;
            stage = (Stage) mostraB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/visualizzaPage.fxml")));
            stage.setTitle("Benvenuto nella schermata del riepilogo di oggetto");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }
    @FXML
    private void indietro() throws IOException, CsvValidationException, SQLException, IdException, ClassNotFoundException {
        String type="";
        Stage stage;
        Parent root;
        if(databaseButton.isSelected()) type=DATABASE;
        if (fileButton.isSelected()) type=FILE;
        if (memoriaButton.isSelected()) type=MEMORIA;

        /*
        if(cR.logout(type)) {

            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella schermata della home Page");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {

            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/ricerca.fxml")));
            stage.setTitle("Benvenuto nella schermata della ricerca");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

         */

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cR=new ControllerRicerca();
        if(vis.getTipologiaApplicazione().equals("demo"))
        {
            databaseButton.setVisible(false);
            fileButton.setVisible(false);
        }

    }

    private boolean checkPersistenza()
    {
        return databaseButton.isSelected()||fileButton.isSelected()||memoriaButton.isSelected();
    }
}
