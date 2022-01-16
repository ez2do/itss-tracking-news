package controller;

import java.io.IOException;
import models.newsTable;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import views.Main;
import models.Article;
import models.Category;
import models.newsTable;
import database.ArticleFilter;
import database.ArticleStorage;
import database.CategoryStorage;
import database.SettingStorage;


public class newsController implements Initializable {
	
	public newsController() {
		super();
		// TODO Auto-generated constructor stub
	}

	Main sw = new Main();

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
	    public Button historyButton;

	    @FXML
	    public TableColumn<newsTable, String> image;  //Image type

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
	    public TableColumn<newsTable, String> title;

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
	    public Pagination pagination;
	    
	    @FXML
	    public DatePicker searchDate1;
	    
	    @FXML
	    public DatePicker searchDate2;
	    
	    public Stage stage;
	    public Scene scene;
	    public BorderPane root;
	    
	    ArticleFilter myFilter = new ArticleFilter();
	    ArticleStorage articleStorage;
	    SettingStorage settingStorage;
	    
		public newsController(ArticleStorage articleStorage, SettingStorage settingStorage) {
			super();
			this.articleStorage = articleStorage;
			this.settingStorage = settingStorage;
		}

//    public newsController(TableColumn<Article, String> category, TableColumn<Article, String> description,
//			TableColumn<Article, String> image, TableColumn<Article, String> source,
//			TableColumn<Article, String> title, TableColumn<Article,String> articleDate, CheckBox articleCheckBox) {
//			super();
//			this.category = category;
//			this.description = description;
//			this.image = image;
//			this.source = source;
//			this.title = title;
//			this.articleDate = articleDate;
//			this.articleCheckBox = articleCheckBox;
//		}

    @FXML
    void save1(MouseEvent event) {
    	if(save1.isSelected()) {
    		settingStorage.updateDay(7);
    	}
    }

    @FXML
    void save2(MouseEvent event) {
    	if(save2.isSelected()) {
    		settingStorage.updateDay(30);
    	}
    }

    @FXML
    void searchNews(ActionEvent event) {
    	myFilter.setKeyword(searchField.getText());
    	myFilter.setCategory(categoryBox.getValue());
    	myFilter.setSource(sourceBox.getValue());
    	myFilter.setFrom(searchDate1.getValue().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")));
    	myFilter.setTo(searchDate2.getValue().format(DateTimeFormatter.ofPattern("yyyy-mm-dd")));
    	ArticleStorage.buildQuery(myFilter);
    }

    ObservableList<String> cateBoxContent = FXCollections.observableArrayList("VÄƒn hÃ³a & GiÃ¡o dá»¥c","Sá»©c khá»�e & Ä�á»�i sá»‘ng","Khoa há»�c & CÃ´ng nghá»‡","Tháº¿ giá»›i","Thá»�i sá»±","TÃ i chÃ­nh & Kinh doanh","Giáº£i trÃ­","Thá»ƒ thao");
    @FXML
    void showCategory(ActionEvent event) {
    	categoryBox.setItems(cateBoxContent);
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
    void update1(ActionEvent event) {
    	if(update1.isSelected()) {
    		settingStorage.updateHour(3);
    	}
    }

    @FXML
    void update2(MouseEvent event) {
    	if(update2.isSelected()) {
    		settingStorage.updateHour(6);
    	}
    }

    @FXML
    void update3(MouseEvent event) {
    	if(update3.isSelected()) {
    		settingStorage.updateHour(9);
    	}
    }
    
    
//    protected static Article a;
    ObservableList<Article> articles = FXCollections.observableArrayList(ArticleStorage.getArticle(myFilter));
    
    @Override
	public void initialize(URL url, ResourceBundle rb){
//    	System.out.print("hello");
    	category.setCellValueFactory(new PropertyValueFactory<newsTable,String>("category"));
    	description.setCellValueFactory(new PropertyValueFactory<newsTable,String>("source"));
    	image.setCellValueFactory(new PropertyValueFactory<newsTable,String>("link"));
    	source.setCellValueFactory(new PropertyValueFactory<newsTable,String>("link"));
    	articleDate.setCellValueFactory(new PropertyValueFactory<newsTable,String>("link"));
    	title.setCellValueFactory(new PropertyValueFactory<newsTable,String>("link"));
//    	cateCheckBox.setCellValueFactory(new PropertyValueFactory<newsTable,CheckBox>("cateCheckBox"));
    	newsTable.setItems(null);
//    	System.out.print(categories.size());
    	newsTable.setItems(articles);
    }



}

