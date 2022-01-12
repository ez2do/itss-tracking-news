package database;

public class ArticleFilter {
	public String keyword;
	public String from;
	public String to;
	public String category;
	public String source;
	public int page;

	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public ArticleFilter() {
		super();
		this.page=1;
		this.keyword = "";
		this.from = "";
		this.to = "";
		this.category="";
		this.source="";
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		if(!this.keyword.equals(keyword)) {
			this.page=1;
		}
		this.keyword=keyword;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		if(!this.from.equals(from)) {
			this.page=1;
		}
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		if(!this.to.equals(to)) {
			this.page=1;
		}
		
		this.to = to;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		if(!this.category.equals(category)) {
			this.page=1;
		}
		
		this.category = category;
		
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		if(!this.source.equals(source)) {
			this.page=1;
		}
		
		
		this.source = source;
	}

	
}

