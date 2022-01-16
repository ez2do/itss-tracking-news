package controller;

import java.io.IOException;
//import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Article;
import views.Main;
import java.time.format.DateTimeFormatter;
public class historyController {

	 @FXML
	    public TableColumn<Article, String> category;

	    @FXML
	    public Button deleteButton;

	    @FXML
	    public TableColumn<Article, String> description;

	    @FXML
	    public DatePicker endDatepicker;

	    @FXML
	    public TableColumn<Article, String> hisBox;

	    @FXML
	    public TableView<Article> hisTable;

	    @FXML
	    public TableColumn<Article, String> image;

	    @FXML
	    public CheckBox selectAllBox;

	    @FXML
	    public Button showButton;

	    @FXML
	    public TableColumn<Article, String> source;

	    @FXML
	    public DatePicker startDatePicker;

	    @FXML
	    public TableColumn<Article, String> title;

	    @FXML
	    public Button troveButton;
	    
	    public Stage stage;
	    public Scene scene;
	    public BorderPane root;
    
    Main sw = new Main();
    
    
//    @FXML
//    void ShowByDate(ActionEvent event) {
//    	LocalDate myStartDate = startDatePicker.getValue();
//    	String formatStartDate = myStartDate.format(DateTimeFormatter.ofPattern("YYYY-MM-DD"));
//    	LocalDate myEndDate = endDatepicker.getValue();
//    	String formatEndDate = myEndDate.format(DateTimeFormatter.ofPattern("YYYY-MM-DD"));
//    	int dateCheck = formatStartDate.compareTo(formatEndDate);
//    	
//    	if (dateCheck <= 0) {
//    		
//    	}
//    	else {
//    		//tao 1 label thong bao date ko hop le
//    	}
//    }

    @FXML
    void deleteSelected(ActionEvent event) {

    }
    
    //selectAll

    @FXML
    void toNews(MouseEvent event) throws IOException{
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../views/news.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root,1166,768);
    	stage.setScene(scene);
    	stage.show();
    }

//    @FXML
//    void initialize() {
//
//    }

}

