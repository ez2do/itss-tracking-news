package views;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	public static Stage stg;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stg = primaryStage;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("base.fxml"));
//			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("news.fxml"));
			
			
//			FXMLLoader loader = new FXMLLoader(getClass().getResource("base.fxml"));
//			FXMLDocumentController fXMLDocumentController = loader.getController();
//			fXMLDocumentController.setGetHostController(getHostServices());
			Scene scene = new Scene(root,1166,768);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    public void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root, 1166, 768);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());       
        stg.setScene(scene);
        stg.show();
    }
    
    public void exitScene() throws IOException {
    	stg.close();
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}
