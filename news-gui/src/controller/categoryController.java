package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import views.Main;
import java.io.IOException;
import models.Category;
import models.categoryTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.CategoryStorage;

public class categoryController implements Initializable {
	
    Category newC;
    
	@FXML
    public ResourceBundle resources;

    @FXML
    public URL location;
    
    @FXML
    public Button addCategory;

    @FXML
    public ComboBox<String> chude;

    @FXML
    public Label chudeLabel;

    @FXML
    public Button trove;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    public TextField source;
    
    @FXML
    private CheckBox selectAll;
    
    @FXML
    public TextField link;
    
    @FXML
    public TableView<Category> cateTable;
    
    @FXML
    public TableColumn<categoryTable, String> linkColumn;

    @FXML
    public TableColumn<categoryTable, String> name;
    
	@FXML
    public TableColumn<categoryTable, String> sourceColumn;
    
	@FXML 
    public TableColumn <categoryTable,CheckBox> cateCheckBox;
	
    public Stage stage;
    public Scene scene;
    public BorderPane root;
	
//	ObservableList<Category> cateList = FXCollections.observableArrayList();


    public String[] chudeBoxContent = {"VÄƒn hÃ³a & GiÃ¡o dá»¥c","Sá»©c khá»�e & Ä�á»�i sá»‘ng","Khoa há»�c & CÃ´ng nghá»‡","Tháº¿ giá»›i","Thá»�i sá»±","TÃ i chÃ­nh & Kinh doanh","Giáº£i trÃ­","Thá»ƒ thao"};
    
    @FXML
    void selectCategory(ActionEvent event) throws IOException {
//        s = categoryBox.getSelectionModel().getSelectedItem().toString();
//        chudeLabel.setText(s);
    	chude.getItems().addAll(chudeBoxContent);
    }
    ObservableList<Category> categories = FXCollections.observableArrayList(CategoryStorage.getCategory(1));
    
//    @Override
//    void initialize() {
//    	for(int i = 0; i<10; i++) {
//    		CheckBox ch = new CheckBox("" + 1);
//   		cateList.add(new categoryTable("name","source","link", ch)); //connecting to database
//    	}
//    	cateTable.setItems(cateList);
//    	name.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("Name"));
//    	sourceColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("SourceColumn"));
//    	linkColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("Link"));
//    	cateCheckBox.setCellValueFactory(new PropertyValueFactory<categoryTable,CheckBox>("cateCheckBox"));
//    	cateTable.setItems(cateData);

    @FXML
    void showCategory(ActionEvent event) {
//    	System.out.print("hello");
//    	name.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("Name"));
//    	sourceColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("SourceColumn"));
//    	linkColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("Link"));
//    	cateCheckBox.setCellValueFactory(new PropertyValueFactory<categoryTable,CheckBox>("cateCheckBox"));
//    	cateTable.setItems(null);
//    	System.out.print(categories.size());
//    	cateTable.setItems(categories);

    }

    @FXML
    void addSource(ActionEvent event) {
    	newC.setName(chude.getValue());
    	newC.setSource(source.getText());
    	newC.setName(link.getText());
    	CategoryStorage.addtoCategory(newC.name,newC.source, newC.link);
    } //done

    Main sw = new Main();
    
    @FXML
    void toBase(MouseEvent event) throws IOException {
    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../views/base.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root,1166,768);
    	stage.setScene(scene);
    	stage.show();
    }
    
    //delete all selected rows
    @FXML
    void deleteSelected(ActionEvent event) {
//    	for(int i=0; i< cateTable.getItems().size(); i++) {
//    		if(cateTable.getItems().get(i).getCateCheckBox().isSelected()) {
//    			cateTable.getItems().remove(cateTable.getItems().get(i));
//    		}
//    	}
    	
    	for(Category cate :  cateTable.getItems()) {
    		cateTable.getItems().remove(cate);
    	}
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
//    	System.out.print("hello");
    	name.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("name"));
    	sourceColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("source"));
    	linkColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("link"));
//    	cateCheckBox.setCellValueFactory(new PropertyValueFactory<categoryTable,CheckBox>("cateCheckBox"));
    	cateTable.setItems(null);
//    	System.out.print(categories.size());
    	cateTable.setItems(categories);
	}

}

