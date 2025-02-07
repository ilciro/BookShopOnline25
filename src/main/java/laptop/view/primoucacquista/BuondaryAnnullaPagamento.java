package laptop.view.primoucacquista;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import laptop.controller.primoucacquista.ControllerAnnullaPagamento;
import laptop.controller.ControllerSystemState;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuondaryAnnullaPagamento implements Initializable {
    private  ControllerAnnullaPagamento cannP;

    @FXML
    private Label header;
    @FXML
    private Pane pane;
    @FXML
    private TextArea tAPagamento;
    @FXML
    private TextArea tAFattura;
    @FXML
    private Button buttonI;
    @FXML
    private TextField idFattura;
    @FXML
    private TextField idPagamento;
    @FXML
    private RadioButton databaseButton;
    @FXML
    private RadioButton fileButton;
    @FXML
    private RadioButton memoriaButton;
    @FXML
    private VBox vbox;
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private ToggleGroup toggleGroupP;
    @FXML
    private ToggleGroup toggleGroupF;
    @FXML
    private RadioButton buttonGeneraFattura;
    @FXML
    private RadioButton buttonCancellaFattura;
    @FXML
    private RadioButton buttonGeneraPagamento;
    @FXML
    private RadioButton buttonCancellaPagamento;

    private final ControllerSystemState vis= ControllerSystemState.getInstance();
    private static final String CASH="cash";
    private  static final String CCREDITO="cCredito";
    protected Scene scene;

    @FXML
    private void generaFattura() throws CsvValidationException, IOException, ClassNotFoundException {
        tAFattura.setText(cannP.getFattura(returnPersistenza()));
    }

    @FXML
    private void generaPagamento() throws CsvValidationException, IOException, ClassNotFoundException {
        tAPagamento.setText(cannP.getPagamento(returnPersistenza()));
    }



    @FXML
    private void cancellaFattura() throws CsvValidationException, IOException, ClassNotFoundException {

        if(cannP.cancellaFattura(idFattura.getText(),returnPersistenza())) {
            buttonGeneraFattura.setVisible(false);
            Logger.getLogger("cancella Pagamento ok ").log(Level.INFO, "payment deleted with success!!");
            tAFattura.clear();
        }
    }
    @FXML
    private void cancellaPagamento() throws CsvValidationException, IOException, ClassNotFoundException {

        if(cannP.cancellaPagamento(idPagamento.getText(),returnPersistenza())) {
            buttonGeneraPagamento.setVisible(false);
            Logger.getLogger("cancella Pagamento ok ").log(Level.INFO, "payment deleted with success!!");
            tAPagamento.clear();
        }
    }
    @FXML
    private void indietro() throws IOException {
        boolean status = false;
        switch (vis.getMetodoP())
        {
            case CASH -> {

                if(tAPagamento.getText().isEmpty() && tAFattura.getText().isEmpty())
                    status=true;

            }
            case CCREDITO ->
            {
                if (tAPagamento.getText().isEmpty()) status=true;
            }
            default ->  Logger.getLogger("indietro ").log(Level.INFO," textareas not empty !!!");

        }
        if(status)
        {
            Stage stage;
            Parent root;
            stage = (Stage) buttonI.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("homePageFinale.fxml")));
            stage.setTitle("Benvenuto nella schermata del riepilogo ordine");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

            cannP = new ControllerAnnullaPagamento();
            if(vis.getMetodoP().equals(CCREDITO))
            {
                buttonGeneraFattura.setVisible(false);
                buttonCancellaFattura.setVisible(false);


            }


    }
    private String returnPersistenza()
    {
        String type="";
        if(databaseButton.isSelected()) type="database";
        if(fileButton.isSelected()) type="file";
        if(memoriaButton.isSelected()) type="memoria";
        return type;
    }
}
