package controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import database.HistoryStorage;
import database.WatchLaterStorage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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

public class watchLaterController implements Initializable{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Label currentPage;
    
    @FXML
    private Label maxPage;

    @FXML
    private Button next;

    @FXML
    private Button previous;

    @FXML
    private TableColumn<Article, String> category;


    @FXML
    private TableColumn<Article,String> description;

    @FXML
    private TableColumn<Article, Button> hisBox;

    @FXML
    private TableColumn<Article, ImageView> image;

    @FXML
    private TableView<Article> laterTable;

    @FXML
    private TableColumn<Article, String> source;

    @FXML
    private TableColumn<Article, Hyperlink> hyperLink;

    @FXML
    private Button troveButton;

    public Stage stage;
    public Scene scene;
    public BorderPane root;

    Main sw = new Main();

    int laterPage;
	ObservableList<Article> later = FXCollections.observableArrayList(WatchLaterStorage.getWatchLater(laterPage));
    @FXML
    void previousPage(ActionEvent event) {
    	if(laterPage >1) {
    		laterPage -= 1;
    		currentPage.setText(String.valueOf(laterPage));
    		later = FXCollections.observableArrayList(WatchLaterStorage.getWatchLater(laterPage));
    		showLaterTable(later);
    	}
    }

    @FXML
    void nextPage(ActionEvent event) {
    	if(laterPage < HistoryStorage.pageCount() ) {
    		laterPage += 1;
    		currentPage.setText(String.valueOf(laterPage));
    		later = FXCollections.observableArrayList(WatchLaterStorage.getWatchLater(laterPage));
    		showLaterTable(later);
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
    public void showLaterTable(ObservableList<Article> later) {
        //hisTable view
    	for (int i = 0; i < later.size(); i++) {
    		//ImageView
    		Article article = later.get(i);
    		Image image = new Image(article.image);
    		ImageView imageView = new ImageView(image);
    		// set width height
    		
    		later.get(i).image_view = imageView;
    		imageView.setFitHeight(100);
    		imageView.setFitWidth(180);
    		//hyperlink
    		Hyperlink myHyperLink = new Hyperlink();
    		
    		myHyperLink.setText(article.title);

			myHyperLink.setOnAction(e -> {
	    		URI linkURI = null;
				try {
					linkURI = new URI(article.link);
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
				try {
					OpenLink(linkURI);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			later.get(i).hyperLink = myHyperLink;
    	}
    	category.setCellValueFactory(new PropertyValueFactory<Article,String>("category"));
    	description.setCellValueFactory(new PropertyValueFactory<Article,String>("description"));
    	image.setCellValueFactory(new PropertyValueFactory<Article,ImageView>("image_view"));
    	source.setCellValueFactory(new PropertyValueFactory<Article,String>("source"));
    	hyperLink.setCellValueFactory(new PropertyValueFactory<Article,Hyperlink>("hyperLink"));
    	laterTable.setItems(later);
    }
    private EventHandler<ActionEvent> OpenLink(URI url) throws IOException{
    	Desktop.getDesktop().browse(url);
    	return null;
}

    @Override
	public void initialize(URL url, ResourceBundle rb){
    	laterPage =1;
    	//page
    	currentPage.setText(String.valueOf(laterPage));
    	maxPage.setText(String.valueOf(HistoryStorage.pageCount()));
        showLaterTable(later);
    }

}

