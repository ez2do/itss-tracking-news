package database;
import java.sql.Connection;
import java.util.ArrayList;

import models.Article;

public class ArticleStorage {
	private Connection db;
	
	
	public ArticleStorage(Connection db) {
		super();
		this.db = db;
	}

	public ArrayList<Article> getArticle(ArticleFilter filter ) {
		String sql = "SELECT * FROM articles";
		ArrayList<Article> articlelist = new ArrayList<Article>();
		ArrayList<String> str = new ArrayList<String>();
		if(filter.keyword.length() != 0) {
			String s = "title LIKE '%"+filter.keyword+"%' OR description LIKE '%"+filter.keyword+"%'";
			str.add(s);
		}
		if(filter.category.length() != 0) {
			String s = "source = "+filter.category;
			str.add(s);
		}
		if(filter.page > 0) {
			
		}
		
		if(!str.isEmpty()) {
			sql+="WHERE";
			String.join(" and ", str);
		}
		
		
		sql += "limit 10 offset ? order by published_parse desc";
		return articlelist;
	}
	
	public int countArticle(ArticleFilter filter) {
		int pagecount=0;
		String sql = "SELECT COUNT(*) FROM articles";
		return pagecount;
	}
}
