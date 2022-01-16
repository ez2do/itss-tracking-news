package controller;

//import java.net.URL;
//import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import views.Main;



public class baseController {

//    @FXML
//    public ResourceBundle resources;

//    @FXML
//    public URL location;
	
	Main sw = new Main();

    @FXML
    void exit(MouseEvent event) throws IOException {
    	sw.exitScene();
    }
    
    public Stage stage;
    public Scene scene;
    public BorderPane root;
	
	@FXML
	public Button cateButton;
	@FXML
	public Button newsButton;
	@FXML
	public Button exitButton;

    @FXML
    void toCategory(ActionEvent event) throws IOException {
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../views/category.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root,1166,768);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void toNews(ActionEvent event) throws IOException {
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../views/news.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//    	scene = new Scene(root);
    	Scene scene = new Scene(root,1166,768);
    	stage.setScene(scene);
    	stage.show();
//    	sw.changeScene("news.fxml");
    }

//    @FXML
//    void initialize() {
//
//    }

}

