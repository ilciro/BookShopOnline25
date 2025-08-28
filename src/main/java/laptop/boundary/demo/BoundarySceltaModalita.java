package laptop.boundary.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BoundarySceltaModalita implements Initializable {
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
    private void demo()  {
        vis.setTipologiaApplicazione("demo");
        homePage();
    }

    @FXML
    private void full()  {
        vis.setTipologiaApplicazione("full");
        homePage();
    }


    private void homePage() {
        try {
            Stage stage;
            Parent root;
            Scene scene;
            if (vis.getTipologiaApplicazione().equals("demo")) stage = (Stage) demoB.getScene().getWindow();
            else stage = (Stage) fullB.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/homePageFinale.fxml")));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e)
        {
            Logger.getLogger("home page").log(Level.SEVERE,"exception :{0}",e);

        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File path=new File("memory");
        File[] files = path.listFiles();
        for(int i = 0; i< Objects.requireNonNull(files).length; i++) {
            Logger.getLogger("inizializza modalita file").log(Level.INFO,"file name :{0} -> esito {1}",new Object[]{files[i].getName(),files[i].delete()});
        }

    }
}
