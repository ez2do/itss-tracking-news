package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import view.Main;

public class baseController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void exit(MouseEvent event) {
		Main m = new Main();
		m.changeScene("news.fxml");
    }

    @FXML
    void toCategory(MouseEvent event) {
		Main m = new Main();
		m.changeScene("category.fxml");
    }

    @FXML
    void toNews(MouseEvent event) {
		Main m = new Main();
		m.changeScene("news.fxml");
    }

    @FXML
    void initialize() {

    }

}

