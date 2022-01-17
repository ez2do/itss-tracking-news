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
    public TextField chude;

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
    ObservableList<Category> categories = FXCollections.observableArrayList(CategoryStorage.getCategory());


    @FXML
    void addSource(ActionEvent event) {
    	CategoryStorage.addtoCategory(chude.getText(),source.getText(), link.getText());
    	ObservableList<Category> categories = FXCollections.observableArrayList(CategoryStorage.getCategory());
    	showCategory(categories);
    } 

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
    
    public void showCategory(ObservableList<Category> categories) {
    	name.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("name"));
    	sourceColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("source"));
    	linkColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("link"));
    	cateTable.setItems(null);
    	cateTable.setItems(categories);
    }

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//category box view
//        ObservableList<String> cateBoxContent = FXCollections.observableArrayList(CategoryStorage.getCategoryNames());
//        chude.setItems(cateBoxContent);
		//cateTable view
        showCategory(categories);
//    	name.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("name"));
//    	sourceColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("source"));
//    	linkColumn.setCellValueFactory(new PropertyValueFactory<categoryTable,String>("link"));
////    	cateCheckBox.setCellValueFactory(new PropertyValueFactory<categoryTable,CheckBox>("cateCheckBox"));
//    	cateTable.setItems(null);
////    	System.out.print(categories.size());
//    	cateTable.setItems(categories);
	}

}