package database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Hyperlink;
import models.Article;

public class ArticleStorage {
	private static Connection db;
	static {
		db = DbConnection.connect();
	}

	public static ArrayList<Article> getArticle(ArticleFilter filter ) {
		ArrayList<Article> articleList= new ArrayList<Article>();
		String statement = "SELECT * FROM articles ";
		String condition = buildQuery(filter);
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
//		          System.out.println("id: " + id);
//		          System.out.println("title: " + title);
//		          System.out.println("description: " + description);
//		          System.out.println("link: " + link);
//		          System.out.println("published_parsed: " + published_parsed);
//		          System.out.println("image: " + image);
//		          System.out.println("category: " + category);
//		          System.out.println("source: " + source);
		    	  Article a = new Article(id,title,description,link,published_parsed,image,category,source);
		    	  articleList.add(a);
		      }
		} catch(SQLException e) {
		      System.out.println(e.toString());
		}
		return articleList;
	
	}
	
	
	public static String buildQuery(ArticleFilter filter) {
		ArrayList<String> conditions = new ArrayList<String>();
		
		if(filter.keyword!= null && filter.keyword.length()!=0) {
			String condition = "(title LIKE '%" + filter.keyword + "%' OR description LIKE '%" + filter.keyword + "%')";
			conditions.add(condition);
		}
		if(filter.category != null && filter.category.length()!=0 && !filter.category.equals("Ch??? ?????: T???t c???")) {
			String condition = "category = '" + filter.category + "'";
			conditions.add(condition);
		}
		if(filter.source != null && filter.source.length()!=0 && !filter.source.equals("Ngu???n: T???t c???")) {
			String condition = "source = '"+ filter.source + "'";
			conditions.add(condition);
		}
		if(filter.from != null && filter.from.length()!=0) {
			String condition = "published_parsed >= '"+ filter.from + "'";
			conditions.add(condition);
		}
		if(filter.to != null && filter.to.length()!=0) {
			String condition = "published_parsed <= '" + filter.to + " 23:59:59+07:00'";
			conditions.add(condition);
		}
		
		if (!conditions.isEmpty()) {
			return " where " + String.join(" AND ", conditions);
		}
		
		return "";
	}
	
	public static int countArticle(ArticleFilter filter) {
		String statement = "SELECT COUNT(*) FROM articles";
		String condition = buildQuery(filter);
		
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
	
	public static int pageCount(ArticleFilter filter) {
		int n = countArticle(filter);
		if(n%10==0 && n!=0) return n/10; 
		
		return n/10+1;
	}

	

	
	
}