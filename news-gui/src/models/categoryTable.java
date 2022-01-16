package models;


import javafx.scene.control.CheckBox;

public class categoryTable extends Category{
    public CheckBox cateCheckBox;
	public categoryTable(String name, String source, String link, CheckBox cateCheckBox) {
		super(name,source,link);
		this.cateCheckBox = cateCheckBox;
	}
	public CheckBox getCateCheckBox() {
		return cateCheckBox;
	}
	public void setCateCheckBox(CheckBox cateCheckBox) {
		this.cateCheckBox = cateCheckBox;
	}
}

