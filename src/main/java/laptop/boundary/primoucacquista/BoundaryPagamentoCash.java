package laptop.boundary.primoucacquista;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import laptop.controller.ControllerSystemState;
import laptop.controller.primoucacquista.ControllerPagamentoCash;


public class BoundaryPagamentoCash implements Initializable{
	@FXML
	private Pane panel;
	@FXML
	private GridPane grid;
	@FXML
	private Label header;
	@FXML
	private Label labelN;
	@FXML
	private Label labelC;
	@FXML
	private Label labelVP;
	@FXML
	private Label labelCom;
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField cognomeTF;
	@FXML
	private TextField viaTF;
	@FXML
	private TextArea eventualiArea;
	@FXML
	private Button buttonI;
	@FXML
	private RadioButton databaseButton;
	@FXML
	private RadioButton fileButton;
	@FXML
	private RadioButton memoriaButton;
	@FXML
	private ToggleGroup toggleGroup;


	private ControllerPagamentoCash cPC;

	protected String n; 
	protected String c;
	protected String v;
	protected String com;
	protected Scene scene;
	private static final ControllerSystemState vis = ControllerSystemState.getInstance();

	@FXML
	private void procediCash()  {
		
			vis.setMetodoP("cash");
			n = nomeTF.getText();
			c = cognomeTF.getText();
			v = viaTF.getText();
			com = eventualiArea.getText();
			Stage stage;
			Parent root;

			if (n.isEmpty() || c.isEmpty() || v.isEmpty()) {
				Logger.getLogger("procedi cash").log(Level.SEVERE,"\n errore nel pagamento");

				try {

					stage = (Stage) buttonI.getScene().getWindow();
					root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/pagamentoContrassegno.fxml")));
					stage.setTitle("Benvenuto nella schermata del riepilogo ordine");
					scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
				}catch (IOException e)
				{
					Logger.getLogger("procedi cash").log(Level.SEVERE,"payment fattura not avalaible {0}",e);
				}

			} else {

				String type = "";
				if(databaseButton.isSelected()) type="database";
				if(fileButton.isSelected()) type="file";
				if(memoriaButton.isSelected()) type="memoria";


				cPC.controlla(n, c, v, com,type);
				
				Logger.getLogger("pagamento cash").log(Level.INFO,"\n pagamento avvenuto");


				if(vis.getIsPickup()) 
				{
					try {
						stage = (Stage) buttonI.getScene().getWindow();
						root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/scegliNegozio.fxml")));
						stage.setTitle("Benvenuto nella schermata per scegliere il negozio");
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
					}catch (IOException e)
						{
							Logger.getLogger("procedi cash negozio").log(Level.SEVERE,"display shop not avalaible {0}",e);
						}
				}
				else
				{

				download();
				}
			}


	}



	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

					cPC = new ControllerPagamentoCash();


					

				if(vis.getIsLogged())
				{
					nomeTF.setText(cPC.getInfo()[0]);
					cognomeTF.setText(cPC.getInfo()[1]);
					nomeTF.setEditable(false);
					cognomeTF.setEditable(false);
				}
				if(vis.getTipologiaApplicazione().equals("demo") && !vis.getIsLogged())
				{
					databaseButton.setVisible(false);
					fileButton.setVisible(false);

					nomeTF.setText("prova");
					cognomeTF.setText("prova");
					viaTF.setText("viale prova 8");
					eventualiArea.setText(" dopo le 12 ");
				}
				if(vis.getTipologiaApplicazione().equals("demo"))
				{
					databaseButton.setVisible(false);
					fileButton.setVisible(false);
				}
			if (vis.getTipologiaApplicazione().equals("full")) memoriaButton.setVisible(false);

	}

	private void download()  {
		try {
			Stage stage;
			Parent root;
			stage = (Stage) buttonI.getScene().getWindow();
			root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/primoucacquista/download.fxml")));
			stage.setTitle("Benvenuto nella schermata per il download");
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch (IOException e)
		{
			Logger.getLogger("download cash").log(Level.SEVERE,"download not avalaible {0}",e);
		}
	}

}
