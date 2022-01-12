package models;

public class Category {
	public String name;
	public String source;
	public String link;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	
	public Category(String name, String source, String link) {
		super();
		this.name = name;
		this.source = source;
		this.link = link;
	}
	
	
}
