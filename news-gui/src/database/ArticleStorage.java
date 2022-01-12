package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Article;

public class ArticleStorage {
	private Connection db;
	public ArrayList<Article> articleList;
	public ArrayList<Article> subarticleList;
	public ArrayList<Article> historyList;
	public ArrayList<Article> watchlaterList;
	
	
	public ArticleStorage(Connection db) {
		super();
		this.db = db;
	}

	public Connection getDb() {
		return db;
	}

	public void setDb(Connection db) {
		this.db = db;
	}


	public ArrayList<Article> getArticle(ArticleFilter filter ) {
		ArrayList<Article> articleList= new ArrayList<Article>();
		String statement = "SELECT * FROM articles ";
		String condition = this.buildQuery(filter);
		int offset = (filter.page-1)*10;
		statement = statement + condition + " order by published_parsed desc LIMIT 10 OFFSET "+offset;
		System.out.println(statement);
		PreparedStatement ps = null; 
	    ResultSet rs = null;
		
		
		try {
		      ps = db.prepareStatement(statement); 
		      rs = ps.executeQuery();
		      while(rs.next()) {
		    	  int id = rs.getInt("id");
		    	  String title = rs.getString("title");
		    	  String description = rs.getString("description");
		    	  String link = rs.getString("link");
		          String published_parsed = rs.getString("published_parsed");
		          String image = rs.getString("image");
		          String category = rs.getString("category");
		          String source = rs.getString("source");
		    	  Article a = new Article(id,title,description,link,published_parsed,image,category,source);
		    	  articleList.add(a);
		      }
		} catch(SQLException e) {
		      System.out.println(e.toString());
		}
		return articleList;
		
	}
	
	
	private String buildQuery(ArticleFilter filter) {
		ArrayList<String> conditions = new ArrayList<String>();
		
		if(filter.keyword.length()!=0) {
			String condition = "(title LIKE '%" + filter.keyword + "%' OR description LIKE '%" + filter.keyword + "%')";
			conditions.add(condition);
		}
		if(filter.category.length()!=0) {
			String condition = "category = '" + filter.category + "'";
			conditions.add(condition);
		}
		if(filter.source.length()!=0) {
			String condition = "source = '"+ filter.source + "'";
			conditions.add(condition);
		}
		if(filter.from.length()!=0) {
			String condition = "published_parsed >= '"+ filter.from + "'";
			conditions.add(condition);
		}
		if(filter.to.length()!=0) {
			String condition = "published_parsed <= '" + filter.to + "'";
			conditions.add(condition);
		}
		
		if (!conditions.isEmpty()) {
			return " where " + String.join(" AND ", conditions);
		}
		
		return "";
	}
	
	public int countArticle(ArticleFilter filter) {
		String statement = "SELECT COUNT(*) FROM articles";
		String condition = this.buildQuery(filter);
		
		statement = statement + condition ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count =0;
		try {
		      ps = db.prepareStatement(statement); 
		      rs = ps.executeQuery();
		      count = rs.getInt(1);
		} catch(SQLException e) {
		      System.out.println(e.toString());
		}
		return count;
	}
	
	public int pageCount(ArticleFilter filter) {
		int n = countArticle(filter);
		if(n%10==0 && n!=0) return n/10; 
		
		return n/10+1;
	}

	

	
	
}