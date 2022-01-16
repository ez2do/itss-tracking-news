package models;

import javafx.scene.control.CheckBox;

public class newsTable extends Article{
	public CheckBox newsCheckBox;
	public newsTable(int id, String title, String description, String link, String published_parsed, String image,
			String category, String source) {
		super(id, title, description, link, published_parsed, image, category, source);
		this.newsCheckBox = new CheckBox();
	}
	public CheckBox getNewsCheckBox() {
		return newsCheckBox;
	}
	public void setNewsCheckBox(CheckBox newsCheckBox) {
		this.newsCheckBox = newsCheckBox;
	}

}
