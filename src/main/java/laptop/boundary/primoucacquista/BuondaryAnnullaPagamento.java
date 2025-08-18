package laptop.boundary.primoucacquista;


import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
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
import laptop.controller.primoucacquista.ControllerAnnullaPagamento;
import laptop.pagamento.PagamentoCartaCredito;
import laptop.pagamento.PagamentoFattura;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuondaryAnnullaPagamento implements Initializable {

    private ControllerAnnullaPagamento cAP;


    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private TableView<PagamentoFattura> pagamentoFattura;
    @FXML
    private TableColumn<PagamentoFattura, SimpleStringProperty> nomeF = new TableColumn<>("nome");
    @FXML
    private TableColumn<PagamentoFattura, SimpleStringProperty> cognomeF = new TableColumn<>("cognome");
    @FXML
    private TableColumn<PagamentoFattura, SimpleFloatProperty> spesaF = new TableColumn<>("ammontare");
    @FXML
    private TableColumn<PagamentoFattura, Integer> idProdottoF = new TableColumn<>("idProdotto");
    @FXML
    private TableColumn<PagamentoFattura, Integer> idF = new TableColumn<>("idFattura");
    @FXML
    private TextField fatturaTF;
    @FXML
    private VBox vbox1;
    @FXML
    private RadioButton generaF;
    @FXML
    private RadioButton cancellaF;
    @FXML
    private ToggleGroup toggleGroup1;
    @FXML
    private TableView<PagamentoCartaCredito> pagamentoCC;
    @FXML
    private TableColumn<PagamentoCartaCredito, SimpleStringProperty> nomeCC = new TableColumn<>("nomeUtente");
    @FXML
    private TableColumn<PagamentoCartaCredito, SimpleStringProperty> cognomeCC = new TableColumn<>("cognomeUtente");
    @FXML
    private TableColumn<PagamentoCartaCredito, SimpleFloatProperty> ammontareCC = new TableColumn<>("spesaTotale");
    @FXML
    private TableColumn<PagamentoCartaCredito, Integer> idProdottoCC = new TableColumn<>("idProdotto");
    @FXML
    private TableColumn<PagamentoCartaCredito, Integer> idCC = new TableColumn<>("idPagCC");
    @FXML
    private TextField ccTF;
    @FXML
    private VBox vbox2;
    @FXML
    private RadioButton generaPagCC;
    @FXML
    private RadioButton cancellaCC;
    @FXML
    private ToggleGroup toggleGroup2;
    @FXML
    private HBox hbox1;
    @FXML
    private RadioButton databaseB;
    @FXML
    private RadioButton fileB;
    @FXML
    private RadioButton memoriaB;
    @FXML
    private Button buttonI;
    @FXML
    private ToggleGroup toggleGroup3;

    protected Scene scene;



    /*
    finire per versione file/csv e memoria
     */



    private static final ControllerSystemState vis=ControllerSystemState.getInstance();

    private static final String DATABASE="database";
    private static final String FILE="file";
    private static final String MEMORIA="memoria";

    @FXML
    private void generaFattura()  {
        if(databaseB.isSelected()) pagamentoFattura.setItems(cAP.getFattura(DATABASE));
        if(fileB.isSelected()) pagamentoFattura.setItems(cAP.getFattura(FILE));
        if(memoriaB.isSelected())pagamentoFattura.setItems(cAP.getFattura(MEMORIA));

    }
    @FXML
    private void cancellaFattura()  {
        try {
            String persistency = "";
            if (databaseB.isSelected()) persistency = DATABASE;
            if (fileB.isSelected()) persistency = FILE;
            if (memoriaB.isSelected()) persistency = MEMORIA;
            if (!persistency.isEmpty()) {
                if (cAP.cancellaFattura(Integer.parseInt(fatturaTF.getText()), persistency)) {

                    Stage stage;
                    Parent root;
                    stage = (Stage) cancellaF.getScene().getWindow();
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }
            } else {
                Stage stage;
                Parent root;
                stage = (Stage) cancellaF.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/annullaPagamento.fxml")));
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (IOException e)
        {
            Logger.getLogger("cancelal fattura").log(Level.SEVERE,"delete fattura not avalaible {0}",e);
        }



    }
    @FXML
    private void generaPagamento()  {
        if(databaseB.isSelected()) pagamentoCC.setItems(cAP.getPagamentoCartaCredito(DATABASE));
        if(fileB.isSelected()) pagamentoCC.setItems(cAP.getPagamentoCartaCredito(FILE));
        if(memoriaB.isSelected())pagamentoCC.setItems(cAP.getPagamentoCartaCredito(MEMORIA));
    }
    @FXML
    private void cancellaPagCC()  {
        try {
            String persistency = "";
            if (databaseB.isSelected()) persistency = DATABASE;
            if (fileB.isSelected()) persistency = FILE;
            if (memoriaB.isSelected()) persistency = MEMORIA;


            if (cAP.cancellaPagamentoCC(Integer.parseInt(ccTF.getText()), persistency)) {

                Stage stage;
                Parent root;
                stage = (Stage) cancellaCC.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } else {
                Stage stage;
                Parent root;
                stage = (Stage) cancellaCC.getScene().getWindow();
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/annullaPagamento.fxml")));
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }catch (IOException e)
        {
            Logger.getLogger("cancella cc").log(Level.SEVERE,"delete cc  not avalaible {0}",e);
        }
    }

    @FXML
    private void indietro()  {
        try {
            Stage stage;
            Parent root;
            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/download.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e)
        {
            Logger.getLogger("indietro").log(Level.SEVERE,"go back not possible {0}",e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        cAP=new ControllerAnnullaPagamento();
        if(vis.getTipologiaApplicazione().equals("demo"))
        {
            databaseB.setVisible(false);
            fileB.setVisible(false);
        }

        if (vis.getMetodoP().equals("cash"))
        {
            pagamentoCC.setEditable(false);
            vbox2.setDisable(true);
            ccTF.setEditable(false);

            nomeF.setCellValueFactory(new PropertyValueFactory<>("Nome"));
            cognomeF.setCellValueFactory(new PropertyValueFactory<>("Cognome"));
            spesaF.setCellValueFactory(new PropertyValueFactory<>("Ammontare"));
            idProdottoF.setCellValueFactory(new PropertyValueFactory<>("idProdotto"));
            idF.setCellValueFactory(new PropertyValueFactory<>("idFattura"));


        }
        else
        {
            pagamentoFattura.setEditable(false);
            vbox1.setDisable(true);
            fatturaTF.setEditable(false);

            nomeCC.setCellValueFactory(new PropertyValueFactory<>("nomeUtente"));
            cognomeCC.setCellValueFactory(new PropertyValueFactory<>("cognomeUtente"));
            ammontareCC.setCellValueFactory(new PropertyValueFactory<>("spesaTotale"));
            idProdottoCC.setCellValueFactory(new PropertyValueFactory<>("idProdotto"));
            idCC.setCellValueFactory(new PropertyValueFactory<>("idPagCC"));




        }

    }


}
