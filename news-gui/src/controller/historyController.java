package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import database.ArticleStorage;
import database.HistoryStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Article;
import views.Main;
public class historyController implements Initializable{
		
		
	
    @FXML
    private Label currentPage;
    
    @FXML
    private Label maxPage;
    
    @FXML
    private Button next;

    @FXML
    private Button previous;
    	
	 	@FXML
	    public TableColumn<Article, String> category;

	    @FXML
	    public Button deleteButton;

	    @FXML
	    public TableColumn<Article, String> description;

	    @FXML
	    public TableColumn<Article, String> hisBox;

	    @FXML
	    public TableView<Article> hisTable;

	    @FXML
	    public TableColumn<Article, ImageView> image;

	    @FXML
	    public CheckBox selectAllBox;

	    @FXML
	    public TableColumn<Article, String> source;

	    @FXML
	    public TableColumn<Article, String> title;

	    @FXML
	    public Button troveButton;
	    
	    public Stage stage;
	    public Scene scene;
	    public BorderPane root;
    
    Main sw = new Main();
    int historyPage ;
    
	ObservableList<Article> histories = FXCollections.observableArrayList(HistoryStorage.getHistory(1));
    @FXML
    void previousPage(ActionEvent event) {
    	if(historyPage >1) {
    		historyPage -= 1;
    		currentPage.setText(String.valueOf(historyPage));
    		histories = FXCollections.observableArrayList(HistoryStorage.getHistory(historyPage));
    		showHistoryTable(histories);
    	}
    }

    @FXML
    void nextPage(ActionEvent event) {
    	if(historyPage < HistoryStorage.pageCount() ) {
    		historyPage += 1;
    		currentPage.setText(String.valueOf(historyPage));
    		histories = FXCollections.observableArrayList(HistoryStorage.getHistory(historyPage));
    		showHistoryTable(histories);
    	}
    }

    @FXML
    void toNews(MouseEvent event) throws IOException{
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../views/news.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root,1166,768);
    	stage.setScene(scene);
    	stage.show();
    }
    public void showHistoryTable(ObservableList<Article> histories) {
        //hisTable view
    	for (int i = 0; i < histories.size(); i++) {
    		//ImageView
    		Article article = histories.get(i);
    		Image image = new Image(article.image);
    		ImageView imageView = new ImageView(image);
    		// set width height
    		
    		histories.get(i).image_view = imageView;
    		imageView.setFitHeight(100);
    		imageView.setFitWidth(180);
    	}
    	category.setCellValueFactory(new PropertyValueFactory<Article,String>("category"));
    	description.setCellValueFactory(new PropertyValueFactory<Article,String>("description"));
    	image.setCellValueFactory(new PropertyValueFactory<Article,ImageView>("image_view"));
    	source.setCellValueFactory(new PropertyValueFactory<Article,String>("source"));
    	title.setCellValueFactory(new PropertyValueFactory<Article,String>("title"));
    	hisTable.setItems(null);
    	hisTable.setItems(histories);
    }

    @Override
	public void initialize(URL url, ResourceBundle rb){
    	historyPage =1;
    	//page
    	currentPage.setText(String.valueOf(historyPage));
    	maxPage.setText(String.valueOf(HistoryStorage.pageCount()));
        showHistoryTable(histories);
    }
}



