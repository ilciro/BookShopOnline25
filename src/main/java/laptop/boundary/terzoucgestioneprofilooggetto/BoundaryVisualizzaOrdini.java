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
import laptop.controller.terzoucgestioneprofiloggetto.ControllerVisualizzaOrdini;
import laptop.model.pagamento.PagamentoCartaCredito;
import laptop.model.pagamento.PagamentoFattura;
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
    private TableView<PagamentoFattura> tableview;
    @FXML
    private TableColumn<PagamentoFattura,String> idOggetto;
    @FXML
    private TableColumn<PagamentoFattura,Float> spesaTotale;
    @FXML
    private TableColumn<PagamentoFattura,String> tipoAcquisto;
    @FXML
    private TableColumn<PagamentoFattura,Integer> idFattura;
    @FXML
    private TableView<PagamentoCartaCredito> tableview1;
    @FXML
    private TableColumn<PagamentoCartaCredito,String> idOggettoCC;
    @FXML
    private TableColumn<PagamentoCartaCredito,Float> spesaTotaleCC;
    @FXML
    private TableColumn<PagamentoCartaCredito,String> tipoAcquistoCC;
    @FXML
    private TableColumn<PagamentoCartaCredito,Integer> idPagamentoCC;
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
    private RadioButton fattura;
    @FXML
    private RadioButton cartaCredito;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private ToggleGroup toggleGroup1;

    private ControllerVisualizzaOrdini cV;
    protected Scene scene;
    private static final ControllerSystemState vis=ControllerSystemState.getInstance();
    @FXML
    private void genera() {



        if(checkPersistenza()) {


            String type="";
            if(databaseButton.isSelected()) type="database";
            if(fileButton.isSelected()) type="file";
            if(memoriaButton.isSelected()) type="memoria";

            if(fattura.isSelected())
            {
                tableview.setItems(cV.getListaFattura(type));
                tableview1.setEditable(false);
                vis.setMetodoP("cash");
            }
            if(cartaCredito.isSelected())
            {
                tableview1.setItems(cV.getListaCC(type));
                tableview.setEditable(false);
                vis.setMetodoP("cCredito");
            }


        }
    }

    @FXML
    private void elimina() {
        String type="";
        if(databaseButton.isSelected()) type="database";
        if(fileButton.isSelected()) type="file";
        if(memoriaButton.isSelected()) type="memoria";

        int id=0;

        if(fattura.isSelected())
        {
            id=tableview.getSelectionModel().getSelectedItem().getIdFattura();
        }
        if(cartaCredito.isSelected())
        {
            id=tableview1.getSelectionModel().getSelectedItem().getIdPagCC();

        }

        if(cV.cancellaPagamento(id,type))
        {

            try {

                Stage stage;
                Parent root;
                stage = (Stage) eliminaB.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
                stage.setTitle("Benvenuto nella home page");
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch (IOException e)
            {
                Logger.getLogger("elimina").log(Level.SEVERE,"delete not avalaible {0}",e);
            }
        }
        else {
           eliminaEVisualizza();
        }


    }
    @FXML
    private void indietro()  {
        if(checkPersistenza()) homePage();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cV=new ControllerVisualizzaOrdini();
        header.setText(header.getText()+"\t"+ cV.getEmail());

       idOggetto.setCellValueFactory(new PropertyValueFactory<>("idProdotto"));
       spesaTotale.setCellValueFactory(new PropertyValueFactory<>("ammontare"));
       tipoAcquisto.setCellValueFactory(new PropertyValueFactory<>("tipoAcquisto"));
       idFattura.setCellValueFactory(new PropertyValueFactory<>("idFattura"));

        if(vis.getTipologiaApplicazione().equals("demo"))
        {
            databaseButton.setVisible(false);
            fileButton.setVisible(false);
        }
        else memoriaButton.setVisible(false);

        idOggettoCC.setCellValueFactory(new PropertyValueFactory<>("idProdotto"));
        spesaTotaleCC.setCellValueFactory(new PropertyValueFactory<>("spesaTotale"));
        tipoAcquistoCC.setCellValueFactory(new PropertyValueFactory<>("tipoAcquisto"));
        idPagamentoCC.setCellValueFactory(new PropertyValueFactory<>("idPagCC"));



    }

    private boolean checkPersistenza()
    {
        return databaseButton.isSelected()|| fileButton.isSelected()||memoriaButton.isSelected();
    }

    private void eliminaEVisualizza()  {
        try {
            Logger.getLogger("elimina").log(Level.SEVERE, " deleted payment failed");
            Stage stage;
            Parent root;
            stage = (Stage) indietroB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/terzoucgestioneprofilooggetto/visualizzaOrdini.fxml")));
            stage.setTitle("Benvenuto nella schermata degli ordini");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e)
        {
            Logger.getLogger("elimina").log(Level.SEVERE,"delete error {0}",e);
        }

    }
    private void homePage()  {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) indietroB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella home page");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e)
        {
            Logger.getLogger("home page").log(Level.SEVERE,"homepage not avalaible {0}",e);
        }
    }
}
