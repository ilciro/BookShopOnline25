package laptop.view;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main  extends Application {




	// insert a comment
	
	@Override
	public void start(Stage primaryStage) {
		Scene scene;

		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("view/homePageFinale.fxml")));
			scene = new Scene(root);
			primaryStage.setTitle("Benvenuto nella homePage");
			primaryStage.setScene(scene);
			primaryStage.show();
		} 
		catch (Exception e)
		{
			Logger.getLogger("main page").log(Level.SEVERE,"\n eccezione ottenuta .",e);

			
		}

	}

	public static void main(String[] args)  {


		if(Files.exists(Path.of("/sql/tablePopulate.properties"))) System.out.println("sql/tablePopulate.sql exist");
		else System.out.println("sql/tablePopulate.sql not exists");

		if(Files.exists(Path.of("src/main/resources/sql/tablePopulate.properties"))) System.out.println("src/main/resources/sql/tablePopulate.sql exist");
		else System.out.println("src/main/resources/sql/tablePopulate.sql not exists");

		//launch(args);

	}




}
