package controller;

import java.awt.Desktop;
import java.io.IOException;
import models.newsTable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.Main;
import models.Article;
import database.ArticleFilter;
import database.ArticleStorage;
import database.CategoryStorage;
import database.HistoryStorage;
import database.SettingStorage;
import database.WatchLaterStorage;


public class newsController extends Application implements Initializable  {
	
	public newsController() {
		super();
		// TODO Auto-generated constructor stub
	}

	Main sw = new Main();
	

    	@FXML
    	private Label allPage;
    
    	@FXML
    	private Button back;
    
    	@FXML
    	private Label currentPage;
    
    	@FXML
    	private Button foward;

	    @FXML
	    public ResourceBundle resources;

	    @FXML
	    public URL location;

	    @FXML
	    public Button backButton;

	    @FXML
	    public TableColumn<newsTable, String> category;

	    @FXML
	    public ComboBox<String> categoryBox;

	    @FXML
	    public TableColumn<newsTable, String> description;
	    
	    @FXML
	    public TableColumn<newsTable, CheckBox> checkArticle;
	    
	    @FXML 
	    TableColumn<newsTable, Button> xemsau;

	    @FXML
	    public Button historyButton;

	    @FXML
	    public TableColumn<newsTable, ImageView> image;  //Image type

	    @FXML
	    public TableView<Article> newsTable;

	    @FXML
	    public ToggleGroup save;

	    @FXML
	    public RadioButton save1;

	    @FXML
	    public RadioButton save2;

	    @FXML
	    public Button searchButton;

	    @FXML
	    public DatePicker searchDate;

	    @FXML
	    public TextField searchField;

	    @FXML
	    public TableColumn<newsTable, String> source;

	    @FXML
	    public ComboBox<String> sourceBox;

	    @FXML
	    public TableColumn<newsTable, Hyperlink> hyperLink;

	    @FXML
	    public ToggleGroup update;

	    @FXML
	    public RadioButton update1;

	    @FXML
	    public RadioButton update2;

	    @FXML
	    public RadioButton update3;
	    
	    @FXML
	    public CheckBox articleCheckBox;
	    
	    @FXML
	    public TableColumn<newsTable, String> articleDate;
	    
	    @FXML
	    public DatePicker searchDate1;
	    
	    @FXML
	    public DatePicker searchDate2;
	    
	    public Stage stage;
	    public Scene scene;
	    public BorderPane root;
	    
//	    ArticleFilter myFilter = new ArticleFilter();
//	    ArticleStorage articleStorage;
//	    SettingStorage settingStorage;
	    
//		public newsController(ArticleStorage articleStorage, SettingStorage settingStorage) {
//			super();
//			this.articleStorage = articleStorage;
//			this.settingStorage = settingStorage;
//		}


    
    @FXML
    void saveSettingDay(ActionEvent event) {
    	if(save1.isSelected()) {
    		SettingStorage.updateDay(7);
    	}
    	else {
    		SettingStorage.updateDay(30);
    	}
    }
	ArticleFilter mySearchFilter = new ArticleFilter();

    @FXML
    void searchNews(ActionEvent event) {
    	if(searchField.getText() != null) mySearchFilter.setKeyword(searchField.getText());
    	if(categoryBox.getValue() != null) mySearchFilter.setCategory(categoryBox.getValue());
    	if(sourceBox.getValue() != null) mySearchFilter.setSource(sourceBox.getValue());
    	if(searchDate1.getValue() != null) mySearchFilter.setFrom(searchDate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    	if(searchDate2.getValue() != null) mySearchFilter.setTo(searchDate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    	if(searchDate1.getValue() != null && searchDate2.getValue() != null && !searchDate2.getValue().isAfter(searchDate1.getValue())) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("L???i th??ng tin t??m ki???m");
    		alert.setHeaderText(null);
    		alert.setContentText("Gi?? tr??? c???a ng??y b???t ?????u ph???i tr?????c ng??y k???t th??c, h??y th??? l???i.");
    		alert.showAndWait();
    		return;
    	}
    	ObservableList<Article> articles = FXCollections.observableArrayList(ArticleStorage.getArticle(mySearchFilter));
    	if(articles.isEmpty()) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("R???ng");
    		alert.setHeaderText(null);
    		alert.setContentText("Kh??ng c?? b???t k?? k???t qu??? n??o cho th??ng tin b???n t??m.");
    		alert.showAndWait();
    		return;
    	}
    	currentPage.setText(String.valueOf(mySearchFilter.page));
    	allPage.setText(String.valueOf(ArticleStorage.pageCount(mySearchFilter)));
    	showNewsTable(articles);   	
    }
    

    @FXML
    void toBase(ActionEvent event) throws IOException {
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../views/base.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root,1166,768);
    	stage.setScene(scene);
    	stage.show();
    }

    @FXML
    void toHistory(ActionEvent event) throws IOException {
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../views/history.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root,1166,768);
    	stage.setScene(scene);
    	stage.show();
    }


    @FXML
    void updateSettingHour(ActionEvent event) {
    	if(update1.isSelected()) {
    		SettingStorage.updateHour(3);
    	}
    	else if(update2.isSelected()) {
    		SettingStorage.updateHour(6);
    	}
    	else {
    		SettingStorage.updateHour(9);
    	}
    }
    
    ObservableList<Article> articles = FXCollections.observableArrayList(ArticleStorage.getArticle(mySearchFilter));
    
    int i;
    public void showNewsTable(ObservableList<Article> articles) {
    	for ( i = 0; i < articles.size(); i++) {
    		Article article = articles.get(i);
    		//button
    		Button button = new Button();
    		button.setText("Xem sau");
    		button.setOnAction(e -> {
    			WatchLaterStorage.addtoWatchLater(article.id);
    			System.out.print(article.id);
    		});
    		articles.get(i).button = button;
        	//image
    		Image image = new Image(article.image);
    		ImageView imageView = new ImageView(image);
    		// set width height
    		
    		articles.get(i).image_view = imageView;
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
				
				HistoryStorage.addtoHistory(article.id); //add read to history
			});
			articles.get(i).hyperLink = myHyperLink;
    	}
    	category.setCellValueFactory(new PropertyValueFactory<newsTable,String>("category"));
    	description.setCellValueFactory(new PropertyValueFactory<newsTable,String>("description"));
    	image.setCellValueFactory(new PropertyValueFactory<newsTable,ImageView>("image_view"));
    	source.setCellValueFactory(new PropertyValueFactory<newsTable,String>("source"));
    	articleDate.setCellValueFactory(new PropertyValueFactory<newsTable,String>("published_parsed"));
    	hyperLink.setCellValueFactory(new PropertyValueFactory<newsTable,Hyperlink>("hyperLink"));
    	xemsau.setCellValueFactory(new PropertyValueFactory<newsTable,Button>("button"));
    	//wrap text
    	description.setCellFactory(tc -> {
    	    TableCell<newsTable, String> cell = new TableCell<>();
    	    Text text = new Text();
    	    cell.setGraphic(text);
    	    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
    	    text.wrappingWidthProperty().bind(description.widthProperty());
    	    text.textProperty().bind(cell.itemProperty());
    	    return cell ;
    	});
//    	hyperLink.setCellFactory(tc -> {
//    	    TableCell<newsTable, Hyperlink> cell = new TableCell<>();
//    	    Text text = new Text();
//    	    cell.setGraphic(text);
//    	    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
//    	    text.wrappingWidthProperty().bind(hyperLink.widthProperty());
//    	    text.textProperty().bind(cell.itemProperty());
//    	    return cell ;
//    	});
    	newsTable.setItems(articles);
    }
    
    @FXML
    void goBack(ActionEvent event) {
    	if(mySearchFilter.page > 1) {
    		mySearchFilter.page -= 1;
    		currentPage.setText(String.valueOf(mySearchFilter.page));
    		articles = FXCollections.observableArrayList(ArticleStorage.getArticle(mySearchFilter));
    		showNewsTable(articles);
    	}
    }

    @FXML
    void goFront(ActionEvent event) {
    	if(mySearchFilter.page < ArticleStorage.pageCount(mySearchFilter)) {
    		mySearchFilter.page += 1;
    		currentPage.setText(String.valueOf(mySearchFilter.page));
    		articles = FXCollections.observableArrayList(ArticleStorage.getArticle(mySearchFilter));
    		showNewsTable(articles);
    	}
    }
    
    @Override
	public void initialize(URL url, ResourceBundle rb){
    	currentPage.setText(String.valueOf(mySearchFilter.page));
    	allPage.setText(String.valueOf(ArticleStorage.pageCount(mySearchFilter)));
    	//set default for RadioButoon update and save
    	int hour = SettingStorage.getSettingValueByKey(SettingStorage.updateIntervalHourKey);
    	if(hour ==3) update1.setSelected(true);
    	else if(hour ==6) update2.setSelected(true);
    	else update3.setSelected(true);
    	
    	int day = SettingStorage.getSettingValueByKey(SettingStorage.deleteIntervalDayKey);
    	if(day == 7) save1.setSelected(true);
    	else save2.setSelected(true);
    	//set default for filter
    	categoryBox.setValue(null);
    	sourceBox.setValue(null);
    	searchDate1.setValue(null);
    	searchDate2.setValue(null);

    	//categoryBox content
        ObservableList<String> cateBoxContent = FXCollections.observableArrayList(CategoryStorage.getCategoryNames());
        categoryBox.setItems(cateBoxContent);
    	
        ObservableList<String> sourceBoxContent = FXCollections.observableArrayList(CategoryStorage.getCategorySources());
        sourceBox.setItems(sourceBoxContent);
        
        //newsTable view
        showNewsTable(this.articles);
    }

    private EventHandler<ActionEvent> OpenLink(URI url) throws IOException{
    	if( Desktop.isDesktopSupported() )
    	{
    	    new Thread(() -> {
    	           try {
    	               Desktop.getDesktop().browse(url);
    	           } catch (IOException e) {
    	               e.printStackTrace();
    	           }
    	       }).start();
    	}
    	return null;
}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
}

