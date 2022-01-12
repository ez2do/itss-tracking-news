package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Article;

public class HistoryStorage {
	private Connection db;
	public HistoryStorage(Connection db) {
		super();
		this.db = db;
	}
	public void addtoHistory(int id) {
		PreparedStatement ps = null; 
		try {
			String statement = "INSERT INTO article_histories(article_id) VALUES (?) ON CONFLICT(article_id) DO UPDATE SET created_at=CURRENT_TIMESTAMP";
			System.out.println(statement);
			ps = db.prepareStatement(statement); 
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	
	public ArrayList<Article> getHistory(int page) {
		ArrayList<Article> historyList= new ArrayList<Article>();
		int offset = (page-1)*10;
		String statement = "SELECT articles.* from articles,article_histories WHERE id = article_id order by created_at DESC LIMIT 10 OFFSET "+offset;
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
		    	  historyList.add(a);
		      }
		} catch(SQLException e) {
		      System.out.println(e.toString());
		}
		return historyList;
	}
	
	public int countHistory() {
		String statement = "SELECT COUNT(*) FROM article_histories";
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
	public int pageCount() {
		int n = countHistory();
		if(n%10==0 && n!=0) return n/10;
		return n/10+1;
	}
	
	public void deleteHistory() {
		PreparedStatement ps = null; 
		try {
			String statement = "DELETE FROM article_histories";
			ps = db.prepareStatement(statement); 
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
	public void deleteHistorybyIds(int[] ids) {
		PreparedStatement ps = null;
		String[] s = new String[ids.length];
		for(int i=0;i<ids.length;i++) {
			s[i]=String.valueOf(ids[i]);
		}
		String str = String.join(",", s);
		try {
			String statement = "DELETE FROM article_histories WHERE article_id in ("+str+")";
			ps = db.prepareStatement(statement); 
			ps.execute();
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
	}
}
