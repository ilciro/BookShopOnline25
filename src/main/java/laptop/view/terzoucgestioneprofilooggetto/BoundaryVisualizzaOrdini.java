package laptop.view.terzoucgestioneprofilooggetto;

import com.opencsv.exceptions.CsvValidationException;
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
import laptop.controller.terzoucgestioneprofilooggetto.ControllerVisualizzaOrdini;
import laptop.model.Pagamento;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoundaryVisualizzaOrdini implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TableView<Pagamento> tableview;
    @FXML
    private TableColumn<Pagamento,String> idPagamento;
    @FXML
    private TableColumn<Pagamento,String> metodo;
    @FXML
    private TableColumn<Pagamento,Float> spesaTotale;
    @FXML
    private TableColumn<Pagamento,String> tipoAcquisto;
    @FXML
    private TableColumn<Pagamento,Integer> idProdotto;
    @FXML
    private  HBox hbox;
    @FXML
    private Button generaB;
    @FXML
    private Button eliminaB;
    @FXML
    private Button indietroB;
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

    private ControllerVisualizzaOrdini cV;
    protected Scene scene;

    @FXML
    private void genera() throws CsvValidationException, IOException, ClassNotFoundException {



        if(checkPersistenza()) {


            String type="";
            if(databaseButton.isSelected()) type="database";
            if(fileButton.isSelected()) type="file";
            if(memoriaButton.isSelected()) type="memoria";


            tableview.setItems(cV.getLista(type));
        }
    }
    @FXML
    private void elimina() throws CsvValidationException, IOException, ClassNotFoundException {
        int id=tableview.getSelectionModel().getSelectedItem().getIdPag();
        String type="";
        if(databaseButton.isSelected()) type="database";
        if(fileButton.isSelected()) type="file";
        if(memoriaButton.isSelected()) type="memoria";
        if(cV.cancellaPagamento(id,type))
        {


            Stage stage;
            Parent root;
            stage = (Stage) eliminaB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella home page");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Logger.getLogger("elimina").log(Level.SEVERE," deleted payment failed");
            Stage stage;
            Parent root;
            stage = (Stage) indietroB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("visualizzaOrdini.fxml")));
            stage.setTitle("Benvenuto nella schermata degli ordini");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    @FXML
    private void indietro() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) indietroB.getScene().getWindow();
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
        stage.setTitle("Benvenuto nella home page");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cV=new ControllerVisualizzaOrdini();
        header.setText(header.getText()+"\t"+ cV.getEmail());

        idPagamento.setCellValueFactory(new PropertyValueFactory<>("idPag"));
        metodo.setCellValueFactory(new PropertyValueFactory<>("metodo"));
        spesaTotale.setCellValueFactory(new PropertyValueFactory<>("ammontare"));
        tipoAcquisto.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        idProdotto.setCellValueFactory(new PropertyValueFactory<>("idOggetto"));


    }

    private boolean checkPersistenza()
    {
        return databaseButton.isSelected()|| fileButton.isSelected()||memoriaButton.isSelected();
    }
}
