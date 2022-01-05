package views;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private static Stage stg;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stg = primaryStage;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../views/base.fxml"));
			Scene scene = new Scene(root,600,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    public void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene sence = new Scene(root, 870, 780);
        stg.setScene(sence);
        stg.show();
    }
	public static void main(String[] args) {
		launch(args);
	}
}
