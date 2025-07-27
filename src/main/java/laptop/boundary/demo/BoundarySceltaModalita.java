package laptop.boundary.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;

import java.io.IOException;
import java.util.Objects;


public class BoundarySceltaModalita {
    @FXML
    private Pane pane;
    @FXML
    private Label header;
    @FXML
    private Button demoB;
    @FXML
    private Button fullB;
    @FXML
    private ImageView imageDemo;
    @FXML
    private ImageView imageFull;

    private static final ControllerSystemState vis=ControllerSystemState.getInstance();


    @FXML
    private void demo() throws IOException {
        vis.setTipologiaApplicazione("demo");
        homePage();
    }

    @FXML
    private void full() throws IOException {
        vis.setTipologiaApplicazione("full");
        homePage();
    }


    private void homePage() throws IOException {
        Stage stage;
        Parent root;
        Scene scene;
        if(vis.getTipologiaApplicazione().equals("demo")) stage = (Stage) demoB.getScene().getWindow();
        else stage = (Stage) fullB.getScene().getWindow();

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

}
