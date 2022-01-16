package models;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;

public class Article {
	public int id;
	public String title;
	public String description;
	public String link;
	public String published_parsed;
	public String image;
	public String category;
	public String source;
	public ImageView image_view;
	public Hyperlink hyperLink;

	public Article(int id, String title, String description, String link, String published_parsed, String image,
			String category, String source, ImageView image_view) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.published_parsed = published_parsed;
		this.image = image;
		this.category = category;
		this.source = source;
		this.image_view = image_view;
	}

	public Article(int id, String title, String description, String link, String published_parsed, String image,
			String category, String source, ImageView image_view, Hyperlink hyperLink) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.published_parsed = published_parsed;
		this.image = image;
		this.category = category;
		this.source = source;
		this.image_view = image_view;
		this.hyperLink = hyperLink;
	}

	public Article(int id, String title, String description, String link, String published_parsed, String image,
			String category, String source) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.published_parsed = published_parsed;
		this.image = image;
		this.category = category;
		this.source = source;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPublished_parsed() {
		return published_parsed;
	}

	public void setPublished_parsed(String published_parsed) {
		this.published_parsed = published_parsed;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	public ImageView getImage_view() {
		return image_view;
	}

	public void setImage_view(ImageView image_view) {
		this.image_view = image_view;
	}
	
}
